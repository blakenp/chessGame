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
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.*;

/**
 * Object representation of the chess game server that handles client requests and helps
 * store game data and logic
 */
@WebSocket
public class Server {

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
            System.out.println("we be in join player command bro");

            Game game = WSService.handleJoinPlayerCommand(joinPlayerCommand);

            ChessGame.TeamColor teamColor = joinPlayerCommand.getPlayerColor();
            if (teamColor == ChessGame.TeamColor.WHITE && game.whiteUsername().isEmpty()) {
                ErrorMessage errorMessage = new ErrorMessage("Error: game wasn't correctly updated to show you as team white player", ServerMessage.ServerMessageType.ERROR);
                response = gson.toJson(errorMessage);
            } else if (teamColor == ChessGame.TeamColor.BLACK && game.blackUsername().isEmpty()) {
                ErrorMessage errorMessage = new ErrorMessage("Error: game wasn't correctly updated to show you as team black player", ServerMessage.ServerMessageType.ERROR);
                response = gson.toJson(errorMessage);
            } else {
                LoadGameMessage loadGameMessage = new LoadGameMessage(game, ServerMessage.ServerMessageType.LOAD_GAME);
                response = gson.toJson(loadGameMessage);
            }
        }

        session.getRemote().sendString(response);
    }
}
