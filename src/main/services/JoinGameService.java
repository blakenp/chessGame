package services;

import requests.JoinGameRequest;
import responses.JoinGameResponse;

/**
 * Object representation of the service used to make API calls to help a client join an online chess game
 */
public class JoinGameService {
    /**
     * A method that takes in a request from the client to attempt to join an online chess game and play
     * @param request An object representation of the client's request to join a game
     * @return The response that can either successfully allow the client to join a chess game or result in an error when attempting to join
     */
    public JoinGameResponse joinGame(JoinGameRequest request) {
        return null;
    }
}
