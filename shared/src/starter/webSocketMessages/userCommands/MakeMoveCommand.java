package webSocketMessages.userCommands;

import chess.ChessMove;

public class MakeMoveCommand extends UserGameCommand {
    private final Integer gameID;
    private ChessMove chessMove;
    private final String username;

    public MakeMoveCommand(Integer gameID, ChessMove chessMove, String username, String authToken, CommandType commandType) {
        super(authToken, commandType);
        this.gameID = gameID;
        this.chessMove = chessMove;
        this.username = username;
    }

    public Integer getGameID() {
        return gameID;
    }

    public ChessMove getChessMove() {
        return chessMove;
    }

    public void setChessMove(ChessMove chessMove) {
        this.chessMove = chessMove;
    }

    public String getUsername() {
        return username;
    }
}
