package tiktaktoefx20;

import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import tiktaktoefx20.strategies.*;


public class GameController extends GameLogic {

    // Создаем объекты стратегий
    MoveStrategy easyStrategy = new EasyStrategy();
    MoveStrategy hardStrategy = new HardStrategy();

    // Создаем объекты обработчиков хода с разными стратегиями
    Context easyMoveHandler = new Context(easyStrategy);
    Context hardMoveHandler = new Context(hardStrategy);

    private final char[][] gameField = new char[Constants.FIELD_SIZE][Constants.FIELD_SIZE]; // добавляем игровое поле


    @FXML
    private ComboBox<String> comb;

    @FXML
    void Select() {
    }

    public void setStage() {
    }

    private Context computerStrategicMoveHandler;


    @FXML
    void btnClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource(); // Получаем кнопку, на которую было нажато
        clickedButton.setText(String.valueOf(Constants.PLAYER_SYMBOL));
        clickedButton.setDisable(true);

        // Получаем индексы кнопки
        int row = GridPane.getRowIndex(clickedButton) == null ? 0 : GridPane.getRowIndex(clickedButton);
        int col = GridPane.getColumnIndex(clickedButton) == null ? 0 : GridPane.getColumnIndex(clickedButton);

        // Обновляем поле игры
        gameField[row][col] = Constants.PLAYER_SYMBOL;

        // Проверяем условия победы или ничьи
        if (checkForDrawS(gameField) || checkForWinS(gameField)) {

            // Если условие победы или ничьи выполнено, игра заканчивается
            winnerSymbol = "The player"; // Устанавливаем символ победителя
            endGame(gameField);

        } else {
            // Выбираем уровень сложности (стратегию)
            String selectedLevel = comb.getSelectionModel().getSelectedItem();

            // Делаем ход компьютера с выбранной стратегией
            int[] computerMove = switch (selectedLevel) {
                case "EASY" -> easyMoveHandler.makeMove(gameField ,selectedLevel);
                case "HARD" -> hardMoveHandler.makeMove(gameField ,selectedLevel);
                default -> easyMoveHandler.makeMove(gameField,selectedLevel); // По умолчанию используем случайную стратегию
            };

            // По полученным координатам обновляем графический интерфейс от имени компьютера
            int computerRow = computerMove[0];
            int computerCol = computerMove[1];
            Button computerButton = getButtonByIndexes(computerRow, computerCol);
            computerButton.setText(String.valueOf(Constants.COMPUTER_SYMBOL));
            computerButton.setDisable(true);

            gameField[computerRow][computerCol] = Constants.COMPUTER_SYMBOL;

            if (checkForDrawS(gameField) || checkForWinS(gameField)) {

                winnerSymbol = "The computer"; // Устанавливаем символ победителя
                endGame(gameField);
            }
        }
    }

    @FXML
    void initialize() {
        initializeComboBox();
        initializeGameField();
        computerStrategicMoveHandler = new Context(new EasyStrategy());
    }
    private void initializeComboBox() {

        ObservableList<String> list = FXCollections.observableArrayList("EASY", "HARD", "IMPOSSIBLE");
        comb.setItems(list);
        comb.setValue("EASY");
        comb.setOnAction(event -> startNewGame(gameField));
    }

    private void initializeGameField() {
        for (int i = 0; i < Constants.FIELD_SIZE; i++) {
            for (int j = 0; j < Constants.FIELD_SIZE; j++) {
                gameField[i][j] = Constants.EMPTY_SYMBOL;
            }
        }
    }
}

