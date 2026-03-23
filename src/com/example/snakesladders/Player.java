package com.example.snakesladders;

public class Player {
    private final String name;
    private int position;
    private int rank;
    private boolean finished;

    public Player(String name) {
        this.name = name;
        this.position = 0;
        this.rank = 0;
        this.finished = false;
    }

    public String getName()     { return name; }
    public int getPosition()    { return position; }
    public int getRank()        { return rank; }
    public boolean isFinished() { return finished; }

    public void setPosition(int position)  { this.position = position; }
    public void setRank(int rank)          { this.rank = rank; }
    public void setFinished(boolean f)     { this.finished = f; }

    @Override
    public String toString() {
        return name + " (pos=" + position + ")";
    }
}
