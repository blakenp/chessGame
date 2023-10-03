package chessImplementation;

import chess.*;

import java.util.Collection;
import java.util.Set;

public class ChessPieceImplementation implements ChessPiece {
    private ChessGame.TeamColor color;
    private PieceType type;
    private Set<ChessMove> validMoves;

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
        return null;
    }
}
