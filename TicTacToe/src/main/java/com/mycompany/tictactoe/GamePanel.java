/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tictactoe;

import javax.swing.*;
import java.awt.*;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class GamePanel extends JPanel {
    private static final int TURN_SECONDS = 10;
    private static final int COMPUTER_DELAY_MS = 500;
    private static final Color WALL_COLOR = new Color(60, 60, 60);
    private static final Color ACTIVE_COLOR = new Color(255, 215, 120);

    private final JPanel boardPanel = new JPanel();
    private final JPanel powerUpBar = new JPanel(new GridLayout(1, 0, 10, 0));
    private final Map<PowerUp, JButton> powerUpButtons = new EnumMap<>(PowerUp.class);
    private final Map<Character, EnumSet<PowerUp>> powerUpsLeft = new HashMap<>();
    private final JLabel turnLabel = new JLabel();
    private final JLabel timerLabel = new JLabel();
    private final Timer turnTimer;

    private JButton[][] squares;
    private Board board;
    private ComputerPlayer computer;
    private PowerUp activePowerUp = null;
    private boolean powerUpUsedThisTurn = false;
    private int movesLeftThisTurn = 1;
    private char currentPlayer = 'X';
    private boolean gameOver = false;
    private int secondsLeft = TURN_SECONDS;

    public GamePanel(Runnable onBackToMenu) {
        setLayout(new BorderLayout());

        Font labelFont = new Font("Arial", Font.BOLD, 20);
        turnLabel.setFont(labelFont);
        timerLabel.setFont(labelFont);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.add(turnLabel, BorderLayout.WEST);
        topPanel.add(timerLabel, BorderLayout.EAST);

        powerUpBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        for (PowerUp powerUp : PowerUp.values()) {
            JButton button = new JButton(buttonText(powerUp), new PowerUpIcon(powerUp));
            button.setVerticalTextPosition(SwingConstants.BOTTOM);
            button.setHorizontalTextPosition(SwingConstants.CENTER);
            button.setFocusPainted(false);
            button.addActionListener(e -> togglePowerUp(powerUp));
            powerUpButtons.put(powerUp, button);
            powerUpBar.add(button);
        }

        JButton menuButton = new JButton("Menu");
        menuButton.setFont(new Font("Arial", Font.PLAIN, 20));
        menuButton.addActionListener(e -> onBackToMenu.run());

        JPanel bottomPanel = new JPanel(new BorderLayout(0, 10));
        bottomPanel.add(powerUpBar, BorderLayout.NORTH);
        bottomPanel.add(menuButton, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        turnTimer = new Timer(1000, e -> tick());
    }

    public void startNewGame(GameMode mode) {
        board = new Board(mode.getBoardSize(), mode.getWinLength());
        computer = (mode == GameMode.VS_COMPUTER) ? new ComputerPlayer('O') : null;
        buildBoardButtons();

        powerUpsLeft.put('X', EnumSet.allOf(PowerUp.class));
        powerUpsLeft.put('O', EnumSet.allOf(PowerUp.class));
        powerUpBar.setVisible(mode == GameMode.ARCADE);
        activePowerUp = null;
        powerUpUsedThisTurn = false;
        movesLeftThisTurn = 1;

        currentPlayer = 'X';
        gameOver = false;
        secondsLeft = TURN_SECONDS;
        updateTurnLabel();
        updateTimerLabel();
        updatePowerUpButtons();
        turnTimer.restart();
    }

    public void stopGame() {
        gameOver = true;
        turnTimer.stop();
    }

    private void buildBoardButtons() {
        int size = board.getSize();
        boardPanel.removeAll();
        boardPanel.setLayout(new GridLayout(size, size));
        squares = new JButton[size][size];
        Font markFont = new Font("Arial", Font.BOLD, size == 3 ? 60 : 36);

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                JButton square = new JButton("");
                square.setFont(markFont);
                square.setFocusPainted(false);
                final int r = row;
                final int c = col;
                square.addActionListener(e -> handleClick(r, c));
                squares[row][col] = square;
                boardPanel.add(square);
            }
        }
        boardPanel.revalidate();
        boardPanel.repaint();
    }

    private void handleClick(int row, int col) {
        if (gameOver || isComputerTurn()) {
            return;
        }
        if (activePowerUp == PowerUp.BOMB) {
            useBomb(row, col);
        } else if (activePowerUp == PowerUp.WALL) {
            useWall(row, col);
        } else {
            makeMove(row, col);
        }
    }

    private void makeMove(int row, int col) {
        if (gameOver) {
            return;
        }
        if (!board.placeMark(row, col, currentPlayer)) {
            return;
        }
        squares[row][col].setText(String.valueOf(currentPlayer));

        if (checkGameEnd()) {
            return;
        }

        movesLeftThisTurn--;
        if (movesLeftThisTurn > 0) {
            updateTurnLabel();
            return;
        }
        switchPlayer();
    }

    private void togglePowerUp(PowerUp powerUp) {
        if (gameOver || isComputerTurn() || powerUpUsedThisTurn
                || !powerUpsLeft.get(currentPlayer).contains(powerUp)) {
            return;
        }
        if (powerUp == PowerUp.DOUBLE_MOVE) {
            powerUpsLeft.get(currentPlayer).remove(powerUp);
            powerUpUsedThisTurn = true;
            activePowerUp = null;
            movesLeftThisTurn = 2;
        } else if (activePowerUp == powerUp) {
            activePowerUp = null;
        } else {
            activePowerUp = powerUp;
        }
        updateTurnLabel();
        updatePowerUpButtons();
    }

    private void useBomb(int row, int col) {
        if (board.getMark(row, col) != opponent(currentPlayer)) {
            return;
        }
        board.clearCell(row, col);
        squares[row][col].setText("");
        consumeActivePowerUp();
        switchPlayer();
    }

    private void useWall(int row, int col) {
        if (!board.placeMark(row, col, Board.WALL)) {
            return;
        }
        squares[row][col].setBackground(WALL_COLOR);
        consumeActivePowerUp();
        if (checkGameEnd()) {
            return;
        }
        switchPlayer();
    }

    private void consumeActivePowerUp() {
        powerUpsLeft.get(currentPlayer).remove(activePowerUp);
        activePowerUp = null;
        powerUpUsedThisTurn = true;
    }

    private boolean checkGameEnd() {
        char winner = board.getWinner();
        if (winner != Board.EMPTY) {
            endGame();
            turnLabel.setText(winner + " wins!");
            return true;
        }
        if (board.isFull()) {
            endGame();
            turnLabel.setText("Draw!");
            return true;
        }
        return false;
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

    private char opponent(char player) {
        return (player == 'X') ? 'O' : 'X';
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
        currentPlayer = opponent(currentPlayer);
        activePowerUp = null;
        powerUpUsedThisTurn = false;
        movesLeftThisTurn = 1;
        secondsLeft = TURN_SECONDS;
        updateTurnLabel();
        updateTimerLabel();
        updatePowerUpButtons();
        turnTimer.restart();

        if (isComputerTurn()) {
            Timer delay = new Timer(COMPUTER_DELAY_MS, e -> computerMove());
            delay.setRepeats(false);
            delay.start();
        }
    }

    private void endGame() {
        gameOver = true;
        activePowerUp = null;
        turnTimer.stop();
        updatePowerUpButtons();
    }

    private void updateTurnLabel() {
        String text = "Turn: " + currentPlayer;
        if (activePowerUp == PowerUp.BOMB) {
            text = currentPlayer + ": pick an " + opponent(currentPlayer) + " to remove";
        } else if (activePowerUp == PowerUp.WALL) {
            text = currentPlayer + ": pick a square to block";
        } else if (movesLeftThisTurn > 1) {
            text = currentPlayer + ": place 2 marks";
        }
        turnLabel.setText(text);
    }

    private void updateTimerLabel() {
        timerLabel.setText("Time: " + secondsLeft);
    }

    private void updatePowerUpButtons() {
        EnumSet<PowerUp> available = powerUpsLeft.get(currentPlayer);
        for (PowerUp powerUp : PowerUp.values()) {
            JButton button = powerUpButtons.get(powerUp);
            boolean usable = !gameOver && !powerUpUsedThisTurn && available.contains(powerUp);
            button.setEnabled(usable);
            button.setForeground(usable ? Color.BLACK : Color.GRAY);
            if (powerUp == activePowerUp) {
                button.setBackground(ACTIVE_COLOR);
            } else {
                button.setBackground(UIManager.getColor("Button.background"));
            }
        }
    }

    private String buttonText(PowerUp powerUp) {
        return "<html><center><b>" + powerUp.getLabel() + "</b><br>"
                + powerUp.getDescription() + "</center></html>";
    }
}