package responses;

/**
 * Object representation of the response received by the client when making a request to join a chess game session
 */
public class JoinGameResponse extends ErrorResponse {

    /**
     * JoinResponse object constructor that just sets the error message if the response
     * is in the form of a 500 error. Otherwise, a 200 status code will be sent with no message
     *
     * @param message The error message
     */
    public JoinGameResponse(String message) {
        super(message);
    }
}