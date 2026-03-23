```java
package com.example.snakesladders;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    // ANSI Escape Codes for Colors
    private static final String RESET = "\u001B[0m";
    private static final String YELLOW = "\u001B[33m";
    private static final String CYAN = "\u001B[36m";
    private static final String BOLD = "\u001B[1m";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print(BOLD + CYAN + "🎲 Enter board dimension (n for nxn board): " + RESET);
        int n = scanner.nextInt();

        System.out.print(BOLD + CYAN + "👥 Enter number of players: " + RESET);
        int numPlayers = scanner.nextInt();
        scanner.nextLine();

        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= numPlayers; i++) {
            System.out.print(BOLD + YELLOW + "👤 Enter name for Player " + i + ": " + RESET);
            String name = scanner.nextLine();
            players.add(new Player(name));
        }

        System.out.print(BOLD + CYAN + "🔥 Enter difficulty (easy/hard): " + RESET);
        String diffInput = scanner.nextLine().trim().toUpperCase();
        DifficultyLevel difficulty = DifficultyLevel.valueOf(diffInput);

        Board board = new BoardBuilder().build(n, difficulty);
        Dice dice = new Dice();

        GameEngine engine = new GameEngine(board, players, dice);
        engine.play();

        scanner.close();
    }
}
