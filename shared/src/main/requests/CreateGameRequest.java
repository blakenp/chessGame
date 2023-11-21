package requests;

/**
 * Object representation of a request the client can make to create a new game session to play chess.
 */
public record CreateGameRequest (String gameName, String authToken) {
}
