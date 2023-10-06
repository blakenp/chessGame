package chessImplementation;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Knight extends ChessPieceImplementation {
    private Set<ChessMove> moves = new HashSet<>();
    public Knight(ChessGame.TeamColor color, PieceType type) {
        super(color, type);
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        // Waluigi backwards L check below start position ( - 2, - 1)
        if ((myPosition.getRow() - 2 >= 1 && myPosition.getColumn() - 1 >= 1) && (board.getPiece(new ChessPositionImplementation(myPosition.getRow() - 2, myPosition.getColumn() - 1)) == null || board.getPiece(new ChessPositionImplementation(myPosition.getRow() - 2, myPosition.getColumn() - 1)).getTeamColor() != this.getTeamColor())) {
            moves.add(new ChessMoveImplementation(myPosition, new ChessPositionImplementation(myPosition.getRow() - 2, myPosition.getColumn() - 1), null));
        }
        // Luigi normal L below start position ( - 2, + 1)
        if ((myPosition.getRow() - 2 >= 1 && myPosition.getColumn() + 1 <= 8) && (board.getPiece(new ChessPositionImplementation(myPosition.getRow() - 2, myPosition.getColumn() + 1)) == null || board.getPiece(new ChessPositionImplementation(myPosition.getRow() - 2, myPosition.getColumn() + 1)).getTeamColor() != this.getTeamColor())) {
            moves.add(new ChessMoveImplementation(myPosition, new ChessPositionImplementation(myPosition.getRow() - 2, myPosition.getColumn() + 1), null));
        }
        // Waluigi backwards L to the right on its back ( - 1, + 2)
        if ((myPosition.getRow() - 1 >= 1 && myPosition.getColumn() + 2 <= 8) && (board.getPiece(new ChessPositionImplementation(myPosition.getRow() - 1, myPosition.getColumn() + 2)) == null || board.getPiece(new ChessPositionImplementation(myPosition.getRow() - 1, myPosition.getColumn() + 2)).getTeamColor() != this.getTeamColor())) {
            moves.add(new ChessMoveImplementation(myPosition, new ChessPositionImplementation(myPosition.getRow() - 1, myPosition.getColumn() + 2), null));
        }
        // Luigi normal L to the left on its back ( - 1, - 2)
        if ((myPosition.getRow() - 1 >= 1 && myPosition.getColumn() - 2 >= 1) && (board.getPiece(new ChessPositionImplementation(myPosition.getRow() - 1, myPosition.getColumn() - 2)) == null || board.getPiece(new ChessPositionImplementation(myPosition.getRow() - 1, myPosition.getColumn() - 2)).getTeamColor() != this.getTeamColor())) {
            moves.add(new ChessMoveImplementation(myPosition, new ChessPositionImplementation(myPosition.getRow() - 1, myPosition.getColumn() - 2), null));
        }
        // Waluigi backwards L to the right hanging upside down ( + 1, - 2)
        if ((myPosition.getRow() + 1 <= 8 && myPosition.getColumn() - 2 >= 1) && (board.getPiece(new ChessPositionImplementation(myPosition.getRow() + 1, myPosition.getColumn() - 2)) == null || board.getPiece(new ChessPositionImplementation(myPosition.getRow() + 1, myPosition.getColumn() - 2)).getTeamColor() != this.getTeamColor())) {
            moves.add(new ChessMoveImplementation(myPosition, new ChessPositionImplementation(myPosition.getRow() + 1, myPosition.getColumn() - 2), null));
        }
        // Luigi normal L to the right hanging upside down ( + 1, + 2)
        if ((myPosition.getRow() + 1 <= 8 && myPosition.getColumn() + 2 <= 8) && (board.getPiece(new ChessPositionImplementation(myPosition.getRow() + 1, myPosition.getColumn() + 2)) == null || board.getPiece(new ChessPositionImplementation(myPosition.getRow() + 1, myPosition.getColumn() + 2)).getTeamColor() != this.getTeamColor())) {
            moves.add(new ChessMoveImplementation(myPosition, new ChessPositionImplementation(myPosition.getRow() + 1, myPosition.getColumn() + 2), null));
        }
        // Waluigi backwards L above ( + 2, - 1)
        if ((myPosition.getRow() + 2 <= 8 && myPosition.getColumn() - 1 >= 1) && (board.getPiece(new ChessPositionImplementation(myPosition.getRow() + 2, myPosition.getColumn() - 1)) == null || board.getPiece(new ChessPositionImplementation(myPosition.getRow() + 2, myPosition.getColumn() - 1)).getTeamColor() != this.getTeamColor())) {
            moves.add(new ChessMoveImplementation(myPosition, new ChessPositionImplementation(myPosition.getRow() + 2, myPosition.getColumn() - 1), null));
        }
        // Luigi normal L above ( + 2, + 1)
        if ((myPosition.getRow() + 2 <= 8 && myPosition.getColumn() + 1 <= 8) && (board.getPiece(new ChessPositionImplementation(myPosition.getRow() + 2, myPosition.getColumn() + 1)) == null || board.getPiece(new ChessPositionImplementation(myPosition.getRow() + 2, myPosition.getColumn() + 1)).getTeamColor() != this.getTeamColor())) {
            moves.add(new ChessMoveImplementation(myPosition, new ChessPositionImplementation(myPosition.getRow() + 2, myPosition.getColumn() + 1), null));
        }
        return moves;
    }
}
