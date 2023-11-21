package chessImplementation;

import chess.*;

import java.util.Collection;

public class Bishop extends ChessPieceImpl {
    public Bishop(ChessGame.TeamColor color, PieceType type) {
        super(color, type);
    }

    public Bishop(ChessPiece originalPiece) {
        super(originalPiece);
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return diagonalMoves(board, myPosition);
    }
}
