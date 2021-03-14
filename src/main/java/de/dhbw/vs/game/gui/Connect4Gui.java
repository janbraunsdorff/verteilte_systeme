package de.dhbw.vs.game.gui;

import de.dhbw.vs.game.logic.GameInterface;
import de.dhbw.vs.game.logic.Move;
import de.dhbw.vs.game.logic.Square;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Connect4Gui extends JFrame {
    private final GameInterface game;
    private final JLabel stateText = new JLabel();
    private final JPanel[][] gameField = new JPanel[7][6];

    public Connect4Gui(GameInterface game) {
        this.game = game;
        init();
    }

    private void init() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(500, 300);
        this.setTitle("Connect Four");
        this.setLayout(new BorderLayout());
        JPanel statusPanel = new JPanel();
        statusPanel.add(stateText);
        this.stateText.setText(game.getPlayer().toString() + ", " + game.getStatus().toString());
        this.add(stateText, BorderLayout.NORTH);

        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(1, 7));

        for (int i = 0; i < 7; i++) {
            JPanel p = new JPanel();
            p.setLayout(new GridLayout(7, 1));
            int finalI = i;
            JButton j = new JButton("x");
            p.add(j);
            j.addActionListener(e -> insertDisc(finalI));
            JPanel[] column = new JPanel[6];

            for (int u = 0; u < 6; u++) {
                JPanel pp = new JPanel();
                pp.setBackground(Color.WHITE);
                pp.setBorder(BorderFactory.createLineBorder(Color.black));
                p.add(pp);
                column[5-u] = pp;
            }
            gameField[i] = column;
            gamePanel.add(p);
        }
        this.add(gamePanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void insertDisc(int column) {
        Move move = new Move(column);
        if(game.moveIsPossible(move))
            this.game.executeInternMove(move);
    }

    public void update(Square[][] field) {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                JPanel panel = gameField[i][j];
                panel.setBackground(field[i][j].getSquareState().getColor());
            }
        }
        this.stateText.setText(game.getPlayer().toString() + ", " + game.getStatus().toString());
    }

    public void displayWinner(List<int[]> winningSquares) {
        for(int[] squarePosition : winningSquares) {
            gameField[squarePosition[0]][squarePosition[1]].setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));
        }
    }
}
