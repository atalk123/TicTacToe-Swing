/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tictactoe;

/**
 *
 * @author atalk
 */
public class Board {
    private final char[][] cells = new char[3][3];

    public Board() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                cells[row][col] = ' ';
            }
        }
    }

    public boolean isEmpty(int row, int col) {
        return cells[row][col] == ' ';
    }

    public boolean placeMark(int row, int col, char player) {
        if (!isEmpty(row, col)) {
            return false;
        }
        cells[row][col] = player;
        return true;
    }

    public char getMark(int row, int col) {
        return cells[row][col];
    }
}