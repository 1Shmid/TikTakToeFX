package tiktaktoefx20;

import java.net.URL;
import java.util.*;

import javafx.event.*;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;



public class GameController extends ComputerMoveHandler {

    @FXML
    private ComboBox comb;

    @FXML
    void Select(ActionEvent event) {
        String s = comb.getSelectionModel().getSelectedItem().toString();
    }


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }



    @FXML
    void btnClick(ActionEvent event) {

        Button clickedButton = (Button) event.getSource(); // Получаем кнопку, на которую было нажато

        clickedButton.setText(String.valueOf(playerSymbol));
        clickedButton.setDisable(true);

        // Получаем индексы кнопки
        int row = GridPane.getRowIndex(clickedButton) == null ? 0 : GridPane.getRowIndex(clickedButton);
        int col = GridPane.getColumnIndex(clickedButton) == null ? 0 : GridPane.getColumnIndex(clickedButton);

        // Обновляем состояние игры
        gameField[row][col] = playerSymbol;

        // Проверяем условия победы или ничьи
        if (checkForWin() || checkForDraw()) {
            // Если условие победы или ничьи выполнено, игра заканчивается
            winnerSymbol = "The player"; // Устанавливаем символ победителя
            endGame(clickedButton.getScene().getRoot());
        } else {

            // Вызываем соответствующий метод для хода компьютера в зависимости от выбранного уровня сложности
            String selectedLevel = comb.getSelectionModel().getSelectedItem().toString();
            if ("EASY".equals(selectedLevel)) {
                computerMoveRandom();
            } else if ("HARD".equals(selectedLevel)) {
                computerMoveSmart();
            } else if ("IMPOSSIBLE".equals(selectedLevel)) {
                computerMoveGenius();
            }
        }
    }

    @FXML
    void initialize() {
        initializeComboBox();
        initializeGameField();
    }

    private void initializeComboBox() {
        ObservableList<String> list = FXCollections.observableArrayList("EASY", "HARD", "IMPOSSIBLE");
        comb.setItems(list);
        comb.setValue("EASY");
        comb.setOnAction(event -> startNewGame());
    }

    private void initializeGameField() {
        for (int i = 0; i < Constants.FIELD_SIZE; i++) {
            for (int j = 0; j < Constants.FIELD_SIZE; j++) {
                gameField[i][j] = Constants.EMPTY_SYMBOL;
            }
        }
    }





//    @FXML
//    void initialize() {
//
//        ObservableList<String> list = FXCollections.observableArrayList("EASY", "HARD", "IMPOSSIBLE");
//        comb.setItems(list);
//
//        // Установка "EASY" по умолчанию
//        comb.setValue("EASY");
//
//        // Добавление обработчика событий для ComboBox
//        comb.setOnAction(this::handleComboBoxAction);
//
//    // Объявляем состояние игры
//        for (int i = 0; i < Constants.FIELD_SIZE; i++) {
//            for (int j = 0; j < Constants.FIELD_SIZE; j++) {
//                gameField[i][j] = Constants.EMPTY_SYMBOL;
//            }
//        }
//    }
//
//    // Метод для обработки изменения выбора в ComboBox
//    private void handleComboBoxAction(Event event) {
//        // Здесь вы можете вызвать метод начала новой игры
//        startNewGame();
//    }

//    public char[][] getGameField() {
//        return gameField;
//    }

}

