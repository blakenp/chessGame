package dataAccess;

import models.AuthToken;

import java.util.List;

public class AuthDAO implements DAO<AuthToken> {
    @Override
    public AuthToken get() throws DataAccessException {
        return null;
    }

    @Override
    public List<AuthToken> getAll() throws DataAccessException {
        return null;
    }

    @Override
    public void post(AuthToken authToken) throws DataAccessException {

    }

    @Override
    public void put(AuthToken authToken) throws DataAccessException {

    }

    @Override
    public void delete(AuthToken authToken) throws DataAccessException {

    }
}
