package services;

import chess.ChessGame;
import chessImplementation.ChessGameImpl;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import models.AuthToken;
import models.Game;
import webSocketMessages.userCommands.JoinObserverCommand;
import webSocketMessages.userCommands.JoinPlayerCommand;

public class WSService {
    private static AuthDAO authDAO = AuthDAO.getInstance();
    private static GameDAO gameDAO = GameDAO.getInstance();

    public static Game handleJoinPlayerCommand(JoinPlayerCommand joinPlayerCommand) {
        Game game;
        AuthToken authToken;
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

        if (playerColor == ChessGame.TeamColor.WHITE && game.whiteUsername() == null) {
            return null;
        } else if(playerColor == ChessGame.TeamColor.BLACK && game.blackUsername() == null) {
            return null;
        }

        // return null if the teams are empty or if the team names don't match with the username
        if (playerColor == ChessGame.TeamColor.WHITE && !game.whiteUsername().equals(authToken.username())) {
            return null;
        } else if (playerColor == ChessGame.TeamColor.BLACK && !game.blackUsername().equals(authToken.username())) {
            return null;
        }

        return game;
    }

    public static Game handleJoinObserverCommand(JoinObserverCommand joinObserverCommand) {
        Game game;

        Integer gameID = joinObserverCommand.getGameID();
        Game storedGame = new Game(gameID, null, null, null, new ChessGameImpl());
        AuthToken storedAuthToken = new AuthToken(null, joinObserverCommand.getAuthString());

        // return null for Server class to handle errors if game not found in database
        try {
            game = gameDAO.get(storedGame);
        } catch (DataAccessException dataAccessException) {
            return null;
        }

        // return null if auth token not found in database so then error handling can be done in Server class
        try {
            authDAO.get(storedAuthToken);
        } catch (DataAccessException dataAccessException) {
            return null;
        }

        return game;
    }
}
