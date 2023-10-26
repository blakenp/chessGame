package services;

import chessImplementation.ChessGameImpl;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import models.Game;
import requests.CreateGameRequest;
import requests.JoinGameRequest;
import responses.CreateGameResponse;
import responses.JoinGameResponse;
import responses.ListGamesResponse;

import java.util.Random;
import java.util.UUID;

/**
 * Object representation of the service used to make API calls to create games, join games, and get a list of all
 * the games in the game history of the database (includes games that were played already and those that are still in session)
 */
public class GameService {
    private GameDAO gameDAO = GameDAO.getInstance();
    /**
     * A method that takes in a request from the client and parses it to create a new chess game session to play
     * @param request Object representation of the client's request to create a new chess game session to play
     * @return A successful CreateGameResponse or an error message due to errors that can occur with the request
     */
    public CreateGameResponse createGame(CreateGameRequest request) {
        Random random = new Random();
        int randomGameID = random.nextInt(1000);

        Game newGame = new Game(randomGameID, null, null, request.getGameName(), new ChessGameImpl());

        try {
            if (request.getGameName() == null) {
                CreateGameResponse errorResponse = new CreateGameResponse("Error: bad request");
                errorResponse.setGameID(null);

                return errorResponse;
            }

            if (gameDAO.get(newGame) != null) {
                CreateGameResponse errorResponse = new CreateGameResponse("Error: already taken");
                errorResponse.setGameID(null);

                return errorResponse;
            }

            gameDAO.post(newGame);
        } catch (DataAccessException dataAccessException) {
            CreateGameResponse errorResponse = new CreateGameResponse("Error: an error occurred accessing, creating, deleting, or updating data");
            errorResponse.setGameID(null);

            return errorResponse;
        } catch (Exception exception) {
            CreateGameResponse errorResponse = new CreateGameResponse("Error: an internal server error has occurred");
            errorResponse.setGameID(null);

            return errorResponse;
        }
        return new CreateGameResponse(newGame.gameID());
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
