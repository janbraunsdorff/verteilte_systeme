package de.dhbw.vs.repo;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
public class Peer {
    @Column(name = "last_port_seen")
    private int port;

    @Column(name = "last_date_seen")
    private LocalDateTime lastUpdated;

    @Column(name = "id_deleted")
    private boolean isDeleted;

    @Column(name = "ranking")
    private int ranking;

    @Id
    @Column(name = "public_key", unique = true)
    private byte[] publicKey;

    @Column(name="ranking_history")
    @OneToMany(mappedBy = "id", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Set<GameHistory> rankingHistories;

    public Peer() {
    }

    public Peer(int port, LocalDateTime lastUpdated, byte[] publicKey, Set<GameHistory> rankingHistories) {
        this.port = port;
        this.lastUpdated = lastUpdated;
        this.rankingHistories = rankingHistories;
        this.isDeleted = false;
        this.publicKey = publicKey;
        this.ranking = 0;
    }

    public Peer(int port, LocalDateTime lastUpdated, byte[] publicKey, int ranking) {
        this.port = port;
        this.lastUpdated = lastUpdated;
        this.isDeleted = false;
        this.publicKey = publicKey;
        this.ranking = ranking;
    }

    private Peer(int port, LocalDateTime lastUpdated, boolean isDeleted, byte[] publicKey,  Set<GameHistory> rankingHistories) {
        this.port = port;
        this.lastUpdated = lastUpdated;
        this.isDeleted = isDeleted;
        this.publicKey = publicKey;
        this.rankingHistories = rankingHistories;
        this.ranking = 0;
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

    public byte[] getPublicKey() {
        return publicKey;
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
        return new Peer(port, LocalDateTime.now(), true, publicKey, new HashSet<>());
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public int increaseRanking() {
        this.ranking++;
        return this.ranking;
    }


    @Override
    public String toString() {
        return "Peer{" +
                "port=" + port +
                ", lastUpdated=" + lastUpdated +
                ", isDeleted=" + isDeleted +
                ", ranking=" + ranking +
                '}';
    }

    public String rankingInfo() {
        return "Peer{" +
                "port=" + port +
                ", ranking=" + ranking +
                '}';
    }
}


