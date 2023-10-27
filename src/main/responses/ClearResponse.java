package responses;

/**
 * Object representation of the response returned from clearing the database of its data.
 * Mainly used just for testing purposes.
 */
public class ClearResponse extends ErrorResponse {
    /**
     * ClearResponse object constructor that just sets the error message if the response
     * is in the form of a 500 error. Otherwise, a 200 status code will be sent with no message
     *
     * @param message The error message
     */
    public ClearResponse(String message) {
        super(message);
    }
}
