package services;

import requests.CreateGameRequest;
import responses.CreateGameResponse;

/**
 * Object representation of service used to make an API call to create a new chess game to play
 */
public class CreateGameService {
    /**
     * A method that takes in a request from the client and parses it to create a new chess game session to play
     * @param request Object representation of the client's request to create a new chess game session to play
     * @return A successful CreateGameResponse or an error message due to errors that can occur with the request
     */
    public CreateGameResponse createGame(CreateGameRequest request) {
        return null;
    }
}
