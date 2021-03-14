package de.dhbw.vs.domain.game.logic;

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

    public boolean isRed() {
        return this.getSquareState().equals(SquareState.RED);
    }

    public boolean isYellow() {
        return this.getSquareState().equals(SquareState.YELLOW);
    }

    public boolean isEmpty() {
        return this.getSquareState().equals(SquareState.EMPTY);
    }
}
