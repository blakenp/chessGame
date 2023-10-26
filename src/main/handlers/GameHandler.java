package handlers;

import com.google.gson.Gson;
import dataAccess.AuthDAO;
import requests.CreateGameRequest;
import requests.JoinGameRequest;
import requests.ListGamesRequest;
import responses.CreateGameResponse;
import responses.JoinGameResponse;
import responses.ListGamesResponse;
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

        JoinGameRequest requestBody = gson.fromJson(request.body(), JoinGameRequest.class);

        JoinGameRequest joinGameRequest = new JoinGameRequest(requestBody.playerColor(), requestBody.gameID(), authToken);

        JoinGameResponse joinGameResponse = gameService.joinGame(joinGameRequest);

        if (joinGameResponse.getErrorMessage() != null) {
            switch (joinGameResponse.getErrorMessage()) {
                case "Error: bad request" -> response.status(400);
                case "Error: unauthorized" -> response.status(401);
                case "Error: already taken" -> response.status(403);
                case "Error: an error occurred accessing, creating, deleting, or updating data" -> response.status(500);
                case "Error: an internal server error has occurred" -> response.status(500);
            }
        }

        System.out.println(gson.toJson(joinGameResponse));
        return gson.toJson(joinGameResponse);
    }

    public static String handleListGames(Request request, Response response) {
        Gson gson = new Gson();
        String authToken = request.headers("Authorization");

        if (authToken == null || authToken.isEmpty()) {
            response.status(401);
            return gson.toJson(new ListGamesResponse("Error: unauthorized"));
        }

        ListGamesRequest listGamesRequest = new ListGamesRequest(authToken);

        ListGamesResponse listGamesResponse = gameService.listGames(listGamesRequest);

        if (listGamesResponse.getErrorMessage() != null) {
            switch (listGamesResponse.getErrorMessage()) {
                case "Error: bad request" -> response.status(400);
                case "Error: unauthorized" -> response.status(401);
                case "Error: already taken" -> response.status(403);
                case "Error: an error occurred accessing, creating, deleting, or updating data" -> response.status(500);
                case "Error: an internal server error has occurred" -> response.status(500);
            }
        }

        System.out.println(gson.toJson(listGamesResponse));
        return gson.toJson(listGamesResponse);
    }
}
