package services;

import requests.CreateGameRequest;
import requests.JoinGameRequest;
import responses.CreateGameResponse;
import responses.JoinGameResponse;
import responses.ListGamesResponse;

/**
 * Object representation of the service used to make API calls to create games, join games, and get a list of all
 * the games in the game history of the database (includes games that were played already and those that are still in session)
 */
public class GameService {
    /**
     * A method that takes in a request from the client and parses it to create a new chess game session to play
     * @param request Object representation of the client's request to create a new chess game session to play
     * @return A successful CreateGameResponse or an error message due to errors that can occur with the request
     */
    public CreateGameResponse createGame(CreateGameRequest request) {
        return null;
    }

    /**
     * A method that takes in a request from the client to attempt to join an online chess game and play
     * @param request An object representation of the client's request to join a game
     * @return The response that can either successfully allow the client to join a chess game or result in an error when attempting to join
     */
    public JoinGameResponse joinGame(JoinGameRequest request) {
        return null;
    }

    /**
     * A method that accesses and grabs the full list of chess games that are currently in session and those that have ended
     * @return A list of all the chess games in the game history of the database
     */
    public ListGamesResponse listGames() {
        return null;
    }
}
