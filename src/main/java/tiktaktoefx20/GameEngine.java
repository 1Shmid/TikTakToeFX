package tiktaktoefx20;

import javafx.collections.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.*;

public class GameEngine extends GameResultHandler {

    // Статическая переменная для хранения координат выигрышных ячеек
    protected static List<int[]> winningCells = new ArrayList<>();

    // Проверка на победу
    public static boolean checkForWin(char[][] gameField) {

        return checkRowsForWin(gameField) || checkColumnsForWin(gameField) || checkDiagonalsForWin(gameField);
    }

    // Проверка на ничью
    public static boolean checkForDraw(char[][] gameField) {

        for (int i = 0; i < Constants.FIELD_SIZE; i++) {
            for (int j = 0; j < Constants.FIELD_SIZE; j++) {
                if (gameField[i][j] == Constants.EMPTY_SYMBOL) {
                    // Найдена пустая ячейка, игра не закончилась вничью
                    return false;
                }
            }
        }
        return true; // Все ячейки заполнены, ничья
    }

    // Определение координат выигрышных ячеек в строках
    static List<int[]> getRowsWinningCellsCoordinates(char[][] gameField, int row) {
        List<int[]> coordinates = new ArrayList<>();
        for (int j = 0; j < Constants.FIELD_SIZE; j++) {
            coordinates.add(new int[]{row, j});
        }
        return coordinates;
    }

    // Определение координат выигрышных ячеек в столбцах
    static List<int[]> getColumnWinningCellsCoordinates(char[][] gameField, int col) {
        List<int[]> coordinates = new ArrayList<>();
        for (int i = 0; i < Constants.FIELD_SIZE; i++) {
            coordinates.add(new int[]{i, col});
        }
        return coordinates;
    }

    // Определение координат выигрышных ячеек по диагоналям
    static List<int[]> getDiagonalWinningCellsCoordinates(char[][] gameField) {
        List<int[]> diagonalLeftRight = new ArrayList<>();
        List<int[]> diagonalRightLeft = new ArrayList<>();
        for (int i = 0; i < Constants.FIELD_SIZE; i++) {
            diagonalLeftRight.add(new int[]{i, i});
            diagonalRightLeft.add(new int[]{i, Constants.FIELD_SIZE - 1 - i});
        }
        // Выбираем одну диагональ в зависимости от символов в ее ячейках
        char symbol = gameField[0][0];
        return (symbol == gameField[1][1] && symbol == gameField[2][2]) ? diagonalLeftRight : diagonalRightLeft;
    }



    static boolean checkRowsForWin(char[][] gameField) {
        for (int i = 0; i < Constants.FIELD_SIZE; i++) {
            if (gameField[i][0] == gameField[i][1] &&
                    gameField[i][0] == gameField[i][2] &&
                    (gameField[i][0] == Constants.X_SYMBOL || gameField[i][0] == Constants.O_SYMBOL)) {
                winningCells = getRowsWinningCellsCoordinates(gameField, i);
                // Здесь можно вызвать метод для отрисовки линии или выполнить другие действия
                System.out.println("checkRowsForWin  Победила строка: " + Arrays.toString(winningCells.get(0)) + ", " +
                        Arrays.toString(winningCells.get(1)) + ", " +
                        Arrays.toString(winningCells.get(2)));
                return true;
            }
        }
        return false;
    }
    static boolean checkColumnsForWin(char[][] gameField) {
        boolean columnWinFound = false; // Добавляем флаг для отслеживания нахождения выигрыша в столбце
        for (int i = 0; i < Constants.FIELD_SIZE; i++) {
            if (gameField[0][i] == gameField[1][i] &&
                    gameField[0][i] == gameField[2][i] &&
                    (gameField[0][i] == Constants.X_SYMBOL || gameField[0][i] == Constants.O_SYMBOL)) {
                winningCells = getColumnWinningCellsCoordinates(gameField, i);
                // Здесь можно вызвать метод для отрисовки линии или выполнить другие действия
                System.out.println("checkColumnsForWin  Победил столбец: " + Arrays.toString(winningCells.get(0)) + ", " +
                        Arrays.toString(winningCells.get(1)) + ", " +
                        Arrays.toString(winningCells.get(2)));
                columnWinFound = true; // Устанавливаем флаг, что выигрыш в столбце найден
                break; // Прерываем цикл, так как выигрыш найден
            }
        }
        return columnWinFound; // Возвращаем флаг, показывающий, был ли найден выигрыш в столбце
    }

