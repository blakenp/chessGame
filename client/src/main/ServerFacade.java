import com.google.gson.Gson;
import requests.LoginRequest;
import requests.RegisterRequest;
import responses.RegisterResponse;

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
            responseBody = new Gson().fromJson(inputStreamReader, responseType);
        }
        return responseBody;
    }

    public static RegisterResponse handleClientRegister(RegisterRequest registerRequest) {
        try {
            String requestBody = new Gson().toJson(registerRequest);
            HttpURLConnection http = sendRequest(baseBackendUrl + "/user", "POST", requestBody);
            return readResponseBody(http, RegisterResponse.class);
        } catch (IOException | URISyntaxException exception) {
            exception.getStackTrace();
            return new RegisterResponse("Error: Invalid request or bad URI");
        }
    }

    public static RegisterResponse handleClientLogin(LoginRequest loginRequest) {
        try {
            String requestBody = new Gson().toJson(loginRequest);
            HttpURLConnection http = sendRequest(baseBackendUrl + "/session", "POST", requestBody);
            return readResponseBody(http, RegisterResponse.class);
        } catch (IOException | URISyntaxException exception) {
            exception.getStackTrace();
            return new RegisterResponse("Error: Invalid request or bad URI");
        }
    }
}