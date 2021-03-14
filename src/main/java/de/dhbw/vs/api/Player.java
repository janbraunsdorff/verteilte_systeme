package de.dhbw.vs.api;

import de.dhbw.vs.Config;
import de.dhbw.vs.domain.player.Brain;
import de.dhbw.vs.domain.player.State;

import de.dhbw.vs.repo.PeerRepository;
import org.springframework.stereotype.Service;


@Service
public class Player {

    private final Config config;
    private final PeerRepository repo;
    private State state;
    private Brain brain;



    public Player(Config config, PeerRepository repository) {
        this.config = config;
        this.repo = repository;
        this.state = State.FIND_FIRST_PEER;
        this.brain =  new Brain(config, repo, this);
    }

    public void start() throws Exception {
        brain.changeState(state);
        this.state = State.ASK_TO_PLAY;
        brain.changeState(state);
    }

    public void interrupt(State state, String... args) throws Exception{
        this.brain.changeState(state, args);
    }

    public Brain getBrain() {
        return brain;
    }
}
