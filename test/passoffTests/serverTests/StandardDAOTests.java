package passoffTests.serverTests;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import models.AuthToken;
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
}
