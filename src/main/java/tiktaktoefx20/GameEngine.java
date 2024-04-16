package tiktaktoefx20;

import javafx.collections.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

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

    private static boolean checkRowsForWin(char[][] gameField) {
        for (int i = 0; i < Constants.FIELD_SIZE; i++) {
            if (gameField[i][0] == gameField[i][1] &&
                    gameField[i][0] == gameField[i][2] &&
                    (gameField[i][0] == Constants.X_SYMBOL || gameField[i][0] == Constants.O_SYMBOL)) {
                return true;
            }
        }
        return false;
    }
    private static boolean checkColumnsForWin(char[][] gameField) {
        for (int i = 0; i < Constants.FIELD_SIZE; i++) {
            if (gameField[0][i] == gameField[1][i] &&
                    gameField[0][i] == gameField[2][i] &&
                    (gameField[0][i] == Constants.X_SYMBOL || gameField[0][i] == Constants.O_SYMBOL)) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkDiagonalsForWin(char[][] gameField) {
        return (gameField[0][0] == gameField[1][1] && gameField[0][0] == gameField[2][2] &&
                (gameField[0][0] == Constants.X_SYMBOL || gameField[0][0] == Constants.O_SYMBOL)) ||
                (gameField[0][2] == gameField[1][1] && gameField[0][2] == gameField[2][0]) &&
                        (gameField[0][2] == Constants.X_SYMBOL || gameField[0][2] == Constants.O_SYMBOL);
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
}