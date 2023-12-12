package webSocketMessages.userCommands;

public class JoinObserverCommand extends UserGameCommand {
    private final Integer gameID;

    public JoinObserverCommand(Integer gameID, String authToken, CommandType commandType) {
        super(authToken, commandType);
        this.gameID = gameID;
    }

    public Integer getGameID() {
        return gameID;
    }
}
