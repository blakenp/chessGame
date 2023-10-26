package requests;

/**
 * Object representation of requests the client can make to log into the chess game application
 */
public record LoginRequest(String username, String password) {
}
