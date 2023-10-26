package handlers;

import com.google.gson.Gson;
import requests.LoginRequest;
import requests.LogoutRequest;
import responses.LoginResponse;
import responses.LogoutResponse;
import services.AuthService;
import spark.Request;
import spark.Response;

public class AuthHandler {
    private static AuthService authService = new AuthService();
    public static String handleLogin(Request request, Response response) {
        Gson gson = new Gson();
        LoginRequest loginRequest = gson.fromJson(request.body(), LoginRequest.class);

        LoginResponse logoutResponse = authService.login(loginRequest);

        if (logoutResponse.getErrorMessage() != null) {
            switch (logoutResponse.getErrorMessage()) {
                case "Error: user doesn't exist" -> response.status(404);
                case "Error: invalid auth token" -> response.status(401);
                case "Error: an error occurred accessing, creating, deleting, or updating data" -> response.status(401);
                case "Error: unauthorized" -> response.status(401);
                case "Error: an internal server error has occurred" -> response.status(500);
            }
        }

        System.out.println(gson.toJson(logoutResponse));
        return gson.toJson(logoutResponse);
    }

    public static String handleLogout(Request request, Response response) {
        Gson gson = new Gson();
        String authToken = request.headers("Authorization");

        if (authToken == null || authToken.isEmpty()) {
            response.status(401);
            return gson.toJson(new LogoutResponse("Error: unauthorized"));
        }

        LogoutRequest logoutRequest = new LogoutRequest(authToken);

        LogoutResponse logoutResponse = authService.logout(logoutRequest);

        if (logoutResponse.getErrorMessage() != null) {
            switch (logoutResponse.getErrorMessage()) {
                case "Error: invalid auth token" -> response.status(401);
                case "Error: an internal server error has occurred" -> response.status(500);
            }
        }

        System.out.println(gson.toJson(logoutResponse));
        return gson.toJson(logoutResponse);
    }
}
