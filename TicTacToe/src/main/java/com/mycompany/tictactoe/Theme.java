
package com.mycompany.tictactoe;

import java.awt.*;

public class Theme {
    public static final Color BACKGROUND = new Color(30, 30, 46);
    public static final Color SURFACE = new Color(49, 50, 68);
    public static final Color SURFACE_HOVER = new Color(69, 71, 90);
    public static final Color SURFACE_DISABLED = new Color(40, 41, 58);
    public static final Color TEXT = new Color(230, 233, 245);
    public static final Color TEXT_MUTED = new Color(140, 145, 170);
    public static final Color X_COLOR = new Color(243, 139, 168);
    public static final Color O_COLOR = new Color(137, 220, 235);
    public static final Color HIGHLIGHT = new Color(249, 226, 175);
    public static final Color WALL_COLOR = new Color(150, 90, 70);
    public static final Color DANGER = new Color(200, 80, 90);

    public static Font font(int style, int size) {
        return new Font("Segoe UI", style, size);
    }

    public static Color colorOf(char player) {
        return (player == 'X') ? X_COLOR : O_COLOR;
    }
}
