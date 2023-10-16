package dataAccess;

import models.Game;

import java.util.List;

/**
 * Object representation of the data access object used to store and fetch data relating to chess games in the database
 */
public class GameDAO implements DAO<Game> {

    /**
     * A method that gets a chess game stored in the database
     * @return A chess game
     * @throws DataAccessException An exception if an error occurs in accessing the data
     */
    @Override
    public Game get(Game game) throws DataAccessException {
        return null;
    }

    /**
     *
     * @return
     * @throws DataAccessException
     */
    @Override
    public List<Game> getAll() throws DataAccessException {
        return null;
    }

    @Override
    public void post(Game game) throws DataAccessException {

    }

    @Override
    public void put(Game game) throws DataAccessException {

    }

    @Override
    public void delete(Game game) throws DataAccessException {

    }
}
