package tiktaktoefx20.database;

import java.util.*;

public class Game {

    private final List<GameMove> moves;
    private final int totalMoves;
    private final int playerMoves;
    private final int computerMoves;
    private final String result;
    private final int duration;
    private final String level;

    public Game(List<GameMove> moves, int totalMoves, int playerMoves, int computerMoves, String result, int duration, String level) {
        this.moves = moves;
        this.totalMoves = totalMoves;
        this.playerMoves = playerMoves;
        this.computerMoves = computerMoves;
        this.result = result;
        this.duration = duration;
        this.level = level; // Добавляем уровень сложности
    }

    public void recordGame() {

        SQLiteDBManager.addGame(moves, totalMoves, playerMoves, computerMoves, result, duration, level);
    }
}