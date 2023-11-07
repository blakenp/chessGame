package handlers;

import com.google.gson.Gson;
import requests.RegisterRequest;
import responses.RegisterResponse;
import services.UserService;
import spark.Request;
import spark.Response;

/**
 * Object representation of http handler used to handle client's requests to register a new account in the chess
 * game server
 */
public class UserHandler {

    /**
     * service used to perform user creation related actions
     */
    private static UserService userService = new UserService();

    /**
     * Method that registers new users and creates an account for them
     *
     * @param request Client's request
     * @param response Server's response
     * @return Returns server's response to client's request to create new account in form of RegisterResponse object
     */
    public static String handleRegisterUser(Request request, Response response) {
        Gson gson = new Gson();
        RegisterRequest registerRequest = gson.fromJson(request.body(), RegisterRequest.class);

        RegisterResponse registerResponse = userService.register(registerRequest);

        if (registerResponse.getMessage() != null) {
            switch (registerResponse.getMessage()) {
                case "Error: bad request" -> response.status(400);
                case "Error: already taken" -> response.status(403);
                case "Error: an error occurred accessing, creating, deleting, or updating data" -> response.status(403);
                case "Error: an internal server error has occurred" -> response.status(500);
            }
        }

        return gson.toJson(registerResponse);
    }
}
