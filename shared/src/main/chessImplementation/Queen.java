package chessImplementation;

import chess.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Queen extends ChessPieceImpl {
    public Queen(ChessGame.TeamColor color, PieceType type) {
        super(color, type);
    }

    public Queen(ChessPiece originalPiece) {
        super(originalPiece);
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Set<ChessMove> allMoves = new HashSet<>();
        allMoves.addAll(diagonalMoves(board, myPosition));
        allMoves.addAll(cardinalMoves(board, myPosition));
        return allMoves;
    }
}
