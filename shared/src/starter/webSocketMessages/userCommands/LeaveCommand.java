package webSocketMessages.userCommands;

public class LeaveCommand extends UserGameCommand {
    private final Integer gameID;

    public LeaveCommand(Integer gameID, String authToken, CommandType commandType) {
        super(authToken, commandType);
        this.gameID = gameID;
    }

    public Integer getGameID() {
        return gameID;
    }
}
