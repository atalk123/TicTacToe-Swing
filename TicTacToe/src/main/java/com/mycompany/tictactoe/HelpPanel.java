package com.mycompany.tictactoe;

import javax.swing.*;
import java.awt.*;

public class HelpPanel extends JPanel {

    public HelpPanel(Runnable onBack) {
        setLayout(new BorderLayout(0, 15));
        setBackground(Theme.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(25, 30, 20, 30));

        JLabel title = new JLabel("How to play", SwingConstants.CENTER);
        title.setFont(Theme.font(Font.BOLD, 32));
        title.setForeground(Theme.TEXT);

        JLabel rules = new JLabel(rulesText());
        rules.setFont(Theme.font(Font.PLAIN, 15));
        rules.setForeground(Theme.TEXT);
        rules.setVerticalAlignment(SwingConstants.TOP);

        ThemedButton backButton = new ThemedButton("Back");
        backButton.addActionListener(e -> onBack.run());

        add(title, BorderLayout.NORTH);
        add(rules, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);
    }

    private String rulesText() {
        return "<html><body style='width: 300px'>"
                + "<b>Goal</b><br>"
                + "Get your marks in a row, horizontally, vertically or diagonally. "
                + "X always starts.<br><br>"
                + "<b>Timer</b><br>"
                + "You have 10 seconds per turn. If time runs out, your turn is skipped.<br><br>"
                + "<b>Player vs Player</b><br>"
                + "Two players take turns on the same computer. Get 3 in a row.<br><br>"
                + "<b>Player vs Computer</b><br>"
                + "You are X. The computer plays O and never makes a mistake.<br><br>"
                + "<b>Arcade</b><br>"
                + "A 5x5 board where you need 4 in a row. Each player can use every "
                + "power-up once per game, and one power-up per turn:<br>"
                + "- Bomb: remove one of your opponent's marks<br>"
                + "- Wall: block a square so nobody can use it<br>"
                + "- Double: place 2 marks this turn"
                + "</body></html>";
    }
}
