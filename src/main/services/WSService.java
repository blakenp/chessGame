package services;

import chess.ChessGame;
import chessImplementation.ChessGameImpl;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import models.AuthToken;
import models.Game;
import webSocketMessages.userCommands.JoinPlayerCommand;
import webSocketMessages.userCommands.UserGameCommand;

public class WSService {
    private UserDAO userDAO = UserDAO.getInstance();
    private static AuthDAO authDAO = AuthDAO.getInstance();
    private static GameDAO gameDAO = GameDAO.getInstance();

    public static Game handleJoinPlayerCommand(JoinPlayerCommand joinPlayerCommand) {
        Game game = null;
        AuthToken authToken = null;
        ChessGame.TeamColor playerColor = joinPlayerCommand.getPlayerColor();

        Integer gameID = joinPlayerCommand.getGameID();
        Game storedGame = new Game(gameID, null, null, null, new ChessGameImpl());
        AuthToken storedAuthToken = new AuthToken(null, joinPlayerCommand.getAuthString());

        try {
            game = gameDAO.get(storedGame);
        } catch (DataAccessException dataAccessException) {
            return null;
        }

        // return null if auth token not found in database so then error handling can be done in Server class
        try {
             authToken = authDAO.get(storedAuthToken);
        } catch (DataAccessException dataAccessException) {
            return null;
        }

        if (playerColor == ChessGame.TeamColor.WHITE && !game.whiteUsername().equals(authToken.username())) {
            return null;
        } else if (playerColor == ChessGame.TeamColor.BLACK && !game.blackUsername().equals(authToken.username())) {
            return null;
        }

        return game;
    }
}
