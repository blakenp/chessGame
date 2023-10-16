package services;

import responses.LogoutResponse;

/**
 * Object representation of the service that makes API calls to log out the client from their user session
 */
public class LogoutService {
    /**
     * A method that logs the user out of their user session
     * @return This will return either null and a successful 200 status code for logging the user out or a LogoutResponse with an error message
     */
    public LogoutResponse logout() {
        return null;
    }
}
