package de.dhbw.vs.domain.statemaschine;

import org.springframework.stereotype.Service;

@Service
public class Controller {
    private Executable currentThread;
    private Thread thread;

    public boolean changeCurrentWork(Executable executable) {
        if (currentThread == null || currentThread.interruptable()) {
            interrupt();
            this.currentThread = executable;
            this.thread = new Thread(this.currentThread);
            this.thread.start();
            return true;
        }
        return false;
    }

    public Thread getThread() {
        return thread;
    }

    public Executable getCurrentThread() {
        return currentThread;
    }

    public void interrupt() {
        if (this.currentThread != null) this.currentThread.interrupt();
        if (this.thread != null) this.thread.interrupt();
    }
}
