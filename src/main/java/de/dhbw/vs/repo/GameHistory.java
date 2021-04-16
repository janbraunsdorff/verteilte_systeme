package de.dhbw.vs.repo;

import de.dhbw.vs.domain.game.logic.Move;

import javax.persistence.*;
import java.util.*;

@Entity
public class GameHistory {
    @Id
    @Column(name = "history_id")
    private String id;

    @Column(name = "moves")
    @OneToMany( mappedBy = "signature", fetch = FetchType.EAGER)
    private Set<Move> moves;

    public GameHistory(String id, Set<Move> moves) {
        this.id = id;
        this.moves = moves;
    }

    public GameHistory() {
        this.id = UUID.randomUUID().toString();
        this.moves = new HashSet<>();
    }

    public void addMove(Move move){
        this.moves.add(move);
    }


    public String getId() {
        return id;
    }

    public Set<Move> getMoves() {
        return moves;
    }
}
