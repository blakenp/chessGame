package services;

import chessImplementation.ChessGameImpl;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import models.Game;
import webSocketMessages.userCommands.JoinPlayerCommand;
import webSocketMessages.userCommands.UserGameCommand;

public class WSService {
    private UserDAO userDAO = UserDAO.getInstance();
    private AuthDAO authDAO = AuthDAO.getInstance();
    private static GameDAO gameDAO = GameDAO.getInstance();

    public static Game handleJoinPlayerCommand(JoinPlayerCommand joinPlayerCommand) throws DataAccessException {
        Gson gson = new Gson();
        Game game = null;

        Integer gameID = joinPlayerCommand.getGameID();
        Game storedGame = new Game(gameID, null, null, null, new ChessGameImpl());

        try {
            game = gameDAO.get(storedGame);
        } catch (DataAccessException dataAccessException) {
            throw new DataAccessException("Error: failed to get game from database");
        }
        
        return game;
    }
}
