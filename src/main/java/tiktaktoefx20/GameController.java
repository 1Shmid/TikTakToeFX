package tiktaktoefx20;

import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import tiktaktoefx20.database.*;
import tiktaktoefx20.strategies.*;

import java.util.*;


public class GameController extends GameEngine {

    // Создаем объекты стратегий
    MoveStrategy easyStrategy = new EasyStrategy();
    MoveStrategy hardStrategy = new HardStrategy();

    // Создаем объекты обработчиков хода с разными стратегиями
    Context easyMoveHandler = new Context(easyStrategy);
    Context hardMoveHandler = new Context(hardStrategy);

    private final char[][] gameField = new char[Constants.FIELD_SIZE][Constants.FIELD_SIZE]; // добавляем игровое поле
    private int moveCounter = 0; // Переменная для хранения счетчика ходов
    private int playerMovesCounter = 0; // Переменная для хранения счетчика ходов
    private int computerMovesCounter = 0; // Переменная для хранения счетчика ходов

    private List<GameMove> moves;
    private Game currentGame;
    private long startTime;



    @FXML
    private ComboBox<String> comb;

    @FXML
    void Select() {
    }

    public void setStage() {
    }


    @FXML
    void btnClick(ActionEvent event) {

        startGameTimer();

        Button clickedButton = (Button) event.getSource(); // Получаем кнопку, на которую было нажато
        clickedButton.setText(String.valueOf(Constants.PLAYER_SYMBOL));
        clickedButton.setDisable(true);

        // Получаем индексы кнопки
        int row = GridPane.getRowIndex(clickedButton) == null ? 0 : GridPane.getRowIndex(clickedButton);
        int col = GridPane.getColumnIndex(clickedButton) == null ? 0 : GridPane.getColumnIndex(clickedButton);

        // Обновляем поле игры
        gameField[row][col] = Constants.PLAYER_SYMBOL;

        // Увеличиваем счетчик ходов
        moveCounter++;
        playerMovesCounter++;

        // Записываем ход игрока
        GameMove.addMove(moveCounter, "player", row, col);

        checkAndUpdateGameState();
    }

    private void checkAndUpdateGameState() {
        if (checkForWin(gameField) || checkForDraw(gameField)) {
            String winnerSymbol = checkForWin(gameField) ? "The player" : "It's a draw";
            endGame(gameField, winnerSymbol, convertMovesToGameMovesList(), moveCounter, 0, 0, 0);
        } else {
            // Выбираем уровень сложности (стратегию)
            String selectedLevel = comb.getSelectionModel().getSelectedItem();

            // Делаем ход компьютера с выбранной стратегией
            int[] computerMove = switch (selectedLevel) {
                case "EASY" -> easyMoveHandler.makeMove(gameField, selectedLevel);
                case "HARD" -> hardMoveHandler.makeMove(gameField, selectedLevel);
                default -> easyMoveHandler.makeMove(gameField, selectedLevel); // По умолчанию используем случайную стратегию
            };

            updateGameField(computerMove[0], computerMove[1], Constants.COMPUTER_SYMBOL);

            // Увеличиваем счетчик ходов
            moveCounter++;
            computerMovesCounter++;

            // Записываем ход компьютера
            GameMove.addMove(moveCounter, "computer", computerMove[0], computerMove[1]); // Записываем ход компьютера

            if (checkForWin(gameField) || checkForDraw(gameField)) {
                String winnerSymbol = checkForWin(gameField) ? "The computer" : "It's a draw";
                endGame(gameField, winnerSymbol, convertMovesToGameMovesList(), moveCounter, playerMovesCounter, computerMovesCounter, stopGameTimer());
            }
        }
    }

    // Метод для старта отсчета времени игры
    public void startGameTimer() {
        startTime = System.currentTimeMillis();
    }

    // Метод для остановки отсчета времени игры и получения продолжительности игры в секундах
    public int stopGameTimer() {
        long endTime = System.currentTimeMillis();
        return (int) ((endTime - startTime) / 1000);
    }

    private List<GameMove> convertMovesToGameMovesList() {
        List<GameMove> gameMovesList = new ArrayList<>();
        for (Object[] move : GameMove.getMoves()) {
            int moveNumber = (int) move[0];
            String player = (String) move[1];
            int row = (int) move[2];
            int col = (int) move[3];
            gameMovesList.add(new GameMove(moveNumber, player, row, col));
        }
        return gameMovesList;
    }


    private void updateGameField(int row, int col, char symbol) {
        Button button = getButtonByIndexes(row, col);
        button.setText(String.valueOf(symbol));
        button.setDisable(true);
        gameField[row][col] = symbol;
    }

    @FXML
    void initialize() {
        initializeComboBox();
        initializeGameField();
        initializeComputerStrategicMoveHandler();
    }

    private void initializeComboBox() {
        ObservableList<String> list = FXCollections.observableArrayList("EASY", "HARD", "IMPOSSIBLE");
        comb.setItems(list);
        comb.setValue("EASY");
        comb.setOnAction(this::handleComboBoxAction);
    }

    private void initializeGameField() {
        for (int i = 0; i < Constants.FIELD_SIZE; i++) {
            for (int j = 0; j < Constants.FIELD_SIZE; j++) {
                gameField[i][j] = Constants.EMPTY_SYMBOL;
            }
        }
    }

    private void initializeComputerStrategicMoveHandler() {
        Context computerStrategicMoveHandler = new Context(new EasyStrategy());
    }

    private void handleComboBoxAction(ActionEvent event) {
        startNewGame(gameField);
    }
}


