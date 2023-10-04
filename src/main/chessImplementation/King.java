package chessImplementation;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class King extends ChessPieceImplementation {
    private Set<ChessMove> moves = new HashSet<>();
    public King(ChessGame.TeamColor color, PieceType type) {
        super(color, type);
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        if (myPosition.getColumn() + 1 <= 8 && (board.getPiece(new ChessPositionImplementation(myPosition.getRow(), myPosition.getColumn() + 1)) == null || board.getPiece(new ChessPositionImplementation(myPosition.getRow(), myPosition.getColumn() + 1)).getTeamColor() != this.getTeamColor())) {
            moves.add(new ChessMoveImplementation(myPosition, new ChessPositionImplementation(myPosition.getRow(), myPosition.getColumn() + 1), null));
        }
        // upward diagonal and straight move checks
        if ((myPosition.getRow() + 1 <= 8 && myPosition.getColumn() + 1 <= 8) && (board.getPiece(new ChessPositionImplementation(myPosition.getRow() + 1, myPosition.getColumn() + 1)) == null || board.getPiece(new ChessPositionImplementation(myPosition.getRow() + 1, myPosition.getColumn() + 1)).getTeamColor() != this.getTeamColor())) {
            moves.add(new ChessMoveImplementation(myPosition, new ChessPositionImplementation(myPosition.getRow() + 1, myPosition.getColumn() + 1), null));
        }
        if (myPosition.getRow() + 1 <= 8 && (board.getPiece(new ChessPositionImplementation(myPosition.getRow() + 1, myPosition.getColumn())) == null || board.getPiece(new ChessPositionImplementation(myPosition.getRow() + 1, myPosition.getColumn())).getTeamColor() != this.getTeamColor())) {
            moves.add(new ChessMoveImplementation(myPosition, new ChessPositionImplementation(myPosition.getRow() + 1, myPosition.getColumn()), null));
        }
        if ((myPosition.getRow() + 1 <= 8 && myPosition.getColumn() - 1 >= 1) && (board.getPiece(new ChessPositionImplementation(myPosition.getRow() + 1, myPosition.getColumn() - 1)) == null || board.getPiece(new ChessPositionImplementation(myPosition.getRow() + 1, myPosition.getColumn() - 1)).getTeamColor() != this.getTeamColor())) {
            moves.add(new ChessMoveImplementation(myPosition, new ChessPositionImplementation(myPosition.getRow() + 1, myPosition.getColumn() - 1), null));
        }
        if (myPosition.getColumn() - 1 >= 1 && (board.getPiece(new ChessPositionImplementation(myPosition.getRow(), myPosition.getColumn() - 1)) == null || board.getPiece(new ChessPositionImplementation(myPosition.getRow(), myPosition.getColumn() - 1)).getTeamColor() != this.getTeamColor())) {
            moves.add(new ChessMoveImplementation(myPosition, new ChessPositionImplementation(myPosition.getRow(), myPosition.getColumn() - 1), null));
        }
        // downward diagonal and straight move checks
        if ((myPosition.getRow() - 1 >= 1 && myPosition.getColumn() - 1 >= 1) && (board.getPiece(new ChessPositionImplementation(myPosition.getRow() - 1, myPosition.getColumn() - 1)) == null || board.getPiece(new ChessPositionImplementation(myPosition.getRow() - 1, myPosition.getColumn() - 1)).getTeamColor() != this.getTeamColor())) {
            moves.add(new ChessMoveImplementation(myPosition, new ChessPositionImplementation(myPosition.getRow() - 1, myPosition.getColumn() - 1), null));
        }
        if (myPosition.getRow() - 1 >= 1 && (board.getPiece(new ChessPositionImplementation(myPosition.getRow() - 1, myPosition.getColumn())) == null || board.getPiece(new ChessPositionImplementation(myPosition.getRow() - 1, myPosition.getColumn())).getTeamColor() != this.getTeamColor())) {
            moves.add(new ChessMoveImplementation(myPosition, new ChessPositionImplementation(myPosition.getRow() - 1, myPosition.getColumn()), null));
        }
        if ((myPosition.getRow() - 1 >= 1 && myPosition.getColumn() + 1 <= 8) && (board.getPiece(new ChessPositionImplementation(myPosition.getRow() - 1, myPosition.getColumn() + 1)) == null || board.getPiece(new ChessPositionImplementation(myPosition.getRow() - 1, myPosition.getColumn() + 1)).getTeamColor() != this.getTeamColor())) {
            moves.add(new ChessMoveImplementation(myPosition, new ChessPositionImplementation(myPosition.getRow() - 1, myPosition.getColumn() + 1), null));
        }
        return moves;
    }
}
