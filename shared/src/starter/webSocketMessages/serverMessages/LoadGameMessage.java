package webSocketMessages.serverMessages;

import models.Game;

public class LoadGameMessage extends ServerMessage {
    private final Game game;

    public LoadGameMessage(Game game, ServerMessageType type) {
        super(type);
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}
