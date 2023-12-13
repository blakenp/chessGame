package unitTests;

import chess.ChessGame;
import chessImplementation.ChessGameImpl;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import models.AuthToken;
import models.Game;
import models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import services.AuthService;
import services.GameService;
import services.TestingService;
import services.UserService;

import static org.junit.jupiter.api.Assertions.*;

public class StandardDAOTests {
    UserService userService;
    AuthService authService;
    GameService gameService;
    TestingService testingService;
    UserDAO userDAO;
    AuthDAO authDAO;
    GameDAO gameDAO;

    @BeforeEach
    public void setup() throws DataAccessException {
        userDAO = UserDAO.getInstance();
        authDAO = AuthDAO.getInstance();
        gameDAO = GameDAO.getInstance();

        userService = new UserService();
        authService = new AuthService();
        gameService = new GameService();
        testingService = new TestingService();

        userDAO.deleteAll();
        authDAO.deleteAll();
        gameDAO.deleteAll();
    }

    @Test
    @DisplayName("Successful UserDAO user creation")
    public void successCreateUser() throws DataAccessException {
        User testUser = new User("hermano", "Sanchez", "sanchez123@gmail.com");
        userDAO.post(testUser);
        assertEquals(userDAO.get(testUser), testUser, "After creating a user, fetching it from the database should return the added user");
    }

    @Test
    @DisplayName("Negative UserDAO user creation")
    public void negativeCreateUser() throws DataAccessException {
        User testUser = new User("Jose", "Sanchez", "sanchez123@gmail.com");
        userDAO.post(testUser);

        User badUser = new User("Jose", "Martinez", "martinez@gmail.com");
        assertThrows(DataAccessException.class, () -> userDAO.post(badUser), "After creating a user, fetching it from the database should result in a DataAccessException");
    }

    @Test
    @DisplayName("Successful UserDAO user fetch")
    public void successFetchUser() throws DataAccessException {
        User testUser = new User("Mario", "Sanchez", "sanchez123@gmail.com");
        userDAO.post(testUser);
        assertEquals(userDAO.get(testUser), testUser, "After creating a user, fetching it from the database should return the added user");
    }

    @Test
    @DisplayName("Successful UserDAO user fetch")
    public void negativeFetchUser() throws DataAccessException {
        User testUser = new User("Mario", "Sanchez", "sanchez123@gmail.com");
        User badUser = new User("Jose", "Martinez", "martinez@gmail.com");
        userDAO.post(testUser);
        assertThrows(DataAccessException.class, () -> userDAO.get(badUser), "Trying to fetch a user not stored in the database should throw an error");
    }

    @Test
    @DisplayName("Successful UserDAO users clear")
    public void SuccessfulClearUsers() throws DataAccessException {
        User testUser = new User("Jose", "Sanchez", "sanchez123@gmail.com");
        User testUser1 = new User("Josuke", "Saro", "josuke123@gmail.com");

        userDAO.post(testUser);
        userDAO.post(testUser1);

        userDAO.deleteAll();
        assertThrows(DataAccessException.class, () -> userDAO.get(testUser), "After clearing all users, trying to fetch one from the database should result in a DataAccessException");
    }

    @Test
    @DisplayName("Successful AuthDAO token creation")
    public void successCreateToken() throws DataAccessException {
        AuthToken testToken = new AuthToken("hermano", "123d;safjaou");
        authDAO.post(testToken);
        assertEquals(authDAO.get(testToken), testToken, "After creating an auth token and posting it to DB, fetching it from the database should return the added auth token");
    }

    @Test
    @DisplayName("Successful AuthDAO token creation")
    public void negativeCreateToken() throws DataAccessException {
        AuthToken testToken = new AuthToken("hermano", "123d;safjaou");
        AuthToken badToken = new AuthToken("Luigi", "123d;safjaou");

        authDAO.post(testToken);
        assertThrows(DataAccessException.class, () -> authDAO.post(badToken), "Trying to create an existing auth token and post it to the database should throw an error");
    }

