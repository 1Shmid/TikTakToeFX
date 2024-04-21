package tiktaktoefx20.drafts;

import java.util.Date;

// класс представляет собой запись о ходе в игре, включая номер хода, игрока, координаты хода и временную метку.

public class GameTurn {
    private int turnNumber;
    private char player;
    private int row;
    private int col;
    private Date timestamp;

    public GameTurn(int turnNumber, char player, int row, int col, Date timestamp) {
        this.turnNumber = turnNumber;
        this.player = player;
        this.row = row;
        this.col = col;
        this.timestamp = timestamp;
    }

    // Геттеры и сеттеры для всех полей
}