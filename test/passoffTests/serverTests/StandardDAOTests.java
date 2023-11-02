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
        User testUser = new User("hermano", "Sanchez", "anchez123@gmail.com");
        userDAO.post(testUser);
    }
}
