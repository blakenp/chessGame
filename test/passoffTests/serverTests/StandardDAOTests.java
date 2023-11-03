package passoffTests.serverTests;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
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
    @DisplayName("Successful UserDAO users clear")
    public void SuccessfulClearUsers() throws DataAccessException {
        User testUser = new User("Jose", "Sanchez", "sanchez123@gmail.com");
        User testUser1 = new User("Josuke", "Saro", "josuke123@gmail.com");

        userDAO.post(testUser);
        userDAO.post(testUser1);

        userDAO.deleteAll();
        assertThrows(DataAccessException.class, () -> userDAO.get(testUser), "After clearing all users, trying to fetch one from the database should result in a DataAccessException");
    }


}
