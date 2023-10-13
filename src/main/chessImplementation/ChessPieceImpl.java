package chessImplementation;

import chess.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ChessPieceImpl implements ChessPiece {
    private ChessGame.TeamColor color;
    private PieceType type;
    private Set<ChessMove> moves = new HashSet<>();

    public ChessPieceImpl(ChessGame.TeamColor color, PieceType type) {
        this.color = color;
        this.type = type;
    }

    public ChessPieceImpl(ChessPiece originalPiece) {
        this.color = originalPiece.getTeamColor();
        this.type = originalPiece.getPieceType();
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
        moves.clear();
        return moves;
    }

    public Collection<ChessMove> diagonalMoves(ChessBoard board, ChessPosition myPosition) {
        moves.clear();
        ChessPosition currentPosition = myPosition;

        // left one and down one ( - - )
        while ((currentPosition.getRow() - 1 >= 1 && currentPosition.getColumn() - 1 >= 1) && (board.getPiece(new ChessPositionImpl(currentPosition.getRow() - 1, currentPosition.getColumn() - 1)) == null || board.getPiece(new ChessPositionImpl(currentPosition.getRow() - 1, currentPosition.getColumn() - 1)).getTeamColor() != this.color)) {
            if (board.getPiece(new ChessPositionImpl(currentPosition.getRow() - 1, currentPosition.getColumn() - 1)) != null && board.getPiece(new ChessPositionImpl(currentPosition.getRow() - 1, currentPosition.getColumn() - 1)).getTeamColor() != this.getTeamColor()) {
                moves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(currentPosition.getRow() - 1, currentPosition.getColumn() - 1), null));
                break;
            }
            moves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(currentPosition.getRow() - 1, currentPosition.getColumn() - 1), null));
            currentPosition = new ChessPositionImpl(currentPosition.getRow() - 1, currentPosition.getColumn() - 1);
        }

        // left one and up one ( + - )
        currentPosition = myPosition;
        while ((currentPosition.getRow() + 1 <= 8 && currentPosition.getColumn() - 1 >= 1) && (board.getPiece(new ChessPositionImpl(currentPosition.getRow() + 1, currentPosition.getColumn() - 1)) == null || board.getPiece(new ChessPositionImpl(currentPosition.getRow() + 1, currentPosition.getColumn() - 1)).getTeamColor() != this.color)) {
            if (board.getPiece(new ChessPositionImpl(currentPosition.getRow() + 1, currentPosition.getColumn() - 1)) != null && board.getPiece(new ChessPositionImpl(currentPosition.getRow() + 1, currentPosition.getColumn() - 1)).getTeamColor() != this.getTeamColor()) {
                moves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(currentPosition.getRow() + 1, currentPosition.getColumn() - 1), null));
                break;
            }
            moves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(currentPosition.getRow() + 1, currentPosition.getColumn() - 1), null));
            currentPosition = new ChessPositionImpl(currentPosition.getRow() + 1, currentPosition.getColumn() - 1);
        }

        // right one and down one ( - + )
        currentPosition = myPosition;
        while ((currentPosition.getRow() - 1 >= 1 && currentPosition.getColumn() + 1 <= 8) && (board.getPiece(new ChessPositionImpl(currentPosition.getRow() - 1, currentPosition.getColumn() + 1)) == null || board.getPiece(new ChessPositionImpl(currentPosition.getRow() - 1, currentPosition.getColumn() + 1)).getTeamColor() != this.color)) {
            if (board.getPiece(new ChessPositionImpl(currentPosition.getRow() - 1, currentPosition.getColumn() + 1)) != null && board.getPiece(new ChessPositionImpl(currentPosition.getRow() - 1, currentPosition.getColumn() + 1)).getTeamColor() != this.getTeamColor()) {
                moves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(currentPosition.getRow() - 1, currentPosition.getColumn() + 1), null));
                break;
            }
            moves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(currentPosition.getRow() - 1, currentPosition.getColumn() + 1), null));
            currentPosition = new ChessPositionImpl(currentPosition.getRow() - 1, currentPosition.getColumn() + 1);
        }

        // right one and up one ( + + )
        currentPosition = myPosition;
        while ((currentPosition.getRow() + 1 <= 8 && currentPosition.getColumn() + 1 <= 8) && (board.getPiece(new ChessPositionImpl(currentPosition.getRow() + 1, currentPosition.getColumn() + 1)) == null || board.getPiece(new ChessPositionImpl(currentPosition.getRow() + 1, currentPosition.getColumn() + 1)).getTeamColor() != this.color)) {
            if (board.getPiece(new ChessPositionImpl(currentPosition.getRow() + 1, currentPosition.getColumn() + 1)) != null && board.getPiece(new ChessPositionImpl(currentPosition.getRow() + 1, currentPosition.getColumn() + 1)).getTeamColor() != this.getTeamColor()) {
                moves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(currentPosition.getRow() + 1, currentPosition.getColumn() + 1), null));
                break;
            }
            moves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(currentPosition.getRow() + 1, currentPosition.getColumn() + 1), null));
            currentPosition = new ChessPositionImpl(currentPosition.getRow() + 1, currentPosition.getColumn() + 1);
        }
        return moves;
    }

    public Collection<ChessMove> cardinalMoves(ChessBoard board, ChessPosition myPosition) {
        moves.clear();
        ChessPosition currentPosition = myPosition;

        // move up
        while (currentPosition.getRow() + 1 <= 8 && (board.getPiece(new ChessPositionImpl(currentPosition.getRow() + 1, currentPosition.getColumn())) == null || board.getPiece(new ChessPositionImpl(currentPosition.getRow() + 1, currentPosition.getColumn())).getTeamColor() != this.color)) {
            if (board.getPiece(new ChessPositionImpl(currentPosition.getRow() + 1, currentPosition.getColumn())) != null && board.getPiece(new ChessPositionImpl(currentPosition.getRow() + 1, currentPosition.getColumn())).getTeamColor() != this.getTeamColor()) {
                moves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(currentPosition.getRow() + 1, currentPosition.getColumn()), null));
                break;
            }
            moves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(currentPosition.getRow() + 1, currentPosition.getColumn()), null));
            currentPosition = new ChessPositionImpl(currentPosition.getRow() + 1, currentPosition.getColumn());
        }

        // move left
        currentPosition = myPosition;
        while (currentPosition.getColumn() - 1 >= 1 && (board.getPiece(new ChessPositionImpl(currentPosition.getRow(), currentPosition.getColumn() - 1)) == null || board.getPiece(new ChessPositionImpl(currentPosition.getRow(), currentPosition.getColumn() - 1)).getTeamColor() != this.color)) {
            if (board.getPiece(new ChessPositionImpl(currentPosition.getRow(), currentPosition.getColumn() - 1)) != null && board.getPiece(new ChessPositionImpl(currentPosition.getRow(), currentPosition.getColumn() - 1)).getTeamColor() != this.getTeamColor()) {
                moves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(currentPosition.getRow(), currentPosition.getColumn() - 1), null));
                break;
            }
            moves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(currentPosition.getRow(), currentPosition.getColumn() - 1), null));
            currentPosition = new ChessPositionImpl(currentPosition.getRow(), currentPosition.getColumn() - 1);
        }

        // move down
        currentPosition = myPosition;
        while (currentPosition.getRow() - 1 >= 1 && (board.getPiece(new ChessPositionImpl(currentPosition.getRow() - 1, currentPosition.getColumn())) == null || board.getPiece(new ChessPositionImpl(currentPosition.getRow() - 1, currentPosition.getColumn())).getTeamColor() != this.color)) {
            if (board.getPiece(new ChessPositionImpl(currentPosition.getRow() - 1, currentPosition.getColumn())) != null && board.getPiece(new ChessPositionImpl(currentPosition.getRow() - 1, currentPosition.getColumn())).getTeamColor() != this.getTeamColor()) {
                moves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(currentPosition.getRow() - 1, currentPosition.getColumn()), null));
                break;
            }
            moves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(currentPosition.getRow() - 1, currentPosition.getColumn()), null));
            currentPosition = new ChessPositionImpl(currentPosition.getRow() - 1, currentPosition.getColumn());
        }

        // move right
        currentPosition = myPosition;
        while (currentPosition.getColumn() + 1 <= 8 && (board.getPiece(new ChessPositionImpl(currentPosition.getRow(), currentPosition.getColumn() + 1)) == null || board.getPiece(new ChessPositionImpl(currentPosition.getRow(), currentPosition.getColumn() + 1)).getTeamColor() != this.color)) {
            if (board.getPiece(new ChessPositionImpl(currentPosition.getRow(), currentPosition.getColumn() + 1)) != null && board.getPiece(new ChessPositionImpl(currentPosition.getRow(), currentPosition.getColumn() + 1)).getTeamColor() != this.getTeamColor()) {
                moves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(currentPosition.getRow(), currentPosition.getColumn() + 1), null));
                break;
            }
            moves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(currentPosition.getRow(), currentPosition.getColumn() + 1), null));
            currentPosition = new ChessPositionImpl(currentPosition.getRow(), currentPosition.getColumn() + 1);
        }
        return moves;
    }
}
