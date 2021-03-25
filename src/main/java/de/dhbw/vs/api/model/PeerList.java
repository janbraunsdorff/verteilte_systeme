package de.dhbw.vs.api.model;

import de.dhbw.vs.repo.Peer;

import java.util.List;

public class PeerList {
    private List<Peer> peerList;
    private byte[] publicKey;

    public PeerList() {
    }

    public PeerList(List<Peer> peerList, byte[] publicKey) {
        this.peerList = peerList;
        this.publicKey = publicKey;
    }

    public List<Peer> getPeerList() {
        return peerList;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }
}
