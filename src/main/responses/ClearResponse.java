package responses;

/**
 * Object representation of the response returned from clearing the database of its data.
 * Mainly used just for testing purposes.
 */
public class ClearResponse {
    /**
     * Data field representing an error message
     */
    private String message;

    /**
     * ClearResponse object constructor that just sets the error message if the response
     * is in the form of a 500 error. Otherwise, a 200 status code will be sent with no message
     *
     * @param message The error message
     */
    public ClearResponse(String message) {
        this.message = message;
    }

    /**
     * A getter method for the clear response's error message
     *
     * @return The clear response's error message
     */
    public String getMessage() {
        return message;
    }

    /**
     * A setter method for setting the clear response's error message
     *
     * @param message The new error message that will be set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
