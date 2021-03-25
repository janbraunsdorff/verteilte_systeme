package de.dhbw.vs.api;

import de.dhbw.vs.Config;
import de.dhbw.vs.api.model.HelloExchange;
import de.dhbw.vs.api.model.PeerList;
import de.dhbw.vs.domain.statemaschine.Controller;
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
}
