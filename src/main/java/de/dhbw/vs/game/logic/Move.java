package de.dhbw.vs.game.logic;

public class Move {
    private final int columnNumber;

    public Move(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }
}
