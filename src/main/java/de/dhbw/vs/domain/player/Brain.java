package de.dhbw.vs.domain.player;

import de.dhbw.vs.Config;
import de.dhbw.vs.api.Player;
import de.dhbw.vs.domain.game.logic.Move;
import de.dhbw.vs.domain.player.executers.AskToPlay;
import de.dhbw.vs.domain.player.executers.FindFirstPeer;
import de.dhbw.vs.domain.player.executers.Play;
import de.dhbw.vs.repo.PeerRepository;

import java.util.HashMap;
import java.util.Map;

public class Brain {
    private Map<State, StateExecute> executions;
    private StateExecute currentExecution;

    public Brain(Config config, PeerRepository repository, Player player) {
        this.executions = new HashMap<>();
        executions.put(State.FIND_FIRST_PEER, new FindFirstPeer(config, repository));
        executions.put(State.ASK_TO_PLAY, new AskToPlay(repository, player, config));
        executions.put(State.PLAY_FIRST, new Play(true));
        executions.put(State.PLAY_SECOND, new Play(false));
    }

    public void changeState(State state, String ...args) throws Exception {
        System.out.println(state);
        if (currentExecution !=null) {
            currentExecution.interrupt();
        }
        this.currentExecution = this.executions.get(state);

        this.executions.get(state).execute(args);

        System.out.println("end");
    }

    public void executeMove(Move move){
        if (currentExecution instanceof Play) {
            ((Play) currentExecution).executeMove(move);
        }
    }
}
