package de.dhbw.vs.domain.game.logic;


import de.dhbw.vs.repo.GameHistory;
import de.dhbw.vs.repo.Peer;

import javax.persistence.*;
import java.util.Arrays;

@Entity
public class Move {
    @Transient
    public static int counter = 0;

    @Column (name = "column_number")
    private int columnNumber;

    // @Id
    @Column(name = "signatuer")
    private byte[] signature;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "move_id")
    private Long id;

    @Column(name = "order_moves")
    private int order;

    public Move() {
        Move.counter++;
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


    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public static void setCounter(int counter) {
        Move.counter = counter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Move{" +
                "columnNumber=" + columnNumber +
                // ", signature=" + Arrays.toString(signature) +
                ", order=" + order +
                '}';
    }
}
