package tiktaktoefx20;

import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;



public class GameController extends ComputerMoveHandler {

    @FXML
    private ComboBox<String> comb;


    @FXML
    void Select() {
    }

    public void setStage() {
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
            endGame();
        } else {

            // Вызываем соответствующий метод для хода компьютера в зависимости от выбранного уровня сложности
            String selectedLevel = comb.getSelectionModel().getSelectedItem();
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
}

