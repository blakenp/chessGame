package responses;

/**
 * Object representation of the response and possible strings it can send back to the client.
 */
public class LoginResponse {
    /**
     * Data field representing a user's username
     */
    private String username;
    /**
     * Data field representing an auth token
     */
    private String authToken;
    /**
     * Data field representing an error message
     */
    private String message;

    /**
     * LoginResponse object constructor that will be instantiated to respond to client login requests.
     * It can return either a username and auth token upon successful requests or possible error
     * messages upon failed requests.
     *
     * @param username The user's username that they will appear to others as
     * @param authToken The user's authToken used to make api requests to other endpoints on the server
     * @param message The error message that will appear for certain API error status codes if the LoginRequest object received is rejected
     */
    public LoginResponse(String username, String authToken, String message) {
        this.username = username;
        this.authToken = authToken;
        this.message = message;
    }

    /**
     * A getter method for the user's username
     *
     * @return The user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * A setter method for setting the user's username
     *
     * @param username The user's new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * A getter method for the user's auth token
     *
     * @return The user's auth token
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     * A setter method for setting the user's auth token
     *
     * @param authToken The user's new auth token
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * A getter method for the response's error message
     *
     * @return The error message
     */
    public String getMessage() {
        return message;
    }

    /**
     * A setter method for setting the error message
     *
     * @param message The new desired error message to set message to
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
