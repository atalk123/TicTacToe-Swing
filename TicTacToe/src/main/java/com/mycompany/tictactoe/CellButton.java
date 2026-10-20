
package com.mycompany.tictactoe;

import javax.swing.*;
import java.awt.*;

public class CellButton extends JButton {
    private static final int GAP = 4;

    private char mark = Board.EMPTY;
    private char preview = Board.EMPTY;
    private boolean clickable = false;
    private boolean winning = false;

    public CellButton() {
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setRolloverEnabled(true);
    }

    public void setMark(char mark) {
        this.mark = mark;
        repaint();
    }

    public void setPreview(char preview) {
        this.preview = preview;
        repaint();
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
        repaint();
    }

    public void setWinning(boolean winning) {
        this.winning = winning;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth() - 2 * GAP;
        int h = getHeight() - 2 * GAP;
        boolean hovered = clickable && getModel().isRollover();

        Color background = Theme.SURFACE;
        if (winning) {
            background = Theme.HIGHLIGHT;
        } else if (mark == Board.WALL) {
            background = Theme.WALL_COLOR;
        } else if (hovered && mark == Board.EMPTY) {
            background = Theme.SURFACE_HOVER;
        } else if (hovered) {
            background = Theme.DANGER;
        }
        g2.setColor(background);
        g2.fillRoundRect(GAP, GAP, w, h, 18, 18);

        if (mark == 'X' || mark == 'O') {
            if (winning) {
                g2.setColor(Theme.BACKGROUND);
            } else {
                g2.setColor(Theme.colorOf(mark));
            }
            drawMark(g2, mark, w, h);
        } else if (hovered && (preview == 'X' || preview == 'O')) {
            g2.setColor(Theme.colorOf(preview));
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
            drawMark(g2, preview, w, h);
        }
        g2.dispose();
    }

    private void drawMark(Graphics2D g2, char m, int w, int h) {
        int size = Math.min(w, h) / 2;
        int x = GAP + (w - size) / 2;
        int y = GAP + (h - size) / 2;
        g2.setStroke(new BasicStroke(size / 6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        if (m == 'X') {
            g2.drawLine(x, y, x + size, y + size);
            g2.drawLine(x + size, y, x, y + size);
        } else {
            g2.drawOval(x, y, size, size);
        }
    }
}
