package handlers;

import com.google.gson.Gson;
import requests.LoginRequest;
import requests.LogoutRequest;
import responses.LoginResponse;
import responses.LogoutResponse;
import services.AuthService;
import spark.Request;
import spark.Response;

/**
 * Object representation of handler used for anything related to the auth service. Allows users to login and logout
 * with the help of the service classes
 */
public class AuthHandler {
    /**
     * services used to perform authentication related actions
     */
    private static AuthService authService = new AuthService();

    /**
     * Method for handling client requests to log into the chess game server. Can return a success status code
     * or certain error codes.
     *
     * @param request Client's request to login
     * @param response Handler's response that can either be a successful response or a negative response with an error message
     * @return the response after handling the client's request in form of LoginResponse object
     */
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

    /**
     * Method that handles client's requests to log out of the chess game server
     *
     * @param request The client's request
     * @param response The response returned by the server
     * @return This method returns the server's response after handling the client's request in form of LogoutResponse object
     */

    public static String handleLogout(Request request, Response response) {
        Gson gson = new Gson();
        String authToken = request.headers("Authorization");

        // if no auth token is found in the authorization header, then return an error response
        if (authToken == null || authToken.isEmpty()) {
            response.status(401);
            return gson.toJson(new LogoutResponse("Error: unauthorized"));
        }

        LogoutRequest logoutRequest = new LogoutRequest(authToken);

        LogoutResponse logoutResponse = authService.logout(logoutRequest);

        if (logoutResponse.getErrorMessage() != null) {
            switch (logoutResponse.getErrorMessage()) {
                case "Error: invalid auth token" -> response.status(401);
                case "Error: an error occurred accessing, creating, deleting, or updating data" -> response.status(500);
                case "Error: an internal server error has occurred" -> response.status(500);
            }
        }

        System.out.println(gson.toJson(logoutResponse));
        return gson.toJson(logoutResponse);
    }
}
