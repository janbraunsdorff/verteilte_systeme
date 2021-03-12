package de.dhbw.vs.game.network;

import de.dhbw.vs.game.logic.Move;

public interface NetworkInterface {
    void sendMove(Move move);
}
