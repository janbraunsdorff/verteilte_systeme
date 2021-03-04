package de.dhbw.vs.api;

import de.dhbw.vs.api.model.HelloExchange;
import de.dhbw.vs.api.model.PeerList;
import de.dhbw.vs.repo.Peer;
import de.dhbw.vs.repo.PeerRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class ApiConnection {

    private final PeerRepository repo;

    public ApiConnection(PeerRepository repo){
        this.repo = repo;
    }

    @PostMapping("/online")
    public PeerList isOnline(@RequestBody HelloExchange exchange){
        this.repo.addPeer(new Peer(exchange.getPort(), LocalDateTime.now()));
        this.repo.addPeer(exchange.getPeers());
        return new PeerList(this.repo.getValues());
    }
}
