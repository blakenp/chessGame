package webSocketMessages.userCommands;

public class ResignCommand extends UserGameCommand {
    private final Integer gameID;

    public ResignCommand(Integer gameID, String authToken, CommandType commandType) {
        super(authToken, commandType);
        this.gameID = gameID;
    }

    public Integer getGameID() {
        return gameID;
    }
}
