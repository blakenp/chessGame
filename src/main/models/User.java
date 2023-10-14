package models;

/**
 * Object representation of a user. Mainly just consists of the username, password, and email fields
 */
public class User {
    private String username;
    private String password;
    private String email;

    /**
     * User object constructor that will be used to instantiate a user and eventually put them in the database
     * when they register for an account.
     *
     * @param username The user's username
     * @param password The user's password
     * @param email The user's email
     */
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    /**
     * A getter method for user's username
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

    /**
     * A getter method for user's email
     * @return The user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * A setter method for the user's username
     * @param username The user's new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * A setter method for the user's password
     * @param password The user's new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * A setter method for the user's email
     * @param email The user's new email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
