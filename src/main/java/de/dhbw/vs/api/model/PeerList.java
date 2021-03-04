package de.dhbw.vs.api.model;

import de.dhbw.vs.repo.Peer;

import java.util.List;

public class PeerList {
    private List<Peer> peerList;

    public PeerList() {
    }

    public PeerList(List<Peer> peerList) {
        this.peerList = peerList;
    }

    public List<Peer> getPeerList() {
        return peerList;
    }
}
