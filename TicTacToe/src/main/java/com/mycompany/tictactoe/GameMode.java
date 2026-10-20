
package com.mycompany.tictactoe;

public enum GameMode {
    CLASSIC(3, 3),
    VS_COMPUTER(3, 3),
    ARCADE(5, 4);

    private final int boardSize;
    private final int winLength;

    GameMode(int boardSize, int winLength) {
        this.boardSize = boardSize;
        this.winLength = winLength;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public int getWinLength() {
        return winLength;
    }
}
