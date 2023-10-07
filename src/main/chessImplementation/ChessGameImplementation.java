package chessImplementation;

import chess.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ChessGameImplementation implements ChessGame {
    private TeamColor team;
    private ChessBoard board = new ChessBoardImplementation();
    private Set<ChessMove> possibleMoves = new HashSet<>();
    @Override
    public TeamColor getTeamTurn() {
        return team;
    }

    @Override
    public void setTeamTurn(TeamColor team) {
        this.team = team;
    }

    @Override
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        possibleMoves.clear();
        ChessPiece chessPiece = board.getPiece(startPosition);
        possibleMoves.addAll(chessPiece.pieceMoves(board, startPosition));
        return possibleMoves;
    }

    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition startPosition = move.getStartPosition();
        ChessPosition endPosition = move.getEndPosition();
        ChessPiece chessPiece = board.getPiece(startPosition);

        validMoves(startPosition);

        if (!possibleMoves.contains(move)) {
            throw new InvalidMoveException("Attempted move is not one of the possible moves you can make for this piece!");
        }

        board.removePiece(startPosition);
        board.addPiece(endPosition, chessPiece);
    }

    @Override
    public boolean isInCheck(TeamColor teamColor) {
        return false;
    }

    @Override
    public boolean isInCheckmate(TeamColor teamColor) {
        return false;
    }

    @Override
    public boolean isInStalemate(TeamColor teamColor) {
        return false;
    }

    @Override
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    @Override
    public ChessBoard getBoard() {
        return board;
    }
}
