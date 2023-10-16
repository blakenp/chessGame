package services;

import requests.RegisterRequest;
import responses.RegisterResponse;

/**
 * Object representation of the service that makes API calls to register the client for a new user account so they can
 * log in afterward and play chess games with others online
 */
public class RegisterService {
    /**
     * A method that will register the client for a new user account
     * @param request The client's request to register for an account
     * @return A successful register response for registering the client for a new account or a response with an error message
     */
    public RegisterResponse register(RegisterRequest request) {
        return null;
    }
}
