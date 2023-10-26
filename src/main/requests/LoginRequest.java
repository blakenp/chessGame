package requests;

/**
 * Object representation of requests the client can make to log into the chess game application
 */
public class LoginRequest {
    private String username;
    private String password;

    /**
     * LoginRequest object constructor for the login Web API. Pass in the username and password to create
     * a login request object to attempt to authenticate with when sending to the Server.
     *
     * @param username The user's username
     * @param password The user's password
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * A getter method for the user's username
     * @return The user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * A getter method for the user's password
     * @return The user's password
     */
    public String getPassword() {
        return password;
    }
}
