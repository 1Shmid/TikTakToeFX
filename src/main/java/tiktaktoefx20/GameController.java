package tiktaktoefx20;

import javafx.animation.*;
import javafx.application.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.util.*;
import tiktaktoefx20.database.*;
import tiktaktoefx20.menu.*;
import tiktaktoefx20.strategies.*;

import javax.sound.midi.*;
import java.beans.*;
import java.util.*;

import static tiktaktoefx20.GameEngine.*;


public class GameController implements PropertyChangeListener {

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
    private ToggleGroup difficultyNewGame;

    GameEngine gameEngine = new GameEngine();

    public GridPane getGridPane() {
        return gridPane;
    }

    @FXML
    protected GridPane gridPane;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    protected Text dynamicText;

    public Text getStaticText() {
        return staticText;
    }

    @FXML
    protected Text staticText;

    @FXML
    protected HBox hbox;

    @FXML
    protected MenuBar menuBar;

    public Line getBottomHLine() {
        return bottomHLine;
    }

    @FXML
    protected Line bottomHLine;

    @FXML
    protected Line rightVLine;

    @FXML
    protected Line upHLine;

    @FXML
    protected Line leftVLine;



    @FXML
    void btnClick(ActionEvent event) {

        final ClickResult clickResult = getClickResult(event);

        magicOn(clickResult);
    }

    Line line;
    @FXML
    void initialize() {

        Text staticText = getStaticText();

        System.out.println(staticText);


        initializeGameField();

        initializeLevelInfoLine();

        initializeMenuBar();

        startGameTimer();

        Platform.runLater(this::initializeLines);
    }

    private void magicOn(ClickResult clickResult) {
        // Обновляем поле игры
        gameField[clickResult.row()][clickResult.col()] = Constants.PLAYER_SYMBOL;

        // Увеличиваем счетчик ходов
        moveCounter++;
        playerMovesCounter++;

        // Записываем ход игрока
        GameMove.addMove(moveCounter, "player", clickResult.row(), clickResult.col());

        checkAndUpdateGameState();
    }

    private static ClickResult getClickResult(ActionEvent event) {
        Button clickedButton = (Button) event.getSource(); // Получаем кнопку, на которую было нажато
        clickedButton.setText(String.valueOf(Constants.PLAYER_SYMBOL));
        clickedButton.setStyle("-fx-text-fill: #545454; -fx-opacity: 1.0; -fx-background-color: transparent;"); // Устанавливаем черный цвет текста для кнопки

        clickedButton.setDisable(true);

        // Получаем индексы кнопки
        int row = GridPane.getRowIndex(clickedButton) == null ? 0 : GridPane.getRowIndex(clickedButton);
        int col = GridPane.getColumnIndex(clickedButton) == null ? 0 : GridPane.getColumnIndex(clickedButton);
        return new ClickResult(row, col);
    }

    protected void initializeLines() {

        animateLine(bottomHLine);
        animateLine(rightVLine);
        animateLine(upHLine);
        animateLine(leftVLine);

    }

    protected void animateLine(Line line) {

        if (line == null) {
            System.out.println("Line is null!");
            return;
        }

        // Получаем текущие координаты X начальной и конечной точек линии
        double startX = line.getStartX();
        double endX = line.getEndX();

        double startY = line.getStartY();
        double endY = line.getEndY();

        Duration speed = Duration.millis(500);

        if (startY == endY){
            // Задаем новые координаты для начальной и конечной точек
            animateHLine(line, startX, endX, speed);
        }

        if (startX == endX){
            // Задаем новые координаты для начальной и конечной точек
            animateVLine(line, startY, endY, speed);
        }
    }

    protected void animateVLine(Line line, double startY, double endY, Duration speed) {
        double newStartY = startY + (1.0 / 2) * (endY - startY); // Двигаем начальную точку влево от центра
        double newEndY = endY - (1.0 / 2) * (endY - startY); // Двигаем конечную точку вправо от центра

        line.setStartY(newStartY);
        line.setEndY(newEndY);

        // Создаем анимацию для изменения координаты X начальной точки
        Timeline startAnimation = new Timeline();
        startAnimation.getKeyFrames().add(
                new KeyFrame(speed, new KeyValue(line.startYProperty(), startY))
        );

        // Создаем анимацию для изменения координаты X конечной точки
        Timeline endAnimation = new Timeline();
        endAnimation.getKeyFrames().add(
                new KeyFrame(speed, new KeyValue(line.endYProperty(), endY))
        );

        // Запускаем анимации
        startAnimation.play();
        endAnimation.play();
    }

