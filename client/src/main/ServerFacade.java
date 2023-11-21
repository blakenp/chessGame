import com.google.gson.Gson;
import requests.*;
import responses.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;

public class ServerFacade {
    private static String baseBackendUrl = "http://localhost:8080";

    private static HttpURLConnection sendRequest(String url, String method, String body) throws URISyntaxException, IOException {
        URI uri = new URI(url);
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod(method);
        writeRequestBody(body, http);
        http.connect();
        System.out.printf("= Request =========\n[%s] %s\n\n%s\n\n", method, url, body);
        return http;
    }

    private static HttpURLConnection sendAuthRequest(String url, String method, String body, String authToken) throws URISyntaxException, IOException {
        URI uri = new URI(url);
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod(method);
        http.setRequestProperty("Authorization", authToken);
        writeRequestBody(body, http);
        http.connect();
        System.out.printf("= Request =========\n[%s] %s\n\n%s\n\n", method, url, body);
        return http;
    }

    private static void writeRequestBody(String body, HttpURLConnection http) throws IOException {
        if (!body.isEmpty()) {
            http.setDoOutput(true);
            try (var outputStream = http.getOutputStream()) {
                outputStream.write(body.getBytes());
            }
        }
    }

    private static <T> T readResponseBody(HttpURLConnection http, Class<T> responseType) throws IOException {
        T responseBody = null;
        try (InputStream respBody = http.getInputStream()) {
            InputStreamReader inputStreamReader = new InputStreamReader(respBody);
            System.out.println("here");
            responseBody = new Gson().fromJson(inputStreamReader, responseType);
        }
        System.out.println("success return");
        return responseBody;
    }

    public static RegisterResponse handleClientRegister(RegisterRequest registerRequest) {
        try {
            String requestBody = new Gson().toJson(registerRequest);
            HttpURLConnection http = sendRequest(baseBackendUrl + "/user", "POST", requestBody);
            return readResponseBody(http, RegisterResponse.class);
        } catch (IOException | URISyntaxException exception) {
            exception.getStackTrace();
            return new RegisterResponse("Error: Server error or username already taken");
        }
    }

    public static LoginResponse handleClientLogin(LoginRequest loginRequest) {
        try {
            String requestBody = new Gson().toJson(loginRequest);
            HttpURLConnection http = sendRequest(baseBackendUrl + "/session", "POST", requestBody);
            return readResponseBody(http, LoginResponse.class);
        } catch (IOException | URISyntaxException exception) {
            exception.getStackTrace();
            return new LoginResponse("Error: Server error or unauthorized");
        }
    }

    public static LogoutResponse handleClientLogout(LogoutRequest logoutRequest) {
        try {
            String requestBody = new Gson().toJson(logoutRequest);
            HttpURLConnection http = sendAuthRequest(baseBackendUrl + "/session", "DELETE", requestBody, logoutRequest.authToken());
            return readResponseBody(http, LogoutResponse.class);
        } catch (IOException | URISyntaxException exception) {
            exception.getStackTrace();
            return new LogoutResponse("Error: Server error or unauthorized");
        }
    }

    public static CreateGameResponse handleClientCreateGame(CreateGameRequest createGameRequest) {
        try {
            String requestBody = new Gson().toJson(createGameRequest);
            HttpURLConnection http = sendAuthRequest(baseBackendUrl + "/game", "POST", requestBody, createGameRequest.authToken());
            return readResponseBody(http, CreateGameResponse.class);
        } catch (IOException | URISyntaxException exception) {
            exception.getStackTrace();
            return new CreateGameResponse("Error: Server error, unauthorized, or game with specified game name already exists");
        }
    }

    public static JoinGameResponse handleClientJoinGame(JoinGameRequest joinGameRequest) {
        try {
            String requestBody = new Gson().toJson(joinGameRequest);
            HttpURLConnection http = sendAuthRequest(baseBackendUrl + "/game", "PUT", requestBody, joinGameRequest.authToken());
            return readResponseBody(http, JoinGameResponse.class);
        } catch (IOException | URISyntaxException exception) {
            exception.getStackTrace();
            return new JoinGameResponse("Error: Server error, unauthorized, or game already has a player for the specified team");
        }
    }

    public static ListGamesResponse handleClientListGames(ListGamesRequest listGamesRequest) {
        try {
            HttpURLConnection http = sendAuthRequest(baseBackendUrl + "/game", "GET", "", listGamesRequest.authToken());
            return readResponseBody(http, ListGamesResponse.class);
        } catch (IOException | URISyntaxException exception) {
            System.out.println("what?");
            exception.getStackTrace();
            return new ListGamesResponse("Error: Server error or unauthorized");
        }
    }
}
