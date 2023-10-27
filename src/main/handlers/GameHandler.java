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

/**
 * Object representation of a http handler for handling requests to create, join, and fetch games in the database
 */
public class GameHandler {

    /**
     * service used to perform game related actions in the chess server
     */
    private static GameService gameService = new GameService();

    /**
     * Method that handles client's request to create a new chess game
     *
     * @param request Client's request
     * @param response Handlers response (either successful or negative)
     * @return Returns the server's response after handling the client's request in form of CreateGameResponse object
     */
    public static String handleCreateGame(Request request, Response response) {
        Gson gson = new Gson();
        String authToken = request.headers("Authorization");

        if (authToken == null || authToken.isEmpty()) {
            response.status(401);
            return gson.toJson(new CreateGameResponse("Error: unauthorized"));
        }

        CreateGameRequest createGameRequest = new CreateGameRequest(request.body(), authToken);

        CreateGameResponse createGameResponse = gameService.createGame(createGameRequest);

        if (createGameResponse.getMessage() != null) {
            switch (createGameResponse.getMessage()) {
                case "Error: bad request" -> response.status(400);
                case "Error: unauthorized" -> response.status(401);
                case "Error: already taken" -> response.status(403);
                case "Error: an internal server error has occurred" -> response.status(500);
            }
        }

        return gson.toJson(createGameResponse);
    }

    /**
     * Method for handling client's request to join a chess game
     *
     * @param request Client's request
     * @param response Server's response
     * @return Returns server's response to client's request in form of joinGameResponse object
     */
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

        if (joinGameResponse.getMessage() != null) {
            switch (joinGameResponse.getMessage()) {
                case "Error: bad request" -> response.status(400);
                case "Error: unauthorized" -> response.status(401);
                case "Error: already taken" -> response.status(403);
                case "Error: an error occurred accessing, creating, deleting, or updating data" -> response.status(500);
                case "Error: an internal server error has occurred" -> response.status(500);
            }
        }

        return gson.toJson(joinGameResponse);
    }

    /**
     * Method that handles client's request to fetch all chess games stored in database
     *
     * @param request Client's request
     * @param response Server's response
     * @return Returns the server's response after handling client's request in form of ListGamesResponse object
     */
    public static String handleListGames(Request request, Response response) {
        Gson gson = new Gson();
        String authToken = request.headers("Authorization");

        if (authToken == null || authToken.isEmpty()) {
            response.status(401);
            return gson.toJson(new ListGamesResponse("Error: unauthorized"));
        }

        ListGamesRequest listGamesRequest = new ListGamesRequest(authToken);

        ListGamesResponse listGamesResponse = gameService.listGames(listGamesRequest);

        if (listGamesResponse.getMessage() != null) {
            switch (listGamesResponse.getMessage()) {
                case "Error: bad request" -> response.status(400);
                case "Error: unauthorized" -> response.status(401);
                case "Error: already taken" -> response.status(403);
                case "Error: an error occurred accessing, creating, deleting, or updating data" -> response.status(500);
                case "Error: an internal server error has occurred" -> response.status(500);
            }
        }

        return gson.toJson(listGamesResponse);
    }
}