    protected void animateHLine(Line line, double startX, double endX, Duration speed) {

        double newStartX = startX + (1.0 / 2) * (endX - startX); // Двигаем начальную точку влево от центра
        double newEndX = endX - (1.0 / 2) * (endX - startX); // Двигаем конечную точку вправо от центра

        line.setStartX(newStartX);
        line.setEndX(newEndX);

        // Создаем анимацию для изменения координаты X начальной точки
        Timeline startAnimation = new Timeline();
        startAnimation.getKeyFrames().add(
                new KeyFrame(speed, new KeyValue(line.startXProperty(), startX))
        );

        // Создаем анимацию для изменения координаты X конечной точки
        Timeline endAnimation = new Timeline();
        endAnimation.getKeyFrames().add(
                new KeyFrame(speed, new KeyValue(line.endXProperty(), endX))
        );

        // Запускаем анимации
        startAnimation.play();
        endAnimation.play();
    }

// =========================================================================================================

    private static GameController instance;

    // Приватный конструктор, чтобы предотвратить создание экземпляров через оператор new
//    private GameController() {
//        // Ваша логика инициализации здесь
//    }

    // Метод для получения экземпляра класса GameController
    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    @Override

    public void propertyChange(PropertyChangeEvent evt) {
        if ("isOpen".equals(evt.getPropertyName())) {
            boolean isOpen = (boolean) evt.getNewValue();
            if (isOpen) {
                System.out.println("GameController: Window is open");
            } else {
                System.out.println("GameController: Window is closed");

                Line line = getBottomHLine();

                System.out.println(line);

                //initialize();
            }
        }
    }


    private void setNews(String newValue) {

        //GameResultWindow observable = new GameResultWindow();

        System.out.println("Window open: " + newValue);

    }










    private record ClickResult(int row, int col) {
    }

    private void checkAndUpdateGameState() {

        // gameController = GameController.getInstance();

        // Получаем выбранный RadioMenuItem уровня сложности
        RadioMenuItem selectedMenuItem = (RadioMenuItem) difficultyNewGame.getSelectedToggle();

        // Получаем текст выбранного элемента
        String selectedLevel = selectedMenuItem.getText();

        if (checkForWin(gameField) || checkForDraw(gameField)) {
            String winnerSymbol = checkForWin(gameField) ? "The player" : "It's a draw";

            List<int[]> winningCells = GameEngine.winningCells;

            gameEngine.endGame(winningCells,
                    gameField,
                    winnerSymbol,
                    convertMovesToGameMovesList(),
                    moveCounter,
                    playerMovesCounter,
                    computerMovesCounter,
                    stopGameTimer(),
                    selectedLevel,
                    anchorPane,
                    gridPane, bottomHLine, rightVLine, upHLine,leftVLine
            );

            //updateDialogState(isDialogOpen);

        } else {

            // Делаем ход компьютера с выбранной стратегией
            int[] computerMove = switch (selectedLevel) {
                case "EASY" -> easyMoveHandler.makeMove(gameField, selectedLevel);
                case "HARD" -> hardMoveHandler.makeMove(gameField, selectedLevel);
                case "AI" -> aiMoveHandler.makeMove(gameField, selectedLevel);
                default -> easyMoveHandler.makeMove(gameField, selectedLevel); // По умолчанию используем случайную стратегию
            };

            updateGameField(computerMove[0], computerMove[1]);

            // Увеличиваем счетчик ходов
            moveCounter++;
            computerMovesCounter++;

            // Записываем ход компьютера
            GameMove.addMove(moveCounter, "computer", computerMove[0], computerMove[1]); // Записываем ход компьютера

            if (checkForWin(gameField) || checkForDraw(gameField)) {
                String winnerSymbol = checkForWin(gameField) ? "The computer" : "It's a draw";
                gameEngine.endGame(winningCells,
                        gameField,
                        winnerSymbol,
                        convertMovesToGameMovesList(),
                        moveCounter,
                        playerMovesCounter,
                        computerMovesCounter,
                        stopGameTimer(),
                        selectedLevel,
                        anchorPane,
                        gridPane, bottomHLine, rightVLine, upHLine,leftVLine
                );

                //updateDialogState(isDialogOpen);
            }
        }
    }

    private void updateGameField(int row, int col) {
        Button button = getButtonByIndexes(row, col);
        button.setText(String.valueOf(Constants.COMPUTER_SYMBOL));
        button.setDisable(true);
        gameField[row][col] = Constants.COMPUTER_SYMBOL;
        button.setStyle("-fx-text-fill: white; -fx-opacity: 1.0; -fx-background-color: transparent ;");
    }

