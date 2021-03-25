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
            while (controller.getThread().isAlive()) {
                Thread.sleep(1000);
            }

            // frage:
            Integer port = repo.getNextPeersToPlay(1).get(0);
            if (port < config.getMyPort()) {
                System.out.println("frage");
                WannaPlay executable = new WannaPlay(port, config.getMyPort());
                controller.changeCurrentWork(executable);

                while (controller.getThread().isAlive()) {
                    Thread.sleep(1000);
                }

                System.out.println("start game");
                controller.changeCurrentWork(new Play(true, port));
            }else {
                System.out.println("warten");
            }
        };


    }

}
