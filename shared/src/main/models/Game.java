package models;

import chess.ChessGame;

/**
 * Object representation of a game with an id that can be used as a room to host online chess games
 *
 * @param gameID        Data field representing a game's ID
 * @param whiteUsername Data field representing a game's white team player's username
 * @param blackUsername Data field representing a game's black team player's username
 * @param gameName      Data field representing a game's name
 * @param chessGame          Data field representing the chess game of the Game object
 */
public record Game(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame chessGame) {
    /**
     * Game object constructor that will have a game ID and the usernames of the players playing, the name of the game,
     * and the object representing the chess game with its board itself
     *
     * @param gameID The game's ID
     * @param whiteUsername The game's white team player's username
     * @param blackUsername The game's black team player's username
     * @param gameName The game's name
     * @param chessGame The game's game object
     */
    public Game {
    }

    /**
     * A getter method for the game's ID
     *
     * @return The game's ID
     */
    @Override
    public int gameID() {
        return gameID;
    }

    /**
     * A getter method for the username of the game's white team player
     *
     * @return The game's white team player name
     */
    @Override
    public String whiteUsername() {
        return whiteUsername;
    }

    /**
     * A getter method for the username of the game's black team
     *
     * @return The game's black team player name
     */
    @Override
    public String blackUsername() {
        return blackUsername;
    }

    /**
     * A getter method for the game's name
     *
     * @return The game's name
     */
    @Override
    public String gameName() {
        return gameName;
    }

    /**
     * A getter method for the game's chess game object that includes the board, rules, and pieces
     *
     * @return The game's chess game object
     */
    @Override
    public ChessGame chessGame() {
        return chessGame;
    }
}
