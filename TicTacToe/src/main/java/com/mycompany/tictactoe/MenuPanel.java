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

        JLabel title = new JLabel("Tic Tac Toe", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 40));

        JButton classicButton = new JButton("2 Players");
        JButton computerButton = new JButton("vs Computer");
        JButton arcadeButton = new JButton("Arcade (5x5)");
        JButton quitButton = new JButton("Quit");

        Font buttonFont = new Font("Arial", Font.PLAIN, 20);
        classicButton.setFont(buttonFont);
        computerButton.setFont(buttonFont);
        arcadeButton.setFont(buttonFont);
        quitButton.setFont(buttonFont);

        classicButton.addActionListener(e -> onClassic.run());
        computerButton.addActionListener(e -> onVsComputer.run());
        arcadeButton.addActionListener(e -> onArcade.run());
        quitButton.addActionListener(e -> System.exit(0));

        JPanel content = new JPanel(new GridLayout(0, 1, 0, 15));
        content.add(title);
        content.add(classicButton);
        content.add(computerButton);
        content.add(arcadeButton);
        content.add(quitButton);

        add(content);
    }
}