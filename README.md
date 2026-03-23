# Snakes & Ladders

## Class Diagram

```
+-------------------------+
|       <<enum>>          |
|    DifficultyLevel      |
|-------------------------|
| EASY                    |
| HARD                    |
|-------------------------|
| -snakeMinDrop: int      |
| -snakeMaxDrop: int      |
| -ladderMinClimb: int    |
| -ladderMaxClimb: int    |
+-------------------------+

+------------------+       +------------------+
|      Dice        |       |     Player       |
|------------------|       |------------------|
| -random: Random  |       | -name: String    |
|------------------|       | -position: int   |
| +roll(): int     |       | -rank: int       |
+------------------+       | -finished: bool  |
                           +------------------+

+------------------+
|   BoardEntity    |
|------------------|
| -fromCell: int   |
| -toCell: int     |
|------------------|
| +isSnake(): bool |
+------------------+
         |
         v
+----------------------------------------------+
|                   Board                      |
|----------------------------------------------|
| -size: int                                   |
| -snakes: List<BoardEntity>                   |
| -ladders: List<BoardEntity>                  |
| -jumpMap: Map<Integer, Integer>              |
|----------------------------------------------|
| +getDestination(cell): int                   |
| +getFinalCell(): int                         |
+----------------------------------------------+
         ^
         | builds
+----------------------------------------------+
|               BoardBuilder                   |
|----------------------------------------------|
| -random: Random                              |
|----------------------------------------------|
| +build(n, difficulty): Board                 |
| -placeSnake(...): BoardEntity                |
| -placeLadder(...): BoardEntity               |
+----------------------------------------------+

+----------------------------------------------+
|                GameEngine                    |
|----------------------------------------------|
| -board: Board                                |
| -players: List<Player>                       |
| -dice: Dice                                  |
| -currentRank: int                            |
|----------------------------------------------|
| +play(): void                                |
| -playTurn(Player): void                      |
| -activePlayers(): int                        |
+----------------------------------------------+

+----------------------------------------------+
|                    App                       |
|----------------------------------------------|
| +main(String[] args): void                   |
+----------------------------------------------+
```

## Design Approach

### Board & Entities
The board has cells numbered 1 to n*n. Snakes and ladders are both represented by `BoardEntity` which stores `fromCell` and `toCell`. A snake has `toCell < fromCell` (you go down), a ladder has `toCell > fromCell` (you go up). The `Board` pre-computes a `jumpMap` (HashMap) so looking up whether a cell has a snake/ladder is O(1).

### Random Placement (BoardBuilder)
`BoardBuilder` places n snakes and n ladders randomly. The difficulty level controls the drop/climb ranges:
- **EASY**: snakes have short drops, ladders have long climbs
- **HARD**: snakes have long drops, ladders have short climbs

Ranges are stored as percentages and scaled to the board size.

### Cycle Prevention
When placing an entity, we check that its destination cell does not land on the start of another entity. This guarantees no chain reactions (e.g., snake drops you onto a ladder that takes you back up to the snake).

### Game Loop (GameEngine)
Players take turns rolling the dice. On each turn:
1. Roll the dice (1-6)
2. If new position > n*n, player stays put
3. Check if the cell has a snake or ladder, move accordingly
4. If player reaches exactly n*n, they win and get the next rank

The game runs until fewer than 2 players remain active. The last player gets the final rank automatically.

### Input (App)
The app takes user input via Scanner:
- `n` - board dimension (nxn board)
- number of players and their names
- difficulty level (easy/hard)

## How to Build and Run

```bash
cd snakes-ladders
javac -d out src/com/example/snakesladders/*.java
java -cp out com.example.snakesladders.App
```

### Sample Run
```
Enter board dimension (n for nxn board): 10
Enter number of players: 2
Enter name for Player 1: Alice
Enter name for Player 2: Bob
Enter difficulty (easy/hard): easy

=== Snakes & Ladders ===
Board size: 100 cells
Snakes:  [Snake[87 -> 84], Snake[45 -> 42], ...]
Ladders: [Ladder[3 -> 15], Ladder[22 -> 34], ...]
Players: [Alice (pos=0), Bob (pos=0)]

Alice rolled 4 | 0 -> 4
Bob rolled 6 | 0 -> 6
Alice rolled 3 | 4 -> 7 Ladder! -> 19
...
  Alice wins! Rank: #1

=== Final Rankings ===
#1 Alice
#2 Bob
```
