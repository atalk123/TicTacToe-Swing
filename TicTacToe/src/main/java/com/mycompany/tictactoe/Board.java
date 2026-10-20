/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tictactoe;

public class Board {
    public static final char EMPTY = ' ';
    public static final char WALL = '#';

    private final int size;
    private final int winLength;
    private final char[][] cells;

    public Board(int size, int winLength) {
        this.size = size;
        this.winLength = winLength;
        this.cells = new char[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                cells[row][col] = EMPTY;
            }
        }
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty(int row, int col) {
        return cells[row][col] == EMPTY;
    }

    public boolean placeMark(int row, int col, char mark) {
        if (!isEmpty(row, col)) {
            return false;
        }
        cells[row][col] = mark;
        return true;
    }

    public void clearCell(int row, int col) {
        cells[row][col] = EMPTY;
    }

    public char getMark(int row, int col) {
        return cells[row][col];
    }

    public char getWinner() {
        int[][] directions = {{0, 1}, {1, 0}, {1, 1}, {1, -1}};
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                char mark = cells[row][col];
                if (mark == EMPTY || mark == WALL) {
                    continue;
                }
                for (int[] d : directions) {
                    if (hasLine(row, col, d[0], d[1], mark)) {
                        return mark;
                    }
                }
            }
        }
        return EMPTY;
    }

    private boolean hasLine(int row, int col, int dRow, int dCol, char mark) {
        for (int i = 1; i < winLength; i++) {
            int r = row + i * dRow;
            int c = col + i * dCol;
            if (r < 0 || r >= size || c < 0 || c >= size || cells[r][c] != mark) {
                return false;
            }
        }
        return true;
    }

    public boolean isFull() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (cells[row][col] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }
}