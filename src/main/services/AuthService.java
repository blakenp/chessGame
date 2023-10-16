package services;

import requests.LoginRequest;
import responses.LoginResponse;
import responses.LogoutResponse;

/**
 * Object representation of the authentication service for the app responsible for logging in and logging out users
 */
public class AuthService {
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

    /**
     * A method that logs the user out of their user session
     * @return This will return either null and a successful 200 status code for logging the user out or a LogoutResponse with an error message
     */
    public LogoutResponse logout() {
        return null;
    }
}
