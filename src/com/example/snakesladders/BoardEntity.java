package com.example.snakesladders;

public class BoardEntity {
    private final int fromCell;
    private final int toCell;

    public BoardEntity(int fromCell, int toCell) {
        this.fromCell = fromCell;
        this.toCell = toCell;
    }

    public int getFromCell() { return fromCell; }
    public int getToCell()   { return toCell; }

    public boolean isSnake() { return toCell < fromCell; }

    @Override
    public String toString() {
        String type = isSnake() ? "Snake" : "Ladder";
        return type + "[" + fromCell + " -> " + toCell + "]";
    }
}
