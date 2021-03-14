package de.dhbw.vs.domain.game.logic;

public interface GameInterface {
    Status getStatus();

    Player getPlayer();

    void executeExternMove(Move move);
    void executeInternMove(Move move);
    boolean moveIsPossible(Move move);

}
