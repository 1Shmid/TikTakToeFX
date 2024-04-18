package tiktaktoefx20.database;

import java.util.*;

public class Game {
    private int gameNumber;
    private List<GameMove> moves;
    private int totalMoves;
    private int playerMoves;
    private int computerMoves;
    private String result;
    private int duration;

    public Game(int gameNumber, List<GameMove> moves, int totalMoves, int playerMoves, int computerMoves, String result, int duration) {
        this.gameNumber = gameNumber;
        this.moves = moves;
        this.totalMoves = totalMoves;
        this.playerMoves = playerMoves;
        this.computerMoves = computerMoves;
        this.result = result;
        this.duration = duration;
    }

    // Геттеры и сеттеры
}