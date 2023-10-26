package dataAccess;

import models.Game;
import models.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Object representation of the data access object used to store and fetch data relating to chess games in the database
 */
public class GameDAO implements DAO<Game> {

    private static GameDAO instance = null;

    private Map<String, User> games = new HashMap<>();

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
        return null;
    }

    /**
     * A method that will return all the games stored in the database as a list
     * @return A list of all the games stored in the database
     * @throws DataAccessException An exception if an error occurs in accessing the data
     */
    @Override
    public List<Game> getAll() throws DataAccessException {
        return null;
    }

    /**
     * A method that create a new game and adds that new game to the database
     * @param game The game that is inserted into the database
     * @throws DataAccessException An exception if an error occurs in accessing the data
     */
    @Override
    public void post(Game game) throws DataAccessException {

    }

    /**
     * A method that updates the state of an existing game stored in the database
     * @param game The game in the database that will be updated
     * @throws DataAccessException An exception if an error occurs in accessing the data
     */
    @Override
    public void put(Game game) throws DataAccessException {

    }

    /**
     * A method that delete an existing game stored in the database
     * @param game The game in the database that will be deleted
     * @throws DataAccessException An exception if an error occurs in accessing the data
     */
    @Override
    public void delete(Game game) throws DataAccessException {

    }

    public void deleteAll() throws DataAccessException {
        try {
            games.clear();
        } catch (Exception exception) {
            throw new DataAccessException("Error: failed to clear games from database");
        }
    }
}
