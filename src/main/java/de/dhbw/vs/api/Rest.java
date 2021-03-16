package de.dhbw.vs.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class Rest {

    public boolean isPresent(int port){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + port + "/present";

        try {
            ResponseEntity<Void> response = restTemplate.getForEntity(url, Void.class);
            return response.getStatusCode() == HttpStatus.OK;
        } catch (RestClientException ex) {
            System.out.println("Peer on port: " + port + " is not present.");
        }

        return false;
    }
}
