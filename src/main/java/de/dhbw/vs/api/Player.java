package de.dhbw.vs.api;

import de.dhbw.vs.Config;
import de.dhbw.vs.api.model.HelloExchange;
import de.dhbw.vs.api.model.PeerList;
import de.dhbw.vs.repo.Peer;
import de.dhbw.vs.repo.PeerRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class Player {

    private final Config config;
    private final PeerRepository repo;

    public Player(Config config, PeerRepository repository) {
        this.config = config;
        this.repo = repository;
    }

    public boolean findFirstRandomInNet() {
        for (int portNumber = config.getFromPort(); portNumber <= config.getToPort(); portNumber++) {
            if (portNumber == config.getMyPort()) continue;

            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:" + portNumber + "/online";
            System.out.print("Try: " + url + " -> ");

            try {
                HttpEntity<HelloExchange> request = new HttpEntity<HelloExchange>(new HelloExchange(config.getMyPort(), this.repo.getValues()));
                ResponseEntity<PeerList> response = restTemplate.postForEntity(url, request, PeerList.class);
                System.out.println(response.getStatusCode() + "   " + response.getBody());
                this.repo.addPeer(Objects.requireNonNull(response.getBody()).getPeerList());
                this.repo.addPeer(new Peer(portNumber, LocalDateTime.now()));
                return true;
            } catch (RestClientException ex) {
                System.out.println("---");
            }
        }
        return false;
    }

    public boolean askToPlay() {
        return false;
    }

    public boolean play(){
        return false;
    }
}
