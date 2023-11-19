package chessImplementation;

import chess.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Pawn extends ChessPieceImpl {
    private Set<ChessMove> pawnMoves = new HashSet<>();
    public Pawn(ChessGame.TeamColor color, PieceType type) {
        super(color, type);
    }

    public Pawn(ChessPiece originalPiece) {
        super(originalPiece);
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        pawnMoves.clear();
        // First check team color to know if you can move up or down
        if (this.getTeamColor() == ChessGame.TeamColor.WHITE) {
            // check if pawn is in starting position first
            if (myPosition.getRow() == 2) {
                // cardinal direction checks
                if (board.getPiece(new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn())) == null) {
                    pawnMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn()), null));
                    if (board.getPiece(new ChessPositionImpl(myPosition.getRow() + 2, myPosition.getColumn())) == null) {
                        pawnMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() + 2, myPosition.getColumn()), null));
                    }
                }

                // diagonal direction capture checks
                if ((myPosition.getRow() + 1 <= 8 && myPosition.getColumn() - 1 >= 1) && (board.getPiece(new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn() - 1)) != null && board.getPiece(new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn() - 1)).getTeamColor() != this.getTeamColor())) {
                    pawnMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn() - 1), null));
                }
                if ((myPosition.getRow() + 1 <= 8 && myPosition.getColumn() + 1 <= 8) && (board.getPiece(new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn() + 1)) != null && board.getPiece(new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn() + 1)).getTeamColor() != this.getTeamColor())) {
                    pawnMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn() + 1), null));
                }
            } else {
                // cardinal direction check
                if (board.getPiece(new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn())) == null) {
                    // check if you can promote your piece
                    if (myPosition.getRow() + 1 == 8) {
                        pawnMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn()), PieceType.QUEEN));
                        pawnMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn()), PieceType.BISHOP));
                        pawnMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn()), PieceType.ROOK));
                        pawnMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn()), PieceType.KNIGHT));
                    } else {
                        pawnMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn()), null));
                    }
                }

                // diagonal direction capture checks
                if ((myPosition.getRow() + 1 <= 8 && myPosition.getColumn() - 1 >= 1) && (board.getPiece(new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn() - 1)) != null && board.getPiece(new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn() - 1)).getTeamColor() != this.getTeamColor())) {
                    if (myPosition.getRow() + 1 == 8) {
                        pawnMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn() - 1), PieceType.QUEEN));
                        pawnMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn() - 1), PieceType.BISHOP));
                        pawnMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn() - 1), PieceType.ROOK));
                        pawnMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn() - 1), PieceType.KNIGHT));
                    } else {
                        pawnMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn() - 1), null));
                    }
                }
                if ((myPosition.getRow() + 1 <= 8 && myPosition.getColumn() + 1 <= 8) && (board.getPiece(new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn() + 1)) != null && board.getPiece(new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn() + 1)).getTeamColor() != this.getTeamColor())) {
                    if (myPosition.getRow() + 1 == 8) {
                        pawnMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn() + 1), PieceType.QUEEN));
                        pawnMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn() + 1), PieceType.BISHOP));
                        pawnMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn() + 1), PieceType.ROOK));
                        pawnMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn() + 1), PieceType.KNIGHT));
                    } else {
                        pawnMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn() + 1), null));
                    }
                }
            }
        } else {
            // check if pawn is in starting position first
            if (myPosition.getRow() == 7) {
                // cardinal direction checks
                if (board.getPiece(new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn())) == null) {
                    pawnMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn()), null));
                    if (board.getPiece(new ChessPositionImpl(myPosition.getRow() - 2, myPosition.getColumn())) == null) {
                        pawnMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() - 2, myPosition.getColumn()), null));
                    }
                }

                // diagonal direction capture checks
                if ((myPosition.getRow() - 1 >= 1 && myPosition.getColumn() - 1 >= 1) && (board.getPiece(new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn() - 1)) != null && board.getPiece(new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn() - 1)).getTeamColor() != this.getTeamColor())) {
                    pawnMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn() - 1), null));
                }
                if ((myPosition.getRow() - 1 >= 1 && myPosition.getColumn() + 1 <= 8) && (board.getPiece(new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn() + 1)) != null && board.getPiece(new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn() + 1)).getTeamColor() != this.getTeamColor())) {
                    pawnMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn() + 1), null));
                }
            } else {
                // cardinal direction check
                if (board.getPiece(new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn())) == null) {
                    // check if you can promote your piece
                    if (myPosition.getRow() - 1 == 1) {
                        pawnMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn()), PieceType.QUEEN));
                        pawnMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn()), PieceType.BISHOP));
                        pawnMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn()), PieceType.ROOK));
                        pawnMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn()), PieceType.KNIGHT));
                    } else {
                        pawnMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn()), null));
                    }
                }

                // diagonal direction capture checks
                if ((myPosition.getRow() - 1 >= 1 && myPosition.getColumn() - 1 >= 1) && (board.getPiece(new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn() - 1)) != null && board.getPiece(new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn() - 1)).getTeamColor() != this.getTeamColor())) {
                    if (myPosition.getRow() - 1 == 1) {
                        pawnMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn() - 1), PieceType.QUEEN));
                        pawnMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn() - 1), PieceType.BISHOP));
                        pawnMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn() - 1), PieceType.ROOK));
                        pawnMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn() - 1), PieceType.KNIGHT));
                    } else {
                        pawnMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn() - 1), null));
                    }
                }
                if ((myPosition.getRow() - 1 >= 1 && myPosition.getColumn() + 1 <= 8) && (board.getPiece(new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn() + 1)) != null && board.getPiece(new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn() + 1)).getTeamColor() != this.getTeamColor())) {
                    if (myPosition.getRow() - 1 == 1) {
                        pawnMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn() + 1), PieceType.QUEEN));
                        pawnMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn() + 1), PieceType.BISHOP));
                        pawnMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn() + 1), PieceType.ROOK));
                        pawnMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn() + 1), PieceType.KNIGHT));
                    } else {
                        pawnMoves.add(new ChessMoveImpl(myPosition, new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn() + 1), null));
                    }
                }
            }
        }
        return pawnMoves;
    }
}
