package tiktaktoefx20;

import javafx.application.*;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.*;

public class ComputerMoveHandler {
    protected final char playerSymbol = Constants.X_SYMBOL; // Символ игрока всегда 'X'
    protected final char[][] gameField = new char[Constants.FIELD_SIZE][Constants.FIELD_SIZE]; // создание массива для хранения получаемых в процессе игры значений от кнопки
    private final char computerSymbol = Constants.O_SYMBOL;   // Символ компьютера всегда 'O'
    protected String winnerSymbol;
    @FXML
    private GridPane gridPane;




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
        computerButton.setText(String.valueOf(computerSymbol));
        computerButton.setDisable(true);
        gameField[row][col] = computerSymbol;

        // Проверяем условия победы или ничьи
        if (checkForWin() || checkForDraw()) {
            // Если условие победы или ничьи выполнено, игра заканчивается
            winnerSymbol = "The computer"; // Устанавливаем символ победителя
            endGame(computerButton.getScene().getRoot());
        }
    }

    // Реализация хода компьютера через логику для выбора случайной свободной ячейки
    void computerMoveSmart() {

        // Ищем выигрышную ячейку и если ячейка свободна, пытаемся сделать ход компьютера и проверяем, выиграет ли он

        if (MakeWinMove()) return;

        // Если ни компьютер, ни игрок не может выиграть на следующем ходе, делаем случайный ход
        computerMoveRandom();
    }

    // Реализация хода компьютера через логику для выбора случайной свободной ячейки и проверку углов и центра
    void computerMoveGenius() {

        // Ищем выигрышную ячейку и если ячейка свободна, пытаемся сделать ход компьютера и проверяем, выиграет ли он

        if (MakeWinMove()) return;

        boolean moveMade = false; // Флаг для отслеживания сделанного хода
        // Если ни компьютер, ни игрок не может выиграть на следующем ходе, пытаемся занять углы
        moveMade = occupyCorners();
        if (moveMade) {
            return;
        }
        occupyCenter(); // Попробовать занять центральную клетку

        // Если ни компьютер, ни игрок не может выиграть на следующем ходе, делаем случайный ход
        computerMoveRandom();

    }

    private boolean MakeWinMove() {
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


    // Проверка на победителя
    protected boolean checkForWin() {
        // Проверка победы по строкам
        for (int i = 0; i < Constants.FIELD_SIZE; i++) {
            if (gameField[i][0] == gameField[i][1] &&
                    gameField[i][0] == gameField[i][2] &&
                    (gameField[i][0] == 'X' || gameField[i][0] == 'O')) {
                return true;
            }
        }

        // Проверка победы по столбцам
        for (int i = 0; i < Constants.FIELD_SIZE; i++) {
            if (gameField[0][i] == gameField[1][i] &&
                    gameField[0][i] == gameField[2][i] &&
                    (gameField[0][i] == 'X' || gameField[0][i] == 'O')) {
                return true;
            }
        }

        // Проверка победы по диагоналям
        return (gameField[0][0] == gameField[1][1] && gameField[0][0] == gameField[2][2] &&
                (gameField[0][0] == 'X' || gameField[0][0] == 'O')) ||
                (gameField[0][2] == gameField[1][1] && gameField[0][2] == gameField[2][0]) &&
                        (gameField[0][2] == 'X' || gameField[0][2] == 'O');
    }

    // Проверка на ничью
    protected boolean checkForDraw() {
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
        computerButton.setText(String.valueOf(computerSymbol));
        computerButton.setDisable(true);
        gameField[row][col] = computerSymbol; // Фиксируем ход компьютера

        // Проверяем условия победы
        if (checkForWin()) {
            winnerSymbol = "The computer"; // Устанавливаем символ победителя
            endGame(computerButton.getScene().getRoot());
            return;
        }

        // Проверяем наличие ничьи
        if (checkForDraw()) {
            endGame(computerButton.getScene().getRoot());
            return;
        }
    }

    @FXML
    void endGame(Parent root) {
        // Проверяем условия победы или ничьи
        String result = "";
        if (checkForWin()) {
            result = winnerSymbol + " wins!";
        } else if (checkForDraw()) {
            result = "It's a draw!!";
        }

        // Создаем новое диалоговое окно

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Game result");
        alert.setHeaderText(null);
        alert.setContentText(result);

        // Создаем кнопки для новой игры и выхода
        ButtonType newGameButton = new ButtonType("New Game", ButtonBar.ButtonData.YES);
        ButtonType exitButton = new ButtonType("Exit", ButtonBar.ButtonData.NO);

        // Устанавливаем кнопки в диалоговом окне
        alert.getButtonTypes().setAll(newGameButton, exitButton);


        // Ожидаем действия пользователя
        Optional<ButtonType> resultButton = alert.showAndWait();

        // Если пользователь выбрал "Новая игра", начинаем новую игру
        if (resultButton.isPresent() && resultButton.get() == newGameButton) {

            startNewGame();

        } else {
            // Иначе закрываем приложение
            Platform.exit();
        }
    }

    protected void startNewGame() {

        // Очищаем игровое поле и включаем все кнопки
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Button button) {
                button.setText("");
                button.setDisable(false);
            }
        }

        // Обнуляем состояние полей массива игры
        for (int i = 0; i < Constants.FIELD_SIZE; i++) {
            for (int j = 0; j < Constants.FIELD_SIZE; j++) {
                gameField[i][j] = Constants.EMPTY_SYMBOL;
            }
        }
    }
}
