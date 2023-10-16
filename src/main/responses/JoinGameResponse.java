package responses;

/**
 * Object representation of the response received by the client when making a request to join a chess game session
 */
public class JoinGameResponse {
    /**
     * Data field representing an error message
     */
    private String message;

    /**
     * JoinResponse object constructor that just sets the error message if the response
     * is in the form of a 500 error. Otherwise, a 200 status code will be sent with no message
     *
     * @param message The error message
     */
    public JoinGameResponse(String message) {
        this.message = message;
    }

    /**
     * A getter method for the JoinGame response's error message
     *
     * @return The JoinGame response's error message
     */
    public String getMessage() {
        return message;
    }

    /**
     * A setter method for setting the JoinGame response's error message
     *
     * @param message The new error message that will be set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}