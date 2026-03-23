package com.example.snakesladders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class BoardBuilder {
    private final Random random;

    public BoardBuilder() {
        this.random = new Random();
    }

    public Board build(int n, DifficultyLevel difficulty) {
        int totalCells = n * n;

        // scale difficulty ranges to board size
        int snakeMinDrop = Math.max(1, totalCells * difficulty.getSnakeMinDrop() / 100);
        int snakeMaxDrop = Math.max(snakeMinDrop + 1, totalCells * difficulty.getSnakeMaxDrop() / 100);
        int ladderMinClimb = Math.max(1, totalCells * difficulty.getLadderMinClimb() / 100);
        int ladderMaxClimb = Math.max(ladderMinClimb + 1, totalCells * difficulty.getLadderMaxClimb() / 100);

        // cells that already have a snake/ladder endpoint
        Set<Integer> occupied = new HashSet<>();
        occupied.add(1);
        occupied.add(totalCells);

        // tracks from -> to for cycle prevention
        Map<Integer, Integer> jumpMap = new HashMap<>();

        List<BoardEntity> snakes = new ArrayList<>();
        List<BoardEntity> ladders = new ArrayList<>();

        // place n snakes
        for (int i = 0; i < n; i++) {
            BoardEntity snake = placeSnake(totalCells, snakeMinDrop, snakeMaxDrop,
                    occupied, jumpMap);
            if (snake != null) {
                snakes.add(snake);
            }
        }

        // place n ladders
        for (int i = 0; i < n; i++) {
            BoardEntity ladder = placeLadder(totalCells, ladderMinClimb, ladderMaxClimb,
                    occupied, jumpMap);
            if (ladder != null) {
                ladders.add(ladder);
            }
        }

        return new Board(totalCells, snakes, ladders);
    }

    private BoardEntity placeSnake(int totalCells, int minDrop, int maxDrop,
                                   Set<Integer> occupied, Map<Integer, Integer> jumpMap) {
        for (int attempt = 0; attempt < 100; attempt++) {
            int drop = minDrop + random.nextInt(maxDrop - minDrop + 1);
            int head = drop + 1 + random.nextInt(totalCells - drop - 1);
            int tail = head - drop;

            if (tail < 1 || head >= totalCells) continue;
            if (occupied.contains(head) || occupied.contains(tail)) continue;
            // no cycle: tail should not land on another entity's start
            if (jumpMap.containsKey(tail)) continue;

            occupied.add(head);
            occupied.add(tail);
            jumpMap.put(head, tail);
            return new BoardEntity(head, tail);
        }
        return null;
    }

    private BoardEntity placeLadder(int totalCells, int minClimb, int maxClimb,
                                    Set<Integer> occupied, Map<Integer, Integer> jumpMap) {
        for (int attempt = 0; attempt < 100; attempt++) {
            int climb = minClimb + random.nextInt(maxClimb - minClimb + 1);
            int start = 2 + random.nextInt(totalCells - climb - 2);
            int end = start + climb;

            if (end >= totalCells || start < 2) continue;
            if (occupied.contains(start) || occupied.contains(end)) continue;
            // no cycle: end should not land on another entity's start
            if (jumpMap.containsKey(end)) continue;

            occupied.add(start);
            occupied.add(end);
            jumpMap.put(start, end);
            return new BoardEntity(start, end);
        }
        return null;
    }
}
