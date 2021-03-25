package de.dhbw.vs.repo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
public class Peer {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "last_port_seen")
    private int port;

    @Column(name = "last_date_seen")
    private LocalDateTime lastUpdated;

    @Column(name = "id_deleted")
    private boolean isDeleted;

    @Column(name = "public_key", unique = true)
    private byte[] publicKey;

    public Peer() {
    }

    public Peer(int port, LocalDateTime lastUpdated, byte[] publicKey) {
        this.id = UUID.randomUUID().toString();
        this.port = port;
        this.lastUpdated = lastUpdated;
        this.isDeleted = false;
        this.publicKey = publicKey;
    }

    private Peer(int port, LocalDateTime lastUpdated, boolean isDeleted, byte[] publicKey) {
        this.id = UUID.randomUUID().toString();
        this.port = port;
        this.lastUpdated = lastUpdated;
        this.isDeleted = isDeleted;
        this.publicKey = publicKey;
    }

    public int getPort() {
        return port;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public String getId() {
        return id;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public void setPublicKey(byte[] publicKey) {
        this.publicKey = publicKey;
    }

    public Peer delete() {
        return new Peer(port, LocalDateTime.now(), true, publicKey);
    }

    @Override
    public String toString() {
        return "Peer{" +
                "port=" + port +
                ", lastUpdated=" + lastUpdated +
                ", isDeleted=" + isDeleted +
                '}';
    }
}


