package passoffTests.serverTests;

import chess.ChessGame;
import chessImplementation.ChessGameImpl;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import models.AuthToken;
import models.Game;
import models.User;
import org.junit.jupiter.api.*;
import requests.*;
import responses.*;
import services.AuthService;
import services.GameService;
import services.TestingService;
import services.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceMethodTests {
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
    @DisplayName("Successful UserService register response")
    public void successRegister() {
        RegisterRequest registerRequest = new RegisterRequest("Billy", "Bob", "billybob@gmail.com");
        RegisterResponse registerResponse = userService.register(registerRequest);
        assertEquals(registerRequest.username(), registerResponse.getUsername(), "make sure username passed in is the same as username sent back as response");
        assertNotNull(registerResponse.getAuthToken(), "auth token was not null and was successfully returned");
    }

    @Test
    @DisplayName("Negative UserService register response")
    public void negativeRegister() {
        RegisterRequest registerRequest = new RegisterRequest("Jack", "Ripper", null);
        RegisterResponse registerResponse = userService.register(registerRequest);
        assertTrue(registerResponse.getMessage() != null && registerResponse.getMessage().startsWith("Error"),
                "register method on userService should return an error message due to the null email field");
        assertEquals(registerResponse.getMessage(), "Error: bad request", "auth token was not null and was successfully returned");
    }

    @Test
    @DisplayName("Successful AuthService login response")
    public void successLogin() throws DataAccessException {
        User testUser = new User("Josuke", "Hikaru", "mario@gmail.com");
        userDAO.post(testUser);

        LoginRequest loginRequest = new LoginRequest("Josuke", "Hikaru");
        LoginResponse loginResponse = authService.login(loginRequest);
        assertNull(loginResponse.getMessage(), "User should have been able to successfully login since their credentials are stored in the users table");
        assertNotNull(loginResponse.getAuthToken(), "make sure auth token is not null and an auth token was returned in response");
    }

    @Test
    @DisplayName("Negative AuthService login response")
    public void negativeLogin() throws DataAccessException {
        User testUser = new User("Mario", "Luigi", "mario@gmail.com");
        userDAO.post(testUser);

        LoginRequest loginRequest = new LoginRequest("Mario", "Peach");
        LoginResponse loginResponse = authService.login(loginRequest);
        assertNotNull(loginResponse.getMessage(), "passwords don't match, so an error should have been thrown");
        assertEquals(loginResponse.getMessage(),"Error: unauthorized" , "make sure correct error message is returned");
    }

    @Test
    @DisplayName("Successful AuthService logout response")
    public void successLogout() throws DataAccessException {
        AuthToken testAuthToken = new AuthToken("Mario", "123j4-ladf[ol-adjf");
        authDAO.post(testAuthToken);

        LogoutRequest logoutRequest = new LogoutRequest("123j4-ladf[ol-adjf");
        LogoutResponse logoutResponse = authService.logout(logoutRequest);
        assertNotEquals(logoutResponse.getMessage(), "Error: invalid auth token", "User shouldn't get this error message when attempting to logout");


        assertNull(authDAO.get(testAuthToken), "trying to access deleted auth token should result in a null response");
    }

    @Test
    @DisplayName("Negative AuthService logout response")
    public void negativeLogout() throws DataAccessException {
        AuthToken testAuthToken = new AuthToken("Mario", "123j4-ladf[oldafslkfj");
        authDAO.post(testAuthToken);

        LogoutRequest logoutRequest = new LogoutRequest("123j4-ladf[ol-adjf");
        LogoutResponse logoutResponse = authService.logout(logoutRequest);
        assertEquals(logoutResponse.getMessage(), "Error: invalid auth token", "User should get this error message when attempting to logout");
        assertNotNull(authDAO.get(testAuthToken), "should still be able to access stored auth token since it wasn't deleted");
    }

    @Test
    @DisplayName("Successful GameService create game response")
    public void successCreateGame() throws DataAccessException {
        AuthToken testAuthToken = new AuthToken("Mario", "123j4-ladf[ol-adjf");
        authDAO.post(testAuthToken);

        CreateGameRequest createGameRequest = new CreateGameRequest("mushroom kingdom", "123j4-ladf[ol-adjf");
        CreateGameResponse createGameResponse = gameService.createGame(createGameRequest);
        assertNull(createGameResponse.getMessage(), "no error message should be thrown");
    }

    @Test
    @DisplayName("Successful GameService create game response")
    public void negativeCreateGame() throws DataAccessException {
        AuthToken testAuthToken = new AuthToken("Mario", "123j4-ladf[ol-adjf");
        authDAO.post(testAuthToken);

        Game game = new Game(123, null, null, "mushroom kingdom", new ChessGameImpl());
        gameDAO.post(game);

        CreateGameRequest createGameRequest = new CreateGameRequest("mushroom kingdom", "123j4-ladf[ol-adjf");
        CreateGameResponse createGameResponse = gameService.createGame(createGameRequest);
        assertNotNull(createGameResponse.getMessage(), "error message should be thrown");
        assertSame(gameDAO.get(game), game, "original game is still in database");
    }

    @Test
    @DisplayName("Successful GameService join game response")
    public void successJoinGame() throws DataAccessException {
        AuthToken testAuthToken = new AuthToken("Mario", "123j4-ladf[ol-adjf");
        authDAO.post(testAuthToken);

        User testUser = new User("Mario", "Luigi", "mario@gmail.com");
        userDAO.post(testUser);

        Game game = new Game(123, null, null, "mushroom kingdom", new ChessGameImpl());
        gameDAO.post(game);

        Game updatedGame = new Game(123, "Mario", null, "mushroom kingdom", new ChessGameImpl());

        JoinGameRequest joinGameRequest = new JoinGameRequest(ChessGame.TeamColor.WHITE,123, testAuthToken.authToken());
        gameService.joinGame(joinGameRequest);
        assertEquals(gameDAO.get(game).whiteUsername(), updatedGame.whiteUsername(), "new game object should have user's username set to white player username");
    }

    @Test
    @DisplayName("Negative GameService join game response")
    public void negativeJoinGame() throws DataAccessException {
        AuthToken testAuthToken = new AuthToken("Mario", "123j4-ladf[ol-adjf");
        authDAO.post(testAuthToken);

        User testUser = new User("Mario", "Luigi", "mario@gmail.com");
        userDAO.post(testUser);

        Game game = new Game(123, "Luigi", null, "mushroom kingdom", new ChessGameImpl());
        gameDAO.post(game);

        JoinGameRequest joinGameRequest = new JoinGameRequest(ChessGame.TeamColor.WHITE,123, testAuthToken.authToken());
        JoinGameResponse joinGameResponse = gameService.joinGame(joinGameRequest);
        assertEquals(joinGameResponse.getMessage(), "Error: already taken", "error is thrown because white team player name is already taken");
    }

    @Test
    @DisplayName("Successful GameService list games response")
    public void successListGames() throws DataAccessException {
        AuthToken testAuthToken = new AuthToken("Mario", "123j4-ladf[ol-adjf");
        authDAO.post(testAuthToken);

        Game game = new Game(123, null, null, "mushroom kingdom", new ChessGameImpl());
        Game game2 = new Game(12345, null, null, "candy land", new ChessGameImpl());
        gameDAO.post(game);
        gameDAO.post(game2);

        List<Game> expectedList = gameDAO.getAll();

        ListGamesRequest listGamesRequest = new ListGamesRequest(testAuthToken.authToken());
        ListGamesResponse listGamesResponse = gameService.listGames(listGamesRequest);
        assertEquals(listGamesResponse.getGames(), expectedList, "response should include the games that are stored in memory");
    }

    @Test
    @DisplayName("Negative GameService list games response")
    public void negativeListGames() throws DataAccessException {
        AuthToken testAuthToken = new AuthToken("Mario", "123j4-ladf[ol-adjf");
        authDAO.post(testAuthToken);

        Game game = new Game(123, null, null, "mushroom kingdom", new ChessGameImpl());
        Game game2 = new Game(12345, null, null, "candy land", new ChessGameImpl());
        gameDAO.post(game);
        gameDAO.post(game2);

        ListGamesRequest listGamesRequest = new ListGamesRequest("adleqwurfdslkn");
        ListGamesResponse listGamesResponse = gameService.listGames(listGamesRequest);
        assertEquals(listGamesResponse.getMessage(), "Error: unauthorized", "should throw error due to invalid auth token");
    }

    @Test
    @DisplayName("Successful TestingService clear response")
    public void successClearData() throws DataAccessException {
        AuthToken testAuthToken = new AuthToken("Mario", "123j4-ladf[ol-adjf");
        authDAO.post(testAuthToken);

        Game game = new Game(123, null, null, "mushroom kingdom", new ChessGameImpl());
        Game game2 = new Game(12345, null, null, "candy land", new ChessGameImpl());
        gameDAO.post(game);
        gameDAO.post(game2);

        User user = new User("Mario", "Luigi", "mario@gmail.com");
        User user2 = new User("Luigi", "Mario", "luigi@gmail.com");
        userDAO.post(user);
        userDAO.post(user2);
        
        List<Game> emptyList = new ArrayList<>();

        testingService.clear();
        assertEquals(gameDAO.getAll(), emptyList, "No users should be left in the database");
    }

    @Test
    @DisplayName("Negative TestingService clear response")
    public void negativeClearData() throws DataAccessException {
        AuthToken testAuthToken = new AuthToken("Mario", "123j4-ladf[ol-adjf");
        authDAO.post(testAuthToken);

        Game game = new Game(123, null, null, "mushroom kingdom", new ChessGameImpl());
        Game game2 = new Game(12345, null, null, "candy land", new ChessGameImpl());
        gameDAO.post(game);
        gameDAO.post(game2);

        User user = new User("Mario", "Luigi", "mario@gmail.com");
        User user2 = new User("Luigi", "Mario", "luigi@gmail.com");
        userDAO.post(user);
        userDAO.post(user2);

        testingService.clear();

        AuthToken testAuthToken2 = new AuthToken("Mario", "123j4-ladf[ol-adjfadf");
        authDAO.post(testAuthToken2);
        assertNotNull(authDAO.get(testAuthToken2), "clearing database does not make it so you can't add more to database after clear");
    }

}
