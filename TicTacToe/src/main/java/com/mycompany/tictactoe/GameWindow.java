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
    private char currentPlayer = 'X';

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
                squares[row][col] = square;
                boardPanel.add(square);
            }
        }

        frame.add(turnLabel, BorderLayout.NORTH);
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void updateTurnLabel() {
        turnLabel.setText("Turn: " + currentPlayer);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameWindow::new);
    }
}