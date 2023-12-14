package server;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import com.google.gson.*;
import models.Game;
import services.WSService;
import spark.Spark;
import handlers.*;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.*;
import typeAdapters.ChessMoveAdapter;
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
        var builder = new GsonBuilder();
        builder.registerTypeAdapter(ChessMove.class, new ChessMoveAdapter());
        Gson gson = builder.create();
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
        } else if (commandType == UserGameCommand.CommandType.MAKE_MOVE) {
            System.out.println("Make Move Command JSON: " + message);

            ChessMove chessMove = gson.fromJson(message, ChessMove.class);

            JsonObject moveCommandObject = gson.fromJson(message, JsonObject.class);
            System.out.println("move command object: " + moveCommandObject);

            JsonElement moveElement = moveCommandObject.get("move");

            // logic to make sure any primitive json objects are handled and converted to pure json objects
            if (moveElement != null && moveElement.isJsonObject()) {
                JsonObject moveObject = moveElement.getAsJsonObject();

                if (moveObject.has("promotionPiece")) {
                    moveObject.remove("promotionPiece");
                }
            } else if (moveElement != null && moveElement.isJsonPrimitive()) {
                JsonObject moveObject = new JsonObject();

                moveObject.addProperty("promotionPiece", moveElement.getAsString());
                moveCommandObject.add("move", moveObject);
            }

            moveCommandObject.remove("move");
            MakeMoveCommand makeMoveCommand = gson.fromJson(moveCommandObject, MakeMoveCommand.class);

            makeMoveCommand.setChessMove(chessMove);

            Game game = WSService.handleMakeMoveCommand(makeMoveCommand);

            if (game != null) {
                Connection connection = new Connection(makeMoveCommand.getAuthString(), session);
                connectionMap.put(makeMoveCommand.getAuthString(), connection);

                Set<Connection> gameConnections = connectionsToGames.get(game.gameID());
                if (gameConnections == null) {
                    gameConnections = new HashSet<>();
                    connectionsToGames.put(game.gameID(), gameConnections);
                }
                gameConnections.add(connection);

                String username = makeMoveCommand.getUsername();
                String opponentUsername = "";
                ChessGame.TeamColor opponentColor = null;

                if (username != null && username.equals(game.whiteUsername())) {
                    opponentColor = ChessGame.TeamColor.BLACK;
                    opponentUsername = game.blackUsername();
                } else if (username != null && username.equals(game.blackUsername())) {
                    opponentColor = ChessGame.TeamColor.WHITE;
                    opponentUsername = game.whiteUsername();
                }

                LoadGameMessage loadGameMessage = new LoadGameMessage(game, ServerMessage.ServerMessageType.LOAD_GAME);
                response = gson.toJson(loadGameMessage);

                ChessPosition startPosition = makeMoveCommand.getChessMove().getStartPosition();
                ChessPosition endPosition = makeMoveCommand.getChessMove().getEndPosition();

                StringBuilder moveString = new StringBuilder();
                moveString.append((char)('a' + (startPosition.getColumn() - 1)));
                moveString.append(startPosition.getRow());
                moveString.append((char)('a' + (endPosition.getColumn() - 1)));
                moveString.append(endPosition.getRow());

                NotificationMessage notificationMessage = new NotificationMessage(username + " made the move " + moveString, ServerMessage.ServerMessageType.NOTIFICATION);
                for (Connection loopConnection : connectionsToGames.get(game.gameID())) {
                    if (!loopConnection.authTokenString().equals(makeMoveCommand.getAuthString())) {
                        loopConnection.session().getRemote().sendString(gson.toJson(notificationMessage));
                        loopConnection.session().getRemote().sendString(gson.toJson(loadGameMessage));
                    }
                }
                if (opponentColor != null && game.chessGame().isInCheckmate(opponentColor)) {
                    notificationMessage = new NotificationMessage(opponentUsername + " is in checkmate! GGs", ServerMessage.ServerMessageType.NOTIFICATION);
                    for (Connection loopConnection : connectionsToGames.get(game.gameID())) {
                        loopConnection.session().getRemote().sendString(gson.toJson(notificationMessage));
                    }
                } else if (opponentColor != null && game.chessGame().isInCheck(opponentColor)) {
                    notificationMessage = new NotificationMessage(opponentUsername + " is in check! :O", ServerMessage.ServerMessageType.NOTIFICATION);
                    for (Connection loopConnection : connectionsToGames.get(game.gameID())) {
                        if (!loopConnection.authTokenString().equals(makeMoveCommand.getAuthString())) {
                            loopConnection.session().getRemote().sendString(gson.toJson(loadGameMessage));
                        }
                        loopConnection.session().getRemote().sendString(gson.toJson(notificationMessage));
                    }
                }
            } else {
                ErrorMessage errorMessage = new ErrorMessage("Error: invalid gameID, auth token, wrong turn, or you can't make moves as an observer", ServerMessage.ServerMessageType.ERROR);
                response = gson.toJson(errorMessage);
            }
        } else if (commandType == UserGameCommand.CommandType.RESIGN) {
            ResignCommand resignCommand = gson.fromJson(message, ResignCommand.class);

            Game game = WSService.handleResignCommand(resignCommand);

            if (game != null) {
                Connection connection = new Connection(resignCommand.getAuthString(), session);
                connectionMap.put(resignCommand.getAuthString(), connection);

                Set<Connection> gameConnections = connectionsToGames.get(game.gameID());
                if (gameConnections == null) {
                    gameConnections = new HashSet<>();
                    connectionsToGames.put(game.gameID(), gameConnections);
                }
                gameConnections.add(connection);

                String username = resignCommand.getUsername();

                NotificationMessage notificationMessage = new NotificationMessage(username + " has resigned and will be missed...", ServerMessage.ServerMessageType.NOTIFICATION);
                for (Connection loopConnection : connectionsToGames.get(game.gameID())) {
                    loopConnection.session().getRemote().sendString(gson.toJson(notificationMessage));
                }
            } else {
                ErrorMessage errorMessage = new ErrorMessage("Error: invalid gameID, auth token, or other error has occurred", ServerMessage.ServerMessageType.ERROR);
                session.getRemote().sendString(gson.toJson(errorMessage));
            }
        } else if (commandType == UserGameCommand.CommandType.LEAVE) {
            LeaveCommand leaveCommand = gson.fromJson(message, LeaveCommand.class);

            Game game = WSService.handleLeaveCommand(leaveCommand);

            if (game != null) {
                Connection connection = new Connection(leaveCommand.getAuthString(), session);
                connectionMap.put(leaveCommand.getAuthString(), connection);

                Set<Connection> gameConnections = connectionsToGames.get(game.gameID());
                if (gameConnections == null) {
                    gameConnections = new HashSet<>();
                    connectionsToGames.put(game.gameID(), gameConnections);
                }
                gameConnections.remove(connection);

                String username = leaveCommand.getUsername();

                NotificationMessage notificationMessage = new NotificationMessage(username + " has left the game. NOOOOOOOO!!!!!", ServerMessage.ServerMessageType.NOTIFICATION);
                for (Connection loopConnection : connectionsToGames.get(game.gameID())) {
                    if (!loopConnection.authTokenString().equals(leaveCommand.getAuthString())) {
                        loopConnection.session().getRemote().sendString(gson.toJson(notificationMessage));
                    }
                }
            } else {
                ErrorMessage errorMessage = new ErrorMessage("Error: invalid gameID, auth token, or other error has occurred", ServerMessage.ServerMessageType.ERROR);
                session.getRemote().sendString(gson.toJson(errorMessage));
            }
        }

        // only send the other response type if the command type received from the client was not a resignation or leave command
        if (commandType != UserGameCommand.CommandType.RESIGN && commandType != UserGameCommand.CommandType.LEAVE) {
            session.getRemote().sendString(response);
        }
    }
}
