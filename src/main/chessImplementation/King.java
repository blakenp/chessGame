package chessImplementation;

import chess.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class King extends ChessPieceImpl {
    private Set<ChessMove> kingMoves = new HashSet<>();
    public King(ChessGame.TeamColor color, PieceType type) {
        super(color, type);
    }

    public King(ChessPiece originalPiece) {
        super(originalPiece);
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        kingMoves.clear();
        if (myPosition.getColumn() + 1 <= 8 && (board.getPiece(new ChessPositionImpl(myPosition.getRow(), myPosition.getColumn() + 1)) == null || board.getPiece(new ChessPositionImpl(myPosition.getRow(), myPosition.getColumn() + 1)).getTeamColor() != this.getTeamColor())) {
            kingMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow(), myPosition.getColumn() + 1), null));
        }
        // upward diagonal and straight move checks
        if ((myPosition.getRow() + 1 <= 8 && myPosition.getColumn() + 1 <= 8) && (board.getPiece(new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn() + 1)) == null || board.getPiece(new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn() + 1)).getTeamColor() != this.getTeamColor())) {
            kingMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn() + 1), null));
        }
        if (myPosition.getRow() + 1 <= 8 && (board.getPiece(new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn())) == null || board.getPiece(new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn())).getTeamColor() != this.getTeamColor())) {
            kingMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn()), null));
        }
        if ((myPosition.getRow() + 1 <= 8 && myPosition.getColumn() - 1 >= 1) && (board.getPiece(new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn() - 1)) == null || board.getPiece(new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn() - 1)).getTeamColor() != this.getTeamColor())) {
            kingMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn() - 1), null));
        }
        if (myPosition.getColumn() - 1 >= 1 && (board.getPiece(new ChessPositionImpl(myPosition.getRow(), myPosition.getColumn() - 1)) == null || board.getPiece(new ChessPositionImpl(myPosition.getRow(), myPosition.getColumn() - 1)).getTeamColor() != this.getTeamColor())) {
            kingMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow(), myPosition.getColumn() - 1), null));
        }
        // downward diagonal and straight move checks
        if ((myPosition.getRow() - 1 >= 1 && myPosition.getColumn() - 1 >= 1) && (board.getPiece(new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn() - 1)) == null || board.getPiece(new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn() - 1)).getTeamColor() != this.getTeamColor())) {
            kingMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn() - 1), null));
        }
        if (myPosition.getRow() - 1 >= 1 && (board.getPiece(new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn())) == null || board.getPiece(new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn())).getTeamColor() != this.getTeamColor())) {
            kingMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn()), null));
        }
        if ((myPosition.getRow() - 1 >= 1 && myPosition.getColumn() + 1 <= 8) && (board.getPiece(new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn() + 1)) == null || board.getPiece(new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn() + 1)).getTeamColor() != this.getTeamColor())) {
            kingMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn() + 1), null));
        }
        return kingMoves;
    }
}
