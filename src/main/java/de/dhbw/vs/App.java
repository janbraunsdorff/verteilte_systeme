package de.dhbw.vs;

import de.dhbw.vs.domain.statemaschine.Controller;
import de.dhbw.vs.domain.statemaschine.work.FindAnyPeer;
import de.dhbw.vs.domain.statemaschine.work.Play;
import de.dhbw.vs.domain.statemaschine.work.WannaPlay;
import de.dhbw.vs.repo.PeerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(App.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);
    }


    @Bean
    public CommandLineRunner commandLineRunner(Controller controller, Config config, PeerRepository repo) throws Exception {
        return args -> {
            controller.changeCurrentWork(new FindAnyPeer(config, repo));
            controller.waitCompletion();

            // find player and play
            List<Integer> nextPeersToPlay = repo.getNextPeersToPlay(1);
            if (!nextPeersToPlay.isEmpty()) {
                WannaPlay executable = new WannaPlay(nextPeersToPlay.get(0), config.getMyPort());
                controller.changeCurrentWork(executable);
                controller.waitCompletion();

                if (executable.getResponse()) {
                    controller.changeCurrentWork(new Play(true, nextPeersToPlay.get(0), controller));
                    controller.waitCompletion();
                }
            }
        };

    }

}
