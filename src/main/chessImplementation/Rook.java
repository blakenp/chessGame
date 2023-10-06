package chessImplementation;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.Collection;

public class Rook extends ChessPieceImplementation {
    public Rook(ChessGame.TeamColor color, PieceType type) {
        super(color, type);
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return cardinalMoves(board, myPosition);
    }
}
