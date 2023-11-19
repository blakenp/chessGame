package responses;

import models.Game;

import java.util.List;

/**
 * Object representation of the response you can get when requesting a list of all the chess games
 * in the database.
 */
public class ListGamesResponse extends ResponseMessage {
    /**
     * Data field representing a list of chess games
     */
    private List<Game> games;

    /**
     * ListGamesResponse object constructor that can be instantiated by passing in a list of all the games
     * that are in the database and if an error happens, an error message that can be set
     *
     * @param games A list of games in the database
     */
    public ListGamesResponse(List<Game> games) {
        super(null);
        this.games = games;
    }

    public ListGamesResponse(String message) {
        super(message);
    }

    /**
     * A getter method for the list of games in the response
     *
     * @return The list of games from the database
     */
    public List<Game> getGames() {
        return games;
    }
}
