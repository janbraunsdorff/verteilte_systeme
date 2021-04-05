package de.dhbw.vs.domain.statemaschine;

public interface Executable extends Runnable{
    boolean interruptable();
    void interrupt();
}
