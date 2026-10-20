/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tictactoe;

public class ComputerPlayer {
    private final char me;
    private final char opponent;

    public ComputerPlayer(char me) {
        this.me = me;
        this.opponent = (me == 'X') ? 'O' : 'X';
    }

    public int[] chooseMove(Board board) {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = null;
        int size = board.getSize();

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board.isEmpty(row, col)) {
                    board.placeMark(row, col, me);
                    int score = minimax(board, 0, false);
                    board.clearCell(row, col);

                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = new int[] {row, col};
                    }
                }
            }
        }
        return bestMove;
    }

    private int minimax(Board board, int depth, boolean myTurn) {
        char winner = board.getWinner();
        if (winner == me) {
            return 10 - depth;
        }
        if (winner == opponent) {
            return depth - 10;
        }
        if (board.isFull()) {
            return 0;
        }

        int size = board.getSize();
        int best = myTurn ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board.isEmpty(row, col)) {
                    board.placeMark(row, col, myTurn ? me : opponent);
                    int score = minimax(board, depth + 1, !myTurn);
                    board.clearCell(row, col);

                    if (myTurn) {
                        best = Math.max(best, score);
                    } else {
                        best = Math.min(best, score);
                    }
                }
            }
        }
        return best;
    }
}