package tiktaktoefx20.strategies;

import tiktaktoefx20.constants.Constants;

import java.util.Random;

/**
 * Класс, представляющий реализацию простой стратегии ответного хода. A class representing the
 * implementation of a easy strategy for a computer move.
 */

public class EasyStrategy implements Strategic {

  @Override
  public int[] makeMove(char[][] gameField, String selectedLevel) {
    Random random = new Random();
    int row, col;
    do {
      row = random.nextInt(gameField.length);
      col = random.nextInt(gameField[0].length);
    } while (gameField[row][col]
        != Constants.EMPTY_SYMBOL); // Проверяем, что выбранная ячейка свободна
    return new int[]{row, col};
  }
}


