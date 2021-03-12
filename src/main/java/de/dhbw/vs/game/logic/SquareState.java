package de.dhbw.vs.game.logic;

import java.awt.*;

public enum SquareState {
    EMPTY(Color.WHITE),
    YELLOW(Color.YELLOW),
    RED(Color.RED);

    private final Color color;

    SquareState(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
