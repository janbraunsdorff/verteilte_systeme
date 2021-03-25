package de.dhbw.vs.api;

import de.dhbw.vs.Config;
import de.dhbw.vs.domain.game.logic.Move;
import de.dhbw.vs.domain.statemaschine.Controller;
import de.dhbw.vs.domain.statemaschine.work.Play;
import de.dhbw.vs.repo.PeerRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    private final PeerRepository repo;
    private final Controller controller;
    private final Config config;


    public GameController(PeerRepository repo, Controller controller, Config config){
        this.repo = repo;
        this.controller = controller;
        this.config = config;
    }

    @PostMapping("/receiveMove")
    public void receiveMove(@RequestBody Move move){
        if (controller.getCurrentThread() instanceof Play) {
            ((Play) controller.getCurrentThread()).executeMove(move);
        }
    }
}
