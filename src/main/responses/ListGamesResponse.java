package responses;

import models.Game;

/**
 * Object representation of the response you can get when requesting a list of all the chess games
 * in the database.
 */
public class ListGamesResponse {
    /**
     * Data field representing a list of chess games
     */
    private Game[] games;
    /**
     * Data field representing an error message
     */
    private String message;

    /**
     * ListGamesResponse object constructor that can be instantiated by passing in a list of all the games
     * that are in the database and if an error happens, an error message that can be set
     *
     * @param games A list of games in the database
     * @param message The error message in case the response returns an error
     */
    public ListGamesResponse(Game[] games, String message) {
        this.games = games;
        this.message = message;
    }

    /**
     * A getter method for the list of games in the response
     *
     * @return The list of games from the database
     */
    public Game[] getGames() {
        return games;
    }

    /**
     * A getter method for the error message
     *
     * @return The error message
     */
    public String getMessage() {
        return message;
    }
}
