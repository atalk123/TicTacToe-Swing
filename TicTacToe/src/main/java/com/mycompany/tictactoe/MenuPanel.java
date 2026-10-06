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

public class MenuPanel extends JPanel {

    public MenuPanel(Runnable onTwoPlayer, Runnable onVsComputer) {
        setLayout(new GridBagLayout());

        JLabel title = new JLabel("Tic Tac Toe", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 40));

        JButton twoPlayerButton = new JButton("2 Players");
        JButton computerButton = new JButton("vs Computer");
        JButton quitButton = new JButton("Quit");

        Font buttonFont = new Font("Arial", Font.PLAIN, 20);
        twoPlayerButton.setFont(buttonFont);
        computerButton.setFont(buttonFont);
        quitButton.setFont(buttonFont);

        twoPlayerButton.addActionListener(e -> onTwoPlayer.run());
        computerButton.addActionListener(e -> onVsComputer.run());
        quitButton.addActionListener(e -> System.exit(0));

        JPanel content = new JPanel(new GridLayout(0, 1, 0, 15));
        content.add(title);
        content.add(twoPlayerButton);
        content.add(computerButton);
        content.add(quitButton);

        add(content);
    }
}