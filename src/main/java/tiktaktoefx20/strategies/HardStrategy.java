package tiktaktoefx20.strategies;

import tiktaktoefx20.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HardStrategy implements MoveStrategy {
    private final MoveStrategy easyStrategy = new EasyStrategy();

    @Override
    public int[] makeMove(char[][] gameField, String selectedLevel) {

        int[] move = findWinningMove(gameField);
        if (move != null) return move;

        move = blockPlayerWin(gameField);
        if (move != null) return move;

        move = occupyCenter(gameField);
        if (move != null) return move;

        move = occupyCorner(gameField);
        if (move != null) return move;

        // Если ни одно из условий не выполнено, делаем ход по простой стратегии
        return easyStrategy.makeMove(gameField, selectedLevel);
    }

    private int[] findWinningMove(char[][] gameField) {
        // Поиск выигрышного хода
        for (int row = 0; row < gameField.length; row++) {
            for (int col = 0; col < gameField[0].length; col++) {
                if (gameField[row][col] == Constants.EMPTY_SYMBOL) {
                    gameField[row][col] = Constants.COMPUTER_SYMBOL;
                    if (GameLogic.checkForWin(gameField)) {
                        gameField[row][col] = Constants.EMPTY_SYMBOL; // Возвращаем ячейку в исходное состояние
                        return new int[]{row, col};
                    }
                    gameField[row][col] = Constants.EMPTY_SYMBOL; // Возвращаем ячейку в исходное состояние
                }
            }
        }
        return null;
    }

    private int[] blockPlayerWin(char[][] gameField) {
        // Блокирование выигрыша игрока
        for (int row = 0; row < gameField.length; row++) {
            for (int col = 0; col < gameField[0].length; col++) {
                if (gameField[row][col] == Constants.EMPTY_SYMBOL) {
                    gameField[row][col] = Constants.PLAYER_SYMBOL; // Пытаемся сделать ход игрока
                    if (GameLogic.checkForWin(gameField)) {
                        gameField[row][col] = Constants.EMPTY_SYMBOL; // Возвращаем ячейку в исходное состояние
                        return new int[]{row, col};
                    }
                    gameField[row][col] = Constants.EMPTY_SYMBOL; // Возвращаем ячейку в исходное состояние
                }
            }
        }
        return null;
    }

    private int[] occupyCenter(char[][] gameField) {
        // Занятие центральной клетки
        int centerRow = 1; // Индекс строки центральной клетки
        int centerCol = 1; // Индекс столбца центральной клетки
        if (gameField[centerRow][centerCol] == Constants.EMPTY_SYMBOL) {
            return new int[]{centerRow, centerCol};
        }
        return null;
    }

    private int[] occupyCorner(char[][] gameField) {
        // Занятие свободного угла
        List<int[]> freeCorners = new ArrayList<>();
        if (gameField[0][0] == Constants.EMPTY_SYMBOL) {
            freeCorners.add(new int[]{0, 0});
        }
        if (gameField[0][2] == Constants.EMPTY_SYMBOL) {
            freeCorners.add(new int[]{0, 2});
        }
        if (gameField[2][0] == Constants.EMPTY_SYMBOL) {
            freeCorners.add(new int[]{2, 0});
        }
        if (gameField[2][2] == Constants.EMPTY_SYMBOL) {
            freeCorners.add(new int[]{2, 2});
        }
        if (!freeCorners.isEmpty()) {
            Random random = new Random();
            int[] selectedCorner = freeCorners.get(random.nextInt(freeCorners.size()));
            return selectedCorner;
        }
        return null;
    }
}
