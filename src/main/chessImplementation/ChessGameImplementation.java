package chessImplementation;

import chess.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ChessGameImplementation implements ChessGame {
    private ChessGame game;
    private TeamColor team;
    private ChessBoard board;
    private ChessBoard tempBoard;
    private Set<ChessMove> validMoves = new HashSet<>();
    private Set<ChessMove> enemyValidMoves = new HashSet<>();

    public ChessGameImplementation() {
        this.board = new ChessBoardImplementation();
        this.team = TeamColor.WHITE;
    }

    public ChessGameImplementation(ChessGame other) {
        setBoard(new ChessBoardImplementation(other.getBoard()));
        setTeamTurn(other.getTeamTurn());
    }

    @Override
    public ChessGameImplementation clone() {
        // Create a new ChessGameImplementation and clone important data fields
        ChessGameImplementation cloneGame = new ChessGameImplementation();
        cloneGame.setBoard(new ChessBoardImplementation(this.getBoard()));
        cloneGame.setTeamTurn(this.getTeamTurn());

        return cloneGame;
    }

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
        Set<ChessMove> playerMoves = new HashSet<>();
        Set<ChessMove> tempPlayerMoves = new HashSet<>();
        ChessPiece chessPiece = getBoard().getPiece(startPosition);

        if (chessPiece == null) {
            return null;
        }

        // figure out what team turn it is with this
        setTeamTurn(chessPiece.getTeamColor());
        ChessGame copyGame = new ChessGameImplementation(this);

        playerMoves.addAll(chessPiece.pieceMoves(copyGame.getBoard(), startPosition));
        tempPlayerMoves.addAll(playerMoves);

        for (ChessMove chessMove : tempPlayerMoves) {
            // reset the game to the original game state before checking each move in playerMoves
            copyGame = new ChessGameImplementation(this);
            copyGame.getBoard().removePiece(chessMove.getStartPosition());
            copyGame.getBoard().addPiece(chessMove.getEndPosition(), chessPiece);
            tempBoard = copyGame.getBoard();
            if (isInCheckValidMovesHelper(getTeamTurn())) {
                playerMoves.remove(chessMove);
            }
        }
        return playerMoves;
    }

    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition startPosition = move.getStartPosition();
        ChessPosition endPosition = move.getEndPosition();
        ChessPiece chessPiece = board.getPiece(startPosition);
        ChessPiece promotionPiece = null;

        // check if it's the turn of the player making the move first
        if (chessPiece.getTeamColor() != getTeamTurn()) {
            throw new InvalidMoveException("It's not this player's turn to move yet!");
        }

        validMoves.addAll(validMoves(startPosition));

        if (!validMoves.contains(move)) {
            throw new InvalidMoveException("Attempted move is not one of the possible moves you can make for this piece!");
        }

        getBoard().removePiece(startPosition);

        if (move.getPromotionPiece() != null) {
            if (move.getPromotionPiece().name().equals("ROOK")) {
                promotionPiece = new Rook(chessPiece.getTeamColor(), ChessPiece.PieceType.ROOK);
            } else if (move.getPromotionPiece().name().equals("QUEEN")) {
                promotionPiece = new Rook(chessPiece.getTeamColor(), ChessPiece.PieceType.QUEEN);
            } else if (move.getPromotionPiece().name().equals("BISHOP")) {
                promotionPiece = new Rook(chessPiece.getTeamColor(), ChessPiece.PieceType.BISHOP);
            } else if (move.getPromotionPiece().name().equals("KNIGHT")) {
                promotionPiece = new Rook(chessPiece.getTeamColor(), ChessPiece.PieceType.KNIGHT);
            }
            getBoard().addPiece(endPosition, promotionPiece);
        } else {
            getBoard().addPiece(endPosition, chessPiece);
        }

        // set new board state and clear current validMoves set for current piece
        setBoard(getBoard());
        validMoves.clear();

        // change whose turn it is to make the next move
        if (chessPiece.getTeamColor() == TeamColor.WHITE) {
            setTeamTurn(TeamColor.BLACK);
        } else {
            setTeamTurn(TeamColor.WHITE);
        }
    }

    @Override
    public boolean isInCheck(TeamColor teamColor) {
        // Initialize to arbitrary value to start
        ChessPosition kingPosition = new ChessPositionImplementation(0,0);
        // reset enemyValidMoves to check their new valid moves
        enemyValidMoves.clear();

        for (var i = 1; i <= 8; i++) {
            for (var j = 1; j <= 8; j++) {
                ChessPosition chessPosition = new ChessPositionImplementation(i, j);
                ChessPiece currentPiece = board.getPiece(chessPosition);

                if (currentPiece != null && currentPiece.getTeamColor() != teamColor) {
                    enemyValidMoves.addAll(currentPiece.pieceMoves(board, chessPosition));
                }
                if (currentPiece != null && (currentPiece.getTeamColor() == teamColor && currentPiece.getPieceType() == ChessPiece.PieceType.KING)) {
                    kingPosition = chessPosition;
                }
            }
        }
        return isInCheckHelper(kingPosition);
    }

    private boolean isInCheckHelper(ChessPosition kingPosition) {
        for (ChessMove chessMove : enemyValidMoves) {
            ChessPosition endPosition = chessMove.getEndPosition();
            if (endPosition.equals(kingPosition)) {
                return true;
            }
        }
        return false;
    }

    private boolean isInCheckValidMovesHelper(TeamColor teamColor) {
        // Initialize to arbitrary value to start
        ChessPosition kingPosition = new ChessPositionImplementation(0,0);
        // reset enemyValidMoves to check their new valid moves
        enemyValidMoves.clear();

        for (var i = 1; i <= 8; i++) {
            for (var j = 1; j <= 8; j++) {
                ChessPosition chessPosition = new ChessPositionImplementation(i, j);
                ChessPiece currentPiece = tempBoard.getPiece(chessPosition);

                if (currentPiece != null && currentPiece.getTeamColor() != teamColor) {
                    enemyValidMoves.addAll(currentPiece.pieceMoves(tempBoard, chessPosition));
                }
                if (currentPiece != null && (currentPiece.getTeamColor() == teamColor && currentPiece.getPieceType() == ChessPiece.PieceType.KING)) {
                    kingPosition = chessPosition;
                }
            }
        }
        return isInCheckHelper(kingPosition);
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
