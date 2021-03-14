package de.dhbw.vs.domain.game.logic;

public class Move {
    private int columnNumber;

    public Move(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    public Move() {
    }

    public int getColumnNumber() {
        return columnNumber;
    }
}
