package tiktaktoefx20.strategies;

/**
 * Класс определяет интерфейс стратегии
 * <p>
 * Defines the strategy interface
 */

public interface Strategic {

  int[] makeMove(char[][] gameField, String selectedLevel);
}