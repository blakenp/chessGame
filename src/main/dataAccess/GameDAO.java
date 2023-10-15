package dataAccess;

import models.Game;

import java.util.List;

public class GameDAO implements DAO<Game> {

    @Override
    public Game get() throws DataAccessException {
        return null;
    }

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
