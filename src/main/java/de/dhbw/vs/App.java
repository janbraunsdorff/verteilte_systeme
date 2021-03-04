package de.dhbw.vs;

import de.dhbw.vs.api.Player;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }


    @Bean
    public CommandLineRunner commandLineRunner(Player player) throws Exception {
       return args -> {
           // find first in net
           while (!player.findFirstRandomInNet());

           // ask to play
           while (player.askToPlay()) ;

           // play
           player.play();
       };
    }
}
