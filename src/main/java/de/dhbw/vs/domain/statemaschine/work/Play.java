package de.dhbw.vs.domain.statemaschine.work;

import de.dhbw.vs.domain.game.logic.GameField;
import de.dhbw.vs.domain.game.logic.Move;
import de.dhbw.vs.domain.game.network.NetworkInterface;
import de.dhbw.vs.domain.statemaschine.Controller;
import de.dhbw.vs.domain.statemaschine.Executable;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Play implements Executable, NetworkInterface {

    private final boolean isFirst;
    private GameField game;
    private int portNumber;
    private final Controller controller;

    public Play(boolean isFirst, int port, Controller controller) {
        this.isFirst = isFirst;
        this.portNumber = port;
        this.controller = controller;
    }

    @Override
    public boolean interruptable() {
        return false;
    }

    @Override
    public void interrupt() {
        this.game.destroy();
        this.game = null;
    }

    @Override
    public void run() {
        System.out.println("I am " + (isFirst ? "first" : "second") + " and i send to " + portNumber);
        this.game = new GameField(this, isFirst, controller, portNumber);
    }

    public void executeMove(Move move) {
        game.executeExternMove(move);
    }

    @Override
    public void sendMove(Move move) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + portNumber + "/receiveMove";

        HttpEntity<Move> request = new HttpEntity<Move>(move);
        ResponseEntity<Void> response = restTemplate.postForEntity(url, request, Void.class);
    }
}
