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

    public MenuPanel(Runnable onTwoPlayer) {
        setLayout(new GridBagLayout());

        JLabel title = new JLabel("Tic Tac Toe: The Best Game Ever", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 40));

        JButton twoPlayerButton = new JButton("Player vs Player");
        JButton computerButton = new JButton("a button");
        JButton quitButton = new JButton("Quit");

        Font buttonFont = new Font("Arial", Font.PLAIN, 20);
        twoPlayerButton.setFont(buttonFont);
        computerButton.setFont(buttonFont);
        quitButton.setFont(buttonFont);
        computerButton.setEnabled(false);

        twoPlayerButton.addActionListener(e -> onTwoPlayer.run());
        quitButton.addActionListener(e -> System.exit(0));

        JPanel content = new JPanel(new GridLayout(0, 1, 0, 15));
        content.add(title);
        content.add(twoPlayerButton);
        content.add(computerButton);
        content.add(quitButton);

        add(content);
    }
}
