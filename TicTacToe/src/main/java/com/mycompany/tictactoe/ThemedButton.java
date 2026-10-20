/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tictactoe;

import javax.swing.*;
import java.awt.*;

public class ThemedButton extends JButton {
    private Color baseColor = Theme.SURFACE;

    public ThemedButton(String text) {
        super(text);
        setFont(Theme.font(Font.BOLD, 18));
        setForeground(Theme.TEXT);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setRolloverEnabled(true);
        setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
    }

    public void setBaseColor(Color color) {
        baseColor = color;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color background = baseColor;
        if (!isEnabled()) {
            background = Theme.SURFACE_DISABLED;
        } else if (getModel().isPressed()) {
            background = baseColor.darker();
        } else if (getModel().isRollover()) {
            background = baseColor.brighter();
        }
        g2.setColor(background);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
        g2.dispose();

        super.paintComponent(g);
    }
}