    // Проверка на победу по диагоналям
    static boolean checkDiagonalsForWin(char[][] gameField) {
        char symbol = gameField[0][0];

        if ((symbol == gameField[1][1] && symbol == gameField[2][2] &&
                (symbol == Constants.X_SYMBOL || symbol == Constants.O_SYMBOL))) {
            for (int i = 0; i < Constants.FIELD_SIZE; i++) {
                winningCells.add(new int[]{i, i});

                winningCells = getDiagonalWinningCellsCoordinates(gameField);
                // Здесь можно вызвать метод для отрисовки линии или выполнить другие действия
                System.out.println("checkRowsForWin  Победила диагональ 1: " + Arrays.toString(winningCells.get(0)) + ", " +
                        Arrays.toString(winningCells.get(1)) + ", " +
                        Arrays.toString(winningCells.get(2)));

            }
            // Здесь можно вызвать метод для отрисовки линии или выполнить другие действия
            System.out.println("Победила диагональ: " + winningCells);
            return true;
        }

        if ((gameField[0][2] == gameField[1][1] && gameField[0][2] == gameField[2][0]) &&
                (gameField[0][2] == Constants.X_SYMBOL || gameField[0][2] == Constants.O_SYMBOL)) {
            for (int i = 0; i < Constants.FIELD_SIZE; i++) {
                winningCells.add(new int[]{i, Constants.FIELD_SIZE - 1 - i});

                winningCells = getDiagonalWinningCellsCoordinates(gameField);
                // Здесь можно вызвать метод для отрисовки линии или выполнить другие действия
                System.out.println("checkRowsForWin  Победила диагональ 2: " + Arrays.toString(winningCells.get(0)) + ", " +
                        Arrays.toString(winningCells.get(1)) + ", " +
                        Arrays.toString(winningCells.get(2)));
            }
            // Здесь можно вызвать метод для отрисовки линии или выполнить другие действия
//            System.out.println("Победила диагональ: " + winningCells);
            return true;
        }

        return false;
    }

    public Button getButtonByIndexes(int row, int col) {
        ObservableList<Node> children = gridPane.getChildren(); // Получаем список детей GridPane
        for (Node node : children) {
            if (node instanceof Button button) { // Проверяем, является ли дочерний элемент кнопкой
                // Получаем индексы кнопки
                int rowIndex = GridPane.getRowIndex(button) == null ? 0 : GridPane.getRowIndex(button);
                int colIndex = GridPane.getColumnIndex(button) == null ? 0 : GridPane.getColumnIndex(button);
                // Если индексы совпадают с переданными, возвращаем кнопку
                if (rowIndex == row && colIndex == col) {
                    return button;
                }
            }
        }
        return null; // Возвращаем null, если кнопка не найдена
    }

    // Метод для получения выигрышных ячеек
    // Метод для получения выигрышных ячеек
    public static List<int[]> getWinningCells(char[][] gameField) {
        List<int[]> winningCells = new ArrayList<>();
        if (checkRowsForWin(gameField)) {
            // Добавляем координаты выигрышных ячеек в строках
            for (int i = 0; i < Constants.FIELD_SIZE; i++) {
                winningCells.addAll(getRowsWinningCellsCoordinates(gameField, i));
            }
        } else if (checkColumnsForWin(gameField)) {
            // Добавляем координаты выигрышных ячеек в столбцах
            for (int i = 0; i < Constants.FIELD_SIZE; i++) {
                winningCells.addAll(getColumnWinningCellsCoordinates(gameField, i));
            }
        } else if (checkDiagonalsForWin(gameField)) {
            // Добавляем координаты выигрышных ячеек по диагоналям
            winningCells.addAll(getDiagonalWinningCellsCoordinates(gameField));
        }
        return winningCells;
    }


}
