package webSocketMessages.userCommands;

import chess.ChessMove;

public class MakeMoveCommand extends UserGameCommand {
    private final Integer gameID;
    private final ChessMove chessMove;

    public MakeMoveCommand(Integer gameID, ChessMove chessMove, String authToken, CommandType commandType) {
        super(authToken, commandType);
        this.gameID = gameID;
        this.chessMove = chessMove;
    }

    public Integer getGameID() {
        return gameID;
    }

    public ChessMove getChessMove() {
        return chessMove;
    }
}
