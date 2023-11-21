package chessImplementation;

import chess.ChessPosition;

import java.util.Objects;

public class ChessPositionImpl implements ChessPosition {
    private int row = 1;
    private int col = 1;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPositionImpl that = (ChessPositionImpl) o;
        return row == that.row && col == that.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    public ChessPositionImpl(int row, int col) {
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
