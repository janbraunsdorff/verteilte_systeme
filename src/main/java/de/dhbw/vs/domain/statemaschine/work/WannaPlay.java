package de.dhbw.vs.domain.statemaschine.work;

import de.dhbw.vs.api.model.WannaPlayExchange;
import de.dhbw.vs.domain.statemaschine.Controller;
import de.dhbw.vs.domain.statemaschine.Executable;
import de.dhbw.vs.repo.PeerRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class WannaPlay implements Executable {

    private final int myPort;
    private final PeerRepository repo;
    private final Controller controller;

    public WannaPlay(int myPort, PeerRepository repo, Controller controller) {
        this.myPort = myPort;
        this.repo = repo;
        this.controller = controller;
    }

    @Override
    public boolean interruptable() {
        return true;
    }

    @Override
    public void interrupt() {

    }

    @Override
    public void run() {
        while(true) {
            List<Integer> nextPeersToPlay = repo.getNextPeersToPlay(1);
            int port = nextPeersToPlay.get(0);

            System.out.println("Press p to ask other players for a game, press r to view ranking: ");
            String input = System.console().readLine();

            if (input.equals("p")) {
                RestTemplate restTemplate = new RestTemplate();
                String url = "http://localhost:" + port + "/wannaPlay";
                System.out.println("Ask To play: " + url + " -> ");

                try {
                    HttpEntity<WannaPlayExchange> request = new HttpEntity<WannaPlayExchange>(new WannaPlayExchange(myPort));
                    ResponseEntity<Boolean> response = restTemplate.postForEntity(url, request, Boolean.class);
                    System.out.println(response.getStatusCode() + "   " + response.getBody());
                    if (response.getBody()) {
                        if(response.getBody()) {
                            controller.startGame(true, port);
                        }
                        return;
                    }
                } catch (RestClientException ex) {
                    System.out.println("no reachable");
                }

            } else if (input.equals("r")) {
                this.repo.getPeerList().forEach(p -> System.out.println(p.rankingInfo()));
            }
        }
    }
}
