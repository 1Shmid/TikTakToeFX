package tiktaktoefx20;

import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import tiktaktoefx20.strategies.*;
import tiktaktoefx20.TTTGameLogic;

import java.util.*;


public class GameController extends TTTGameLogic {

    @FXML
    private ComboBox<String> comb;

    @FXML
    void Select() {
    }

    public void setStage() {
    }

    private ComputerStrategicMoveHandler computerStrategicMoveHandler;

    //char computerSymbol = getComputerSymbol();

    private final char[][] gameField = new char[Constants.FIELD_SIZE][Constants.FIELD_SIZE]; // добавляем игровое поле

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

        System.out.println("Game field after Player's move" + Arrays.deepToString(gameField));

        // Проверяем условия победы или ничьи
        if (TTTGameLogic.checkForDrawS(gameField) || TTTGameLogic.checkForWinS(gameField)) {

            // Если условие победы или ничьи выполнено, игра заканчивается
            winnerSymbol = "The player"; // Устанавливаем символ победителя
            endGame(gameField);

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
            // System.out.println("Стратегия - " + selectedLevel);
            System.out.println(" ");
            System.out.println("Computer move: row = " + computerMove[0] + ", col = " + computerMove[1]);
            System.out.println(" ");

            // По полученным координатам обновляем графический интерфейс от имени компьютера
            int computerRow = computerMove[0];
            int computerCol = computerMove[1];
            Button computerButton = getButtonByIndexes(computerRow, computerCol);
            computerButton.setText(String.valueOf(Constants.COMPUTER_SYMBOL));
            computerButton.setDisable(true);

            gameField[computerRow][computerCol] = Constants.COMPUTER_SYMBOL;

            System.out.println("Game field after Computers's move" + Arrays.deepToString(gameField));


            if (TTTGameLogic.checkForDrawS(gameField) || TTTGameLogic.checkForWinS(gameField)) {

                System.out.println(" ");

                System.out.println("checkForDrawS - " + TTTGameLogic.checkForDrawS(gameField));

                System.out.println("checkForWinS - " + TTTGameLogic.checkForWinS(gameField));

                winnerSymbol = "The computer"; // Устанавливаем символ победителя
                endGame(gameField);
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

