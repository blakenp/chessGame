package dataAccess;

import models.User;

import java.util.List;

public class UserDAO implements DAO<User> {

    @Override
    public User get() throws DataAccessException {
        return null;
    }

    @Override
    public List<User> getAll() throws DataAccessException {
        return null;
    }

    @Override
    public void post(User user) throws DataAccessException {

    }

    @Override
    public void put(User user) throws DataAccessException {

    }

    @Override
    public void delete(User user) throws DataAccessException {

    }
}
