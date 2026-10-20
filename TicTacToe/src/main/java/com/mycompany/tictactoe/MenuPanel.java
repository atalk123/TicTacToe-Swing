/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tictactoe;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

    public MenuPanel(Runnable onClassic, Runnable onVsComputer, Runnable onArcade) {
        setLayout(new GridBagLayout());
        setBackground(Theme.BACKGROUND);

        JLabel title = new JLabel("Tic Tac Toe", SwingConstants.CENTER);
        title.setFont(Theme.font(Font.BOLD, 44));
        title.setForeground(Theme.TEXT);

        JLabel subtitle = new JLabel("Choose a mode", SwingConstants.CENTER);
        subtitle.setFont(Theme.font(Font.PLAIN, 16));
        subtitle.setForeground(Theme.TEXT_MUTED);

        ThemedButton classicButton = new ThemedButton("2 Players");
        ThemedButton computerButton = new ThemedButton("vs Computer");
        ThemedButton arcadeButton = new ThemedButton("Arcade (5x5)");
        ThemedButton quitButton = new ThemedButton("Quit");
        quitButton.setForeground(Theme.TEXT_MUTED);

        classicButton.addActionListener(e -> onClassic.run());
        computerButton.addActionListener(e -> onVsComputer.run());
        arcadeButton.addActionListener(e -> onArcade.run());
        quitButton.addActionListener(e -> System.exit(0));

        JPanel content = new JPanel(new GridLayout(0, 1, 0, 15));
        content.setOpaque(false);
        content.setPreferredSize(new Dimension(280, 400));
        content.add(title);
        content.add(subtitle);
        content.add(classicButton);
        content.add(computerButton);
        content.add(arcadeButton);
        content.add(quitButton);

        add(content);
    }
}
