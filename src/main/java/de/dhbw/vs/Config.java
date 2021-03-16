package de.dhbw.vs;

import de.dhbw.vs.api.Rest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.security.KeyPair;
import java.util.Arrays;

@Configuration
public class Config implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    @Value("${network.start}")
    private int fromPort;

    @Value("${key.path}")
    private String keyPath;

    @Value("${db.path}")
    private String databasePath;

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
        byte encoded = sum(this.getKeyPair().getPublic().getEncoded());
        System.out.println(encoded);
        int keyPort =  encoded % 255;
        this.myPort = keyPort + fromPort;

        while (rest.isPresent(myPort)){
            keyPort = (keyPort+ 7) % 255;
            this.myPort = keyPort + fromPort;
        }

        System.out.println("Starting Peer on port: " + myPort);
        factory.setPort(myPort);
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
