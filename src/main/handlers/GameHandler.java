package handlers;

import com.google.gson.Gson;
import dataAccess.AuthDAO;
import requests.CreateGameRequest;
import requests.JoinGameRequest;
import responses.CreateGameResponse;
import services.GameService;
import spark.Request;
import spark.Response;

public class GameHandler {
    private static AuthDAO authDAO = AuthDAO.getInstance();
    private static GameService gameService = new GameService();

    public static String handleCreateGame(Request request, Response response) {
        Gson gson = new Gson();
        String authToken = request.headers("Authorization");

        if (authToken == null || authToken.isEmpty()) {
            response.status(401);
            return gson.toJson(new CreateGameResponse("Error: unauthorized"));
        }

        CreateGameRequest createGameRequest = new CreateGameRequest(request.body(), authToken);

        CreateGameResponse createGameResponse = gameService.createGame(createGameRequest);

        if (createGameResponse.getErrorMessage() != null) {
            switch (createGameResponse.getErrorMessage()) {
                case "Error: bad request" -> response.status(400);
                case "Error: unauthorized" -> response.status(401);
                case "Error: already taken" -> response.status(403);
                case "Error: an internal server error has occurred" -> response.status(500);
            }
        }

        System.out.println(gson.toJson(createGameResponse));
        return gson.toJson(createGameResponse);
    }

    public static String handleJoinGame(Request request, Response response) {
        Gson gson = new Gson();
        String authToken = request.headers("Authorization");

        if (authToken == null || authToken.isEmpty()) {
            response.status(401);
            return gson.toJson(new CreateGameResponse("Error: unauthorized"));
        }

        CreateGameRequest createGameRequest = new CreateGameRequest(request.body(), authToken);

        CreateGameResponse createGameResponse = gameService.createGame(createGameRequest);

        if (createGameResponse.getErrorMessage() != null) {
            switch (createGameResponse.getErrorMessage()) {
                case "Error: bad request" -> response.status(400);
                case "Error: already taken" -> response.status(403);
                case "Error: an internal server error has occurred" -> response.status(500);
            }
        }

        System.out.println(gson.toJson(createGameResponse));
        return gson.toJson(createGameResponse);
    }

    public static String handleListGames(Request request, Response response) {
        Gson gson = new Gson();
        String authToken = request.headers("Authorization");

        if (authToken == null || authToken.isEmpty()) {
            response.status(401);
            return gson.toJson(new CreateGameResponse("Error: unauthorized"));
        }

        CreateGameRequest createGameRequest = new CreateGameRequest(request.body(), authToken);

        CreateGameResponse createGameResponse = gameService.createGame(createGameRequest);

        if (createGameResponse.getErrorMessage() != null) {
            switch (createGameResponse.getErrorMessage()) {
                case "Error: bad request" -> response.status(400);
                case "Error: already taken" -> response.status(403);
                case "Error: an internal server error has occurred" -> response.status(500);
            }
        }

        System.out.println(gson.toJson(createGameResponse));
        return gson.toJson(createGameResponse);
    }
}
