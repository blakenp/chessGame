package webSocketMessages.userCommands;

import chess.ChessGame;

public class JoinPlayerCommand extends UserGameCommand {
    private final Integer gameID;
    private final ChessGame.TeamColor playerColor;

    public JoinPlayerCommand(Integer gameID, ChessGame.TeamColor playerColor, CommandType commandType, String authToken) {
        super(authToken, commandType);
        this.gameID = gameID;
        this.playerColor = playerColor;
        this.commandType = commandType;
    }

    public Integer getGameID() {
        return gameID;
    }

    public ChessGame.TeamColor getPlayerColor() {
        return playerColor;
    }
}
