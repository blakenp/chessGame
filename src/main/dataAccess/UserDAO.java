package dataAccess;

import models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Object representation of the data access object used to store and fetch the data related to users in the database
 */
public class UserDAO implements DAO<User> {

    private static UserDAO instance = null;
    private Map<String, User> users = new HashMap<>();

    /**
     * Singleton design to make sure just one instance of this class is used in the api endpoints
     * and that you only ever use this instance
     *
     * @return The same instance of this class
     */
    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    /**
     * A method that queries the database for a user and returns it if it is found in the database
     * @param user The user that will be queried for in the database
     * @return The user if found
     * @throws DataAccessException An exception if an error occurs in accessing the data or if user is not found
     */
    @Override
    public User get(User user) throws DataAccessException {
        try {
            return users.get(user.username());
        } catch (Exception exception) {
            throw new DataAccessException("Error: failed to get user data");
        }
    }

    /**
     * A method that create a new user and stores it in the database
     * @param user The user that is inserted into the database
     * @throws DataAccessException An exception if an error occurs in accessing the data
     */
    @Override
    public void post(User user) throws DataAccessException {
        try {
            users.put(user.username(), user);
        } catch (Exception exception) {
            throw new DataAccessException("Error: failed to create new user");
        }
    }

    /**
     * Method for clearing the auth tokens from the database
     *
     * @throws DataAccessException An exception if an error occurs in deleting the data
     */
    @Override
    public void deleteAll() throws DataAccessException {
        try {
            users.clear();
        } catch (Exception exception) {
            throw new DataAccessException("Error: failed to clear users from database");
        }
    }
}
