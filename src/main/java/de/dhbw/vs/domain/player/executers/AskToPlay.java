package de.dhbw.vs.domain.player.executers;

import de.dhbw.vs.Config;
import de.dhbw.vs.api.Player;
import de.dhbw.vs.api.model.HelloExchange;
import de.dhbw.vs.api.model.PeerList;
import de.dhbw.vs.api.model.WannaPlayExchange;
import de.dhbw.vs.domain.player.State;
import de.dhbw.vs.domain.player.StateExecute;
import de.dhbw.vs.repo.PeerRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.*;

public class AskToPlay implements StateExecute {

    private final PeerRepository repo;
    private final Player player;
    private final Config config;
    private ExecutorService executorService;
    private CompletionService<Integer> completionService;

    public AskToPlay(PeerRepository repo, Player player, Config config) {
        this.repo = repo;
        this.player = player;
        this.config = config;
    }

    @Override
    public void execute(String ...args) throws Exception{
        final int numberOfPeersToAsk = 2;
        List<Integer> peersToAsk = this.repo.getNextPeersToPlay(numberOfPeersToAsk);
        executorService = Executors.newFixedThreadPool(numberOfPeersToAsk);
        completionService = new ExecutorCompletionService<Integer>(executorService);

        // Ask
        for (int i = 0; i < peersToAsk.size(); i++) {
            int finalI = i;
            completionService.submit(() -> askToPlayer(peersToAsk.get(finalI)));
        }

        // player
        Future<Integer> resultFuture = completionService.take();
        System.out.println("got from player: " + resultFuture.get());
        player.interrupt(State.PLAY_FIRST, String.valueOf(resultFuture.get()));


        // say no
        for (int i = 1; i < peersToAsk.size(); i++) {
            Integer decideNo = completionService.take().get();
            // TODO: Say no in background
            deniedToPlay(decideNo);
        }
    }

    private void deniedToPlay(int portNumber){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + portNumber + "/deniedToPlay";
        try {
            ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);
        } catch (RestClientException ex) {
            System.out.println("no reachable");
        }
    }

    private int askToPlayer(int portNumber){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + portNumber + "/wannaPlay";
        System.out.print("Ask To play: " + url + " -> ");

        try {
            HttpEntity<WannaPlayExchange> request = new HttpEntity<WannaPlayExchange>(new WannaPlayExchange(config.getMyPort()));
            ResponseEntity<Boolean> response = restTemplate.postForEntity(url, request, Boolean.class);
            System.out.println(response.getStatusCode() + "   " + response.getBody());
            if (response.getBody()){
                return portNumber;
            }
        } catch (RestClientException ex) {
            System.out.println("no reachable");
        }

        return -1;
    }

    @Override
    public void interrupt() {
        executorService.shutdownNow();
    }
}
