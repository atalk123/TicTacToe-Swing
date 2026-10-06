/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tictactoe;

/**
 *
 * @author atalk
 */
import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private static final int TURN_SECONDS = 10;
    private static final int COMPUTER_DELAY_MS = 500;

    private final JButton[][] squares = new JButton[3][3];
    private final JLabel turnLabel = new JLabel();
    private final JLabel timerLabel = new JLabel();
    private final Timer turnTimer;
    private Board board = new Board();
    private ComputerPlayer computer = null;
    private char currentPlayer = 'X';
    private boolean gameOver = false;
    private int secondsLeft = TURN_SECONDS;

    public GamePanel(Runnable onBackToMenu) {
        setLayout(new BorderLayout());

        Font labelFont = new Font("Arial", Font.BOLD, 24);
        turnLabel.setFont(labelFont);
        timerLabel.setFont(labelFont);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.add(turnLabel, BorderLayout.WEST);
        topPanel.add(timerLabel, BorderLayout.EAST);

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

        JButton menuButton = new JButton("Menu");
        menuButton.setFont(new Font("Arial", Font.PLAIN, 20));
        menuButton.addActionListener(e -> onBackToMenu.run());

        add(topPanel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        add(menuButton, BorderLayout.SOUTH);

        turnTimer = new Timer(1000, e -> tick());
    }

    public void startNewGame(boolean vsComputer) {
        board = new Board();
        computer = vsComputer ? new ComputerPlayer('O') : null;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                squares[row][col].setText("");
            }
        }
        currentPlayer = 'X';
        gameOver = false;
        secondsLeft = TURN_SECONDS;
        updateTurnLabel();
        updateTimerLabel();
        turnTimer.restart();
    }

    public void stopGame() {
        gameOver = true;
        turnTimer.stop();
    }

    private void handleClick(int row, int col) {
        if (isComputerTurn()) {
            return;
        }
        makeMove(row, col);
    }

    private void makeMove(int row, int col) {
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
            endGame();
            return;
        }
        if (board.isFull()) {
            turnLabel.setText("Draw!");
            endGame();
            return;
        }

        switchPlayer();
    }

    private void computerMove() {
        if (gameOver || !isComputerTurn()) {
            return;
        }
        int[] move = computer.chooseMove(board);
        makeMove(move[0], move[1]);
    }

    private boolean isComputerTurn() {
        return computer != null && currentPlayer == 'O';
    }

    private void tick() {
        secondsLeft--;
        if (secondsLeft == 0) {
            switchPlayer();
        } else {
            updateTimerLabel();
        }
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        updateTurnLabel();
        secondsLeft = TURN_SECONDS;
        updateTimerLabel();
        turnTimer.restart();

        if (isComputerTurn()) {
            Timer delay = new Timer(COMPUTER_DELAY_MS, e -> computerMove());
            delay.setRepeats(false);
            delay.start();
        }
    }

    private void endGame() {
        gameOver = true;
        turnTimer.stop();
    }

    private void updateTurnLabel() {
        turnLabel.setText("Turn: " + currentPlayer);
    }

    private void updateTimerLabel() {
        timerLabel.setText("Time: " + secondsLeft);
    }
}