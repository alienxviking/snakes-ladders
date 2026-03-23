package com.example.snakesladders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private final int size;
    private final List<BoardEntity> snakes;
    private final List<BoardEntity> ladders;
    private final Map<Integer, Integer> jumpMap;

    public Board(int size, List<BoardEntity> snakes, List<BoardEntity> ladders) {
        this.size = size;
        this.snakes = new ArrayList<>(snakes);
        this.ladders = new ArrayList<>(ladders);

        // pre-compute jump destinations for quick lookup
        this.jumpMap = new HashMap<>();
        for (BoardEntity s : snakes) {
            jumpMap.put(s.getFromCell(), s.getToCell());
        }
        for (BoardEntity l : ladders) {
            jumpMap.put(l.getFromCell(), l.getToCell());
        }
    }

    public int getSize()                 { return size; }
    public int getFinalCell()            { return size; }
    public List<BoardEntity> getSnakes() { return snakes; }
    public List<BoardEntity> getLadders() { return ladders; }

    // returns where you end up after landing on a cell
    public int getDestination(int cell) {
        return jumpMap.getOrDefault(cell, cell);
    }
}
