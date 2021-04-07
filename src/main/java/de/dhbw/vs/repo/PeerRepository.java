package de.dhbw.vs.repo;

import de.dhbw.vs.Config;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PeerRepository {
    private final Integer myPort;
    private final Config config;
    private final SpringPeerRepository repository;


    public PeerRepository(Config config, SpringPeerRepository repository) {
        this.myPort = config.getMyPort();
        this.config = config;
        this.repository = repository;
    }

    public void increasePeerRanking(int port) {
        var dbPeer = this.repository.findByPort(port);
        if (dbPeer.isPresent()){
            dbPeer.get().increaseRanking();
            this.repository.save(dbPeer.get());
        }
    }

    public void addPeer(List<Peer> peers) {
        peers.forEach(this::addPeer);
    }

    public void addPeer(Peer peer) {
        Optional<Peer> dbPeer = this.repository.findByPublicKey(peer.getPublicKey());
        if (dbPeer.isEmpty()){
            this.repository.save(peer);
            return;
        }

        dbPeer.get().setPort(peer.getPort());
        if (dbPeer.get().getLastUpdated().isBefore(peer.getLastUpdated())){
            dbPeer.get().setDeleted(peer.isDeleted());
            dbPeer.get().setLastUpdated(peer.getLastUpdated());
        }
        if(dbPeer.get().getRanking() < peer.getRanking()) {
            dbPeer.get().setRanking(peer.getRanking());
        }

        this.repository.save(dbPeer.get());
    }

    public List<Peer> getPeerList() {
        return (List<Peer>) this.repository.findAll();
    }

    public List<Integer> getNextPeersToPlay(int numberOfPeersToAsk) {
        return  ((List<Peer>) this.repository.findAll())
                .stream()
                .filter(p -> !p.isDeleted())
                .map(Peer::getPort)
                .filter( p -> p != config.getMyPort())
                .limit(numberOfPeersToAsk)
                .collect(Collectors.toList());
    }

    public void setPeerToDeleted(int port) {
        Optional<Peer> p = repository.findByPort(port);
        if(p.isPresent()) {
            Peer peer = p.get().delete();
            repository.save(peer);
        }
    }
}
