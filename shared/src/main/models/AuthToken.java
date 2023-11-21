package models;

/**
 * Object representation of an auth token used to authenticate web API calls after a user has logged in
 *
 * @param username  Data field representing a user's username
 * @param authToken Data field representing the actual auth token for the AuthToken object
 */
public record AuthToken(String username, String authToken) {
    /**
     * AuthToken object constructor that takes in a username and auth token to instantiate
     *
     * @param username  The username of the user the auth token is associated with
     * @param authToken The auth token the user can use to make web API calls
     */
    public AuthToken {
    }

    /**
     * A getter method for user's username
     *
     * @return The associated user's username
     */
    @Override
    public String username() {
        return username;
    }

    /**
     * A getter method for the auth token
     *
     * @return The auth token
     */
    @Override
    public String authToken() {
        return authToken;
    }
}
