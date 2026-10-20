
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

    private final JPanel boardPanel = new JPanel();
    private final JPanel powerUpBar = new JPanel(new GridLayout(1, 0, 10, 0));
    private final JPanel buttonRow = new JPanel(new GridLayout(1, 0, 10, 0));
    private final Map<PowerUp, ThemedButton> powerUpButtons = new EnumMap<>(PowerUp.class);
    private final Map<Character, EnumSet<PowerUp>> powerUpsLeft = new HashMap<>();
    private final JLabel turnLabel = new JLabel();
    private final JLabel timerLabel = new JLabel();
    private final JLabel scoreLabel = new JLabel("", SwingConstants.CENTER);
    private final ThemedButton playAgainButton = new ThemedButton("Play again");
    private final Timer turnTimer;

    private CellButton[][] squares;
    private Board board;
    private GameMode mode;
    private ComputerPlayer computer;
    private PowerUp activePowerUp = null;
    private boolean powerUpUsedThisTurn = false;
    private int movesLeftThisTurn = 1;
    private char currentPlayer = 'X';
    private boolean gameOver = false;
    private int secondsLeft = TURN_SECONDS;
    private int xWins = 0;
    private int oWins = 0;
    private int draws = 0;

    public GamePanel(Runnable onBackToMenu) {
        setLayout(new BorderLayout());
        setBackground(Theme.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        turnLabel.setFont(Theme.font(Font.BOLD, 24));
        timerLabel.setFont(Theme.font(Font.BOLD, 20));
        timerLabel.setForeground(Theme.TEXT_MUTED);
        scoreLabel.setFont(Theme.font(Font.BOLD, 16));
        scoreLabel.setForeground(Theme.TEXT_MUTED);

        JPanel topPanel = new JPanel(new BorderLayout(0, 8));
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 4, 10, 4));
        topPanel.add(turnLabel, BorderLayout.WEST);
        topPanel.add(timerLabel, BorderLayout.EAST);
        topPanel.add(scoreLabel, BorderLayout.SOUTH);

        boardPanel.setOpaque(false);

        powerUpBar.setOpaque(false);
        for (PowerUp powerUp : PowerUp.values()) {
            ThemedButton button = new ThemedButton(buttonText(powerUp));
            button.setIcon(new PowerUpIcon(powerUp));
            button.setFont(Theme.font(Font.PLAIN, 13));
            button.setBorder(BorderFactory.createEmptyBorder(8, 4, 8, 4));
            button.setVerticalTextPosition(SwingConstants.BOTTOM);
            button.setHorizontalTextPosition(SwingConstants.CENTER);
            button.addActionListener(e -> togglePowerUp(powerUp));
            powerUpButtons.put(powerUp, button);
            powerUpBar.add(button);
        }

        ThemedButton menuButton = new ThemedButton("Menu");
        menuButton.addActionListener(e -> onBackToMenu.run());

        playAgainButton.setBaseColor(Theme.HIGHLIGHT);
        playAgainButton.setForeground(Theme.BACKGROUND);
        playAgainButton.addActionListener(e -> startRound());

        buttonRow.setOpaque(false);
        buttonRow.add(menuButton);

        JPanel bottomPanel = new JPanel(new BorderLayout(0, 10));
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        bottomPanel.add(powerUpBar, BorderLayout.NORTH);
        bottomPanel.add(buttonRow, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        turnTimer = new Timer(1000, e -> tick());
    }

    public void startNewGame(GameMode mode) {
        this.mode = mode;
        xWins = 0;
        oWins = 0;
        draws = 0;
        startRound();
    }

    private void startRound() {
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
        buttonRow.remove(playAgainButton);
        buttonRow.revalidate();
        updateTurnLabel();
        updateTimerLabel();
        updateScoreLabel();
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
        squares = new CellButton[size][size];

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                CellButton square = new CellButton();
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
        squares[row][col].setMark(currentPlayer);

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
        squares[row][col].setMark(Board.EMPTY);
        consumeActivePowerUp();
        switchPlayer();
    }

    private void useWall(int row, int col) {
        if (!board.placeMark(row, col, Board.WALL)) {
            return;
        }
        squares[row][col].setMark(Board.WALL);
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
        int[][] winningCells = board.getWinningCells();
        if (winningCells != null) {
            char winner = board.getMark(winningCells[0][0], winningCells[0][1]);
            for (int[] cell : winningCells) {
                squares[cell[0]][cell[1]].setWinning(true);
            }
            if (winner == 'X') {
                xWins++;
            } else {
                oWins++;
            }
            endGame();
            turnLabel.setText(winner + " wins!");
            turnLabel.setForeground(Theme.colorOf(winner));
            return true;
        }
        if (board.isFull()) {
            draws++;
            endGame();
            turnLabel.setText("Draw!");
            turnLabel.setForeground(Theme.TEXT);
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
        timerLabel.setText("");
        updateScoreLabel();
        updatePowerUpButtons();
        buttonRow.add(playAgainButton);
        buttonRow.revalidate();
        buttonRow.repaint();
    }

    private void updateTurnLabel() {
        String text = currentPlayer + "'s turn";
        if (activePowerUp == PowerUp.BOMB) {
            text = currentPlayer + ": pick an " + opponent(currentPlayer) + " to remove";
        } else if (activePowerUp == PowerUp.WALL) {
            text = currentPlayer + ": pick a square to block";
        } else if (movesLeftThisTurn > 1) {
            text = currentPlayer + ": place 2 marks";
        }
        turnLabel.setText(text);
        turnLabel.setForeground(Theme.colorOf(currentPlayer));
        updateBoardHover();
    }

    private void updateTimerLabel() {
        timerLabel.setText(secondsLeft + "s");
    }

    private void updateScoreLabel() {
        scoreLabel.setText("X  " + xWins + "     Draws  " + draws + "     O  " + oWins);
    }

    private void updateBoardHover() {
        int size = board.getSize();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                squares[row][col].setClickable(canClick(row, col));
                if (activePowerUp == PowerUp.WALL) {
                    squares[row][col].setPreview(Board.WALL);
                } else {
                    squares[row][col].setPreview(currentPlayer);
                }
            }
        }
    }

    private boolean canClick(int row, int col) {
        if (gameOver || isComputerTurn()) {
            return false;
        }
        if (activePowerUp == PowerUp.BOMB) {
            return board.getMark(row, col) == opponent(currentPlayer);
        }
        return board.isEmpty(row, col);
    }

    private void updatePowerUpButtons() {
        EnumSet<PowerUp> available = powerUpsLeft.get(currentPlayer);
        for (PowerUp powerUp : PowerUp.values()) {
            ThemedButton button = powerUpButtons.get(powerUp);
            boolean usable = !gameOver && !powerUpUsedThisTurn && available.contains(powerUp);
            button.setEnabled(usable);
            if (powerUp == activePowerUp) {
                button.setBaseColor(Theme.HIGHLIGHT);
                button.setForeground(Theme.BACKGROUND);
            } else {
                button.setBaseColor(Theme.SURFACE);
                button.setForeground(Theme.TEXT);
            }
        }
        updateBoardHover();
    }

    private String buttonText(PowerUp powerUp) {
        return "<html><center><b>" + powerUp.getLabel() + "</b><br>"
                + powerUp.getDescription() + "</center></html>";
    }
}
