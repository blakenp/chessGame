package chessImplementation;

import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.Objects;

public class ChessMoveImplementation implements ChessMove {
    private ChessPosition startPosition;
    private ChessPosition endPosition;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessMoveImplementation that = (ChessMoveImplementation) o;
        return Objects.equals(startPosition, that.startPosition) && Objects.equals(endPosition, that.endPosition) && promotionPiece == that.promotionPiece;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startPosition, endPosition, promotionPiece);
    }

    private ChessPiece.PieceType promotionPiece;

    public ChessMoveImplementation(ChessPosition startPosition, ChessPosition endPosition, ChessPiece.PieceType promotionPiece) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.promotionPiece = promotionPiece;
    }
    @Override
    public ChessPosition getStartPosition() {
        return startPosition;
    }

    @Override
    public ChessPosition getEndPosition() {
        return endPosition;
    }

    @Override
    public ChessPiece.PieceType getPromotionPiece() {
        return promotionPiece;
    }
}
