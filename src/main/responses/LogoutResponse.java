package responses;

/**
 * Object representation of the possible responses a client can receive from requesting to log out.
 */
public class LogoutResponse extends ErrorResponse {
    /**
     * LogoutResponse object constructor that just sets the error message if the response
     * is in the form of a 500 error. Otherwise, a 200 status code will be sent with no message
     */
    public LogoutResponse() {
        super(null);
    }

    public LogoutResponse(String message) {
        super(message);
    }
}
