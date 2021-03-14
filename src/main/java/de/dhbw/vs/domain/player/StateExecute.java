package de.dhbw.vs.domain.player;

public interface StateExecute {
    void execute(String... args) throws Exception;
    void interrupt();
}
