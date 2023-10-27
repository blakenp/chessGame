package responses;

/**
 * Object representation of the response and possible strings it can send back to the client.
 */
public class LoginResponse extends ResponseMessage {
    /**
     * Data field representing a user's username
     */
    private String username;
    /**
     * Data field representing an auth token
     */
    private String authToken;


    /**
     * LoginResponse object constructor that will be instantiated to respond to client login requests.
     * It can return either a username and auth token upon successful requests or possible error
     * messages upon failed requests.
     *
     * @param username The user's username that they will appear to others as
     * @param authToken The user's authToken used to make api requests to other endpoints on the server
     */
    public LoginResponse(String username, String authToken) {
        super(null);
        this.username = username;
        this.authToken = authToken;
    }

    public LoginResponse(String message) {
        super(message);
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
     * A getter method for the user's auth token
     *
     * @return The user's auth token
     */
    public String getAuthToken() {
        return authToken;
    }
}
