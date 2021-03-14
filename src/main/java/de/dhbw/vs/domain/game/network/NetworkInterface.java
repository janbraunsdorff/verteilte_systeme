package de.dhbw.vs.domain.game.network;

import de.dhbw.vs.domain.game.logic.Move;

public interface NetworkInterface {
    void sendMove(Move move);
}
