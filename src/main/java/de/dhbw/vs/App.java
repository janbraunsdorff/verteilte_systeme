package de.dhbw.vs;

import de.dhbw.vs.api.Player;
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
    public CommandLineRunner commandLineRunner(Player player) throws Exception {
       return args -> {
           player.start();
       };
    }
}
