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
    private final JFrame frame = new JFrame("Tic Tac Toe");
    private final CardLayout cards = new CardLayout();
    private final JPanel screens = new JPanel(cards);
    private final GamePanel gamePanel;

    public GameWindow() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 500);
        frame.setLocationRelativeTo(null);

        MenuPanel menuPanel = new MenuPanel(this::startTwoPlayerGame, this::startComputerGame);
        gamePanel = new GamePanel(this::showMenu);

        screens.add(menuPanel, "menu");
        screens.add(gamePanel, "game");

        frame.add(screens);
        frame.setVisible(true);
        showMenu();
    }

    private void startTwoPlayerGame() {
        gamePanel.startNewGame(false);
        cards.show(screens, "game");
    }

    private void startComputerGame() {
        gamePanel.startNewGame(true);
        cards.show(screens, "game");
    }

    private void showMenu() {
        gamePanel.stopGame();
        cards.show(screens, "menu");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameWindow::new);
    }
}