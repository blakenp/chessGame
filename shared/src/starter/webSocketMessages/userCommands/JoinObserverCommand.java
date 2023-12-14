package webSocketMessages.userCommands;

public class JoinObserverCommand extends UserGameCommand {
    private final Integer gameID;
    private final String username;

    public JoinObserverCommand(Integer gameID, String username, String authToken, CommandType commandType) {
        super(authToken, commandType);
        this.gameID = gameID;
        this.username = username;
    }

    public Integer getGameID() {
        return gameID;
    }

    public String getUsername() {
        return username;
    }
}