    public Button getButtonByIndexes(int row, int col) {
        ObservableList<Node> children = gridPane.getChildren(); // Получаем список детей GridPane
        for (Node node : children) {
            if (node instanceof Button button) { // Проверяем, является ли дочерний элемент кнопкой
                // Получаем индексы кнопки
                int rowIndex = GridPane.getRowIndex(button) == null ? 0 : GridPane.getRowIndex(button);
                int colIndex = GridPane.getColumnIndex(button) == null ? 0 : GridPane.getColumnIndex(button);
                // Если индексы совпадают с переданными, возвращаем кнопку
                if (rowIndex == row && colIndex == col) {
                    return button;
                }
            }
        }
        return null; // Возвращаем null, если кнопка не найдена
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

    private void initializeLevelInfoLine() {
        // Вычисляем сумму wrappingWidth для обеих строк
        double totalWrappingWidth = staticText.getWrappingWidth() + dynamicText.getWrappingWidth();

        // Устанавливаем сумму wrappingWidth в качестве prefWidth для HBox
        hbox.setPrefWidth(totalWrappingWidth);

        // Центрируем HBox относительно AnchorPane
        hbox.setLayoutX((anchorPane.getPrefWidth() - hbox.getPrefWidth()) / 2);
    }

    private void initializeMenuBar() {
        difficultyNewGame = new ToggleGroup();

        // Перебираем все меню в MenuBar
        for (Menu menu : menuBar.getMenus()) {
            // Находим меню "Game"
            if ("Game".equals(menu.getText())) {
                // Перебираем все пункты меню в меню "Game"
                for (MenuItem menuItem : menu.getItems()) {
                    // Находим меню "Difficulty Level"
                    if (menuItem instanceof Menu && "Difficulty Level".equals(menuItem.getText())) {
                        initializeDifficultyMenu((Menu) menuItem, difficultyNewGame);
                        break; // Нет смысла продолжать искать другие меню "Difficulty Level"
                    }
                }
            }
        }
    }

    private void initializeDifficultyMenu(Menu difficultyMenu, ToggleGroup toggleGroup) {
        // Перебираем все пункты меню в меню "Difficulty Level"
        for (MenuItem subMenuItem : difficultyMenu.getItems()) {
            // Находим RadioMenuItem EASY
            if (subMenuItem instanceof RadioMenuItem && "EASY".equals(subMenuItem.getText())) {
                ((RadioMenuItem) subMenuItem).setSelected(true); // Устанавливаем EASY по умолчанию
                updateLevelInfoLine((RadioMenuItem) subMenuItem);
            }
            // Устанавливаем обработчик событий для RadioMenuItem
            if (subMenuItem instanceof RadioMenuItem) {
                ((RadioMenuItem) subMenuItem).setToggleGroup(toggleGroup);
                subMenuItem.setOnAction(event -> updateLevelInfoLine((RadioMenuItem) subMenuItem));
            }
        }
    }

    private void initializeGameField() {
        for (int i = 0; i < Constants.FIELD_SIZE; i++) {
            for (int j = 0; j < Constants.FIELD_SIZE; j++) {
                gameField[i][j] = Constants.EMPTY_SYMBOL;
            }
        }
    }

    private void updateLevelInfoLine(RadioMenuItem selected) {
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
    }

    // Метод для вычисления ширины текста в пикселях
    private double computeTextWidth(String text) {
        Text helper = new Text();
        helper.setFont(dynamicText.getFont());
        helper.setText(text);
        return helper.getLayoutBounds().getWidth();
    }

    static void resetMoveCounters() {
        moveCounter = 0; // Переменная для хранения счетчика ходов
        playerMovesCounter = 0; // Переменная для хранения счетчика ходов
        computerMovesCounter = 0; // Переменная для хранения счетчика ходов
    }

    void setStage() {
    }

//    public static GameController instance;
//    GameController gameController;
//    public static synchronized GameController getInstance() {
//        if (instance == null) {
//            instance = new GameController();
//        }
//        return instance;
//    }

    // Метод для старта отсчета времени игры
    public static void startGameTimer() {
        startTime = System.currentTimeMillis();
    }

    // Метод для остановки отсчета времени игры и получения продолжительности игры в секундах
    public int stopGameTimer() {
        long endTime = System.currentTimeMillis();
        return (int) ((endTime - startTime) / 1000);
    }

    public static int getCurrentGameTime() {
        // Получаем текущее время в миллисекундах
        long currentTime = System.currentTimeMillis();
        // Вычисляем разницу между текущим временем и началом игры в секундах
        int currentGameTimeInSeconds = (int) ((currentTime - startTime) / 1000);
        return currentGameTimeInSeconds;
    }

    // Обработчики событий для меню

    public void handleOptionsMenuItem() {
        // Ваш код для настроек
        System.out.println("You selected Options menu item");
    }

    public void handleExitMenuItem() {
        Platform.exit();
    }

    // Обработчики событий для справки
    public void handleHowToMenuItem() {
        HowToWindow.displayHowToDialog();
    }

    StatDialogLoader statDialogLoader = new StatDialogLoader();

    public void handleStatisticMenuItem() {
        statDialogLoader.showDialog(anchorPane);
    }

    public void handleAboutMenuItem() {
        AboutWindow.displayAboutDialog();
    }
}


