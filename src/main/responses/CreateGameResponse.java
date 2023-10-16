package responses;

/**
 * Object representation of the response that can be received by the client when they request
 * to create a chess game session. Can return a game ID or an error message
 */
public class CreateGameResponse {
    /**
     * Data field representing a game ID
     */
    private int gameID;
    /**
     * Data field representing an error message
     */
    private String message;

    /**
     * CreateGameResponse object constructor that will set the gameID and message upon instantiation of
     * the CreateGameResponse object
     *
     * @param gameID The chess game session's game ID
     * @param message The potential error message used for error status codes
     */
    public CreateGameResponse(int gameID, String message) {
        this.gameID = gameID;
        this.message = message;
    }

    /**
     * A getter method for the response's game ID
     *
     * @return The game ID
     */
    public int getGameID() {
        return gameID;
    }

    /**
     * A setter method for setting the response's game ID
     *
     * @param gameID The new game ID that will be used for the response's game ID
     */
    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    /**
     * A getter method for the response's error message
     *
     * @return The error message
     */
    public String getMessage() {
        return message;
    }

    /**
     * A setter method for setting the response's error message
     *
     * @param message The new error message that will be used the response's error message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
