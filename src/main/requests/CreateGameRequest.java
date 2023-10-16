package requests;

/**
 * Object representation of a request the client can make to create a new game session to play chess.
 */
public class CreateGameRequest {
    private String gameName;

    /**
     * CreateGameRequest object constructor that when initialized will request object with a game name
     * that will be used to set the chess game session's name.
     *
     * @param gameName the name of the game that will be created
     */
    public CreateGameRequest(String gameName) {
        this.gameName = gameName;
    }

    /**
     * A getter method for the request's game name
     * @return the game name
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * A setter method for setting the request's game name
     * @param gameName The new game name that will be used for the request's game name
     */
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }
}
