package de.dhbw.vs.api.model;

import de.dhbw.vs.repo.Peer;

import java.util.List;

public class HelloExchange {
    private Integer port;
    private List<Peer> peers;

    public HelloExchange() {
    }

    public HelloExchange(Integer port, List<Peer> peers) {
        this.port = port;
        this.peers = peers;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public List<Peer> getPeers() {
        return peers;
    }
}
