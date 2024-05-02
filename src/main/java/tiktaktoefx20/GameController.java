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
    private final MoveStrategy easyStrategy = new EasyStrategy();
    private final MoveStrategy hardStrategy = new HardStrategy();
    private final MoveStrategy aiStrategy = new AIStrategy();

    // Создаем объекты обработчиков хода с разными стратегиями
    private final Context easyMoveHandler = new Context(easyStrategy);
    private final Context hardMoveHandler = new Context(hardStrategy);
    private final Context aiMoveHandler = new Context(aiStrategy);
    private final char[][] gameField = new char[Constants.FIELD_SIZE][Constants.FIELD_SIZE]; // добавляем игровое поле
    private static int moveCounter = 0; // Переменная для хранения счетчика ходов
    private static int playerMovesCounter = 0; // Переменная для хранения счетчика ходов
    private static int computerMovesCounter = 0; // Переменная для хранения счетчика ходов
    private static long startTime;
    private List<GameMove> moves;
    private Game currentGame;
    private String selectedLevel;
    private GameResultHandler gameResultHandler;

//    @FXML
//    private RadioMenuItem subMenuItem;

    @FXML
    ComboBox<String> comb;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Text dynamicText;

    @FXML
    private Text staticText;

    @FXML
    private HBox hbox;

    @FXML
    void Select() {
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

    @FXML
    void initialize() {

        initializeHBox();
        initializeMenuBar();
        // initializeComboBox();
        initializeGameField();
        initializeComputerStrategicMoveHandler();
        gameResultHandler = new GameResultHandler(); // Передаем ссылку на текущий объект GameController
        gameResultHandler.setGameController(this); // Установка контроллера в gameResultHandler

        // Запускаем таймер
        startGameTimer();
    }

    void setStage() {
    }

    private void checkAndUpdateGameState() {
        // String selectedLevel = comb.getSelectionModel().getSelectedItem();
        //String selectedLevel = handleDifficultyChange(subMenuItem);

        // Получаем выбранный RadioMenuItem
        RadioMenuItem selectedMenuItem = (RadioMenuItem) difficultyNewGame.getSelectedToggle();

        // Получаем текст выбранного элемента
        String selectedLevel = selectedMenuItem.getText();

        System.out.println("selectedLevel for checkAndUpdateGameState: " + selectedLevel);


        if (checkForWin(gameField) || checkForDraw(gameField)) {
            String winnerSymbol = checkForWin(gameField) ? "The player" : "It's a draw";

            List<int[]> winningCells = GameEngine.winningCells;

            endGame(winningCells,
                    gameField,
                    winnerSymbol,
                    convertMovesToGameMovesList(),
                    moveCounter,
                    playerMovesCounter,
                    computerMovesCounter,
                    stopGameTimer(),
                    selectedLevel,
                    anchorPane);
        } else {

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
                endGame(winningCells,
                        gameField,
                        winnerSymbol,
                        convertMovesToGameMovesList(),
                        moveCounter,
                        playerMovesCounter,
                        computerMovesCounter,
                        stopGameTimer(),
                        selectedLevel,
                        anchorPane);
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

    private void initializeHBox() {
        // Вычисляем сумму wrappingWidth для обеих строк
        double totalWrappingWidth = staticText.getWrappingWidth() + dynamicText.getWrappingWidth();

        // Устанавливаем сумму wrappingWidth в качестве prefWidth для HBox
        hbox.setPrefWidth(totalWrappingWidth);

        // Центрируем HBox относительно AnchorPane
        hbox.setLayoutX((anchorPane.getPrefWidth() - hbox.getPrefWidth()) / 2);
    }


    private ToggleGroup difficultyNewGame;

    private void initializeMenuBar() {
        //ToggleGroup difficultyNewGame = new ToggleGroup();

        difficultyNewGame = new ToggleGroup();

        // Перебираем все меню в MenuBar
        for (Menu menu : menuBar.getMenus()) {
            // Находим меню "Game"
            if ("Game".equals(menu.getText())) {
                // Перебираем все пункты меню в меню "Game"
                for (MenuItem menuItem : menu.getItems()) {
                    // Находим меню "New Game"
                    if (menuItem instanceof Menu && "Difficulty Level".equals(menuItem.getText())) {
                        // Перебираем все пункты меню в меню "New Game"
                        for (MenuItem subMenuItem : ((Menu) menuItem).getItems()) {
                            // Находим RadioMenuItem EASY
                            if (subMenuItem instanceof RadioMenuItem && "EASY".equals(subMenuItem.getText())) {
                                ((RadioMenuItem) subMenuItem).setSelected(true); // Устанавливаем EASY по умолчанию
                                handleDifficultyChange((RadioMenuItem) subMenuItem);
                            }
                            // Устанавливаем обработчик событий для RadioMenuItem
                            if (subMenuItem instanceof RadioMenuItem) {
                                ((RadioMenuItem) subMenuItem).setToggleGroup(difficultyNewGame);
                                //subMenuItem.setOnAction(event -> handleDifficultyChange((RadioMenuItem) subMenuItem));

                                subMenuItem.setOnAction(event -> {
                                    // Получаем текст из subMenuItem
                                    String text = ((RadioMenuItem) subMenuItem).getText();

                                    // Проверяем значение текста и вызываем соответствующий метод
                                    switch (text) {
                                        case "EASY":
                                            handleDifficultyChange((RadioMenuItem) subMenuItem);
                                            handleNewGameEasy();
                                            break;
                                        case "HARD":
                                            handleDifficultyChange((RadioMenuItem) subMenuItem);
                                            handleNewGameHard();
                                            break;
                                        case "AI":
                                            handleDifficultyChange((RadioMenuItem) subMenuItem);
                                            handleNewGameAI();
                                            break;
                                        default:
                                            // Действие по умолчанию
                                            break;

                                    }
                                });
                            }
                        }
                    }
                }
            }
        }
    }

    private void initializeComboBox() {
        ObservableList<String> list = FXCollections.observableArrayList("EASY", "HARD", "AI");
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

        resetMoveCounters();
        startNewGame(gameField, anchorPane);
    }

    public static void resetMoveCounters() {
        moveCounter = 0; // Переменная для хранения счетчика ходов
        playerMovesCounter = 0; // Переменная для хранения счетчика ходов
        computerMovesCounter = 0; // Переменная для хранения счетчика ходов
    }

    public GameResultHandler getGameResultHandler() {
        return gameResultHandler;
    }

    private String handleDifficultyChange(RadioMenuItem selected) {
        // Обновляем динамический текст
        dynamicText.setText(selected.getText());

        // Устанавливаем wrappingWidth для dynamicText
        Text level = (Text) hbox.getChildren().get(1);
        level.setWrappingWidth(computeTextWidth(dynamicText.getText()));

        // Вычисляем сумму wrappingWidth для обеих строк
        double totalWrappingWidth = staticText.getWrappingWidth() + dynamicText.getWrappingWidth();

        // Устанавливаем сумму wrappingWidth в качестве prefWidth для HBox
        hbox.setPrefWidth(totalWrappingWidth);

        // Центрируем HBox относительно AnchorPane
        hbox.setLayoutX((anchorPane.getPrefWidth() - hbox.getPrefWidth()) / 2);
        return null;
    }

    // Метод для вычисления ширины текста в пикселях
    private double computeTextWidth(String text) {
        Text helper = new Text();
        helper.setFont(dynamicText.getFont());
        helper.setText(text);
        return helper.getLayoutBounds().getWidth();
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




    // Обработчики событий для меню
    public void handleNewGameEasy() {
        // Ваш код для новой игры
        System.out.println("You selected New Game EASY menu item");
    }

    public void handleNewGameHard() {
        // Ваш код для новой игры
        System.out.println("You selected New Game HARD menu item");
    }

    public void handleNewGameAI() {
        // Ваш код для новой игры
        System.out.println("You selected New Game AI menu item");
    }

    public void handleOptions() {
        // Ваш код для настроек
        System.out.println("You selected Options menu item");
    }

    public void handleExit() {
        // Ваш код для выхода
        System.out.println("You selected Exit menu item");
    }

    // Обработчики событий для справки
    public void handleHowTo() {
        // Ваш код для инструкций
        System.out.println("You selected How To menu item");
    }

    public void handleStatistic() {
        // Ваш код для статистики
        System.out.println("You selected Statistic menu item");
    }

    public void handleAbout() {
        // Ваш код для статистики
        System.out.println("You selected About menu item");
    }
}


