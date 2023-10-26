package requests;

/**
 * Object representation of a request to register for a new user account. Mainly just sets up username,
 * password, and email fields for new users
 */
public record RegisterRequest(String username, String password, String email) {
}
