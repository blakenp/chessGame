package chessImplementation;

import chess.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Knight extends ChessPieceImpl {
    private Set<ChessMove> knightMoves = new HashSet<>();
    public Knight(ChessGame.TeamColor color, PieceType type) {
        super(color, type);
    }

    public Knight(ChessPiece originalPiece) {
        super(originalPiece);
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        knightMoves.clear();
        // Waluigi backwards L check below start position ( - 2, - 1)
        if ((myPosition.getRow() - 2 >= 1 && myPosition.getColumn() - 1 >= 1) && (board.getPiece(new ChessPositionImpl(myPosition.getRow() - 2, myPosition.getColumn() - 1)) == null || board.getPiece(new ChessPositionImpl(myPosition.getRow() - 2, myPosition.getColumn() - 1)).getTeamColor() != this.getTeamColor())) {
            knightMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() - 2, myPosition.getColumn() - 1), null));
        }
        // Luigi normal L below start position ( - 2, + 1)
        if ((myPosition.getRow() - 2 >= 1 && myPosition.getColumn() + 1 <= 8) && (board.getPiece(new ChessPositionImpl(myPosition.getRow() - 2, myPosition.getColumn() + 1)) == null || board.getPiece(new ChessPositionImpl(myPosition.getRow() - 2, myPosition.getColumn() + 1)).getTeamColor() != this.getTeamColor())) {
            knightMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() - 2, myPosition.getColumn() + 1), null));
        }
        // Waluigi backwards L to the right on its back ( - 1, + 2)
        if ((myPosition.getRow() - 1 >= 1 && myPosition.getColumn() + 2 <= 8) && (board.getPiece(new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn() + 2)) == null || board.getPiece(new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn() + 2)).getTeamColor() != this.getTeamColor())) {
            knightMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn() + 2), null));
        }
        // Luigi normal L to the left on its back ( - 1, - 2)
        if ((myPosition.getRow() - 1 >= 1 && myPosition.getColumn() - 2 >= 1) && (board.getPiece(new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn() - 2)) == null || board.getPiece(new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn() - 2)).getTeamColor() != this.getTeamColor())) {
            knightMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn() - 2), null));
        }
        // Waluigi backwards L to the right hanging upside down ( + 1, - 2)
        if ((myPosition.getRow() + 1 <= 8 && myPosition.getColumn() - 2 >= 1) && (board.getPiece(new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn() - 2)) == null || board.getPiece(new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn() - 2)).getTeamColor() != this.getTeamColor())) {
            knightMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn() - 2), null));
        }
        // Luigi normal L to the right hanging upside down ( + 1, + 2)
        if ((myPosition.getRow() + 1 <= 8 && myPosition.getColumn() + 2 <= 8) && (board.getPiece(new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn() + 2)) == null || board.getPiece(new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn() + 2)).getTeamColor() != this.getTeamColor())) {
            knightMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn() + 2), null));
        }
        // Waluigi backwards L above ( + 2, - 1)
        if ((myPosition.getRow() + 2 <= 8 && myPosition.getColumn() - 1 >= 1) && (board.getPiece(new ChessPositionImpl(myPosition.getRow() + 2, myPosition.getColumn() - 1)) == null || board.getPiece(new ChessPositionImpl(myPosition.getRow() + 2, myPosition.getColumn() - 1)).getTeamColor() != this.getTeamColor())) {
            knightMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() + 2, myPosition.getColumn() - 1), null));
        }
        // Luigi normal L above ( + 2, + 1)
        if ((myPosition.getRow() + 2 <= 8 && myPosition.getColumn() + 1 <= 8) && (board.getPiece(new ChessPositionImpl(myPosition.getRow() + 2, myPosition.getColumn() + 1)) == null || board.getPiece(new ChessPositionImpl(myPosition.getRow() + 2, myPosition.getColumn() + 1)).getTeamColor() != this.getTeamColor())) {
            knightMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() + 2, myPosition.getColumn() + 1), null));
        }
        return knightMoves;
    }
}
