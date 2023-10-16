package responses;

/**
 * Object representation of the possible responses a client can receive from requesting to log out.
 */
public class LogoutResponse {
    /**
     * Data field representing an error message
     */
    private String message;

    /**
     * LogoutResponse object constructor that just sets the error message if the response
     * is in the form of a 500 error. Otherwise, a 200 status code will be sent with no message
     *
     * @param message The error message
     */
    public LogoutResponse(String message) {
        this.message = message;
    }

    /**
     * A getter method for the logout response's error message
     *
     * @return The logout response's error message
     */
    public String getMessage() {
        return message;
    }

    /**
     * A setter method for setting the logout response's error message
     *
     * @param message The new error message that will be set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
