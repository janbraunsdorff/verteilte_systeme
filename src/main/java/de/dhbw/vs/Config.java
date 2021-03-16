package de.dhbw.vs;

import de.dhbw.vs.api.Rest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.security.KeyPair;

@Configuration
public class Config implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    @Value("${network.start}")
    private int fromPort;

    @Value("${key.path}")
    private String keyPath;

    private int myPort;

    private Rest rest;

    public Config(Rest rest) {
        this.rest = rest;
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


    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        byte[] encoded = this.getKeyPair().getPrivate().getEncoded();
        var bias = + 128 + fromPort;
        int keyPort =  encoded[encoded.length - 1];
        this.myPort = keyPort + bias;

        while (rest.isPresent(myPort)){
            keyPort = (keyPort+ 7) % 255;
            this.myPort = keyPort + bias;
        }

        System.out.println("Starting Peer on port: " + myPort);
        factory.setPort(myPort);
    }
}
