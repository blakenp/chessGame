package services;

import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import models.User;
import requests.RegisterRequest;
import responses.RegisterResponse;

import java.util.UUID;

/**
 * Object representation of the service that makes API calls to register the client for a new user account, so they can
 * log in afterward and play chess games with others online
 */
public class UserService {
    /**
     * A method that will register the client for a new user account
     * @param request The client's request to register for an account
     * @return A successful register response for registering the client for a new account or a response with an error message
     */
    public RegisterResponse register(RegisterRequest request) {
        UserDAO userDAO = new UserDAO();
        User newUser = new User(request.getUsername(), request.getPassword(), request.getEmail());

        try {
            if (request.getUsername().isEmpty() || request.getPassword().isEmpty() || request.getEmail().isEmpty()) {
                return new RegisterResponse("Error: bad request");
            }

            if (userDAO.contains(newUser)) {
                return new RegisterResponse("Error: already taken");
            }

            userDAO.post(newUser);
        } catch (DataAccessException exception) {
            return new RegisterResponse("Error: an error occurred accessing, creating, deleting, or updating data");
        } catch (Exception exception) {
            return new RegisterResponse("Error: an internal server error has occurred");
        }
        return new RegisterResponse(request.getUsername(), UUID.randomUUID().toString());
    }
}
