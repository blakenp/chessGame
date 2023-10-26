package handlers;

import com.google.gson.Gson;
import requests.RegisterRequest;
import responses.RegisterResponse;
import services.UserService;
import spark.Request;
import spark.Response;

public class UserHandler {

    private static UserService userService = new UserService();

    public static String handleRegisterUser(Request request, Response response) {
        Gson gson = new Gson();
        RegisterRequest registerRequest = gson.fromJson(request.body(), RegisterRequest.class);

        RegisterResponse registerResponse = userService.register(registerRequest);

        if (registerResponse.getErrorMessage() != null) {
            switch (registerResponse.getErrorMessage()) {
                case "Error: bad request" -> response.status(400);
                case "Error: already taken" -> response.status(403);
                case "Error: an error occurred accessing, creating, deleting, or updating data" -> response.status(500);
                case "Error: an internal server error has occurred" -> response.status(500);
            }
        }

        System.out.println(gson.toJson(registerResponse));
        return gson.toJson(registerResponse);
    }
}
