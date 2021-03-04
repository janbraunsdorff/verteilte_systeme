package de.dhbw.vs.repo;

import java.time.LocalDateTime;

public class Peer {
    private int port;
    private LocalDateTime lastUpdated;
    private boolean isDeleted;

    public Peer() {
    }

    public Peer(int port, LocalDateTime lastUpdated) {
        this.port = port;
        this.lastUpdated = lastUpdated;
        this.isDeleted = false;
    }

    private Peer(int port, LocalDateTime lastUpdated, boolean isDeleted) {
        this.port = port;
        this.lastUpdated = lastUpdated;
        this.isDeleted = isDeleted;
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

    public Peer delete() {
        return new Peer(port, LocalDateTime.now(), true);
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


