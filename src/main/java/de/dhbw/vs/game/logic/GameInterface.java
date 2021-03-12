package de.dhbw.vs.game.logic;

public interface GameInterface {
    Status getStatus();
    void executeExternMove(Move move);
    void executeInternMove(Move move);
    boolean moveIsPossible(Move move);

}
