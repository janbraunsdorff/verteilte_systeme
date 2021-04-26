package de.dhbw.vs;

import de.dhbw.vs.domain.crypto.Cryptop;
import de.dhbw.vs.repo.PeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

@Configuration
public class Config implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    @Value("${network.start}")
    private int fromPort;

    @Value("${key.path}")
    private String keyPath;

    @Value("${db.path}")
    private String databasePath;

    @Value("${proof}")
    private boolean proof;

    private int myPort;

    @Autowired
    PeerRepository repository;

    public Config() {
    }

    public int getMyPort() {
        return myPort;
    }

    public int getFromPort() {
        return fromPort;
    }

    public int getToPort() {
        return fromPort + 255;
    }

    public KeyPair getKeyPair() {
        try {
            FileInputStream fi = new FileInputStream(keyPath);
            ObjectInputStream oi = new ObjectInputStream(fi);
            return (KeyPair) oi.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return null;
    }

    public Cryptop getCrypto(){
        return new Cryptop(getKeyPair());
    }


    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        checkIfKeyIsPresentOrGenrate();
        byte encoded = sum(this.getKeyPair().getPublic().getEncoded());
        int encodedInt = Math.abs(encoded);
        int keyPort = encodedInt % 255;
        this.myPort = keyPort + fromPort;

        while (isPresent(myPort)) {
            keyPort = (keyPort + 7) % 255;
            this.myPort = keyPort + fromPort;
        }

        System.out.println("Starting Peer on port: " + myPort);
        factory.setPort(myPort);

        if (this.proof) {
            System.out.println("Last Games:");
            repository.getPeerList().forEach(p ->{
                p.getRankingHistories().forEach(h -> {
                    String moves = h.getMoves().stream().map(m -> m.toString()).reduce(",", String::concat);
                    System.out.println("Peer:" + p.getPublicKey() + ", SpielId: " + h.getId() + ", [" + moves + "]");
                });
            });
        }

    }

    private void checkIfKeyIsPresentOrGenrate() {
        if (Files.exists(Path.of(keyPath))) {
            return;
        }
        try {
            Cryptop c = new Cryptop();
            FileOutputStream fout = new FileOutputStream(keyPath);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(c.getKeys());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isPresent(int port) {
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

    public byte sum(byte... bytes) {
        byte total = 0;
        for (byte b : bytes) {
            total += b;
        }
        return total;
    }


    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.h2.Driver");
        dataSourceBuilder.url(databasePath);
        dataSourceBuilder.username("SA");
        dataSourceBuilder.password("");
        return dataSourceBuilder.build();
    }
}
