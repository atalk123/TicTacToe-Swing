
package com.mycompany.tictactoe;

import javax.swing.*;
import java.awt.*;

public class PowerUpIcon implements Icon {
    private static final int SIZE = 32;
    private final PowerUp powerUp;

    public PowerUpIcon(PowerUp powerUp) {
        this.powerUp = powerUp;
    }

    @Override
    public int getIconWidth() {
        return SIZE;
    }

    @Override
    public int getIconHeight() {
        return SIZE;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.translate(x, y);
        if (!c.isEnabled()) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        switch (powerUp) {
            case BOMB -> drawBomb(g2);
            case WALL -> drawWall(g2);
            case DOUBLE_MOVE -> drawDouble(g2);
        }
        g2.dispose();
    }

    private void drawBomb(Graphics2D g2) {
        g2.setColor(new Color(200, 200, 215));
        g2.fillOval(4, 8, 22, 22);
        g2.setStroke(new BasicStroke(2.5f));
        g2.setColor(new Color(140, 90, 40));
        g2.drawLine(20, 10, 26, 4);
        g2.setColor(new Color(255, 160, 0));
        g2.fillOval(24, 1, 7, 7);
    }

    private void drawWall(Graphics2D g2) {
        g2.setColor(new Color(170, 70, 50));
        int brickHeight = 8;
        for (int row = 0; row < 3; row++) {
            int y = 5 + row * (brickHeight + 2);
            int offset = (row % 2 == 0) ? 0 : 7;
            for (int x = 2 - offset; x < 30; x += 14) {
                int left = Math.max(x, 2);
                int right = Math.min(x + 12, 30);
                g2.fillRect(left, y, right - left, brickHeight);
            }
        }
    }

    private void drawDouble(Graphics2D g2) {
        g2.setStroke(new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setColor(Theme.O_COLOR);
        drawX(g2, 2, 6, 14);
        drawX(g2, 16, 12, 14);
    }

    private void drawX(Graphics2D g2, int x, int y, int size) {
        g2.drawLine(x, y, x + size, y + size);
        g2.drawLine(x + size, y, x, y + size);
    }
}
