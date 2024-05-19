package tiktaktoefx20.strategies;

import tiktaktoefx20.constants.Constants;

import java.util.Random;

import tiktaktoefx20.model.GameParams;

/**
 * Класс, представляющий реализацию простой стратегии ответного хода. A class representing the
 * implementation of a easy strategy for a computer move.
 */

public class EasyStrategy implements Strategic {

    @Override
    public int[] makeMove(GameParams params) {
        Random random = new Random();
        int row, col;
        char[][] gameField = params.getGameField();
        int maxAttempts = 100; // Максимальное количество попыток
        int attempts = 0;

        // Проверка наличия свободных ячеек
        boolean hasEmptyCells = false;
        for (char[] rows : gameField) {
            for (char cell : rows) {
                if (cell == Constants.EMPTY_SYMBOL) {
                    hasEmptyCells = true;
                    break;
                }
            }
            if (hasEmptyCells) {
                break;
            }
        }

        if (!hasEmptyCells) {
            return null; // Возвращаем null, если нет доступных ходов
        }

        do {
            row = random.nextInt(gameField.length);
            col = random.nextInt(gameField[0].length);
            attempts++;
        } while (gameField[row][col] != Constants.EMPTY_SYMBOL && attempts < maxAttempts); // Проверяем, что выбранная ячейка свободна

        if (attempts >= maxAttempts) {
            throw new RuntimeException("Exceeded maximum attempts to find an empty cell");
        }

        return new int[]{row, col};
    }
}


