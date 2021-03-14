package de.dhbw.vs.domain.game.network;

import de.dhbw.vs.domain.game.logic.GameField;
import de.dhbw.vs.domain.game.logic.Move;

public class Network implements NetworkInterface{
    private GameField game1 = new GameField(this, true);
    private GameField game2 = new GameField(this, false);
    private boolean beginnerplayeronmove = true;

    public Network() {

    }

    @Override
    public void sendMove(Move move) {
        if(beginnerplayeronmove) {
            game2.executeExternMove(move);
            this.beginnerplayeronmove = false;
        }
        else {
            game1.executeExternMove(move);
            this.beginnerplayeronmove = true;
        }
    }

    public static void main(String[] args) {
        Network network = new Network();



    }


}
