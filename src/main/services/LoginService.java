package services;

import requests.LoginRequest;
import responses.LoginResponse;

/**
 * Object representation of a login service that makes that API request to log a user in.
 */
public class LoginService {
    /**
     * A method for logging a user in. Will receive help from handlers to communicate with Spark package to
     * successfully make API calls.
     *
     * @param request The request object representing the user's request to log in
     * @return The response given back to the client after requesting to log in. Will result in either a successful login or error
     */
    public LoginResponse login(LoginRequest request) {
        return null;
    }
}
