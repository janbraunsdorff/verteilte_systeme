package de.dhbw.vs.domain.game.gui;

import de.dhbw.vs.domain.game.logic.GameInterface;
import de.dhbw.vs.domain.game.logic.Move;
import de.dhbw.vs.domain.game.logic.Square;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
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
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(500, 300);
        this.setTitle("Connect Four");
        this.setLayout(new BorderLayout());
        JPanel statusPanel = new JPanel();
        statusPanel.add(stateText);
        this.add(statusPanel, BorderLayout.NORTH);
        updateStateText();

        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(1, 7));

        for (int i = 0; i < 7; i++) {
            JPanel columnPanel = new JPanel();
            columnPanel.setLayout(new GridLayout(7, 1));
            int finalI = i;
            JButton columnButton = new JButton("x");
            columnButton.addActionListener(e -> insertDisc(finalI));
            columnPanel.add(columnButton);
            JPanel[] column = new JPanel[6];

            for (int u = 0; u < 6; u++) {
                JPanel pp = new JPanel();
                pp.setBackground(Color.WHITE);
                pp.setBorder(BorderFactory.createLineBorder(Color.black));
                columnPanel.add(pp);
                column[5 - u] = pp;
            }
            gameField[i] = column;
            gamePanel.add(columnPanel);
        }
        this.add(gamePanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void insertDisc(int column) {
        Move move = new Move(column);
        if (game.moveIsPossible(move))
            this.game.executeInternMove(move);
    }

    public void update(Square[][] field) {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                JPanel panel = gameField[i][j];
                panel.setBackground(field[i][j].getSquareState().getColor());
            }
        }
        updateStateText();
    }

    private void updateStateText() {
        this.stateText.setText(game.getPlayer().toString() + ", " + game.getStatus().toString());
    }

    public void displayWinner(List<int[]> winningSquares) {
        for (int[] squarePosition : winningSquares) {
            gameField[squarePosition[0]][squarePosition[1]].setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));
        }

        JOptionPane.showMessageDialog(null, "Das Spiel ist zu Ende", "Ergebnis", JOptionPane.INFORMATION_MESSAGE);
    }
}
