package com.example.snakesladders;

public enum DifficultyLevel {
    //            snakeMinDrop%, snakeMaxDrop%, ladderMinClimb%, ladderMaxClimb%
    EASY(         1,             5,             5,               15),
    HARD(         10,            20,            1,               5);

    private final int snakeMinDrop;
    private final int snakeMaxDrop;
    private final int ladderMinClimb;
    private final int ladderMaxClimb;

    DifficultyLevel(int snakeMinDrop, int snakeMaxDrop,
                    int ladderMinClimb, int ladderMaxClimb) {
        this.snakeMinDrop = snakeMinDrop;
        this.snakeMaxDrop = snakeMaxDrop;
        this.ladderMinClimb = ladderMinClimb;
        this.ladderMaxClimb = ladderMaxClimb;
    }

    public int getSnakeMinDrop()   { return snakeMinDrop; }
    public int getSnakeMaxDrop()   { return snakeMaxDrop; }
    public int getLadderMinClimb() { return ladderMinClimb; }
    public int getLadderMaxClimb() { return ladderMaxClimb; }
}
