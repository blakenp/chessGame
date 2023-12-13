package services;

import chess.ChessGame;
import chess.ChessPiece;
import chess.InvalidMoveException;
import chessImplementation.ChessGameImpl;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import models.AuthToken;
import models.Game;
import webSocketMessages.userCommands.JoinObserverCommand;
import webSocketMessages.userCommands.JoinPlayerCommand;
import webSocketMessages.userCommands.MakeMoveCommand;

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

        // return null for Server class to handle errors if game not found in database
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

    public static Game handleMakeMoveCommand(MakeMoveCommand makeMoveCommand) {
        Game game;
        AuthToken authToken;

        Integer gameID = makeMoveCommand.getGameID();
        Game storedGame = new Game(gameID, null, null, null, new ChessGameImpl());
        AuthToken storedAuthToken = new AuthToken(null, makeMoveCommand.getAuthString());
        ChessGame.TeamColor playerColor;

        // return null for Server class to handle errors if game not found in database
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

        if (authToken.username().equals(game.whiteUsername())) {
            playerColor = ChessGame.TeamColor.WHITE;
        } else if (authToken.username().equals(game.blackUsername())) {
            playerColor = ChessGame.TeamColor.BLACK;
        } else {
            return null;
        }

        // if you try to move one of the opponent's pieces, return null and handle error in Server class
        if (game.chessGame().getBoard().getPiece(makeMoveCommand.getChessMove().getStartPosition()).getTeamColor() != playerColor) {
            return null;
        }

        // try to make a move, but if it's an invalid move, just return null to let Server class handle the error
        try {
            game.chessGame().makeMove(makeMoveCommand.getChessMove());
        } catch (InvalidMoveException invalidMoveException) {
            return null;
        }

        // update the game in the database
        try {
            gameDAO.put(game);
        } catch (DataAccessException dataAccessException) {
            return null;
        }

        return game;
    }
}
