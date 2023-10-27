package dataAccess;

import models.AuthToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Object representation of the data access object that will be used to store and fetch data relating to the
 * authentication of users for the chess game app
 */
public class AuthDAO implements DAO<AuthToken> {

    private static AuthDAO instance = null;
    private Map<String, AuthToken> authTokens = new HashMap<>();

    /**
     * Singleton design to make sure just one instance of this class is used in the api endpoints
     * and that you only ever use this instance
     *
     * @return The same instance of this class
     */
    public static AuthDAO getInstance() {
        if (instance == null) {
            instance = new AuthDAO();
        }
        return instance;
    }

    /**
     * A method that queries the database for an auth token and returns it if it is found in the database
     * @param authToken The auth token that will be queried for in the database
     * @return The auth token if it is found
     * @throws DataAccessException An exception if an error occurs in accessing the data or if auth token is not found
     */
    @Override
    public AuthToken get(AuthToken authToken) throws DataAccessException {
        try {
            return authTokens.get(authToken.authToken());
        } catch (Exception exception) {
            throw new DataAccessException("Error: failed to get auth token");
        }
    }

    /**
     * A method that will retrieve all the currently stored auth tokens in the database. For security reasons,
     * this just throw an exception if someone tries to access it
     *
     * @return A list of all the currently stored auth tokens in the database
     * @throws DataAccessException An exception if an error occurs in accessing the data
     */
    @Override
    public List<AuthToken> getAll() throws DataAccessException {
        throw new DataAccessException("Error: unauthorized");
    }

    /**
     * A method that will create a new auth token and put it in the database
     * @param authToken The auth token that is inserted into the database
     * @throws DataAccessException An exception if an error occurs in accessing the data
     */
    @Override
    public void post(AuthToken authToken) throws DataAccessException {
        authTokens.put(authToken.authToken(), authToken);
    }

    /**
     * A method that updates a specific auth token in the database. This one will just throw an error
     * because of security reasons
     *
     * @param authToken The auth token in the database that will be updated
     * @throws DataAccessException An exception if an error occurs in accessing the data
     */
    @Override
    public void put(AuthToken authToken) throws DataAccessException {
        throw new DataAccessException("Error: unauthorized");
    }

    /**
     * A method that deletes an auth token stored in the database
     *
     * @param authToken The auth token in the database that will be deleted
     * @throws DataAccessException An exception if an error occurs in accessing the data
     */
    @Override
    public void delete(AuthToken authToken) throws DataAccessException {
        try {
            authTokens.remove(authToken.authToken());
        } catch (Exception exception) {
            throw new DataAccessException("Error: Failed to delete auth token");
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
            authTokens.clear();
        } catch (Exception exception) {
            throw new DataAccessException("Error: failed to clear auth tokens from database");
        }
    }
}
