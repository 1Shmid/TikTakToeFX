package tiktaktoefx20.strategies;

import tiktaktoefx20.*;

public class AttackMoveStrategy implements MoveStrategy {

    private TTTGameLogic gameLogic;

    public AttackMoveStrategy(TTTGameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    @Override
    public int[] makeMove(int[][] gameField, char computerSymbol) {
        // Перебираем все клетки игрового поля
        for (int row = 0; row < gameField.length; row++) {
            for (int col = 0; col < gameField[0].length; col++) {
                // Проверяем, если клетка пустая, делаем ход и проверяем, выиграет ли компьютер
                if (gameField[row][col] == Constants.EMPTY_SYMBOL) {
                    // Создаем копию поля для проверки хода
                    int[][] tempField = copyGameField(gameField);
                    // Симулируем ход компьютера в текущую клетку
                    tempField[row][col] = computerSymbol;
                    // Проверяем, выиграет ли компьютер после этого хода
                    if (gameLogic.isWinningMove(tempField, row, col, computerSymbol)) {
                        // Если да, возвращаем координаты этой клетки
                        return new int[]{row, col};
                    }
                }
            }
        }
        // Если не найдено выигрышного хода, возвращаем null
        return null;
    }

    // Метод для создания копии игрового поля
    private int[][] copyGameField(int[][] gameField) {
        int[][] copy = new int[gameField.length][gameField[0].length];
        for (int i = 0; i < gameField.length; i++) {
            System.arraycopy(gameField[i], 0, copy[i], 0, gameField[0].length);
        }
        return copy;
    }
}
