package chessImplementation;

import chess.ChessPosition;

public class ChessPositionImplementation implements ChessPosition {
    private int row = 1;
    private int col = 1;

    public ChessPositionImplementation(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    @Override
    public int getColumn() {
        return col;
    }

    public void setColumn(int col) {
        this.col = col;
    }
}
