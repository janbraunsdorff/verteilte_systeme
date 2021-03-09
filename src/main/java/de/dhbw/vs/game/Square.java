package de.dhbw.vs.game;

public class Square {
    private SquareState squareState;

    public Square() {
        squareState = SquareState.EMPTY;
    }

    public SquareState getSquareState() {
        return squareState;
    }

    public void setSquareState(SquareState squareState) {
        this.squareState = squareState;
    }
}
