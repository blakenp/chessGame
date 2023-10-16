package models;

import chess.ChessGame;

/**
 * Object representation of a game with an id that can be used as a room to host online chess games
 */
public class Game {
    /**
     * Data field representing a game's ID
     */
    private int gameID;
    /**
     * Data field representing a game's white team player's username
     */
    private String whiteUsername;
    /**
     * Data field representing a game's black team player's username
     */
    private String blackUsername;
    /**
     * Data field representing a game's name
     */
    private String gameName;
    /**
     * Data field representing the chess game of the Game object
     */
    private ChessGame game;

    /**
     * Game object constructor that will have a game ID and the usernames of the players playing, the name of the game,
     * and the object representing the chess game with its board itself
     *
     * @param gameID
     * @param whiteUsername
     * @param blackUsername
     * @param gameName
     * @param game
     */
    public Game(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game) {
        this.gameID = gameID;
        this.whiteUsername = whiteUsername;
        this.blackUsername = blackUsername;
        this.gameName = gameName;
        this.game = game;
    }

    /**
     * A getter method for the game's ID
     *
     * @return The game's ID
     */
    public int getGameID() {
        return gameID;
    }

    /**
     * A setter method for setting the game's ID
     *
     * @param gameID The game's new game ID
     */
    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    /**
     * A getter method for the username of the game's white team player
     *
     * @return The game's white team player name
     */
    public String getWhiteUsername() {
        return whiteUsername;
    }

    /**
     * A setter method for setting the username of the game's white team player
     *
     * @param whiteUsername The new username for the game's white team player
     */
    public void setWhiteUsername(String whiteUsername) {
        this.whiteUsername = whiteUsername;
    }

    /**
     * A getter method for the username of the game's black team
     *
     * @return The game's black team player name
     */
    public String getBlackUsername() {
        return blackUsername;
    }

    /**
     * A setter method for setting the username of the game's black team player
     *
     * @param blackUsername The new username for the game's black team player
     */
    public void setBlackUsername(String blackUsername) {
        this.blackUsername = blackUsername;
    }

    /**
     * A getter method for the game's name
     *
     * @return The game's name
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * A setter method for setting the game's name
     *
     * @param gameName The game's new name
     */
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    /**
     * A getter method for the game's chess game object that includes the board, rules, and pieces
     *
     * @return The game's chess game object
     */
    public ChessGame getGame() {
        return game;
    }

    /**
     * A setter method for setting the game's chess game object
     *
     * @param game The game's new chess game object
     */
    public void setGame(ChessGame game) {
        this.game = game;
    }
}
