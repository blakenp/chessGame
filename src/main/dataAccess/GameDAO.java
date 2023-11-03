package dataAccess;

import chessImplementation.ChessGameImpl;
import models.Game;
import models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Object representation of the data access object used to store and fetch data relating to chess games in the database
 */
public class GameDAO implements DAO<Game> {

    private static GameDAO instance = null;
    private Map<String, Game> games = new HashMap<>();

    /**
     * Singleton design to make sure just one instance of this class is used in the api endpoints
     * and that you only ever use this instance
     *
     * @return The same instance of this class
     */
    public static GameDAO getInstance() {
        if (instance == null) {
            instance = new GameDAO();
        }
        return instance;
    }

    /**
     * A method that queries the database for a game and returns it if it is found in the database
     * @param game The game that will be queried for in the database
     * @return The game if it is found
     * @throws DataAccessException An exception if an error occurs in accessing the data or if game is not found
     */
    @Override
    public Game get(Game game) throws DataAccessException {
        var database = new Database();

        try (var connection = database.getConnection()) {
            String sql = "SELECT game_id, white_username, black_username, game_name, chess_game FROM game WHERE game_id = ?";

            try (var preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, game.gameID());

                try (var resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        var gameID = resultSet.getInt("game_id");
                        var whiteUsername = resultSet.getString("white_username");
                        var blackUsername = resultSet.getString("black_username");
                        var gameName = resultSet.getString("game_name");
//                        var chessGame = resultSet.getString("chess_game");

                        // TODO: make a serializer and deserializer
                        return new Game(gameID, whiteUsername, blackUsername, gameName, new ChessGameImpl());
                    }
                }
            }
            throw new DataAccessException("Error: game not found in database");
        } catch (Exception exception) {
            throw new DataAccessException("Error: failed to get game data");
        }
    }

    /**
     * A method that will return all the games stored in the database as a list
     * @return A list of all the games stored in the database
     * @throws DataAccessException An exception if an error occurs in accessing the data
     */

    public List<Game> getAll() throws DataAccessException {
        List<Game> gamesList = new ArrayList<>();

        var database = new Database();

        try (var connection = database.getConnection()) {
            String sql = "SELECT game_id, white_username, black_username, game_name, chess_game FROM game";

            try (var preparedStatement = connection.prepareStatement(sql)) {

                try (var resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        var gameID = resultSet.getInt("game_id");
                        var whiteUsername = resultSet.getString("white_username");
                        var blackUsername = resultSet.getString("black_username");
                        var gameName = resultSet.getString("game_name");
//                        var chessGame = resultSet.getString("chess_game");

                        // TODO: make a serializer and deserializer
                        gamesList.add(new Game(gameID, whiteUsername, blackUsername, gameName, new ChessGameImpl()));
                    }
                }
            }

            return gamesList;
        } catch (Exception exception) {
            throw new DataAccessException("Error: failed to get game data");
        }
    }

    /**
     * A method that create a new game and adds that new game to the database
     * @param game The game that is inserted into the database
     * @throws DataAccessException An exception if an error occurs in accessing the data
     */
    @Override
    public void post(Game game) throws DataAccessException {
        var database = new Database();

        try (var connection = database.getConnection()) {
            // check if the game with specified game name already exists in the database. If it does, then an exception will be thrown
            try (var preparedStatement = connection.prepareStatement("SELECT game_id, white_username, black_username, game_name, chess_game FROM game WHERE game_name = ?")) {
                preparedStatement.setString(1, game.gameName());

                try (var resultSet = preparedStatement.executeQuery()) {
                    // if the query returned any result, throw an error
                    if (resultSet.next()) {
                        throw new DataAccessException("Error: game name  already taken");
                    }
                }
            }

            String sql = "INSERT INTO game (game_id, white_username, black_username, game_name, chess_game) VALUES (?, ?, ?, ?, ?)";

            try (var preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, game.gameID());
                preparedStatement.setString(2, game.whiteUsername());
                preparedStatement.setString(3, game.blackUsername());
                preparedStatement.setString(4, game.gameName());
                //TODO: Again, make serializer/deserializer for chessgame
                preparedStatement.setString(5, "game.chessGame.toString()");

                preparedStatement.executeUpdate();
            }
        } catch (Exception exception) {
            throw new DataAccessException("Error: failed to create new game");
        }
    }

    /**
     * A method that updates the state of an existing game stored in the database
     * @param game The game in the database that will be updated
     * @throws DataAccessException An exception if an error occurs in accessing the data
     */

    public void put(Game game) throws DataAccessException {
        var database = new Database();

        try (var connection = database.getConnection()) {

            String sql = "UPDATE game SET game_id = ?, white_username = ?, black_username = ?, game_name = ?, chess_game = ? WHERE game_name = ?";

            try (var preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, game.gameID());
                preparedStatement.setString(2, game.whiteUsername());
                preparedStatement.setString(3, game.blackUsername());
                preparedStatement.setString(4, game.gameName());
                //TODO: Again, make serializer/deserializer for chessgame
                preparedStatement.setString(5, "game.chessGame.toString()");
                preparedStatement.setString(6, game.gameName());

                preparedStatement.executeUpdate();
            }
        } catch (Exception exception) {
            throw new DataAccessException("Error: failed to update game");
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
            String sql = "DELETE FROM game;";

            try (var preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.executeUpdate();
            }
        } catch (Exception exception) {
            throw new DataAccessException("Error: failed to clear games from database");
        }
    }
}