    @Test
    @DisplayName("Successful AuthDAO token fetch")
    public void successFetchToken() throws DataAccessException {
        AuthToken testToken = new AuthToken("Waluigi", "123d;safjaou");
        authDAO.post(testToken);
        assertEquals(authDAO.get(testToken), testToken, "After creating a user, fetching it from the database should return the added user");
    }

    @Test
    @DisplayName("Negative AuthDAO token fetch")
    public void negativeFetchToken() throws DataAccessException {
        AuthToken testToken = new AuthToken("Waluigi", "123d;safjaou");
        AuthToken badToken = new AuthToken("Luigi", "123d;safjaoljkhu");

        authDAO.post(testToken);
        assertThrows(DataAccessException.class, () -> authDAO.get(badToken), "Trying to access an auth token not stored in DB should result in an error");
    }

    @Test
    @DisplayName("Successful AuthDAO token deletion")
    public void successDeleteToken() throws DataAccessException {
        AuthToken testToken = new AuthToken("Waluigi", "123d;safjaou");
        authDAO.post(testToken);
        authDAO.delete(testToken);
        assertThrows(DataAccessException.class, () -> authDAO.get(testToken), "Trying to access a deleted auth token not stored in DB should result in an error");
    }

    @Test
    @DisplayName("Successful AuthDAO tokens clear")
    public void successClearTokens() throws DataAccessException {
        AuthToken testToken = new AuthToken("Waluigi", "123d;safjaou");
        AuthToken testToken1 = new AuthToken("Wario", "123d;safjaouadf");
        authDAO.post(testToken);
        authDAO.post(testToken1);

        authDAO.deleteAll();
        assertThrows(DataAccessException.class, () -> authDAO.get(testToken1), "Trying to access any deleted auth token not stored in DB should result in an error");
    }

    @Test
    @DisplayName("Successful GameDAO game creation")
    public void successCreateGame() throws DataAccessException {
        ChessGame game = new ChessGameImpl();
        Game testGame = new Game(1234, null, null, "Adventure Time!", game, false);
        gameDAO.post(testGame);
        assertNotNull(gameDAO.get(testGame), "After creating a game, fetching it from the database should not return null when accessing added game");
    }

    @Test
    @DisplayName("Negative GameDAO game creation")
    public void negativeCreateGame() throws DataAccessException {
        ChessGame game = new ChessGameImpl();
        Game testGame = new Game(1234, null, null, "Adventure Time!", game, false);
        Game testGame1 = new Game(1234, null, null, "Adventure Time!", game, false);
        gameDAO.post(testGame);
        assertThrows(DataAccessException.class, () -> gameDAO.post(testGame1), "Trying to create a game with the same name twice should throw an exception");
    }

    @Test
    @DisplayName("Successful GameDAO game fetch")
    public void successFetchGame() throws DataAccessException {
        ChessGame game = new ChessGameImpl();
        Game testGame = new Game(1234, null, null, "Adventure Time!", game, false);
        gameDAO.post(testGame);
        assertEquals(gameDAO.get(testGame).gameName(), testGame.gameName(), "After creating a game, fetching it from the database should return a game with the same game name as the added game");
    }

    @Test
    @DisplayName("Negative GameDAO game fetch")
    public void negativeFetchGame() throws DataAccessException {
        ChessGame game = new ChessGameImpl();
        Game testGame = new Game(1234, null, null, "Adventure Time!", game, false);
        Game testGame1 = new Game(1256, null, null, "The Best Game!", game, false);
        gameDAO.post(testGame);
        assertThrows(DataAccessException.class, () -> gameDAO.get(testGame1), "Trying to access a game that was never added should throw an exception");
    }

