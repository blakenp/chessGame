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

    private ChessBoard getNewBoard(){
        return new ChessBoardImplementation();
    }
    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {
        chessBoard[position.getRow() - 1][position.getColumn() - 1] = piece;
    }

    @Override
    public ChessPiece getPiece(ChessPosition position) {
        return chessBoard[position.getRow() - 1][position.getColumn() - 1];
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
            addPiece(new ChessPositionImplementation(position.getRow(), i), new ChessPieceImplementation(teamColor, rowPieces[i - 1]));
        }
        for (var i = position.getColumn(); i <= 8; i++) { // add all the pawns now
            if (teamColor == ChessGame.TeamColor.BLACK) {
                addPiece(new ChessPositionImplementation(position.getRow() - 1, i), new ChessPieceImplementation(teamColor, ChessPiece.PieceType.PAWN));
            } else {
                addPiece(new ChessPositionImplementation(position.getRow() + 1, i), new ChessPieceImplementation(teamColor, ChessPiece.PieceType.PAWN));
            }
        }
    }

    @Override
    public void resetBoard() {
        setDefaultPositions(new ChessPositionImplementation(1, 1));
        setDefaultPositions(new ChessPositionImplementation(8, 1));
    }
}
