package de.dhbw.vs.domain.player.executers;

import de.dhbw.vs.domain.game.logic.GameField;
import de.dhbw.vs.domain.game.logic.Move;
import de.dhbw.vs.domain.game.logic.Status;
import de.dhbw.vs.domain.game.network.NetworkInterface;
import de.dhbw.vs.domain.player.StateExecute;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class Play implements StateExecute, NetworkInterface {


    private final boolean isFirst;
    private GameField game;
    private Integer portNumber;

    public Play(boolean isFirst) {
        this.isFirst = isFirst;
    }

    @Override
    public void execute(String... args) throws Exception {
        System.out.println("I am " + (isFirst? "first": "second") + " and i send to " + args[0]);
        this.portNumber = Integer.valueOf(args[0]);
        this.game = new GameField(this, isFirst);
    }

    @Override
    public void interrupt() {
        this.game.destroy();
        this.game = null;
    }

    public void executeMove(Move move){
        game.executeExternMove(move);
    }

    // NetworkInterface
    @Override
    public void sendMove(Move move) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + portNumber + "/receiveMove";

        HttpEntity<Move> request = new HttpEntity<Move>(move);
        ResponseEntity<Void> response = restTemplate.postForEntity(url, request, Void.class);
    }
}