    @Test
    @DisplayName("Successful GameDAO game fetch all")
    public void successFetchAllGames() throws DataAccessException {
        ChessGame game = new ChessGameImpl();
        Game testGame = new Game(1234, null, null, "Adventure Time!", game, false);
        Game testGame1 = new Game(1256, null, null, "Dinner is Ready!", game, false);
        Game testGame2 = new Game(1256456, null, null, "I'm Tired!", game, false);
        gameDAO.post(testGame);
        gameDAO.post(testGame1);
        gameDAO.post(testGame2);

        assertEquals(gameDAO.getAll().size(), 3, "After adding 3 games to database, the size of the returned games list should be 3");
    }

    @Test
    @DisplayName("Negative GameDAO game fetch all")
    public void negativeFetchAllGames() throws DataAccessException {
        assertEquals(gameDAO.getAll().size(), 0, "No games were created, so trying to get all games in the database will return an empty list");
    }

    @Test
    @DisplayName("Successful GameDAO game update")
    public void successUpdateGame() throws DataAccessException {
        ChessGame game = new ChessGameImpl();
        Game testGame = new Game(1234, null, null, "Adventure Time!", game, false);
        Game updatedGame = new Game(1234, "Mario", null, "Adventure Time!", game, false);
        gameDAO.post(testGame);
        gameDAO.put(updatedGame);

        assertEquals(gameDAO.get(testGame).whiteUsername(), updatedGame.whiteUsername(), "After creating a game, fetching it from the database should return a game with the same game name as the added game");
    }

    @Test
    @DisplayName("Negative GameDAO game update")
    public void negativeUpdateGame() throws DataAccessException {
        ChessGame game = new ChessGameImpl();
        Game testGame = new Game(1234, null, null, "Adventure Time!", game, false);
        Game updatedGame = new Game(1234, "Mario", null, "Adventure Time Boy!", game, false);
        gameDAO.post(testGame);

        assertThrows(DataAccessException.class, () -> gameDAO.put(updatedGame), "Trying to update a game that isn't stored in the database should throw an error");
    }

    @Test
    @DisplayName("Successful GameDAO games clear")
    public void successClearGames() throws DataAccessException {
        ChessGame game = new ChessGameImpl();
        Game testGame = new Game(1234, null, null, "Adventure Time!", game, false);
        Game testGame1 = new Game(1256, null, null, "Dinner is Ready!", game, false);
        Game testGame2 = new Game(1256456, null, null, "I'm Tired!", game, false);
        gameDAO.post(testGame);
        gameDAO.post(testGame1);
        gameDAO.post(testGame2);

        gameDAO.deleteAll();
        assertEquals(gameDAO.getAll().size(), 0, "After clearing the games database, trying to fetch a list of all games should return an empty list");
    }

    @Test
    @DisplayName("Successful GameDAO games clear")
    public void successClearDatabase() throws DataAccessException {
        ChessGame game = new ChessGameImpl();
        Game testGame = new Game(1234, null, null, "Adventure Time!", game, false);
        Game testGame1 = new Game(1256, null, null, "Dinner is Ready!", game, false);
        Game testGame2 = new Game(1256456, null, null, "I'm Tired!", game, false);
        gameDAO.post(testGame);
        gameDAO.post(testGame1);
        gameDAO.post(testGame2);

        gameDAO.deleteAll();

        AuthToken testToken = new AuthToken("Waluigi", "123d;safjaou");
        AuthToken testToken1 = new AuthToken("Wario", "123d;safjaouadf");
        authDAO.post(testToken);
        authDAO.post(testToken1);

        authDAO.deleteAll();

        User testUser = new User("Jose", "Sanchez", "sanchez123@gmail.com");
        User testUser1 = new User("Josuke", "Saro", "josuke123@gmail.com");
        userDAO.post(testUser);
        userDAO.post(testUser1);

        userDAO.deleteAll();
        assertEquals(gameDAO.getAll().size(), 0, "After clearing the games database, trying to fetch a list of all games should return an empty list");
        assertThrows(DataAccessException.class, () -> authDAO.get(testToken1), "Trying to access any deleted auth token not stored in DB should result in an error");
        assertThrows(DataAccessException.class, () -> userDAO.get(testUser), "After clearing all users, trying to fetch one from the database should result in a DataAccessException");
    }
}
