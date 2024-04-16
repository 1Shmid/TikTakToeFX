package tiktaktoefx20;

import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import tiktaktoefx20.strategies.*;
import tiktaktoefx20.TTTGameLogic;


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

        // Обновляем поле игры
        gameField[row][col] = playerSymbol;

        // Проверяем условия победы или ничьи
        if (TTTGameLogic.checkForWinS(gameField) || TTTGameLogic.checkForDrawS(gameField)) {

            // Если условие победы или ничьи выполнено, игра заканчивается
            winnerSymbol = "The player"; // Устанавливаем символ победителя
            endGame();

        } else {

            // Создаем объекты стратегий
            MoveStrategy randomStrategy = new RandomMoveStrategy();
            MoveStrategy attackStrategy = new AttackMoveStrategy();

            // Создаем объекты обработчиков хода с разными стратегиями
            ComputerStrategicMoveHandler randomMoveHandler = new ComputerStrategicMoveHandler(randomStrategy);
            ComputerStrategicMoveHandler attackMoveHandler = new ComputerStrategicMoveHandler(attackStrategy);

            // Выбираем уровень сложности (стратегию)
            String selectedLevel = comb.getSelectionModel().getSelectedItem();

            // Делаем ход компьютера с выбранной стратегией
            int[] computerMove = switch (selectedLevel) {
                case "EASY" -> randomMoveHandler.makeMove(gameField ,selectedLevel);
                case "HARD" -> attackMoveHandler.makeMove(gameField ,selectedLevel);
                default -> randomMoveHandler.makeMove(gameField,selectedLevel); // По умолчанию используем случайную стратегию
            };

            // Выводим ход компьютера
            System.out.println("Стратегия - " + selectedLevel);
            System.out.println("Computer move: row = " + computerMove[0] + ", col = " + computerMove[1]);

            // По полученным координатам обновляем графический интерфейс от имени компьютера
            int computerRow = computerMove[0];
            int computerCol = computerMove[1];
            Button computerButton = getButtonByIndexes(computerRow, computerCol);
            computerButton.setText(String.valueOf(computerSymbol));
            computerButton.setDisable(true);

            gameField[computerRow][computerCol] = computerSymbol;

            if (TTTGameLogic.checkForWinS(gameField) || TTTGameLogic.checkForDrawS(gameField)) {
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

