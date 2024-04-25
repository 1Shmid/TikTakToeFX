package tiktaktoefx20;

import javafx.collections.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.*;

public class GameEngine extends GameResultHandler {

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
    static List<int[]> getWinningCellsCoordinates(char[][] gameField, int row) {
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
        List<int[]> allDiagonalCells = new ArrayList<>();
        allDiagonalCells.addAll(diagonalLeftRight);
        allDiagonalCells.addAll(diagonalRightLeft);
        return allDiagonalCells;
    }

    static boolean checkRowsForWin(char[][] gameField) {
        for (int i = 0; i < Constants.FIELD_SIZE; i++) {
            if (gameField[i][0] == gameField[i][1] &&
                    gameField[i][0] == gameField[i][2] &&
                    (gameField[i][0] == Constants.X_SYMBOL || gameField[i][0] == Constants.O_SYMBOL)) {
                List<int[]> winningCells = getWinningCellsCoordinates(gameField, i);
                // Здесь можно вызвать метод для отрисовки линии или выполнить другие действия
                System.out.println("Победила строка: " + winningCells);
                return true;
            }
        }
        return false;
    }
    static boolean checkColumnsForWin(char[][] gameField) {
        for (int i = 0; i < Constants.FIELD_SIZE; i++) {
            if (gameField[0][i] == gameField[1][i] &&
                    gameField[0][i] == gameField[2][i] &&
                    (gameField[0][i] == Constants.X_SYMBOL || gameField[0][i] == Constants.O_SYMBOL)) {
                List<int[]> winningCells = getColumnWinningCellsCoordinates(gameField, i);
                // Здесь можно вызвать метод для отрисовки линии или выполнить другие действия
                System.out.println("Победил столбец: " + winningCells);
                return true;
            }
        }
        return false;
    }

    // Проверка на победу по диагоналям
    static boolean checkDiagonalsForWin(char[][] gameField) {
        char symbol = gameField[0][0];
        List<int[]> winningCells = new ArrayList<>();

        if ((symbol == gameField[1][1] && symbol == gameField[2][2] &&
                (symbol == Constants.X_SYMBOL || symbol == Constants.O_SYMBOL))) {
            for (int i = 0; i < Constants.FIELD_SIZE; i++) {
                winningCells.add(new int[]{i, i});
            }
            // Здесь можно вызвать метод для отрисовки линии или выполнить другие действия
            System.out.println("Победила диагональ: " + winningCells);
            return true;
        }

        if ((gameField[0][2] == gameField[1][1] && gameField[0][2] == gameField[2][0]) &&
                (gameField[0][2] == Constants.X_SYMBOL || gameField[0][2] == Constants.O_SYMBOL)) {
            for (int i = 0; i < Constants.FIELD_SIZE; i++) {
                winningCells.add(new int[]{i, Constants.FIELD_SIZE - 1 - i});
            }
            // Здесь можно вызвать метод для отрисовки линии или выполнить другие действия
            System.out.println("Победила диагональ: " + winningCells);
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

//    // Определение выигрышных ячеек
//    public Button[] getWinningCells(char[][] gameField) {
//        if (checkRowsForWin(gameField)) {
//            // Победа по строкам
//            for (int i = 0; i < Constants.FIELD_SIZE; i++) {
//                if (gameField[i][0] == gameField[i][1] && gameField[i][0] == gameField[i][2]) {
//                    return new Button[]{getButtonByIndexes(i, 0), getButtonByIndexes(i, 1), getButtonByIndexes(i, 2)};
//                }
//            }
//        } else if (checkColumnsForWin(gameField)) {
//            // Победа по столбцам
//            for (int i = 0; i < Constants.FIELD_SIZE; i++) {
//                if (gameField[0][i] == gameField[1][i] && gameField[0][i] == gameField[2][i]) {
//                    return new Button[]{getButtonByIndexes(0, i), getButtonByIndexes(1, i), getButtonByIndexes(2, i)};
//                }
//            }
//        } else if (checkDiagonalsForWin(gameField)) {
//            // Победа по диагоналям
//            if (gameField[0][0] == gameField[1][1] && gameField[0][0] == gameField[2][2]) {
//                return new Button[]{getButtonByIndexes(0, 0), getButtonByIndexes(1, 1), getButtonByIndexes(2, 2)};
//            } else if (gameField[0][2] == gameField[1][1] && gameField[0][2] == gameField[2][0]) {
//                return new Button[]{getButtonByIndexes(0, 2), getButtonByIndexes(1, 1), getButtonByIndexes(2, 0)};
//            }
//        }
//        return new Button[0]; // Если нет победы, возвращаем пустой массив
//    }
}