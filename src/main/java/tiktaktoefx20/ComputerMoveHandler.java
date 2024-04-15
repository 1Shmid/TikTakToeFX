package tiktaktoefx20;

import javafx.collections.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.*;

public class ComputerMoveHandler extends GameHandler {
    protected final char playerSymbol = Constants.X_SYMBOL; // Символ игрока всегда 'X'
    private final char computerSymbol = Constants.O_SYMBOL;   // Символ компьютера всегда 'O'


    private Button getButtonByIndexes(int row, int col) {
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
    // Реализация хода компьютера случайным образом
    protected void computerMoveRandom() {

        Random random = new Random();
        int row, col;
        do {
            row = random.nextInt(gameField.length);
            col = random.nextInt(gameField[0].length);
        } while (gameField[row][col] != Constants.EMPTY_SYMBOL); // Проверяем, что выбранная ячейка свободна
        // Находим кнопку по индексам и делаем ход компьютера
        Button computerButton = getButtonByIndexes(row, col);
        assert computerButton != null;
        computerButton.setText(String.valueOf(computerSymbol));
        computerButton.setDisable(true);
        gameField[row][col] = computerSymbol;

        // Проверяем условия победы или ничьи
        if (checkForWin() || checkForDraw()) {
            // Если условие победы или ничьи выполнено, игра заканчивается
            winnerSymbol = "The computer"; // Устанавливаем символ победителя
            endGame();
        }
    }

    // Реализация хода компьютера через логику для выбора случайной свободной ячейки
    void computerMoveSmart() {

        // Ищем выигрышную ячейку и если ячейка свободна, пытаемся сделать ход компьютера и проверяем, выиграет ли он

        if (makeWinMove()) return;

        // Если ни компьютер, ни игрок не может выиграть на следующем ходе, делаем случайный ход
        computerMoveRandom();
    }

    // Реализация хода компьютера через логику для выбора случайной свободной ячейки и проверку углов и центра
    void computerMoveGenius() {

        // Ищем выигрышную ячейку и если ячейка свободна, пытаемся сделать ход компьютера и проверяем, выиграет ли он

        if (makeWinMove()) return;

        boolean moveMade; // Флаг для отслеживания сделанного хода
        // Если ни компьютер, ни игрок не может выиграть на следующем ходе, пытаемся занять углы
        moveMade = occupyCorners();
        if (moveMade) {
            return;
        }
        occupyCenter(); // Попробовать занять центральную клетку

        // Если ни компьютер, ни игрок не может выиграть на следующем ходе, делаем случайный ход
        computerMoveRandom();

    }

    private boolean makeWinMove() {
        // Ищем выигрышную ячейку
        for (int row = 0; row < gameField.length; row++) {
            for (int col = 0; col < gameField[0].length; col++) {
                // Если ячейка свободна, пытаемся сделать ход компьютера и проверяем, выиграет ли он
                if (gameField[row][col] == Constants.EMPTY_SYMBOL) {
                    gameField[row][col] = computerSymbol;
                    if (checkForWin()) {
                        // Если ход компьютера выигрывает игру, делаем этот ход

                        makeMove(row, col);

                        return true;
                    }
                    // Если не выигрывает, отменим этот ход и попробуем следующую ячейку
                    gameField[row][col] = Constants.EMPTY_SYMBOL;
                }
            }
        }

        // Если компьютер не нашел выигрышного хода, пытаемся блокировать выигрыш игрока
        for (int row = 0; row < gameField.length; row++) {
            for (int col = 0; col < gameField[0].length; col++) {
                // Если ячейка свободна, пытаемся сделать ход игрока и проверяем, выиграет ли он
                if (gameField[row][col] == Constants.EMPTY_SYMBOL) {
                    gameField[row][col] = playerSymbol; // Пытаемся сделать ход игрока
                    if (checkForWin()) {

                        makeMove(row, col);

                        return true;
                    }
                    // Если не выигрывает, отменим этот ход и попробуем следующую ячейку
                    gameField[row][col] = Constants.EMPTY_SYMBOL; // Возвращаем ячейку в исходное состояние
                }
            }
        }
        return false;
    }


    // Захват центральной ячейки
    void occupyCenter() {
        int centerRow = 1; // Индекс строки центральной клетки
        int centerCol = 1; // Индекс столбца центральной клетки

        if (gameField[centerRow][centerCol] == Constants.EMPTY_SYMBOL) {
            makeMove(centerRow, centerCol);
        }
    }

    boolean occupyCorners() {
        List<int[]> freeCorners = new ArrayList<>();

        // Проверяем каждый угол
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

        // Если есть свободные углы, выбираем случайный и занимаем его
        if (!freeCorners.isEmpty()) {
            Random random = new Random();
            int[] selectedCorner = freeCorners.get(random.nextInt(freeCorners.size()));
            int row = selectedCorner[0];
            int col = selectedCorner[1];
            makeMove(row, col);
            return true; // Возвращаем true, чтобы указать, что ход был сделан
        }

        return false; // Если ход не был сделан, возвращаем false
    }

    void makeMove(int row, int col) {

        Button computerButton = getButtonByIndexes(row, col);
        assert computerButton != null;
        computerButton.setText(String.valueOf(computerSymbol));
        computerButton.setDisable(true);
        gameField[row][col] = computerSymbol; // Фиксируем ход компьютера

        // Проверяем условия победы
        if (checkForWin()) {
            winnerSymbol = "The computer"; // Устанавливаем символ победителя
            endGame();
            return;
        }

        // Проверяем наличие ничьи
        if (checkForDraw()) {
            endGame();
        }
    }

}
