/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.tictactoe;

/**
 *
 * @author atalk
 */
import javax.swing.*;
import java.awt.*;

public class GameWindow {
    private final JButton[][] squares = new JButton[3][3];
    private final JLabel turnLabel = new JLabel();
    private final Board board = new Board();
    private char currentPlayer = 'X';
    private boolean gameOver = false;

    public GameWindow() {
        JFrame frame = new JFrame("Tic Tac Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 450);
        frame.setLocationRelativeTo(null);

        turnLabel.setFont(new Font("Arial", Font.BOLD, 24));
        turnLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        updateTurnLabel();

        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                JButton square = new JButton("");
                square.setFont(new Font("Arial", Font.BOLD, 60));
                final int r = row;
                final int c = col;
                square.addActionListener(e -> handleClick(r, c));
                squares[row][col] = square;
                boardPanel.add(square);
            }
        }

        frame.add(turnLabel, BorderLayout.NORTH);
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

       private void handleClick(int row, int col) {
       if (gameOver) {
           return;
       }
       if (!board.placeMark(row, col, currentPlayer)) {
           return;
       }
       squares[row][col].setText(String.valueOf(currentPlayer));

       char winner = board.getWinner();
       if (winner != ' ') {
           turnLabel.setText(winner + " wins!");
           gameOver = true;
           return;
       }
       if (board.isFull()) {
           turnLabel.setText("Draw!");
           gameOver = true;
           return;
       }

       currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
       updateTurnLabel();
   }

    private void updateTurnLabel() {
        turnLabel.setText("Turn: " + currentPlayer);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameWindow::new);
    }
}