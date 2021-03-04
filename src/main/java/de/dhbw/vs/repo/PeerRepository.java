package de.dhbw.vs.repo;

import de.dhbw.vs.Config;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class PeerRepository {
    private final Integer myPort;
    private final HashMap<Integer, Peer> peers;


    public PeerRepository(Config config) {
        this.myPort = config.getMyPort();
        this.peers = new HashMap<>();
    }


    public void addPeer(List<Peer> peers) {
        peers.forEach(this::addPeer);
    }

    public void addPeer(Peer peer) {
        if (peer.getPort() == myPort) {
            return;
        }

        if (this.peers.containsKey(peer.getPort())) {
            if (peer.getLastUpdated().isAfter(this.peers.get(peer.getPort()).getLastUpdated())) {
                if (peer.isDeleted()) {
                    this.peers.remove(peer.getPort());
                } else {
                    this.peers.put(peer.getPort(), peer);
                }
            }
        } else if (!peer.isDeleted()) {
            this.peers.put(peer.getPort(), peer);
        }

        this.peers.values().forEach(a -> System.out.println("[" + myPort + "] -> " + a));
    }


    public List<Peer> getValues() {
        return new ArrayList<>(this.peers.values());
    }
}
