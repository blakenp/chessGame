package dataAccess;

import models.AuthToken;

import java.util.List;

/**
 * Object representation of the data access object that will be used to store and fetch data relating to the
 * authentication of users for the chess game app
 */
public class AuthDAO implements DAO<AuthToken> {

    /**
     * A method that queries the database for an auth token and returns it if it is found in the database
     * @param authToken The auth token that will be queried for in the database
     * @return The auth token if it is found
     * @throws DataAccessException An exception if an error occurs in accessing the data or if auth token is not found
     */
    @Override
    public AuthToken get(AuthToken authToken) throws DataAccessException {
        return null;
    }

    /**
     * A method that will retrieve all the currently stored auth tokens in the database. I may make this always
     * throw a DataAccessException just for security reasons though
     *
     * @return A list of all the currently stored auth tokens in the database
     * @throws DataAccessException An exception if an error occurs in accessing the data
     */
    @Override
    public List<AuthToken> getAll() throws DataAccessException {
        return null;
    }

    /**
     * A method that will create a new auth token and put it in the database
     * @param authToken The auth token that is inserted into the database
     * @throws DataAccessException An exception if an error occurs in accessing the data
     */
    @Override
    public void post(AuthToken authToken) throws DataAccessException {

    }

    /**
     * A method that updates a specific auth token in the database
     * @param authToken The auth token in the database that will be updated
     * @throws DataAccessException An exception if an error occurs in accessing the data
     */
    @Override
    public void put(AuthToken authToken) throws DataAccessException {

    }

    /**
     * A method that deletes an auth token stored in the database
     * @param authToken The auth token in the database that will be deleted
     * @throws DataAccessException An exception if an error occurs in accessing the data
     */
    @Override
    public void delete(AuthToken authToken) throws DataAccessException {

    }
}
