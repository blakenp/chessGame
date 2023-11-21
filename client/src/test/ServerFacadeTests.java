import chess.ChessGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import requests.*;
import responses.CreateGameResponse;
import responses.RegisterResponse;

import static org.junit.jupiter.api.Assertions.*;

public class ServerFacadeTests {

    @BeforeEach
    public void setup() {
        ServerFacade.handleClientClearDB();
    }

    @DisplayName("Successful ServerFacade Register Client")
    @Test
    public void successRegisterClient() {
        // Assuming your ServerFacade has a method to register a user
        RegisterRequest registerRequest = new RegisterRequest("Mario", "luigi", "mushrooms");
        assertEquals(ServerFacade.handleClientRegister(registerRequest).getUsername(), "Mario", "Registering a client's account should result in returning the username the client registered");
    }

    @DisplayName("Negative ServerFacade Register Client")
    @Test
    public void negativeRegisterClient() {
        RegisterRequest registerRequest1 = new RegisterRequest("Mario", "luigi", "mushrooms");
        RegisterRequest registerRequest2 = new RegisterRequest("Mario", "Peach", "mushrooms");
        ServerFacade.handleClientRegister(registerRequest1);
        assertEquals(ServerFacade.handleClientRegister(registerRequest2).getMessage(), "Error: Server error or username already taken", "An error should be thrown indicating that username is already taken");
    }

    @DisplayName("Successful ServerFacade Login Client")
    @Test
    public void successLoginClient() {
        RegisterRequest registerRequest = new RegisterRequest("Mario", "luigi", "mushrooms");
        RegisterResponse registerResponse = ServerFacade.handleClientRegister(registerRequest);

        LogoutRequest logoutRequest = new LogoutRequest(registerResponse.getAuthToken());
        ServerFacade.handleClientLogout(logoutRequest);

        LoginRequest loginRequest = new LoginRequest("Mario", "luigi");
        assertNotNull(ServerFacade.handleClientLogin(loginRequest).getAuthToken(), "An auth token should be returned when the client logs in");
    }

    @DisplayName("Negative ServerFacade Login Client")
    @Test
    public void negativeLoginClient() {
        RegisterRequest registerRequest = new RegisterRequest("Mario", "luigi", "mushrooms");
        RegisterResponse registerResponse = ServerFacade.handleClientRegister(registerRequest);

        LogoutRequest logoutRequest = new LogoutRequest(registerResponse.getAuthToken());
        ServerFacade.handleClientLogout(logoutRequest);

        LoginRequest loginRequest = new LoginRequest("Mario", "luggy");
        assertEquals(ServerFacade.handleClientLogin(loginRequest).getMessage(), "Error: Server error or unauthorized", "An error should be thrown due to an incorrect client password");
    }

    @DisplayName("Successful ServerFacade Logout Client")
    @Test
    public void successLogoutClient() {
        RegisterRequest registerRequest = new RegisterRequest("Mario", "luigi", "mushrooms");
        RegisterResponse registerResponse = ServerFacade.handleClientRegister(registerRequest);

        LogoutRequest logoutRequest = new LogoutRequest(registerResponse.getAuthToken());
        assertEquals(ServerFacade.handleClientLogout(logoutRequest).getMessage(), "Successful Logout", "The client should be successfully logged out");
    }

    @DisplayName("Negative ServerFacade Logout Client")
    @Test
    public void negativeLogoutClient() {
        RegisterRequest registerRequest = new RegisterRequest("Mario", "luigi", "mushrooms");
        RegisterResponse registerResponse = ServerFacade.handleClientRegister(registerRequest);

        LogoutRequest logoutRequest = new LogoutRequest("Howdy");
        assertEquals(ServerFacade.handleClientLogout(logoutRequest).getMessage(), "Error: Server error or unauthorized", "Passing in invalid auth token results in error for client logout");
    }

    @DisplayName("Successful ServerFacade Create Game Client")
    @Test
    public void successCreateGameClient() {
        RegisterRequest registerRequest = new RegisterRequest("Mario", "luigi", "mushrooms");
        RegisterResponse registerResponse = ServerFacade.handleClientRegister(registerRequest);

        CreateGameRequest createGameRequest = new CreateGameRequest("Mushroom Kingdom Warfare", registerResponse.getAuthToken());
        assertNotEquals(ServerFacade.handleClientCreateGame(createGameRequest).getMessage(), "Error: Server error, unauthorized, or game with specified game name already exists", "No error should be thrown and client creates game");
    }

