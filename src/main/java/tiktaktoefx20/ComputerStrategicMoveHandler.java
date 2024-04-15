package tiktaktoefx20;

import tiktaktoefx20.strategies.*;

import java.util.*;


public class ComputerStrategicMoveHandler {
    private MoveStrategy moveStrategy;

    public ComputerStrategicMoveHandler(MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }
// НЕ ПОНЯТНО, ЗАЧЕМ ДВА РАЗА ВЫЗЫВАТЬ
    public void setMoveStrategy(MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }

    public int[] makeMove(char[][] gameField, char computerSymbol) {
        Random random = new Random();
        int row, col;
        do {
            row = random.nextInt(gameField.length);
            col = random.nextInt(gameField[0].length);
            System.out.println("Selected row: " + row + ", col: " + col);
        } while (gameField[row][col] != Constants.EMPTY_SYMBOL); // Проверяем, что выбранная ячейка свободна
        return new int[]{row, col};
    }
}