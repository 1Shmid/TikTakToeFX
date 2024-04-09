package tiktaktoefx20;

import java.net.URL;
import java.util.*;

import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import static javafx.application.Application.launch;

public class GameController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    private char CurrentSymbol = Constants.DEFAULT_SYMBOL;
    private char[][] gameField = new char[Constants.FIELD_SIZE][Constants.FIELD_SIZE]; // создание массива для хранения получаемых в процессе игры значений от кнопки
    private boolean gameOver = false;
    private Random random = new Random();
    private GridPane gridPane; // Объявление переменной gridPane

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







    private javafx.scene.Node getNodeFromGridPane(int row, int col) {
        for (javafx.scene.Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                return node;
            }
        }
        return null;
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

        // Сбрасываем флаг окончания игры
        gameOver = false;
    }



    @FXML
    void endGame() {
        // Проверяем условия победы или ничьи
        String result = "";
        if (checkForWin()) {
            result = "Победил игрок!";
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

        // Если пользователь выбрал "Новая игра", начинаем новую игру
        if (resultButton.isPresent() && resultButton.get() == newGameButton) {
            startNewGame();
        } else {
            // Иначе закрываем приложение
            Platform.exit();
        }
    }

//    private void computerMove() {
//        int row, col;
//        do {
//            row = random.nextInt(3);
//            col = random.nextInt(3);
//        } while (gameField[row][col] != Constants.EMPTY_CELL || gameField[row][col] != Constants.DEFAULT_SYMBOL);
//
//        javafx.scene.Node node = getNodeFromGridPane(row, col);
//        if (node != null && node instanceof Button) {
//            Button button = (Button) node;
//            button.setText("O");
//        } else {
//            System.err.println("Ошибка: не удалось найти кнопку для установки хода компьютера.");
//            // Или выполните другие действия в зависимости от вашего приложения
//        }
//
//        gameField[row][col] = 'O';
//        CurrentSymbol = Constants.DEFAULT_SYMBOL;
//    }


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

    @FXML
    void btnClick(ActionEvent event) {

        Button clickedButton = (Button) event.getSource(); // Получаем кнопку, на которую было нажато
/*
        // Проверяем, что кнопка еще не была использована
        if (!clickedButton.getText().isEmpty()) {
            // Если кнопка уже содержит текст (X или O), игнорируем нажатие
            return;
        }

 */

        // Выполняем ход игрока (устанавливаем X на кнопку)
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
            endGame();
        } else {
            // Если условие победы или ничьи не выполнено, передаем ход компьютеру
            //computerMove();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Ход О" + clickedButton.getText(), ButtonType.OK);
        }
        CurrentSymbol = CurrentSymbol == 'X' ? 'O' : 'X';

    }
        // Переключаем символ текущего игрока
        // CurrentSymbol = (CurrentSymbol == 'X') ? 'O' : 'X';


        // if (gameOver || clickedButton.getText() != "") return; // если игра окончена - выходим и на нажатие больше не реагируем

        // получаем координаты кнопки  - индекс ячейки,
        // и если возвращает null присваиваем 0 - здесь это глюк метода get и таким образом мы это исправляем

        /*

        int rowIndex = GridPane.getRowIndex(clickedButton) == null ? 0 : GridPane.getRowIndex(clickedButton);
        int colIndex = GridPane.getColumnIndex(clickedButton) == null ? 0 : GridPane.getColumnIndex(clickedButton);

        gameField[rowIndex][colIndex] = CurrentSymbol; // записываем в массив кликнутую кнопку и символ на ней

        // Проверяем победителя в строке
        if (gameField[0][0] == gameField[0][1] &&
                gameField[0][0] == gameField[0][2] &&
                (gameField[0][0] == 'X' ||
                gameField[0][0] == 'O')) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Победил " + clickedButton.getText(), ButtonType.OK);



            alert.showAndWait();

            gameOver = true;
        }
        else if (gameField[1][0] == gameField[1][1] &&
                gameField[1][0] == gameField[1][2] &&
                (gameField[1][0] == 'X' ||
                gameField[1][0] == 'O')) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Победил " + clickedButton.getText(), ButtonType.OK);
            alert.showAndWait();

            gameOver = true;
        }
        else if (gameField[2][0] == gameField[2][1] &&
                gameField[2][0] == gameField[2][2] &&
                (gameField[2][0] == 'X' ||
                gameField[2][0] == 'O')) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Победил " + clickedButton.getText(), ButtonType.OK);
            alert.showAndWait();

            gameOver = true;
        }
        // Проверяем победителя в строке
        else if (gameField[0][0] == gameField[1][0] &&
                gameField[0][0] == gameField[2][0] &&
                (gameField[0][0] == 'X' ||
                gameField[0][0] == 'O')) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Победил " + clickedButton.getText(), ButtonType.OK);
            alert.showAndWait();

            gameOver = true;
        }
        else if (gameField[0][1] == gameField[1][1] &&
                gameField[0][1] == gameField[2][1] &&
                (gameField[0][1] == 'X' ||
                gameField[0][1] == 'O')) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Победил " + clickedButton.getText(), ButtonType.OK);
            alert.showAndWait();

            gameOver = true;
        }
        else if (gameField[0][2] == gameField[1][2] &&
                gameField[0][2] == gameField[2][2] &&
                (gameField[0][2] == 'X' ||
                gameField[0][2] == 'O')) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Победил " + clickedButton.getText(), ButtonType.OK);
            alert.showAndWait();

            gameOver = true;
        }
        // Проверяем победителя в диагоналях
        else if (gameField[0][0] == gameField[1][1] &&
                gameField[0][0] == gameField[2][2] &&
                (gameField[0][0] == 'X' ||
                gameField[0][0] == 'O')) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Победил " + clickedButton.getText(), ButtonType.OK);
            alert.showAndWait();

            gameOver = true;
        }
        else if (gameField[2][2] == gameField[1][1] &&
                gameField[2][2] == gameField[0][2] &&
                (gameField[2][2] == 'X' ||
                gameField[2][2] == 'O')) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Победил " + clickedButton.getText(), ButtonType.OK);
            alert.showAndWait();

            gameOver = true;
        }
*/





    @FXML
    void initialize() {
        gridPane = new GridPane();
        for (int i = 0; i < Constants.FIELD_SIZE; i++) {
            for (int j = 0; j < Constants.FIELD_SIZE; j++) {
                gameField[i][j] = Constants.EMPTY_CELL;
            }
        }
    }

}
