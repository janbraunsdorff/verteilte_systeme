package de.dhbw.vs.domain.game.network;

import de.dhbw.vs.domain.game.logic.GameField;
import de.dhbw.vs.domain.game.logic.Move;

public class TestNetwork implements NetworkInterface{
    private final GameField game1 = new GameField(this, true, null, 0, null, null, null, null);
    private final GameField game2 = new GameField(this, false, null, 0, null, null, null, null);
    private boolean beginnerplayeronmove = true;

    public TestNetwork() {

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
        TestNetwork network = new TestNetwork();
    }


}
