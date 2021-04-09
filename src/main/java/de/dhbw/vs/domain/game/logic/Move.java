package de.dhbw.vs.domain.game.logic;

public class Move {
    private int columnNumber;
    private byte[] signature;

    public Move() {
    }

    public Move(int columnNumber, byte[] signature) {
        this.columnNumber = columnNumber;
        this.signature = signature;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public byte[] getSignature() {
        return signature;
    }
}
