package de.dhbw.vs.domain.statemaschine;

import de.dhbw.vs.Config;
import de.dhbw.vs.api.model.HelloExchange;
import de.dhbw.vs.api.model.PeerList;
import de.dhbw.vs.domain.statemaschine.work.FindAnyPeer;
import de.dhbw.vs.domain.statemaschine.work.Play;
import de.dhbw.vs.domain.statemaschine.work.WannaPlay;
import de.dhbw.vs.repo.Peer;
import de.dhbw.vs.repo.PeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class Controller {
    private Executable currentThread;
    private Thread thread;

    @Autowired
    private PeerRepository repo;

    @Autowired
    private Config config;

    public void start() {
        changeCurrentWork(new FindAnyPeer(config, repo, this));
    }

    public void changeState() {
        this.interrupt();
        if (this.currentThread instanceof FindAnyPeer) {
            WannaPlay executable = new WannaPlay(config.getMyPort(), repo, this);
            changeCurrentWork(executable);
        }
    }

    public void startGame(boolean first, int port) {
        this.interrupt();
        changeCurrentWork(new Play(first, port, this));
    }

    public void endGameAndWaitForNew() {
        this.interrupt();
        currentThread = null;
        WannaPlay executable = new WannaPlay(config.getMyPort(), repo, this);
        changeCurrentWork(executable);
    }

    public void changeCurrentWork(Executable executable) {
        if (currentThread == null || currentThread.interruptable()) {
            interrupt();
            this.currentThread = executable;
            this.thread = new Thread(this.currentThread);
            this.thread.start();

        }
    }

    public void gameDone(boolean haveIWon, int port){
        System.out.println("You have" + (haveIWon? " " : " not ") + "won.");
        System.out.println("Exchanging Ranking Information...");
        if(!haveIWon) {
            // increase ranking for contestant
            repo.increasePeerRanking(port);
            // send result to all known peers in list

            RestTemplate restTemplate = new RestTemplate();

            for(Peer peer : repo.getPeerList()) {
                System.out.println("send ranking information to: " + peer.getPort());
                String url = "http://localhost:" + peer.getPort() + "/online";
                try {
                    HttpEntity<HelloExchange> request = new HttpEntity<HelloExchange>(new HelloExchange(config.getMyPort(), config.getKeyPair().getPublic().getEncoded(), this.repo.getPeerList()));
                    ResponseEntity<PeerList> response = restTemplate.postForEntity(url, request, PeerList.class);
                } catch (RestClientException ex) {
                }
            }


            this.repo.getPeerList().forEach(System.out::println);
        }
    }

    public Executable getCurrentThread() {
        return currentThread;
    }

    public void interrupt() {
        if (this.currentThread != null) this.currentThread.interrupt();
        if (this.thread != null) this.thread.interrupt();
    }

    public boolean isAlreadyPlaying() {
        return this.currentThread instanceof Play;
    }

}
