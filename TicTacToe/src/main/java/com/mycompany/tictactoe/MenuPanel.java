
package com.mycompany.tictactoe;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

    public MenuPanel(Runnable onClassic, Runnable onVsComputer, Runnable onArcade, Runnable onHelp) {
        setLayout(new GridBagLayout());
        setBackground(Theme.BACKGROUND);

        JLabel title = new JLabel("Tic Tac Toe", SwingConstants.CENTER);
        title.setFont(Theme.font(Font.BOLD, 44));
        title.setForeground(Theme.TEXT);

        JLabel subtitle = new JLabel("Choose a mode", SwingConstants.CENTER);
        subtitle.setFont(Theme.font(Font.PLAIN, 16));
        subtitle.setForeground(Theme.TEXT_MUTED);

        ThemedButton classicButton = new ThemedButton("Player vs Player");
        ThemedButton computerButton = new ThemedButton("Player vs Computer");
        ThemedButton arcadeButton = new ThemedButton("Arcade");
        ThemedButton helpButton = new ThemedButton("Help");
        ThemedButton quitButton = new ThemedButton("Quit");
        quitButton.setForeground(Theme.TEXT_MUTED);

        classicButton.addActionListener(e -> onClassic.run());
        computerButton.addActionListener(e -> onVsComputer.run());
        arcadeButton.addActionListener(e -> onArcade.run());
        helpButton.addActionListener(e -> onHelp.run());
        quitButton.addActionListener(e -> System.exit(0));

        JPanel content = new JPanel(new GridLayout(0, 1, 0, 15));
        content.setOpaque(false);
        content.setPreferredSize(new Dimension(280, 460));
        content.add(title);
        content.add(subtitle);
        content.add(classicButton);
        content.add(computerButton);
        content.add(arcadeButton);
        content.add(helpButton);
        content.add(quitButton);

        add(content);
    }
}
