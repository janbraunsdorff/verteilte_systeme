package de.dhbw.vs.domain.game.logic;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Move {
    private int columnNumber;

    @Id
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
