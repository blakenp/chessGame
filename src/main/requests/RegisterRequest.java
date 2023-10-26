package requests;

/**
 * Object representation of a request to register for a new user account. Mainly just sets up username,
 * password, and email fields for new users
 */
public class RegisterRequest {
    private String username;
    private String password;
    private String email;

    /**
     * RegisterRequest object constructor that instantiates the object with the desired values for
     * username, password, and email
     *
     * @param username The username of the user who is requesting to be registered
     * @param password The password of the user who is requesting to be registered
     * @param email The email of the user who is requesting to be registered
     */
    public RegisterRequest(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    /**
     * A getter method for the registering user's username
     * @return The registering user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * A getter method for the registering user's password
     * @return The registering user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * A getter method for the registering user's email
     * @return The registering user's email
     */
    public String getEmail() {
        return email;
    }
}
