package de.dhbw.vs.domain.game.logic;


import de.dhbw.vs.repo.GameHistory;
import de.dhbw.vs.repo.Peer;

import javax.persistence.*;

@Entity
public class Move {
    @Transient
    public static int counter = 0;

    @Column (name = "column_number")
    private int columnNumber;

    @Id
    @Column(name = "signatuer")
    private byte[] signature;

    @Column(name = "order_moves")
    private int order;

    public Move() {
    }

    public Move(int columnNumber, byte[] signature) {
        this.columnNumber = columnNumber;
        this.signature = signature;
        this.order = counter;
        Move.counter++;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public byte[] getSignature() {
        return signature;
    }

    public static int getCounter() {
        return counter;
    }

    public int getOrder() {
        return order;
    }

}
