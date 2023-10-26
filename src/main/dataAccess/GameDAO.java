package dataAccess;

import models.Game;
import models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Object representation of the data access object used to store and fetch data relating to chess games in the database
 */
public class GameDAO implements DAO<Game> {

    private static GameDAO instance = null;
    private Map<String, Game> games = new HashMap<>();

    public static GameDAO getInstance() {
        if (instance == null) {
            instance = new GameDAO();
        }
        return instance;
    }

    /**
     * A method that queries the database for a game and returns it if it is found in the database
     * @param game The game that will be queried for in the database
     * @return The game if it is found
     * @throws DataAccessException An exception if an error occurs in accessing the data or if game is not found
     */
    @Override
    public Game get(Game game) throws DataAccessException {
        try {
            return games.get(game.gameName());
        } catch (Exception exception) {
            throw new DataAccessException("Error: failed to get a game from the database");
        }
    }

    /**
     * A method that will return all the games stored in the database as a list
     * @return A list of all the games stored in the database
     * @throws DataAccessException An exception if an error occurs in accessing the data
     */
    @Override
    public List<Game> getAll() throws DataAccessException {
        List<Game> gamesList = new ArrayList<>();

        try {
            for (Game game : games.values()) {
                gamesList.add(game);
            }
            return gamesList;
        } catch (Exception exception) {
            throw new DataAccessException("Error: failed to get all users");
        }
    }

    /**
     * A method that create a new game and adds that new game to the database
     * @param game The game that is inserted into the database
     * @throws DataAccessException An exception if an error occurs in accessing the data
     */
    @Override
    public void post(Game game) throws DataAccessException {
        try {
            games.put(game.gameName(), game);
        } catch (Exception exception) {
            throw new DataAccessException("Error: failed to add new game to database");
        }
    }

    /**
     * A method that updates the state of an existing game stored in the database
     * @param game The game in the database that will be updated
     * @throws DataAccessException An exception if an error occurs in accessing the data
     */
    @Override
    public void put(Game game) throws DataAccessException {
        try {
            games.remove(game.gameName());
            games.put(game.gameName(), game);
        } catch (Exception exception) {
            throw new DataAccessException("Error: failed to update user");
        }
    }

    /**
     * A method that delete an existing game stored in the database
     * @param game The game in the database that will be deleted
     * @throws DataAccessException An exception if an error occurs in accessing the data
     */
    @Override
    public void delete(Game game) throws DataAccessException {
        try {
            games.remove(game.gameName());
        } catch (Exception exception) {
            throw new DataAccessException("Error: failed to delete game from database");
        }
    }

    public void deleteAll() throws DataAccessException {
        try {
            games.clear();
        } catch (Exception exception) {
            throw new DataAccessException("Error: failed to clear games from database");
        }
    }
}
