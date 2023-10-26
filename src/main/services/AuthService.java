package services;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import models.AuthToken;
import models.User;
import requests.LoginRequest;
import requests.LogoutRequest;
import responses.LoginResponse;
import responses.LogoutResponse;

import java.util.Objects;
import java.util.UUID;

/**
 * Object representation of the authentication service for the app responsible for logging in and logging out users
 */
public class AuthService {

    private AuthDAO authDAO = AuthDAO.getInstance();

    private UserDAO userDAO = UserDAO.getInstance();

    /**
     * A method for logging a user in. Will receive help from handlers to communicate with Spark package to
     * successfully make API calls.
     *
     * @param request The request object representing the user's request to log in
     * @return The response given back to the client after requesting to log in. Will result in either a successful login or error
     */
    public LoginResponse login(LoginRequest request) {
        AuthToken authToken = new AuthToken(request.username(), UUID.randomUUID().toString());
        User user = new User(authToken.username(), null, null);

        try {
            user = userDAO.get(user);

            if (userDAO.get(user) == null) {
                return new LoginResponse("Error: user doesn't exist");
            }

            if (authDAO.get(authToken) != null) {
                return new LoginResponse("Error: invalid auth token");
            }

            if (!Objects.equals(user.password(), request.password())) {
                return new LoginResponse("Error: unauthorized");
            }

            authDAO.post(authToken);
        } catch (DataAccessException dataAccessException) {
            return new LoginResponse("Error: an error occurred accessing, creating, deleting, or updating data");
        } catch (Exception exception) {
            return new LoginResponse("Error: an internal server error has occurred");
        }
        return new LoginResponse(request.username(), authToken.authToken());
    }

    /**
     * A method that logs the user out of their user session
     * @return This will return either null and a successful 200 status code for logging the user out or a LogoutResponse with an error message
     */
    public LogoutResponse logout(LogoutRequest request) {
        AuthToken authToken = new AuthToken("", request.authToken());

        try {

            if (authDAO.get(authToken) == null) {
                return new LogoutResponse("Error: invalid auth token");
            }

            authDAO.delete(authToken);
        } catch (DataAccessException dataAccessException) {
            return new LogoutResponse("Error: an error occurred accessing, creating, deleting, or updating data");
        } catch (Exception exception) {
            return new LogoutResponse("Error: an internal server error has occurred");
        }
        return new LogoutResponse("Successful Logout");
    }
}
