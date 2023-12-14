package webSocketMessages.userCommands;

import chess.ChessMove;

public class MakeMoveCommand extends UserGameCommand {
    private final Integer gameID;
    private ChessMove move;
    private final String username;

    public MakeMoveCommand(Integer gameID, ChessMove move, String username, String authToken, CommandType commandType) {
        super(authToken, commandType);
        this.gameID = gameID;
        this.move = move;
        this.username = username;
    }

    public Integer getGameID() {
        return gameID;
    }

    public ChessMove getChessMove() {
        return move;
    }

    public void setChessMove(ChessMove move) {
        this.move = move;
    }

    public String getUsername() {
        return username;
    }
}
