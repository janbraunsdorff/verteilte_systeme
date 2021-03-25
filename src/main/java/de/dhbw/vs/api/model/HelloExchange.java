package de.dhbw.vs.api.model;

import de.dhbw.vs.repo.Peer;

import java.util.List;

public class HelloExchange {
    private Integer port;
    private byte[] publicKey;
    private List<Peer> peers;

    public HelloExchange() {
    }

    public HelloExchange(Integer port, byte[] publicKey,  List<Peer> peers) {
        this.port = port;
        this.publicKey = publicKey;
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

    public byte[] getPublicKey() {
        return publicKey;
    }
}
