package dataAccess;

import models.AuthToken;

/**
 * Object representation of the data access object that will be used to store and fetch data relating to the
 * authentication of users for the chess game app
 */
public class AuthDAO implements DAO<AuthToken> {

    private static AuthDAO instance = null;

    /**
     * Singleton design to make sure just one instance of this class is used in the api endpoints
     * and that you only ever use this instance
     *
     * @return The same instance of this class
     */
    public static AuthDAO getInstance() {
        if (instance == null) {
            instance = new AuthDAO();
        }
        return instance;
    }

    /**
     * A method that queries the database for an auth token and returns it if it is found in the database
     * @param authToken The auth token that will be queried for in the database
     * @return The auth token if it is found
     * @throws DataAccessException An exception if an error occurs in accessing the data or if auth token is not found
     */
    @Override
    public AuthToken get(AuthToken authToken) throws DataAccessException {
        var database = new Database();

        try (var connection = database.getConnection()) {
            String sql = "SELECT username, auth_token FROM auth WHERE auth_token = ?";

            try (var preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, authToken.authToken());

                try (var resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        var username = resultSet.getString("username");
                        var token = resultSet.getString("auth_token");

                        return new AuthToken(username, token);
                    } else {
                        throw new DataAccessException("Error: token not found in database");
                    }
                }
            }
        } catch (Exception exception) {
            throw new DataAccessException("Error: failed to get user data");
        }
    }

    /**
     * A method that will create a new auth token and put it in the database
     * @param authToken The auth token that is inserted into the database
     * @throws DataAccessException An exception if an error occurs in accessing the data
     */
    @Override
    public void post(AuthToken authToken) throws DataAccessException {
        var database = new Database();

        try (var connection = database.getConnection()) {
            // check if the token already exists in the database. If it does, then an exception will be thrown
            try (var preparedStatement = connection.prepareStatement("SELECT username, auth_token FROM auth WHERE auth_token = ?")) {
                preparedStatement.setString(1, authToken.authToken());

                try (var resultSet = preparedStatement.executeQuery()) {
                    // if the query returned any result, throw an error
                    if (resultSet.next()) {
                        throw new DataAccessException("Error: auth token already exists in database");
                    }
                }
            }

            String sql = "INSERT INTO auth (username, auth_token) VALUES (?, ?)";

            try (var preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, authToken.username());
                preparedStatement.setString(2, authToken.authToken());

                preparedStatement.executeUpdate();
            }
        } catch (Exception exception) {
            throw new DataAccessException("Error: failed to create new token");
        }
    }

    /**
     * A method that deletes an auth token stored in the database
     *
     * @param authToken The auth token in the database that will be deleted
     * @throws DataAccessException An exception if an error occurs in accessing the data
     */

    public void delete(AuthToken authToken) throws DataAccessException {
        var database = new Database();

        try (var connection = database.getConnection()) {
            String sql = "DELETE FROM auth WHERE auth_token = ?";

            try (var preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, authToken.authToken());

                preparedStatement.executeUpdate();
            }
        } catch (Exception exception) {
            throw new DataAccessException("Error: failed to get auth data");
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
            String sql = "DELETE FROM auth;";

            try (var preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.executeUpdate();
            }
        } catch (Exception exception) {
            throw new DataAccessException("Error: failed to clear auth tokens from database");
        }
    }
}