    @DisplayName("Negative ServerFacade Create Game Client")
    @Test
    public void negativeCreateGameClient() {
        RegisterRequest registerRequest = new RegisterRequest("Mario", "luigi", "mushrooms");
        RegisterResponse registerResponse = ServerFacade.handleClientRegister(registerRequest);

        CreateGameRequest createGameRequest1 = new CreateGameRequest("Mushroom Kingdom Warfare", registerResponse.getAuthToken());
        CreateGameRequest createGameRequest2 = new CreateGameRequest("Mushroom Kingdom Warfare", registerResponse.getAuthToken());

        ServerFacade.handleClientCreateGame(createGameRequest1);
        assertEquals(ServerFacade.handleClientCreateGame(createGameRequest2).getMessage(), "Error: Server error, unauthorized, or game with specified game name already exists", "Error is thrown due to duplicate game name from client requests");
    }

    @DisplayName("Successful ServerFacade Join Game Client")
    @Test
    public void successJoinGameClient() {
        RegisterRequest registerRequest = new RegisterRequest("Mario", "luigi", "mushrooms");
        RegisterResponse registerResponse = ServerFacade.handleClientRegister(registerRequest);

        CreateGameRequest createGameRequest = new CreateGameRequest("Mushroom Kingdom Warfare", registerResponse.getAuthToken());
        CreateGameResponse createGameResponse = ServerFacade.handleClientCreateGame(createGameRequest);

        JoinGameRequest joinGameRequest = new JoinGameRequest(ChessGame.TeamColor.WHITE, createGameResponse.getGameID(), registerResponse.getAuthToken());
        assertEquals(ServerFacade.handleClientJoinGame(joinGameRequest).getMessage(), "Successful Join", "Client should successfully join game");

        ListGamesRequest listGamesRequest = new ListGamesRequest(registerResponse.getAuthToken());
        assertEquals(ServerFacade.handleClientListGames(listGamesRequest).getGames().get(0).whiteUsername(), "Mario", "white username should be client's username now that they joined the game as White Team Player");
    }

    @DisplayName("Negative ServerFacade Join Game Client")
    @Test
    public void negativeJoinGameClient() {
        RegisterRequest registerRequest1 = new RegisterRequest("Mario", "luigi", "mushrooms");
        RegisterResponse registerResponse1 = ServerFacade.handleClientRegister(registerRequest1);

        RegisterRequest registerRequest2 = new RegisterRequest("Toad", "Mario", "blocks");
        RegisterResponse registerResponse2 = ServerFacade.handleClientRegister(registerRequest2);

        CreateGameRequest createGameRequest = new CreateGameRequest("Mushroom Kingdom Warfare", registerResponse1.getAuthToken());
        CreateGameResponse createGameResponse = ServerFacade.handleClientCreateGame(createGameRequest);

        JoinGameRequest joinGameRequest1 = new JoinGameRequest(ChessGame.TeamColor.WHITE, createGameResponse.getGameID(), registerResponse1.getAuthToken());
        ServerFacade.handleClientJoinGame(joinGameRequest1);

        JoinGameRequest joinGameRequest2 = new JoinGameRequest(ChessGame.TeamColor.WHITE, createGameResponse.getGameID(), registerResponse2.getAuthToken());
        assertEquals(ServerFacade.handleClientJoinGame(joinGameRequest2).getMessage(), "Error: Server error, unauthorized, or game already has a player for the specified team", "Client tried to join as White Team Player when White Team was already taken by another client");

        ListGamesRequest listGamesRequest = new ListGamesRequest(registerResponse1.getAuthToken());
        assertEquals(ServerFacade.handleClientListGames(listGamesRequest).getGames().get(0).whiteUsername(), "Mario", "white username should be the first client's username");
    }

