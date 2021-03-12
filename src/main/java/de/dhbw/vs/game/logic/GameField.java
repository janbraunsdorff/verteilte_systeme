package de.dhbw.vs.game.logic;

import de.dhbw.vs.game.gui.Connect4Gui;
import de.dhbw.vs.game.network.NetworkInterface;

import java.util.ArrayList;
import java.util.List;

public class GameField implements GameInterface {
    private final Square[][] field = new Square[7][6];
    private final NetworkInterface network;
    private final Player player;
    private final Connect4Gui gui;
    private Status status;

    public GameField(NetworkInterface network, boolean isBeginningPlayer) {
        for (Square[] col : field) {
            for (int i = 0; i < col.length; i++) {
                col[i] = new Square();
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
    public void executeExternMove(Move move) {
        if (status.equals(Status.ACTIVE))
            return;

        executeMove(move);

        status = Status.ACTIVE;

        gui.update(field);
        checkForWinner();
    }

    @Override
    public void executeInternMove(Move move) {
        if (status.equals(Status.WAITING))
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

    }

    private void winnerDetected(List<int[]> winningSquares, SquareState latestConsecutiveSquareState) {
        gui.displayWinner(winningSquares);
        System.out.println("Player " + latestConsecutiveSquareState.toString() + " has won!");
    }

}
