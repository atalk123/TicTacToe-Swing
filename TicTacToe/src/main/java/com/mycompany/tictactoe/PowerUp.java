
package com.mycompany.tictactoe;

public enum PowerUp {
    BOMB("Bomb", "Remove an enemy mark"),
    WALL("Wall", "Block a square"),
    DOUBLE_MOVE("Double", "Place 2 marks");

    private final String label;
    private final String description;

    PowerUp(String label, String description) {
        this.label = label;
        this.description = description;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }
}
