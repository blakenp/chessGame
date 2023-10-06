package chessImplementation;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Queen extends ChessPieceImplementation {
    public Queen(ChessGame.TeamColor color, PieceType type) {
        super(color, type);
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Set<ChessMove> allMoves = new HashSet<>();
        allMoves.addAll(diagonalMoves(board, myPosition));
        allMoves.addAll(cardinalMoves(board, myPosition));
        return allMoves;
    }
}
