package chessImplementation;

import chess.*;

import java.util.Collection;

public class Rook extends ChessPieceImplementation {
    public Rook(ChessGame.TeamColor color, PieceType type) {
        super(color, type);
    }

    public Rook(ChessPiece originalPiece) {
        super(originalPiece);
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return cardinalMoves(board, myPosition);
    }
}
