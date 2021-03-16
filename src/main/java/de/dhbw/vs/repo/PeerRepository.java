package de.dhbw.vs.repo;

import de.dhbw.vs.Config;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PeerRepository {
    private final Integer myPort;
    private final SpringPeerRepository repository;


    public PeerRepository(Config config, SpringPeerRepository repository) {
        this.myPort = config.getMyPort();
        this.repository = repository;
    }


    public void addPeer(List<Peer> peers) {
        peers.forEach(this::addPeer);
    }

    public void addPeer(Peer peer) {
        var dbPeer = this.repository.findByPublicKey(peer.getPublicKey());
        if (dbPeer.isEmpty()){
            this.repository.save(peer);
            return;
        }

        dbPeer.get().setPort(peer.getPort());
        if (dbPeer.get().getLastUpdated().isBefore(peer.getLastUpdated())){
            dbPeer.get().setDeleted(peer.isDeleted());
            dbPeer.get().setLastUpdated(peer.getLastUpdated());
        }

        this.repository.save(peer);
    }

    public List<Peer> getPeerList() {
        return (List<Peer>) this.repository.findAll();
    }

    public List<Integer> getNextPeersToPlay(int numberOfPeersToAsk) {
        return this.repository.findAll(PageRequest.of(0, numberOfPeersToAsk))
                .stream()
                .map(Peer::getPort)
                .collect(Collectors.toList());
    }
}
