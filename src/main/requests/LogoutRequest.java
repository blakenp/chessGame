package requests;

/**
 * Object representation of client's request to log out of chess game server
 *
 * @param authToken Client's auth token
 */
public record LogoutRequest(String authToken) {
}
