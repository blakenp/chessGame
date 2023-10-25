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

    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    public boolean contains(User user) {
        if (users.containsKey(user.username())) {
            return true;
        }
        return false;
    }

    /**
     * A method that queries the database for a user and returns it if it is found in the database
     * @param user The user that will be queried for in the database
     * @return The user if found
     * @throws DataAccessException An exception if an error occurs in accessing the data or if user is not found
     */
    @Override
    public User get(User user) throws DataAccessException {
        return users.get(user.username());
    }

    /**
     * A method that returns a list of all the users stored in the database
     * @return A list of all the users stored in the database
     * @throws DataAccessException An exception if an error occurs in accessing the data
     */
    @Override
    public List<User> getAll() throws DataAccessException {
        List<User> usersList = new ArrayList<>();

        for (User user : users.values()) {
            usersList.add(user);
        }

        return usersList;
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
