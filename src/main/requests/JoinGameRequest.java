package requests;

import chess.ChessGame;

/**
 * Object representation of the requests the client can make to join a chess game session.
 */
public class JoinGameRequest {
    private ChessGame.TeamColor playerColor;
    private int gameID;

    /**
     * JoinGameRequest object constructor that will set the player's team color and the gameID
     * upon instantiation of this object
     * @param playerColor The player's team color
     * @param gameID The game's ID
     */
    public JoinGameRequest(ChessGame.TeamColor playerColor, int gameID) {
        this.playerColor = playerColor;
        this.gameID = gameID;
    }

    /**
     * A getter method for this request's player's team color
     * @return The player's team color
     */
    public ChessGame.TeamColor getPlayerColor() {
        return playerColor;
    }

    /**
     * A getter method for this request's game ID
     * @return The game ID
     */
    public int getGameID() {
        return gameID;
    }
}
