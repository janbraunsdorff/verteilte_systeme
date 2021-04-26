package de.dhbw.vs.repo;

import de.dhbw.vs.domain.game.logic.Move;

import javax.persistence.*;
import java.util.*;

@Entity(name = "GameHistory")
@Table(name = "game_history")
public class GameHistory {
    @Id
    @Column(name = "id_history")
    private String id;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Move> moves;


    public GameHistory(String id, Set<Move> moves) {
        this.id = id;
        this.moves = moves;
    }

    public GameHistory() {
        this.id =  UUID.randomUUID().toString();
        this.moves = new HashSet<>();
    }

    public void addMove(Move move){
      this.moves.add(move);
    }

    public Set<Move> getMoves() {
        return moves;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMoves(Set<Move> moves) {
        this.moves = moves;
    }
}

