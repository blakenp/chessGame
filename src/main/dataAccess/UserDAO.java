package dataAccess;

import models.User;

import java.util.List;

/**
 * Object representation of the data access object used to store and fetch the data related to users in the database
 */
public class UserDAO implements DAO<User> {

    /**
     * A method that returns a list of all the users stored in the database
     * @return A list of all the users stored in the database
     * @throws DataAccessException An exception if an error occurs in accessing the data
     */
    @Override
    public List<User> getAll() throws DataAccessException {
        return null;
    }

    /**
     * A method that create a new user and stores it in the database
     * @param user The user that is inserted into the database
     * @throws DataAccessException An exception if an error occurs in accessing the data
     */
    @Override
    public void post(User user) throws DataAccessException {

    }

    /**
     * A method that updates an existing user's data in the database
     * @param user The user in the database that will be updated
     * @throws DataAccessException An exception if an error occurs in accessing the data
     */
    @Override
    public void put(User user) throws DataAccessException {

    }

    /**
     * A method that deletes an existing user and their data in the database
     * @param user The user in the database that will be deleted
     * @throws DataAccessException An exception if an error occurs in accessing the data
     */
    @Override
    public void delete(User user) throws DataAccessException {

    }
}
