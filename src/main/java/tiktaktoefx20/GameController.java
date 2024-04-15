package tiktaktoefx20;

import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import tiktaktoefx20.strategies.*;


public class GameController extends ComputerMoveHandler {

    @FXML
    private ComboBox<String> comb;


    @FXML
    void Select() {
    }

    public void setStage() {
    }



//    @FXML
//    void btnClick(ActionEvent event) {
//
//        Button clickedButton = (Button) event.getSource(); // Получаем кнопку, на которую было нажато
//
//        clickedButton.setText(String.valueOf(playerSymbol));
//        clickedButton.setDisable(true);
//
//        // Получаем индексы кнопки
//        int row = GridPane.getRowIndex(clickedButton) == null ? 0 : GridPane.getRowIndex(clickedButton);
//        int col = GridPane.getColumnIndex(clickedButton) == null ? 0 : GridPane.getColumnIndex(clickedButton);
//
//        // Обновляем состояние игры
//        gameField[row][col] = playerSymbol;
//
//        // Проверяем условия победы или ничьи
//        if (checkForWin() || checkForDraw()) {
//            // Если условие победы или ничьи выполнено, игра заканчивается
//            winnerSymbol = "The player"; // Устанавливаем символ победителя
//            endGame();
//        } else {
//
//            // Вызываем соответствующий метод для хода компьютера в зависимости от выбранного уровня сложности
//            String selectedLevel = comb.getSelectionModel().getSelectedItem();
//            if ("EASY".equals(selectedLevel)) {
//                computerMoveRandom();
//            } else if ("HARD".equals(selectedLevel)) {
//                computerMoveSmart();
//            } else if ("IMPOSSIBLE".equals(selectedLevel)) {
//                computerMoveGenius();
//            }
//        }
//    }


    private ComputerStrategicMoveHandler computerStrategicMoveHandler;

    char computerSymbol = getComputerSymbol();

    private char[][] gameField = new char[Constants.FIELD_SIZE][Constants.FIELD_SIZE]; // добавляем игровое поле

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
            // Получаем символ компьютера
            char computerSymbol = getComputerSymbol();

            // Вызываем метод makeMove() у экземпляра ComputerStrategicMoveHandler
            String selectedLevel = comb.getSelectionModel().getSelectedItem();
            int[] computerMove = computerStrategicMoveHandler.makeMove(gameField, computerSymbol);

            // Обновляем интерфейс и проверяем условия победы или ничьи
            int computerRow = computerMove[0];
            int computerCol = computerMove[1];
            Button computerButton = getButtonByIndexes(computerRow, computerCol);
            computerButton.setText(String.valueOf(computerSymbol));
            computerButton.setDisable(true);
            gameField[computerRow][computerCol] = computerSymbol;

            if (checkForWin() || checkForDraw()) {
                winnerSymbol = "The computer"; // Устанавливаем символ победителя
                endGame();
            }
        }
    }



//    @FXML
//    void initialize() {
//        initializeComboBox();
//        initializeGameField();
//    }


    @FXML
    void initialize() {
        initializeComboBox();
        initializeGameField();
        computerStrategicMoveHandler = new ComputerStrategicMoveHandler(new RandomMoveStrategy());
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

