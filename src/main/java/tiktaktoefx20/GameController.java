package tiktaktoefx20;

import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import tiktaktoefx20.database.*;
import tiktaktoefx20.strategies.*;

import java.util.*;


public class GameController extends GameEngine {

    // Создаем объекты стратегий
    MoveStrategy easyStrategy = new EasyStrategy();
    MoveStrategy hardStrategy = new HardStrategy();
    MoveStrategy aiStrategy = new AIStrategy();

    // Создаем объекты обработчиков хода с разными стратегиями
    Context easyMoveHandler = new Context(easyStrategy);
    Context hardMoveHandler = new Context(hardStrategy);
    Context aiMoveHandler = new Context(aiStrategy);


    private final char[][] gameField = new char[Constants.FIELD_SIZE][Constants.FIELD_SIZE]; // добавляем игровое поле
    private static int moveCounter = 0; // Переменная для хранения счетчика ходов
    private static int playerMovesCounter = 0; // Переменная для хранения счетчика ходов
    private static int computerMovesCounter = 0; // Переменная для хранения счетчика ходов

    private List<GameMove> moves;
    private Game currentGame;
    private static long startTime;

    private String selectedLevel;



    @FXML
    ComboBox<String> comb;

    @FXML
    void Select() {
    }

    public void setStage() {
    }


    @FXML
    void btnClick(ActionEvent event) {

        Button clickedButton = (Button) event.getSource(); // Получаем кнопку, на которую было нажато
        clickedButton.setText(String.valueOf(Constants.PLAYER_SYMBOL));
        clickedButton.setStyle("-fx-text-fill: #545454; -fx-opacity: 1.0; -fx-background-color: transparent;"); // Устанавливаем черный цвет текста для кнопки


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
        String selectedLevel = comb.getSelectionModel().getSelectedItem();

        if (checkForWin(gameField) || checkForDraw(gameField)) {
            String winnerSymbol = checkForWin(gameField) ? "The player" : "It's a draw";
            endGame(gameField,
                    winnerSymbol,
                    convertMovesToGameMovesList(),
                    moveCounter,
                    playerMovesCounter,
                    computerMovesCounter,
                    stopGameTimer(),
                    selectedLevel);
        } else {
            // Выбираем уровень сложности (стратегию)
            //String selectedLevel = comb.getSelectionModel().getSelectedItem();

            // Делаем ход компьютера с выбранной стратегией
            int[] computerMove = switch (selectedLevel) {
                case "EASY" -> easyMoveHandler.makeMove(gameField, selectedLevel);
                case "HARD" -> hardMoveHandler.makeMove(gameField, selectedLevel);
                case "AI" -> aiMoveHandler.makeMove(gameField, selectedLevel);
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
                endGame(gameField,
                        winnerSymbol,
                        convertMovesToGameMovesList(),
                        moveCounter,
                        playerMovesCounter,
                        computerMovesCounter,
                        stopGameTimer(),
                        selectedLevel);
            }
        }
    }

    private void updateGameField(int row, int col, char symbol) {
        Button button = getButtonByIndexes(row, col);
        button.setText(String.valueOf(symbol));
        button.setDisable(true);
        gameField[row][col] = symbol;
        button.setStyle("-fx-text-fill: white; -fx-opacity: 1.0; -fx-background-color: transparent ;"); // Устанавливаем черный цвет текста для кнопки
    }

    // Метод для старта отсчета времени игры
    public static void startGameTimer() {
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




    private GameResultHandler gameResultHandler;

    @FXML
    void initialize() {
        initializeComboBox();
        initializeGameField();
        initializeComputerStrategicMoveHandler();
        gameResultHandler = new GameResultHandler(); // Передаем ссылку на текущий объект GameController
        gameResultHandler.setGameController(this); // Установка контроллера в gameResultHandler

        // Запускаем таймер
        startGameTimer();

    }

    private void initializeComboBox() {
        ObservableList<String> list = FXCollections.observableArrayList("EASY", "HARD", "AI");
        comb.setItems(list);
        comb.setValue("EASY");
        comb.setOnAction(this::handleComboBoxAction);
        // Установка шрифта и цвета текста
        // Установка шрифта и цвета текста
        Font font = Font.font("Arial", FontWeight.NORMAL, 12); // Указать нужный шрифт, размер и стиль
        comb.setStyle("-fx-font-size: 12px;"); // Установка размера шрифта
        comb.setStyle("-fx-text-fill: red;"); // Установка цвета текста
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

        resetMoveCounters();
        startNewGame(gameField);
    }

    public static void resetMoveCounters() {
        moveCounter = 0; // Переменная для хранения счетчика ходов
        playerMovesCounter = 0; // Переменная для хранения счетчика ходов
        computerMovesCounter = 0; // Переменная для хранения счетчика ходов
    }

    public GameResultHandler getGameResultHandler() {
        return gameResultHandler;
    }
}


