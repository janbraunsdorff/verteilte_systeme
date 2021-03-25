package de.dhbw.vs.api;

import de.dhbw.vs.Config;
import de.dhbw.vs.api.model.HelloExchange;
import de.dhbw.vs.api.model.PeerList;
import de.dhbw.vs.api.model.WannaPlayExchange;
import de.dhbw.vs.domain.game.logic.Move;
import de.dhbw.vs.domain.player.State;
import de.dhbw.vs.repo.Peer;
import de.dhbw.vs.repo.PeerRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class ApiConnection {

    private final PeerRepository repo;
    private final Player player;
    private final Config config;


    public ApiConnection(PeerRepository repo, Player player, Config config){
        this.repo = repo;
        this.player = player;
        this.config = config;
    }

    @GetMapping("/present")
    public void present(){
    }



    @PostMapping("/wannaPlay")
    public boolean wannaPlay(@RequestBody WannaPlayExchange exchange) throws Exception {
        System.out.println("Do you want to play with someone? [y/N]: ");
        String input = System.console().readLine();

        if(input.equals("y")) {
            player.interrupt(State.PLAY_SECOND, String.valueOf(exchange.getPort()));
            return true;
        }

        return false;
    }

    @GetMapping("/deniedToPlay")
    public void deniedToPlay(){
        System.out.println("Hello Darkness my old friend");
    }


    @PostMapping("/receiveMove")
    public void receiveMove(@RequestBody Move move){
        this.player.getBrain().executeMove(move);
    }
}
