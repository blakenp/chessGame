package responses;

public class ErrorResponse {

    private final String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getErrorMessage() {
        return message;
    }
}
