package tiktaktoefx20.strategies;

import tiktaktoefx20.*;

import java.util.Random;

public class RandomMoveStrategy implements MoveStrategy {

    @Override
    public int[] makeMove(int[][] gameField, char computerSymbol) {
        Random random = new Random();
        int row, col;
        do {
            row = random.nextInt(gameField.length);
            col = random.nextInt(gameField[0].length);
        } while (gameField[row][col] != Constants.EMPTY_SYMBOL); // Проверяем, что выбранная ячейка свободна
        return new int[]{row, col};
    }
}