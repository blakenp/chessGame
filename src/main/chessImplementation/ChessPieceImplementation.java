package chessImplementation;

import chess.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ChessPieceImplementation implements ChessPiece {
    private ChessGame.TeamColor color;
    private PieceType type;
    private Set<ChessMove> moves = new HashSet<>();

    public ChessPieceImplementation(ChessGame.TeamColor color, PieceType type) {
        this.color = color;
        this.type = type;
    }
    @Override
    public ChessGame.TeamColor getTeamColor() {
        return color;
    }

    @Override
    public PieceType getPieceType() {
        return type;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return moves;
    }

    public Collection<ChessMove> diagonalMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPosition currentPosition = myPosition;

        // left one and down one ( - - )
        while ((currentPosition.getRow() - 1 >= 1 && currentPosition.getColumn() - 1 >= 1) && (board.getPiece(new ChessPositionImplementation(currentPosition.getRow() - 1, currentPosition.getColumn() - 1)) == null || board.getPiece(new ChessPositionImplementation(currentPosition.getRow() - 1, currentPosition.getColumn() - 1)).getTeamColor() != this.color)) {
            if (board.getPiece(new ChessPositionImplementation(currentPosition.getRow() - 1, currentPosition.getColumn() - 1)) != null && board.getPiece(new ChessPositionImplementation(currentPosition.getRow() - 1, currentPosition.getColumn() - 1)).getTeamColor() != this.getTeamColor()) {
                moves.add(new ChessMoveImplementation(myPosition, new ChessPositionImplementation(currentPosition.getRow() - 1, currentPosition.getColumn() - 1), null));
                break;
            }
            moves.add(new ChessMoveImplementation(myPosition, new ChessPositionImplementation(currentPosition.getRow() - 1, currentPosition.getColumn() - 1), null));
            currentPosition = new ChessPositionImplementation(currentPosition.getRow() - 1, currentPosition.getColumn() - 1);
        }

        // left one and up one ( + - )
        currentPosition = myPosition;
        while ((currentPosition.getRow() + 1 <= 8 && currentPosition.getColumn() - 1 >= 1) && (board.getPiece(new ChessPositionImplementation(currentPosition.getRow() + 1, currentPosition.getColumn() - 1)) == null || board.getPiece(new ChessPositionImplementation(currentPosition.getRow() + 1, currentPosition.getColumn() - 1)).getTeamColor() != this.color)) {
            if (board.getPiece(new ChessPositionImplementation(currentPosition.getRow() + 1, currentPosition.getColumn() - 1)) != null && board.getPiece(new ChessPositionImplementation(currentPosition.getRow() + 1, currentPosition.getColumn() - 1)).getTeamColor() != this.getTeamColor()) {
                moves.add(new ChessMoveImplementation(myPosition, new ChessPositionImplementation(currentPosition.getRow() + 1, currentPosition.getColumn() - 1), null));
                break;
            }
            moves.add(new ChessMoveImplementation(myPosition, new ChessPositionImplementation(currentPosition.getRow() + 1, currentPosition.getColumn() - 1), null));
            currentPosition = new ChessPositionImplementation(currentPosition.getRow() + 1, currentPosition.getColumn() - 1);
        }

        // right one and down one ( - + )
        currentPosition = myPosition;
        while ((currentPosition.getRow() - 1 >= 1 && currentPosition.getColumn() + 1 <= 8) && (board.getPiece(new ChessPositionImplementation(currentPosition.getRow() - 1, currentPosition.getColumn() + 1)) == null || board.getPiece(new ChessPositionImplementation(currentPosition.getRow() - 1, currentPosition.getColumn() + 1)).getTeamColor() != this.color)) {
            if (board.getPiece(new ChessPositionImplementation(currentPosition.getRow() - 1, currentPosition.getColumn() + 1)) != null && board.getPiece(new ChessPositionImplementation(currentPosition.getRow() - 1, currentPosition.getColumn() + 1)).getTeamColor() != this.getTeamColor()) {
                moves.add(new ChessMoveImplementation(myPosition, new ChessPositionImplementation(currentPosition.getRow() - 1, currentPosition.getColumn() + 1), null));
                break;
            }
            moves.add(new ChessMoveImplementation(myPosition, new ChessPositionImplementation(currentPosition.getRow() - 1, currentPosition.getColumn() + 1), null));
            currentPosition = new ChessPositionImplementation(currentPosition.getRow() - 1, currentPosition.getColumn() + 1);
        }

        // right one and up one ( + + )
        currentPosition = myPosition;
        while ((currentPosition.getRow() + 1 <= 8 && currentPosition.getColumn() + 1 <= 8) && (board.getPiece(new ChessPositionImplementation(currentPosition.getRow() + 1, currentPosition.getColumn() + 1)) == null || board.getPiece(new ChessPositionImplementation(currentPosition.getRow() + 1, currentPosition.getColumn() + 1)).getTeamColor() != this.color)) {
            if (board.getPiece(new ChessPositionImplementation(currentPosition.getRow() + 1, currentPosition.getColumn() + 1)) != null && board.getPiece(new ChessPositionImplementation(currentPosition.getRow() + 1, currentPosition.getColumn() + 1)).getTeamColor() != this.getTeamColor()) {
                moves.add(new ChessMoveImplementation(myPosition, new ChessPositionImplementation(currentPosition.getRow() + 1, currentPosition.getColumn() + 1), null));
                break;
            }
            moves.add(new ChessMoveImplementation(myPosition, new ChessPositionImplementation(currentPosition.getRow() + 1, currentPosition.getColumn() + 1), null));
            currentPosition = new ChessPositionImplementation(currentPosition.getRow() + 1, currentPosition.getColumn() + 1);
        }
        return moves;
    }

    public Collection<ChessMove> cardinalMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPosition currentPosition = myPosition;

        // move up
        while (currentPosition.getRow() + 1 <= 8 && (board.getPiece(new ChessPositionImplementation(currentPosition.getRow() + 1, currentPosition.getColumn())) == null || board.getPiece(new ChessPositionImplementation(currentPosition.getRow() + 1, currentPosition.getColumn())).getTeamColor() != this.color)) {
            if (board.getPiece(new ChessPositionImplementation(currentPosition.getRow() + 1, currentPosition.getColumn())) != null && board.getPiece(new ChessPositionImplementation(currentPosition.getRow() + 1, currentPosition.getColumn())).getTeamColor() != this.getTeamColor()) {
                moves.add(new ChessMoveImplementation(myPosition, new ChessPositionImplementation(currentPosition.getRow() + 1, currentPosition.getColumn()), null));
                break;
            }
            moves.add(new ChessMoveImplementation(myPosition, new ChessPositionImplementation(currentPosition.getRow() + 1, currentPosition.getColumn()), null));
            currentPosition = new ChessPositionImplementation(currentPosition.getRow() + 1, currentPosition.getColumn());
        }

        // move left
        currentPosition = myPosition;
        while (currentPosition.getColumn() - 1 >= 1 && (board.getPiece(new ChessPositionImplementation(currentPosition.getRow(), currentPosition.getColumn() - 1)) == null || board.getPiece(new ChessPositionImplementation(currentPosition.getRow(), currentPosition.getColumn() - 1)).getTeamColor() != this.color)) {
            if (board.getPiece(new ChessPositionImplementation(currentPosition.getRow(), currentPosition.getColumn() - 1)) != null && board.getPiece(new ChessPositionImplementation(currentPosition.getRow(), currentPosition.getColumn() - 1)).getTeamColor() != this.getTeamColor()) {
                moves.add(new ChessMoveImplementation(myPosition, new ChessPositionImplementation(currentPosition.getRow(), currentPosition.getColumn() - 1), null));
                break;
            }
            moves.add(new ChessMoveImplementation(myPosition, new ChessPositionImplementation(currentPosition.getRow(), currentPosition.getColumn() - 1), null));
            currentPosition = new ChessPositionImplementation(currentPosition.getRow(), currentPosition.getColumn() - 1);
        }

        // move down
        currentPosition = myPosition;
        while (currentPosition.getRow() - 1 >= 1 && (board.getPiece(new ChessPositionImplementation(currentPosition.getRow() - 1, currentPosition.getColumn())) == null || board.getPiece(new ChessPositionImplementation(currentPosition.getRow() - 1, currentPosition.getColumn())).getTeamColor() != this.color)) {
            if (board.getPiece(new ChessPositionImplementation(currentPosition.getRow() - 1, currentPosition.getColumn())) != null && board.getPiece(new ChessPositionImplementation(currentPosition.getRow() - 1, currentPosition.getColumn())).getTeamColor() != this.getTeamColor()) {
                moves.add(new ChessMoveImplementation(myPosition, new ChessPositionImplementation(currentPosition.getRow() - 1, currentPosition.getColumn()), null));
                break;
            }
            moves.add(new ChessMoveImplementation(myPosition, new ChessPositionImplementation(currentPosition.getRow() - 1, currentPosition.getColumn()), null));
            currentPosition = new ChessPositionImplementation(currentPosition.getRow() - 1, currentPosition.getColumn());
        }

        // move right
        currentPosition = myPosition;
        while (currentPosition.getColumn() + 1 <= 8 && (board.getPiece(new ChessPositionImplementation(currentPosition.getRow(), currentPosition.getColumn() + 1)) == null || board.getPiece(new ChessPositionImplementation(currentPosition.getRow(), currentPosition.getColumn() + 1)).getTeamColor() != this.color)) {
            if (board.getPiece(new ChessPositionImplementation(currentPosition.getRow(), currentPosition.getColumn() + 1)) != null && board.getPiece(new ChessPositionImplementation(currentPosition.getRow(), currentPosition.getColumn() + 1)).getTeamColor() != this.getTeamColor()) {
                moves.add(new ChessMoveImplementation(myPosition, new ChessPositionImplementation(currentPosition.getRow(), currentPosition.getColumn() + 1), null));
                break;
            }
            moves.add(new ChessMoveImplementation(myPosition, new ChessPositionImplementation(currentPosition.getRow(), currentPosition.getColumn() + 1), null));
            currentPosition = new ChessPositionImplementation(currentPosition.getRow(), currentPosition.getColumn() + 1);
        }
        return moves;
    }
}
