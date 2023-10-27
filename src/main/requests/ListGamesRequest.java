package requests;

/**
 * Object representation of client's request to fetch all games in chess game server
 *
 * @param authToken Client's auth token
 */
public record ListGamesRequest(String authToken) {
}
