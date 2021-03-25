package de.dhbw.vs.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SpringPeerRepository extends CrudRepository<Peer, byte[]> {
    Page<Peer> findAll(Pageable pageable);
    Optional<Peer> findByPublicKey(byte[] publicKey);

}
