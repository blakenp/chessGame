package dataAccess;

import models.User;

/**
 * Object representation of the data access object used to store and fetch the data related to users in the database
 */
public class UserDAO implements DAO<User> {

    private static UserDAO instance = null;

    /**
     * Singleton design to make sure just one instance of this class is used in the api endpoints
     * and that you only ever use this instance
     *
     * @return The same instance of this class
     */
    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    /**
     * A method that queries the database for a user and returns it if it is found in the database
     * @param user The user that will be queried for in the database
     * @return The user if found
     * @throws DataAccessException An exception if an error occurs in accessing the data or if user is not found
     */
    @Override
    public User get(User user) throws DataAccessException {
        var database = new Database();

        try (var connection = database.getConnection()) {
            String sql = "SELECT username, password, email FROM user WHERE username = ?";

            try (var preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, user.username());

                try (var resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        var username = resultSet.getString("username");
                        var password = resultSet.getString("password");
                        var email = resultSet.getString("email");

                        return new User(username, password, email);
                    }
                }
            }
            throw new DataAccessException("Error: User not found in database");
        } catch (Exception exception) {
            throw new DataAccessException("Error: failed to get user data");
        }
    }

    /**
     * A method that create a new user and stores it in the database
     * @param user The user that is inserted into the database
     * @throws DataAccessException An exception if an error occurs in accessing the data
     */
    @Override
    public void post(User user) throws DataAccessException {
        var database = new Database();

        try (var connection = database.getConnection()) {
            // check if the user already exists in the database. If they do, then an exception will be thrown
            try (var preparedStatement = connection.prepareStatement("SELECT username, password, email FROM user WHERE username=?")) {
                preparedStatement.setString(1, user.username());

                try (var resultSet = preparedStatement.executeQuery()) {
                    // if the query returned any result, throw an error
                    if (resultSet.next()) {
                        throw new DataAccessException("Error: username already taken");
                    }
                }
            }

            String sql = "INSERT INTO user (username, password, email) VALUES (?, ?, ?)";

            try (var preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, user.username());
                preparedStatement.setString(2, user.password());
                preparedStatement.setString(3, user.email());

                preparedStatement.executeUpdate();
            }
        } catch (Exception exception) {
            throw new DataAccessException("Error: failed to create new user");
        }
    }

    /**
     * Method for clearing the auth tokens from the database
     *
     * @throws DataAccessException An exception if an error occurs in deleting the data
     */
    @Override
    public void deleteAll() throws DataAccessException {
        var database = new Database();

        try (var connection = database.getConnection()) {
            String sql = "DELETE FROM user;";

            try (var preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.executeUpdate();
            }
        } catch (Exception exception) {
            throw new DataAccessException("Error: failed to clear users from database");
        }
    }
}
