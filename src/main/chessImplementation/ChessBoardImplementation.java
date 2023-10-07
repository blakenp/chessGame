package chessImplementation;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

public class ChessBoardImplementation implements ChessBoard {
    private ChessPiece[][] chessBoard;

    public ChessBoardImplementation() {
        this.chessBoard = new ChessPiece[8][8];
    }

    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {
        chessBoard[position.getRow() - 1][position.getColumn() - 1] = piece;
    }

    public void removePiece(ChessPosition position) {
        chessBoard[position.getRow() - 1][position.getColumn() - 1] = null;
    }

    @Override
    public ChessPiece getPiece(ChessPosition position) {
        return chessBoard[position.getRow() - 1][position.getColumn() - 1];
    }

    /**
     *
     * @param pieceColor
     * @param type
     * @return the new translated piece by calling the correct chessPiece constructor for that pieceType
     */
    private ChessPiece translateToNewPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type){
        if (type == ChessPiece.PieceType.ROOK) {
            return new Rook(pieceColor, type);
        } else if (type == ChessPiece.PieceType.KNIGHT) {
            return new Knight(pieceColor, type);
        } else if (type == ChessPiece.PieceType.BISHOP) {
            return new Bishop(pieceColor, type);
        } else if (type == ChessPiece.PieceType.QUEEN) {
            return new Queen(pieceColor, type);
        } else if (type == ChessPiece.PieceType.KING) {
            return new King(pieceColor, type);
        } else {
            return new Pawn(pieceColor, type);
        }
    }

    private void setDefaultPositions(ChessPosition position) {
        ChessPiece.PieceType[] vitalChessPieces =
                {
                        ChessPiece.PieceType.ROOK,
                        ChessPiece.PieceType.KNIGHT,
                        ChessPiece.PieceType.BISHOP,
                        ChessPiece.PieceType.QUEEN,
                        ChessPiece.PieceType.KING,
                        ChessPiece.PieceType.BISHOP,
                        ChessPiece.PieceType.KNIGHT,
                        ChessPiece.PieceType.ROOK
                };
        if (position.getRow() == 1 && position.getRow() == 1) {
            fillRows(position, vitalChessPieces, ChessGame.TeamColor.WHITE); // fill top rows of chess pieces
        } else {
            fillRows(position, vitalChessPieces, ChessGame.TeamColor.BLACK); // fill bottom rows of chess pieces
        }
    }

    private void fillRows(ChessPosition position, ChessPiece.PieceType[] rowPieces, ChessGame.TeamColor teamColor) {
        for (var i = position.getColumn(); i <= 8; i++) { // add all of first row pieces
            addPiece(new ChessPositionImplementation(position.getRow(), i), translateToNewPiece(teamColor, rowPieces[i - 1]));
        }
        for (var i = position.getColumn(); i <= 8; i++) { // add all the pawns now
            if (teamColor == ChessGame.TeamColor.BLACK) {
                addPiece(new ChessPositionImplementation(position.getRow() - 1, i), translateToNewPiece(teamColor, ChessPiece.PieceType.PAWN));
            } else {
                addPiece(new ChessPositionImplementation(position.getRow() + 1, i), translateToNewPiece(teamColor, ChessPiece.PieceType.PAWN));
            }
        }
    }

    @Override
    public void resetBoard() {
        setDefaultPositions(new ChessPositionImplementation(1, 1));
        setDefaultPositions(new ChessPositionImplementation(8, 1));
    }
}
