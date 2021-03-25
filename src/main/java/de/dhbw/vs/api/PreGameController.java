package de.dhbw.vs.api;

import de.dhbw.vs.Config;
import de.dhbw.vs.api.model.HelloExchange;
import de.dhbw.vs.api.model.PeerList;
import de.dhbw.vs.api.model.WannaPlayExchange;
import de.dhbw.vs.domain.statemaschine.Controller;
import de.dhbw.vs.domain.statemaschine.work.Play;
import de.dhbw.vs.repo.Peer;
import de.dhbw.vs.repo.PeerRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class PreGameController {

    private final PeerRepository repo;
    private final Config config;
    private final Controller controller;


    public PreGameController(PeerRepository repo, Config config, Controller controller){
        this.repo = repo;
        this.config = config;
        this.controller = controller;
    }

    @PostMapping("/online")
    public PeerList isOnline(@RequestBody HelloExchange exchange){
        this.repo.addPeer(new Peer(exchange.getPort(), LocalDateTime.now(), exchange.getPublicKey()));
        this.repo.addPeer(exchange.getPeers());
        return new PeerList(this.repo.getPeerList(), config.getKeyPair().getPublic().getEncoded());
    }


    @PostMapping("/wannaPlay")
    public boolean wannaPlay(@RequestBody WannaPlayExchange exchange) throws Exception {
        System.out.println("Got ask from "+ exchange.getPort()+ " [y/n]: ");
        String input = System.console().readLine();

        if(input.equals("y")) {
            this.controller.interrupt();
            this.controller.changeCurrentWork(new Play(false, exchange.getPort(), controller));
            return true;
        }

        return false;
    }
}
