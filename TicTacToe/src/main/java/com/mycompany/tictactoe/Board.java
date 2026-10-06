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
    public void clearCell(int row, int col) {
        cells[row][col] = ' ';
    }

    public char getMark(int row, int col) {
        return cells[row][col];
    }

    public char getWinner() {
        for (int i = 0; i < 3; i++) {
            char rowWinner = lineWinner(cells[i][0], cells[i][1], cells[i][2]);
            if (rowWinner != ' ') {
                return rowWinner;
            }
            char colWinner = lineWinner(cells[0][i], cells[1][i], cells[2][i]);
            if (colWinner != ' ') {
                return colWinner;
            }
        }
        char diagWinner = lineWinner(cells[0][0], cells[1][1], cells[2][2]);
        if (diagWinner != ' ') {
            return diagWinner;
        }
        return lineWinner(cells[0][2], cells[1][1], cells[2][0]);
    }

    public boolean isFull() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (cells[row][col] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private char lineWinner(char a, char b, char c) {
        if (a != ' ' && a == b && b == c) {
            return a;
        }
        return ' ';
    }
}