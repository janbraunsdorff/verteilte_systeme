package de.dhbw.vs.domain.game.logic;

import de.dhbw.vs.domain.game.gui.Connect4Gui;
import de.dhbw.vs.domain.game.network.NetworkInterface;

import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class GameField implements GameInterface {
    // X, Y
    private final Square[][] field = new Square[7][6];
    private final NetworkInterface network;
    private final Player player;
    private final Connect4Gui gui;
    private Status status;

    public GameField(NetworkInterface network, boolean isBeginningPlayer) {
        for (Square[] col : field) {
            for (int row = 0; row < col.length; row++) {
                col[row] = new Square();
            }
        }
        this.network = network;
        this.player = isBeginningPlayer ? Player.YELLOW : Player.RED;
        this.status = isBeginningPlayer ? Status.ACTIVE : Status.WAITING;
        this.gui = new Connect4Gui(this);
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public Player getPlayer() {return player;}

    @Override
    public void executeExternMove(Move move) {
        if (status.equals(Status.ACTIVE) || status.equals(Status.TERMINATED))
            return;

        executeMove(move);

        status = Status.ACTIVE;

        gui.update(field);
        checkForWinner();
    }

    @Override
    public void executeInternMove(Move move) {
        if (status.equals(Status.WAITING) || status.equals(Status.TERMINATED) )
            return;

        executeMove(move);

        status = Status.WAITING;
        network.sendMove(move);

        gui.update(field);
        checkForWinner();
    }

    private void executeMove(Move move) {
        Square[] column = field[move.getColumnNumber()];

        for (Square square : column) {
            if (square.getSquareState().equals(SquareState.EMPTY)) {
                if (status.equals(Status.ACTIVE)) {
                    square.setSquareState(player.equals(Player.YELLOW) ? SquareState.YELLOW : SquareState.RED);
                } else if (status.equals(Status.WAITING)) {
                    square.setSquareState(player.equals(Player.YELLOW) ? SquareState.RED : SquareState.YELLOW);
                }
                break;
            }
        }
    }

    @Override
    public boolean moveIsPossible(Move move) {
        Square[] column = field[move.getColumnNumber()];

        for (Square square : column) {
            if (square.getSquareState().equals(SquareState.EMPTY))
                return true;
        }
        return false;
    }

    public void checkForWinner() {
        List<int[]> winningSquares = new ArrayList<>();
        int numberOfConsecutiveSquares;
        SquareState latestConsecutiveSquareState = SquareState.EMPTY;

        // check columns for winner
        for (int i = 0; i < field.length; i++) {

            numberOfConsecutiveSquares = 0;

            for (int j = 0; j < field[i].length; j++) {

                SquareState squareState = field[i][j].getSquareState();
                //SquareState squareState = square.getSquareState();

                if (!squareState.equals(SquareState.EMPTY) && squareState.equals(latestConsecutiveSquareState)) {
                    numberOfConsecutiveSquares++;
                    winningSquares.add(new int[]{i, j});
                } else {
                    numberOfConsecutiveSquares = 1;
                    winningSquares = new ArrayList<>();
                    winningSquares.add(new int[]{i, j});
                    latestConsecutiveSquareState = squareState;
                }

                if (numberOfConsecutiveSquares >= 4) {
                    winnerDetected(winningSquares, latestConsecutiveSquareState);
                    return;
                }
            }
        }

        // check rows for winner
        for (int i = 0; i < field[0].length; i++) {

            numberOfConsecutiveSquares = 0;

            for (int j = 0; j < field.length; j++) {

                SquareState squareState = field[j][i].getSquareState();
                //SquareState squareState = square.getSquareState();

                if (!squareState.equals(SquareState.EMPTY) && squareState.equals(latestConsecutiveSquareState)) {
                    numberOfConsecutiveSquares++;
                    winningSquares.add(new int[]{j, i});
                } else {
                    numberOfConsecutiveSquares = 1;
                    winningSquares = new ArrayList<>();
                    winningSquares.add(new int[]{j, i});
                    latestConsecutiveSquareState = squareState;
                }

                if (numberOfConsecutiveSquares >= 4) {
                    winnerDetected(winningSquares, latestConsecutiveSquareState);
                    return;
                }
            }
        }

        checkDiagonalDownUp();
        checkDiagonalUpDown();

    }

    public void destroy() {
        gui.dispatchEvent(new WindowEvent(gui, WindowEvent.WINDOW_CLOSING));
        gui.dispose();
    }

    private static class Diagonal {
        int x1;
        int y1;
        int x2;
        int y2;

        public Diagonal(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
    }

    private void checkDiagonalDownUp() {
        SquareState latestConsecutiveSquareState = SquareState.EMPTY;
        List<int[]> winningSquares = new ArrayList<>();

        List<Diagonal> cord = new ArrayList<Diagonal>();
        cord.add(new Diagonal(0, 2, 3, 5));
        cord.add(new Diagonal(0, 1, 4, 5));
        cord.add(new Diagonal(0, 0, 5, 5));
        cord.add(new Diagonal(1, 0, 6, 5));
        cord.add(new Diagonal(2, 0, 6, 4));
        cord.add(new Diagonal(3, 0, 6, 3));


        for (Diagonal dig : cord) {
            int x = dig.x1;
            int y = dig.y1;
            int numberOfConsecutiveSquares = 0;

            while (y <= dig.y2 && x <= dig.x2) {

                SquareState squareState = field[x][y].getSquareState();
                if (!squareState.equals(SquareState.EMPTY) && squareState.equals(latestConsecutiveSquareState)) {
                    numberOfConsecutiveSquares++;
                    winningSquares.add(new int[]{x, y});
                } else {
                    numberOfConsecutiveSquares = 1;
                    winningSquares = new ArrayList<>();
                    winningSquares.add(new int[]{x, y});
                    latestConsecutiveSquareState = squareState;
                }

                if (numberOfConsecutiveSquares >= 4) {
                    winnerDetected(winningSquares, latestConsecutiveSquareState);
                    return;
                }
                x++;
                y++;
            }
        }
    }

    private void checkDiagonalUpDown() {
        SquareState latestConsecutiveSquareState = SquareState.EMPTY;
        List<int[]> winningSquares = new ArrayList<>();

        List<Diagonal> cord = new ArrayList<Diagonal>();
        cord.add(new Diagonal(3,5,6,2));
        cord.add(new Diagonal(2,5, 6,1));
        cord.add(new Diagonal(1,5, 6,0));
        cord.add(new Diagonal(0,5, 5,0));
        cord.add(new Diagonal(0,4, 4,0));
        cord.add(new Diagonal(0, 3, 3, 0));


        for (Diagonal dig : cord) {
            int x = dig.x1;
            int y = dig.y1;
            int numberOfConsecutiveSquares = 0;

            while (y >= dig.y2 && x <= dig.x2) {

                SquareState squareState = field[x][y].getSquareState();
                if (!squareState.equals(SquareState.EMPTY) && squareState.equals(latestConsecutiveSquareState)) {
                    numberOfConsecutiveSquares++;
                    winningSquares.add(new int[]{x, y});
                } else {
                    numberOfConsecutiveSquares = 1;
                    winningSquares = new ArrayList<>();
                    winningSquares.add(new int[]{x, y});
                    latestConsecutiveSquareState = squareState;
                }

                if (numberOfConsecutiveSquares >= 4) {
                    winnerDetected(winningSquares, latestConsecutiveSquareState);
                    return;
                }
                x++;
                y--;
            }
        }
    }

    private void winnerDetected(List<int[]> winningSquares, SquareState latestConsecutiveSquareState) {
        status = Status.TERMINATED;
        new Thread(() ->{gui.displayWinner(winningSquares);}).start();
        System.out.println("Player " + latestConsecutiveSquareState.toString() + " has won!");
    }
}
