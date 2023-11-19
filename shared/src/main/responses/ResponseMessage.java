package responses;

/**
 * Object representation/abstract class of messages that form parts of responses. These messages could be
 * Error messages or success messages if a particular response doesn't necessarily return
 * a json object upon success
 */
public class ResponseMessage {

    private final String message;

    public ResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
