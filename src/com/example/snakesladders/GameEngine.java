package com.example.snakesladders;

import java.util.ArrayList;
import java.util.List;

public class GameEngine {
    private final Board board;
    private final List<Player> players;
    private final Dice dice;
    private int currentRank;

    // ANSI Escape Codes for Colors
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";
    private static final String BOLD = "\u001B[1m";

    public GameEngine(Board board, List<Player> players, Dice dice) {
        this.board = board;
        this.players = new ArrayList<>(players);
        this.dice = dice;
        this.currentRank = 0;
    }

    public void play() {
        printBoard();

        while (activePlayers() >= 2) {
            for (int i = 0; i < players.size(); i++) {
                Player player = players.get(i);
                if (player.isFinished()) continue;
                if (activePlayers() < 2) break;

                playTurn(player);
            }
        }

        // last remaining player gets the final rank
        for (Player player : players) {
            if (!player.isFinished()) {
                currentRank++;
                player.setRank(currentRank);
                player.setFinished(true);
            }
        }

        printResults();
    }

    private void playTurn(Player player) {
        int roll = dice.roll();
        int oldPos = player.getPosition();
        int newPos = oldPos + roll;

        System.out.print(BOLD + player.getName() + RESET + " rolled " + YELLOW + roll + RESET + " | " + BLUE + oldPos + RESET);

        // can't move beyond the final cell
        if (newPos > board.getFinalCell()) {
            System.out.println(" -> stays (would exceed " + board.getFinalCell() + ")");
            return;
        }

        // check for snake or ladder
        int destination = board.getDestination(newPos);
        if (destination != newPos) {
            boolean isSnake = destination < newPos;
            String type = isSnake ? RED + "SNAKE! 🐍" + RESET : GREEN + "LADDER! 🪜" + RESET;
            System.out.println(" -> " + CYAN + newPos + RESET + " " + type + " -> " + BLUE + destination + RESET);
            newPos = destination;
        } else {
            System.out.println(" -> " + CYAN + newPos + RESET);
        }

        player.setPosition(newPos);

        if (newPos == board.getFinalCell()) {
            currentRank++;
            player.setRank(currentRank);
            player.setFinished(true);
            System.out.println("  " + BOLD + YELLOW + player.getName() + " HAS REACHED THE END! Rank: #" + currentRank + RESET);
        }
    }

    private int activePlayers() {
        int count = 0;
        for (Player p : players) {
            if (!p.isFinished()) count++;
        }
        return count;
    }

    private void printBoard() {
        System.out.println("\n" + BOLD + PURPLE + "╔═══════════════════════════════════════╗" + RESET);
        System.out.println(BOLD + PURPLE + "║           Snakes & Ladders            ║" + RESET);
        System.out.println(BOLD + PURPLE + "╚═══════════════════════════════════════╝" + RESET);
        System.out.println("Board size: " + BLUE + board.getSize() + RESET + " cells");
        System.out.println("Snakes:     " + RED + board.getSnakes() + RESET);
        System.out.println("Ladders:    " + GREEN + board.getLadders() + RESET);
        System.out.println("Players:    " + YELLOW + players + RESET);
        System.out.println();
    }

    private void printResults() {
        System.out.println("\n" + BOLD + YELLOW + "━━━━━━━━━━━ FINAL RANKINGS ━━━━━━━━━━━" + RESET);
        for (int r = 1; r <= players.size(); r++) {
            for (Player p : players) {
                if (p.getRank() == r) {
                    String medal = r == 1 ? "🥇" : (r == 2 ? "🥈" : (r == 3 ? "🥉" : "  "));
                    System.out.println(BOLD + "#" + r + RESET + " " + medal + " " + CYAN + p.getName() + RESET);
                }
            }
        }
        System.out.println(BOLD + YELLOW + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);
    }
}
