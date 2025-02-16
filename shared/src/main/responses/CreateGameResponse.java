package responses;

/**
 * Object representation of the response that can be received by the client when they request
 * to create a chess game session. Can return a game ID or an error message
 */
public class CreateGameResponse extends ResponseMessage {
    /**
     * Data field representing a game ID
     */
    private Integer gameID = null;

    /**
     * CreateGameResponse object constructor that will set the gameID and message upon instantiation of
     * the CreateGameResponse object
     *
     * @param gameID The chess game session's game ID
     */
    public CreateGameResponse(int gameID) {
        super(null);
        this.gameID = gameID;
    }

    public CreateGameResponse(String message) {
        super(message);
    }

    /**
     * A getter method for the response's game ID
     *
     * @return The game ID
     */
    public Integer getGameID() {
        return gameID;
    }
}
