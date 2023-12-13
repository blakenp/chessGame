package services;

import chess.ChessGame;
import chessImplementation.ChessGameImpl;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import models.AuthToken;
import models.Game;
import models.User;
import requests.CreateGameRequest;
import requests.JoinGameRequest;
import requests.ListGamesRequest;
import responses.CreateGameResponse;
import responses.JoinGameResponse;
import responses.ListGamesResponse;

import java.util.List;
import java.util.Random;

/**
 * Object representation of the service used to make API calls to create games, join games, and get a list of all
 * the games in the game history of the database (includes games that were played already and those that are still in session)
 */
public class GameService {
    private GameDAO gameDAO = GameDAO.getInstance();
    private AuthDAO authDAO = AuthDAO.getInstance();
    private UserDAO userDAO = UserDAO.getInstance();
    /**
     * A method that takes in a request from the client and parses it to create a new chess game session to play
     * @param request Object representation of the client's request to create a new chess game session to play
     * @return A successful CreateGameResponse or an error message due to errors that can occur with the request
     */
    public CreateGameResponse createGame(CreateGameRequest request) {
        AuthToken authToken = new AuthToken(null, request.authToken());
        Random random = new Random();
        int randomGameID = random.nextInt(1000);

        Game newGame = new Game(randomGameID, null, null, request.gameName(), new ChessGameImpl(), false);

        try {
            // if the client didn't send a game name in the request, throw an error
            if (request.gameName() == null) {
                return new CreateGameResponse("Error: bad request");
            }

            // if the auth token the client sent to the server is not already stored in the server, throw an error
            try {
                authDAO.get(authToken);
            } catch (DataAccessException dataAccessException) {
                return new CreateGameResponse("Error: unauthorized");
            }

            gameDAO.post(newGame);
        } catch (DataAccessException dataAccessException) {
            return new CreateGameResponse("Error: an error occurred accessing, creating, deleting, or updating data");
        } catch (Exception exception) {
            return new CreateGameResponse("Error: an internal server error has occurred");
        }
        return new CreateGameResponse(newGame.gameID());
    }

    /**
     * A method that takes in a request from the client to attempt to join an online chess game and play
     * @param request An object representation of the client's request to join a game
     * @return The response that can either successfully allow the client to join a chess game or result in an error when attempting to join
     */
    public JoinGameResponse joinGame(JoinGameRequest request) {
        AuthToken authToken = new AuthToken(null, request.authToken());

        try {
            try {
                authToken = authDAO.get(authToken);
            } catch (DataAccessException dataAccessException) {
                return new JoinGameResponse("Error: unauthorized");
            }

            User user = new User(authToken.username(), null, null);
            user = userDAO.get(user);
            Game storedGame = new Game(request.gameID(), null, null, null, new ChessGameImpl(), false);

            try {
                storedGame = gameDAO.get(storedGame);
            } catch (DataAccessException dataAccessException) {
                return new JoinGameResponse("Error: bad request");
            }

            Game updatedGame;

            // just join as an observer if no team color was specified
            if (request.playerColor() == null) {
                return new JoinGameResponse("Successful Join as Observer");
            }

            // join as white or black team player if those options are still not taken. Else, throw an error
            if (request.playerColor() == ChessGame.TeamColor.WHITE && storedGame.whiteUsername() == null) {
                updatedGame = new Game(storedGame.gameID(), user.username(), storedGame.blackUsername(), storedGame.gameName(), storedGame.chessGame(), storedGame.isFinished());
            }
            else if (request.playerColor() == ChessGame.TeamColor.BLACK && storedGame.blackUsername() == null) {
                updatedGame = new Game(storedGame.gameID(), storedGame.whiteUsername(), user.username(), storedGame.gameName(), storedGame.chessGame(), storedGame.isFinished());
            } else {
                return new JoinGameResponse("Error: already taken");
            }

            gameDAO.put(updatedGame);
        } catch (DataAccessException dataAccessException) {
            return new JoinGameResponse("Error: an error occurred accessing, creating, deleting, or updating data");
        } catch (Exception exception) {
            return new JoinGameResponse("Error: an internal server error has occurred");
        }
        return new JoinGameResponse("Successful Join");
    }

    /**
     * A method that accesses and grabs the full list of chess games that are currently in session and those that have ended
     * @return A list of all the chess games in the game history of the database
     */
    public ListGamesResponse listGames(ListGamesRequest request) {
        AuthToken authToken = new AuthToken(null, request.authToken());
        List<Game> games;

        try {
            try {
                authDAO.get(authToken);
            } catch (DataAccessException dataAccessException) {
                return new ListGamesResponse("Error: unauthorized");
            }

            games = gameDAO.getAll();
        } catch (DataAccessException dataAccessException) {
            return new ListGamesResponse("Error: an error occurred accessing, creating, deleting, or updating data");
        } catch (Exception exception) {
            return new ListGamesResponse("Error: an internal server error has occurred");
        }
        return new ListGamesResponse(games);
    }
}
