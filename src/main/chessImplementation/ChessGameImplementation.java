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
        // Create a new ChessGameImplementation
        ChessGameImplementation cloneGame = new ChessGameImplementation();

        // Deep copy the ChessBoard for the new game
        cloneGame.setBoard(new ChessBoardImplementation(this.getBoard()));

        // Copy the team turn
        cloneGame.setTeamTurn(this.getTeamTurn());

        // Copy other properties as needed

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
        ChessGame copyGame = new ChessGameImplementation(this);
        Set<ChessMove> playerMoves = new HashSet<>();
        Set<ChessMove> tempPlayerMoves = new HashSet<>();
        Set<ChessMove> enemyMoves = new HashSet<>();
        ChessPiece chessPiece = copyGame.getBoard().getPiece(startPosition);
        ChessBoard originalBoard = this.getBoard();

        playerMoves.addAll(chessPiece.pieceMoves(copyGame.getBoard(), startPosition));
        tempPlayerMoves.addAll(playerMoves);
//        ChessPosition kingPosition = new ChessPositionImplementation(0, 0);

//        for (var i = 1; i <= 8; i++) {
//            for (var j = 1; j <= 8; j++) {
//                ChessPosition chessPosition = new ChessPositionImplementation(i, j);
//                ChessPiece currentPiece = copyGame.getBoard().getPiece(chessPosition);
//
//                if (currentPiece != null && currentPiece.getTeamColor() != getTeamTurn()) {
//                    enemyMoves.addAll(currentPiece.pieceMoves(copyGame.getBoard(), chessPosition));
//                }
//                if (currentPiece != null && (currentPiece.getTeamColor() == getTeamTurn() && currentPiece.getPieceType() == ChessPiece.PieceType.KING)) {
//                    kingPosition = chessPosition;
//                }
//            }
//        }

        for (ChessMove chessMove : tempPlayerMoves) {
            copyGame = new ChessGameImplementation(this);
//            copyGame.setBoard(board);
//            try {
//                makeMove(chessMove);
//            } catch (InvalidMoveException e) {
//                // remove the possible move if it puts king in check
//                playerMoves.remove(chessMove);
//            }
            copyGame.getBoard().removePiece(chessMove.getStartPosition());
            copyGame.getBoard().addPiece(chessMove.getEndPosition(), chessPiece);
            tempBoard = copyGame.getBoard();
            if (isInCheck(getTeamTurn())) {
                playerMoves.remove(chessMove);
            }

//            for (ChessMove enemyChessMove : enemyMoves) {
//                ChessPosition endPosition = enemyChessMove.getEndPosition();
//                if (endPosition.equals(kingPosition)) {
//                    playerMoves.remove(chessMove);
//                }
//            }
        }
        return playerMoves;
    }

    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition startPosition = move.getStartPosition();
        ChessPosition endPosition = move.getEndPosition();
        ChessPiece chessPiece = board.getPiece(startPosition);
        ChessGame copyGame = new ChessGameImplementation(this);
        ChessBoard copyBoard = board;

        // check if it's the turn of the player making the move first
        if (chessPiece.getTeamColor() != getTeamTurn()) {
            throw new InvalidMoveException("It's not this player's turn to move yet!");
        }

        validMoves.addAll(validMoves(startPosition));

        if (!validMoves.contains(move)) {
            throw new InvalidMoveException("Attempted move is not one of the possible moves you can make for this piece!");
        }

        getBoard().removePiece(startPosition);
        getBoard().addPiece(endPosition, chessPiece);

        // set new board state and clear current validMoves set for current piece
        setBoard(copyGame.getBoard());
        validMoves.clear();

        // Check if new move put King for this team in check. If it did, revert move and throw exception
//        if (isInCheck(chessPiece.getTeamColor())) {
//            setBoard(copyBoard);
//            throw new InvalidMoveException("This move puts the King in check!");
//        }

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

//        for (var i = 1; i <= 8; i++) {
//            for (var j = 1; j <= 8; j++) {
//                ChessPosition chessPosition = new ChessPositionImplementation(i, j);
//                ChessPiece currentPiece = board.getPiece(chessPosition);
//
//                if (currentPiece != null && currentPiece.getTeamColor() != teamColor) {
//                    validMoves.addAll(validMoves(chessPosition));
//                }
//                if (currentPiece != null && (currentPiece.getTeamColor() == teamColor && currentPiece.getPieceType() == ChessPiece.PieceType.KING)) {
//                    kingPosition = chessPosition;
//                }
//            }
//        }
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

    private boolean isInCheckHelper(ChessPosition kingPosition) {
//        for (ChessMove chessMove : validMoves) {
//            ChessPosition endPosition = chessMove.getEndPosition();
//            if (endPosition.equals(kingPosition)) {
//                return true;
//            }
//        }
        for (ChessMove chessMove : enemyValidMoves) {
            ChessPosition endPosition = chessMove.getEndPosition();
            if (endPosition.equals(kingPosition)) {
                return true;
            }
        }
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
