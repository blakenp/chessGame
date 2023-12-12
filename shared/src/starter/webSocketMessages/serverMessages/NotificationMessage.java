package webSocketMessages.serverMessages;

public class NotificationMessage extends ServerMessage {
    private final String message;

    public NotificationMessage(String message, ServerMessageType type) {
        super(type);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
