package de.dhbw.vs.domain.statemaschine.work;

import de.dhbw.vs.api.model.WannaPlayExchange;
import de.dhbw.vs.domain.statemaschine.Executable;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class WannaPlay implements Executable {


    private final int port;
    private final int myPort;
    private boolean ready;

    public WannaPlay(int port, int myPort) {
        this.port = port;
        this.myPort = myPort;
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
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + port + "/wannaPlay";
        System.out.println("Ask To play: " + url + " -> ");

        try {
            HttpEntity<WannaPlayExchange> request = new HttpEntity<WannaPlayExchange>(new WannaPlayExchange(myPort));
            ResponseEntity<Boolean> response = restTemplate.postForEntity(url, request, Boolean.class);
            System.out.println(response.getStatusCode() + "   " + response.getBody());
            if (response.getBody()){
                this.ready = response.getBody();
                return;
            }
        } catch (RestClientException ex) {
            System.out.println("no reachable");
        }

        this.ready = false;

    }

    public Boolean getResponse() {
        return ready;
    }
}