    @DisplayName("Successful ServerFacade List Games Client")
    @Test
    public void successListGamesClient() {
        RegisterRequest registerRequest = new RegisterRequest("Mario", "luigi", "mushrooms");
        RegisterResponse registerResponse = ServerFacade.handleClientRegister(registerRequest);

        CreateGameRequest createGameRequest1 = new CreateGameRequest("Mushroom Kingdom Warfare", registerResponse.getAuthToken());
        CreateGameResponse createGameResponse = ServerFacade.handleClientCreateGame(createGameRequest1);

        CreateGameRequest createGameRequest2 = new CreateGameRequest("Bowser's Castle", registerResponse.getAuthToken());
        ServerFacade.handleClientCreateGame(createGameRequest2);

        JoinGameRequest joinGameRequest = new JoinGameRequest(ChessGame.TeamColor.WHITE, createGameResponse.getGameID(), registerResponse.getAuthToken());
        assertEquals(ServerFacade.handleClientJoinGame(joinGameRequest).getMessage(), "Successful Join", "Client should successfully join game");

        ListGamesRequest listGamesRequest = new ListGamesRequest(registerResponse.getAuthToken());
        assertEquals(ServerFacade.handleClientListGames(listGamesRequest).getGames().get(0).whiteUsername(), "Mario", "white username should be client's username now that they joined the game as White Team Player");
        assertEquals(ServerFacade.handleClientListGames(listGamesRequest).getGames().size(), 2, "2 games should be in database now");
    }

    @DisplayName("Negative ServerFacade List Games Client")
    @Test
    public void negativeListGamesClient() {
        RegisterRequest registerRequest = new RegisterRequest("Mario", "luigi", "mushrooms");
        RegisterResponse registerResponse = ServerFacade.handleClientRegister(registerRequest);

        CreateGameRequest createGameRequest1 = new CreateGameRequest("Mushroom Kingdom Warfare", registerResponse.getAuthToken());
        CreateGameResponse createGameResponse = ServerFacade.handleClientCreateGame(createGameRequest1);

        CreateGameRequest createGameRequest2 = new CreateGameRequest("Bowser's Castle", registerResponse.getAuthToken());
        ServerFacade.handleClientCreateGame(createGameRequest2);

        JoinGameRequest joinGameRequest = new JoinGameRequest(ChessGame.TeamColor.WHITE, createGameResponse.getGameID(), registerResponse.getAuthToken());
        assertEquals(ServerFacade.handleClientJoinGame(joinGameRequest).getMessage(), "Successful Join", "Client should successfully join game");

        ListGamesRequest listGamesRequest = new ListGamesRequest("Yay");
        assertEquals(ServerFacade.handleClientListGames(listGamesRequest).getMessage(), "Error: Server error or unauthorized", "Invalid auth token in header results in an error message for the client");
    }

    // The clear method is inaccessible to the client, however I wrote a test either way because I use this method before each test
    // to make sure the DB is cleared before running other tests
    @DisplayName("Successful ServerFacade Clear DB Client")
    @Test
    public void successClearDBClient() {
        RegisterRequest registerRequest = new RegisterRequest("Mario", "luigi", "mushrooms");
        RegisterResponse registerResponse = ServerFacade.handleClientRegister(registerRequest);

        CreateGameRequest createGameRequest1 = new CreateGameRequest("Mushroom Kingdom Warfare", registerResponse.getAuthToken());
        CreateGameResponse createGameResponse = ServerFacade.handleClientCreateGame(createGameRequest1);

        CreateGameRequest createGameRequest2 = new CreateGameRequest("Bowser's Castle", registerResponse.getAuthToken());
        ServerFacade.handleClientCreateGame(createGameRequest2);

        JoinGameRequest joinGameRequest = new JoinGameRequest(ChessGame.TeamColor.WHITE, createGameResponse.getGameID(), registerResponse.getAuthToken());
        assertEquals(ServerFacade.handleClientJoinGame(joinGameRequest).getMessage(), "Successful Join", "Client should successfully join game");

        ServerFacade.handleClientClearDB();

        ListGamesRequest listGamesRequest = new ListGamesRequest(registerResponse.getAuthToken());
        assertNull(ServerFacade.handleClientListGames(listGamesRequest).getGames(), "The only GET method the Server has should return nothing since the DB was cleared");
    }
}
