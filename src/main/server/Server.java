package server;

import chess.ChessGame;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import models.Game;
import services.WSService;
import spark.Spark;
import handlers.*;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.*;
import webSocketMessages.serverMessages.ErrorMessage;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Object representation of the chess game server that handles client requests and helps
 * store game data and logic
 */
@WebSocket
public class Server {
    private HashMap<String, Connection> connectionMap = new HashMap<>();
    private HashMap<Integer, Set<Connection>> connectionsToGames = new HashMap<>();

    public static void main(String[] args) {
        new Server().run();
    }

    private void run() {
        // Specify the port you want the server to listen on
        Spark.port(8080);

        // Register a directory for hosting static files
        Spark.externalStaticFileLocation("web");

        createRoutes();
    }

    private static void createRoutes() {
        Spark.webSocket("/connect", Server.class);

        Spark.delete("/db", (request, response) ->
                TestingHandler.handleClear()
        );

        Spark.post("/user", (request, response) ->
                UserHandler.handleRegisterUser(request, response)
        );

        Spark.post("/session", (request, response) ->
                AuthHandler.handleLogin(request, response)
        );

        Spark.delete("/session", (request, response) ->
                AuthHandler.handleLogout(request, response)
        );

        Spark.post("/game", (request, response) ->
                GameHandler.handleCreateGame(request, response)
        );

        Spark.put("/game", (request, response) ->
                GameHandler.handleJoinGame(request, response)
        );

        Spark.get("/game", (request, response) ->
                GameHandler.handleListGames(request, response)
        );
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        Gson gson = new Gson();
        String response = "";

        JsonObject jsonObject = gson.fromJson(message, JsonObject.class);
        UserGameCommand.CommandType commandType = UserGameCommand.CommandType.valueOf(jsonObject.get("commandType").getAsString());
        System.out.println(commandType);

        if (commandType == UserGameCommand.CommandType.JOIN_PLAYER) {
            JoinPlayerCommand joinPlayerCommand = gson.fromJson(message, JoinPlayerCommand.class);
            Game game = WSService.handleJoinPlayerCommand(joinPlayerCommand);

            if (game != null) {
                ChessGame.TeamColor teamColor = joinPlayerCommand.getPlayerColor();
                Connection connection = new Connection(joinPlayerCommand.getAuthString(), session);
                connectionMap.put(joinPlayerCommand.getAuthString(), connection);

                Set<Connection> gameConnections = connectionsToGames.get(game.gameID());
                if (gameConnections == null) {
                    gameConnections = new HashSet<>();
                    connectionsToGames.put(game.gameID(), gameConnections);
                }
                gameConnections.add(connection);

                String username = "";
                if (teamColor == ChessGame.TeamColor.WHITE) {
                    username = game.whiteUsername();
                } else if (teamColor == ChessGame.TeamColor.BLACK) {
                    username = game.blackUsername();
                }

                LoadGameMessage loadGameMessage = new LoadGameMessage(game, ServerMessage.ServerMessageType.LOAD_GAME);
                response = gson.toJson(loadGameMessage);

                NotificationMessage notificationMessage = new NotificationMessage(username + " has joined the battle", ServerMessage.ServerMessageType.NOTIFICATION);
                for (Connection loopConnection : connectionsToGames.get(game.gameID())) {
                    if (!loopConnection.authTokenString().equals(joinPlayerCommand.getAuthString())) {
                        System.out.println("going to send notification");
                        loopConnection.session().getRemote().sendString(gson.toJson(notificationMessage));
                    }
                }
            } else {
                ErrorMessage errorMessage = new ErrorMessage("Error: invalid gameID, auth token, or other error has occurred", ServerMessage.ServerMessageType.ERROR);
                response = gson.toJson(errorMessage);
            }
        } else if (commandType == UserGameCommand.CommandType.JOIN_OBSERVER) {
            JoinObserverCommand joinObserverCommand = gson.fromJson(message, JoinObserverCommand.class);

            Game game = WSService.handleJoinObserverCommand(joinObserverCommand);

            if (game != null) {
                Connection connection = new Connection(joinObserverCommand.getAuthString(), session);
                connectionMap.put(joinObserverCommand.getAuthString(), connection);

                Set<Connection> gameConnections = connectionsToGames.get(game.gameID());
                if (gameConnections == null) {
                    gameConnections = new HashSet<>();
                    connectionsToGames.put(game.gameID(), gameConnections);
                }
                gameConnections.add(connection);

                String username = joinObserverCommand.getUsername();

                LoadGameMessage loadGameMessage = new LoadGameMessage(game, ServerMessage.ServerMessageType.LOAD_GAME);
                response = gson.toJson(loadGameMessage);

                NotificationMessage notificationMessage = new NotificationMessage(username + " is observing your every moves bros. Tread carefully...", ServerMessage.ServerMessageType.NOTIFICATION);
                for (Connection loopConnection : connectionsToGames.get(game.gameID())) {
                    if (!loopConnection.authTokenString().equals(joinObserverCommand.getAuthString())) {
                        loopConnection.session().getRemote().sendString(gson.toJson(notificationMessage));
                    }
                }
            } else {
                ErrorMessage errorMessage = new ErrorMessage("Error: invalid gameID, auth token, or other error has occurred", ServerMessage.ServerMessageType.ERROR);
                response = gson.toJson(errorMessage);
            }
        }

        session.getRemote().sendString(response);
    }
}
