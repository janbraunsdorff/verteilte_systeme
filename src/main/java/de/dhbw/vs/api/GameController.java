package de.dhbw.vs.api;

import de.dhbw.vs.domain.game.logic.Move;
import de.dhbw.vs.domain.statemaschine.Controller;
import de.dhbw.vs.domain.statemaschine.work.Play;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    private final Controller controller;

    public GameController(Controller controller){
        this.controller = controller;
    }

    @PostMapping("/receiveMove")
    public void receiveMove(@RequestBody Move move){
        if (controller.getCurrentThread() instanceof Play) {
            ((Play) controller.getCurrentThread()).executeMove(move);
        }
    }
}
