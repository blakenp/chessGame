package services;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import responses.ClearResponse;

/**
 * Object representation of clear service that make the API call to clear the database of all its data for testing purposes
 */
public class TestingService {
    private UserDAO userDAO = UserDAO.getInstance();
    private AuthDAO authDAO = AuthDAO.getInstance();
    private GameDAO gameDAO = GameDAO.getInstance();
    /**
     * A method that executes the logic to clear the whole database of its data. Could also return an error message if server issues occur
     * @return Will either return null and a successful 200 status code that will result in clearing the whole database or an error in doing so
     */
    public ClearResponse clear() {
        try {
            userDAO.deleteAll();
            authDAO.deleteAll();
            gameDAO.deleteAll();
        } catch (DataAccessException dataAccessException) {
            return new ClearResponse("Error: failed to clear database");
        } catch (Exception exception) {
            return new ClearResponse("Error: Internal Server Error");
        }
        return new ClearResponse("");
    }
}
