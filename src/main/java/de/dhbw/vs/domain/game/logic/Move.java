package de.dhbw.vs.domain.game.logic;

public class Move {
    private int columnNumber;

    public Move() {
    }

    public Move(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }
}
