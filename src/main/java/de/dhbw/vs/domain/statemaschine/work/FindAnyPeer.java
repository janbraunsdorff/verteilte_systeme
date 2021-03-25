package de.dhbw.vs.domain.statemaschine.work;

import de.dhbw.vs.Config;
import de.dhbw.vs.api.model.HelloExchange;
import de.dhbw.vs.api.model.PeerList;
import de.dhbw.vs.domain.statemaschine.Executable;
import de.dhbw.vs.repo.Peer;
import de.dhbw.vs.repo.PeerRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Objects;

public class FindAnyPeer implements Executable {

    private final Config config;
    private final PeerRepository repo;
    private boolean hasToInterrupt;


    public FindAnyPeer(Config config, PeerRepository repository) {
        this.config = config;
        this.repo = repository;
        this.hasToInterrupt = false;
    }

    @Override
    public boolean interruptable() {
        return true;
    }

    @Override
    public void interrupt() {
        this.hasToInterrupt = true;
    }

    @Override
    public void run() {
        if (!repo.getPeerList().isEmpty() || hasToInterrupt){
            this.repo.getPeerList().forEach(System.out::println);
            return;
        }
        System.out.println("Find Any partner");
        for (int portNumber = config.getFromPort(); portNumber <= config.getToPort(); portNumber++) {
            if (portNumber == config.getMyPort()) continue;

            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:" + portNumber + "/online";
            System.out.print("Try: " + url + " -> ");

            try {
                HttpEntity<HelloExchange> request = new HttpEntity<HelloExchange>(new HelloExchange(config.getMyPort(), config.getKeyPair().getPublic().getEncoded(), this.repo.getPeerList()));
                ResponseEntity<PeerList> response = restTemplate.postForEntity(url, request, PeerList.class);
                this.repo.addPeer(Objects.requireNonNull(response.getBody()).getPeerList());
                this.repo.addPeer(new Peer(portNumber, LocalDateTime.now(), response.getBody().getPublicKey()));
                System.out.println(response.getStatusCode() + "   " + response.getBody());
                this.repo.getPeerList().forEach(System.out::println);
                return;
            } catch (RestClientException ex) {
                System.out.println("---");
            }

            if (!repo.getPeerList().isEmpty() || hasToInterrupt){
                this.repo.getPeerList().forEach(System.out::println);
                return;
            }

        }
    }
}
