package de.dhbw.vs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Value("${server.port}")
    private int myPort;

    @Value("${port.from}")
    private int fromPort;

    @Value("${port.to}")
    private int toPort;

    public int getMyPort() {
        return myPort;
    }

    public int getFromPort() {
        return fromPort;
    }

    public int getToPort() {
        return toPort;
    }
}
