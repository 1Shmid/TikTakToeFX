package tiktaktoefx20;

import java.net.URL;
import java.util.*;

import javafx.application.*;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;


public class GameController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    private char CurrentSymbol = Constants.DEFAULT_SYMBOL;
    private char[][] gameField = new char[Constants.FIELD_SIZE][Constants.FIELD_SIZE]; // создание массива для хранения получаемых в процессе игры значений от кнопки
    private String winnerSymbol;



    @FXML
    private GridPane gridPane;


    private boolean checkForWin() {
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
        if ((gameField[0][0] == gameField[1][1] && gameField[0][0] == gameField[2][2] &&
                (gameField[0][0] == 'X' || gameField[0][0] == 'O')) ||
                (gameField[0][2] == gameField[1][1] && gameField[0][2] == gameField[2][0]) &&
                        (gameField[0][2] == 'X' || gameField[0][2] == 'O')) {
            return true;
        }

        return false;
    }

    @FXML
    void endGame() {
        // Проверяем условия победы или ничьи
        String result = "";
        if (checkForWin()) {
            result = "Победил " + winnerSymbol + "!";
        } else if (checkForDraw()) {
            result = "Ничья!";
        }

        // Создаем новое диалоговое окно
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Результат игры");
        alert.setHeaderText(null);
        alert.setContentText(result);

        // Создаем кнопки для новой игры и выхода
        ButtonType newGameButton = new ButtonType("Новая игра", ButtonBar.ButtonData.YES);
        ButtonType exitButton = new ButtonType("Выход", ButtonBar.ButtonData.NO);

        // Устанавливаем кнопки в диалоговом окне
        alert.getButtonTypes().setAll(newGameButton, exitButton);

        // Ожидаем действия пользователя
        Optional<ButtonType> resultButton = alert.showAndWait();

        // Проверяем, какую кнопку выбрал пользователь
        if (resultButton.isPresent()) {
            if (resultButton.get() == newGameButton) {
                // Пользователь выбрал "Новая игра"
                System.out.println("Нажата кнопка 'Новая игра'");
            } else {
                // Пользователь выбрал другую кнопку
                System.out.println("Пользователь выбрал другую кнопку");
            }
        } else {
            // Окно закрыто без выбора
            System.out.println("Окно закрыто без выбора");
        }

        // Если пользователь выбрал "Новая игра", начинаем новую игру
        if (resultButton.isPresent() && resultButton.get() == newGameButton) {

            System.out.println(" Go to startNewGame");
            startNewGame();

        } else {
            // Иначе закрываем приложение
            Platform.exit();
        }
    }

    private boolean checkForDraw() {
        for (int i = 0; i < Constants.FIELD_SIZE; i++) {
            for (int j = 0; j < Constants.FIELD_SIZE; j++) {
                if (gameField[i][j] == Constants.EMPTY_CELL) {
                    // Найдена пустая ячейка, игра не закончилась вничью
                    return false;
                }
            }
        }
        return true; // Все ячейки заполнены, ничья
    }
    private void startNewGame() {
        // Очищаем игровое поле и включаем все кнопки
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                button.setText("");
                button.setDisable(false);
            }
        }

        // Обнуляем состояние игры
        for (int i = 0; i < Constants.FIELD_SIZE; i++) {
            for (int j = 0; j < Constants.FIELD_SIZE; j++) {
                gameField[i][j] = Constants.EMPTY_CELL;
            }
        }

        // Обнуляем символ текущего игрока
        CurrentSymbol = Constants.DEFAULT_SYMBOL;
        System.out.println("CurrentSymbol: " + CurrentSymbol);
    }

    private Button getButtonByIndexes(int row, int col) {
        ObservableList<Node> children = gridPane.getChildren(); // Получаем список детей GridPane
        for (Node node : children) {
            if (node instanceof Button) { // Проверяем, является ли дочерний элемент кнопкой
                Button button = (Button) node;
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
    private void computerMove() {
        // Реализация хода компьютера через логику для выбора случайной свободной ячейки
        Random random = new Random();
        int row, col;
        do {
            row = random.nextInt(gameField.length);
            col = random.nextInt(gameField[0].length);
        } while (gameField[row][col] != ' '); // Проверяем, что выбранная ячейка свободна
        // Находим кнопку по индексам и делаем ход компьютера
        Button computerButton = getButtonByIndexes(row, col);
        computerButton.setText(String.valueOf(CurrentSymbol));
        computerButton.setDisable(true);
        gameField[row][col] = CurrentSymbol;
        // Проверяем условия победы или ничьи
        if (checkForWin() || checkForDraw()) {
            // Если условие победы или ничьи выполнено, игра заканчивается
            winnerSymbol = "компьютер";
            System.out.println("Победил: " + winnerSymbol);
            endGame();
        }
        // Переключаем символ текущего игрока
        CurrentSymbol = Constants.DEFAULT_SYMBOL;
    }
    @FXML
    void btnClick(ActionEvent event) {

        Button clickedButton = (Button) event.getSource(); // Получаем кнопку, на которую было нажато

        clickedButton.setText(String.valueOf(CurrentSymbol));
        clickedButton.setDisable(true);

        // Получаем индексы кнопки
        int row = GridPane.getRowIndex(clickedButton) == null ? 0 : GridPane.getRowIndex(clickedButton);
        int col = GridPane.getColumnIndex(clickedButton) == null ? 0 : GridPane.getColumnIndex(clickedButton);

        // Обновляем состояние игры
        gameField[row][col] = CurrentSymbol;

        // Проверяем условия победы или ничьи
        if (checkForWin() || checkForDraw()) {
            // Если условие победы или ничьи выполнено, игра заканчивается
            winnerSymbol = "игрок";
            System.out.println("Победил: " + winnerSymbol);
            endGame();
        } else {
            // Если условие победы или ничьи не выполнено, передаем ход компьютеру и переключаем символ текущего игрока
            CurrentSymbol = CurrentSymbol == 'X' ? 'O' : 'X';
            computerMove();
        }
    }


    @FXML
    void initialize() {
    // Объявляем состояние игры
        for (int i = 0; i < Constants.FIELD_SIZE; i++) {
            for (int j = 0; j < Constants.FIELD_SIZE; j++) {
                gameField[i][j] = Constants.EMPTY_CELL;
            }
        }
    }

}

