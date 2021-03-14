package de.dhbw.vs.api.model;

public class WannaPlayExchange {
    private int Port;

    public WannaPlayExchange(int port) {
        Port = port;
    }

    public WannaPlayExchange() {
    }

    public int getPort() {
        return Port;
    }

    public void setPort(int port) {
        Port = port;
    }
}
