package tiktaktoefx20.database;

import java.util.*;

/**
 * Класс, содержащий методы для записи игры в базу данных. Class containing methods for recording
 * the game in the database.
 */


public class GameRecorder {

  private final List<GameMove> moves;
  private final int totalMoves;
  private final int playerMoves;
  private final int computerMoves;
  private final String result;
  private final int duration;
  private final String level;

  public GameRecorder(List<GameMove> moves, int totalMoves, int playerMoves, int computerMoves,
      String result, int duration, String level) {
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