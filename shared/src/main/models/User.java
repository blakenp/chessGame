package models;

/**
 * Object representation of a user. Mainly just consists of the username, password, and email fields
 *
 * @param username Data field representing a user's username
 * @param password Data field representing a user's password
 * @param email    Data field representing a user's email
 */
public record User(String username, String password, String email) {
    /**
     * User object constructor that will be used to instantiate a user and eventually put them in the database
     * when they register for an account.
     *
     * @param username The user's username
     * @param password The user's password
     * @param email    The user's email
     */
    public User {
    }

    /**
     * A getter method for user's username
     *
     * @return The user's username
     */
    @Override
    public String username() {
        return username;
    }

    /**
     * A getter method for the user's password
     *
     * @return The user's password
     */
    @Override
    public String password() {
        return password;
    }

    /**
     * A getter method for user's email
     *
     * @return The user's email
     */
    @Override
    public String email() {
        return email;
    }
}
