package models;

/**
 * Object representation of an auth token used to authenticate web API calls after a user has logged in
 */
public class AuthToken {
    /**
     * Data field representing a user's username
     */
    private final String username;
    /**
     * Data field representing the actual auth token for the AuthToken object
     */
    private final String authToken;

    /**
     * AuthToken object constructor that takes in a username and auth token to instantiate
     *
     * @param username The username of the user the auth token is associated with
     * @param authToken The auth token the user can use to make web API calls
     */
    public AuthToken(String username, String authToken) {
        this.username = username;
        this.authToken = authToken;
    }

    /**
     * A getter method for user's username
     *
     * @return The associated user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * A getter method for the auth token
     *
     * @return The auth token
     */
    public String getAuthToken() {
        return authToken;
    }
}
