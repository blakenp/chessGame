package responses;

/**
 * Object representation/abstract class of error responses any type of response can throw
 */
public class ErrorResponse {

    private final String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getErrorMessage() {
        return message;
    }
}
