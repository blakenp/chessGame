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
     * A setter method for setting this request's player's team color
     * @param playerColor The new player's team color that this request's playerColor will be set to
     */
    public void setPlayerColor(ChessGame.TeamColor playerColor) {
        this.playerColor = playerColor;
    }

    /**
     * A getter method for this request's game ID
     * @return The game ID
     */
    public int getGameID() {
        return gameID;
    }

    /**
     * A setter method for setting this request's game ID
     * @param gameID The new game ID that this request's gameID will be set to
     */
    public void setGameID(int gameID) {
        this.gameID = gameID;
    }
}
