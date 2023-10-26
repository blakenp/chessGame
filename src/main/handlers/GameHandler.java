package handlers;

import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import models.AuthToken;
import requests.CreateGameRequest;
import requests.JoinGameRequest;
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
        AuthToken authToken = new AuthToken(null, request.headers("Authorization"));

        if (authToken.authToken() == null || authToken.authToken().isEmpty()) {
            response.status(401);
            return gson.toJson(new CreateGameResponse("Error: unauthorized"));
        }

        try {
            if (authDAO.get(authToken) == null) {
                response.status(401);
                return gson.toJson(new CreateGameResponse("Error: unauthorized"));
            }
        } catch (DataAccessException dataAccessException) {
            response.status(401);
            return gson.toJson(new CreateGameResponse("Error: unauthorized"));
        }

        CreateGameRequest createGameRequest = gson.fromJson(request.body(), CreateGameRequest.class);

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

    public static String handleJoinGame(JoinGameRequest req, JoinGameResponse res) {
        return null;
    }

    public static String handleListGames(ListGamesResponse res) {
        return null;
    }
}
