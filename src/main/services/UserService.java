package services;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import models.AuthToken;
import models.User;
import requests.RegisterRequest;
import responses.RegisterResponse;

import java.util.UUID;

/**
 * Object representation of the service that makes API calls to register the client for a new user account, so they can
 * log in afterward and play chess games with others online
 */
public class UserService {
    private UserDAO userDAO = UserDAO.getInstance();
    private AuthDAO authDAO = AuthDAO.getInstance();
    /**
     * A method that will register the client for a new user account
     * @param request The client's request to register for an account
     * @return A successful register response for registering the client for a new account or a response with an error message
     */
    public RegisterResponse register(RegisterRequest request) {
        User newUser = new User(request.username(), request.password(), request.email());
        AuthToken authToken = new AuthToken(newUser.username(), UUID.randomUUID().toString());

        try {
            // If not all required fields were sent by the client, throw an error
            if ((request.username() == null) || (request.password() == null) || (request.email() == null)) {
                return new RegisterResponse("Error: bad request");
            }

            try {
                userDAO.post(newUser);
            } catch (DataAccessException dataAccessException) {
                return new RegisterResponse("Error: already taken");
            }

            authDAO.post(authToken);
        } catch (DataAccessException dataAccessException) {
            return new RegisterResponse("Error: an error occurred accessing, creating, deleting, or updating data");
        } catch (Exception exception) {
            return new RegisterResponse("Error: an internal server error has occurred");
        }
        return new RegisterResponse(request.username(), authToken.authToken());
    }
}
