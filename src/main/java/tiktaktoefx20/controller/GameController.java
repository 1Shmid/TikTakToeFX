package tiktaktoefx20.controller;

import javafx.scene.Group;
import tiktaktoefx20.model.GameParams;
import tiktaktoefx20.model.GameEngine;
import tiktaktoefx20.view.StatWindow;
import tiktaktoefx20.constants.Constants;
import javafx.animation.*;
import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.util.*;
import tiktaktoefx20.view.AboutWindow;
import tiktaktoefx20.view.HowToWindow;

import static tiktaktoefx20.constants.Constants.OColor;
import static tiktaktoefx20.constants.Constants.XColor;


/**
 * Класс, отвечающий за управление игровым интерфейсом и взаимодействие с пользователем. Controls
 * the game interface and interacts with the user.
 */


public class GameController {

    private ToggleGroup difficultyLevels;
    /*
    ToggleGroup - Группа переключателей не является видимым элементом управления пользовательского интерфейса,
    а представляет собой способ изменить поведение набора переключателей . Переключатели, принадлежащие к одной группе,
    ограничены таким образом, что один из них можно включить в любое время — нажатие одного из них для его
    включения автоматически отключает остальные.
     */
    // =========== глобальные переменные игры =============
    private final char[][] gameField = new char[Constants.FIELD_SIZE][Constants.FIELD_SIZE]; // добавляем игровое поле
    private int moveCounter = 0; // Переменная для хранения счетчика ходов
    private int playerMovesCounter = 0; // Переменная для хранения счетчика ходов
    private int computerMovesCounter = 0; // Переменная для хранения счетчика ходов
    private long startTime;

    @FXML
    protected GridPane gridPane;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    protected Text dynamicText;

    @FXML
    protected Text staticText;

    @FXML
    protected HBox hbox;

    @FXML
    protected MenuBar menuBar;

    @FXML
    protected Line bottomHLine;

    @FXML
    protected Line rightVLine;

    @FXML
    protected Line upHLine;

    @FXML
    protected Line leftVLine;

    @FXML
    protected void clearGridPane(GridPane gridPane) {
        gridPane.getChildren().clear();
    }

    @FXML
    private void initialize() {

        initializeGameField();

        initializeLevelInfoLine();

        initializeMenuBar();

        startGameTimer();

        Platform.runLater(this::initializeLines);
    }

    @FXML
    private void mouseClick(MouseEvent event) {

        final ClickResult clickResult = getMouseClickResult(event);
        magicOn(clickResult);

    }

    private void magicOn(ClickResult clickResult) {

        RadioMenuItem selectedMenuItem = (RadioMenuItem) difficultyLevels.getSelectedToggle();
        String difficultyLevel = selectedMenuItem.getText();

        GameParams params = new GameParams("",
                null,
                difficultyLevel,
                null,
                moveCounter,
                playerMovesCounter,
                computerMovesCounter,
                stopGameTimer(),
                anchorPane,
                gridPane,
                gameField,
                bottomHLine,
                rightVLine,
                upHLine,
                leftVLine);

        GameEngine gameEngine = new GameEngine();
        gameEngine.magicOn(clickResult, params);
    }

    private int getColumnIndex(double x) {

        int numColumns = gridPane.getColumnConstraints().size();
        double totalWidth = gridPane.getWidth();
        double columnWidth = totalWidth / numColumns;
        return (int) (x / columnWidth);
    }

    private int getRowIndex(double y) {

        int numRows = gridPane.getRowConstraints().size();
        double totalHeight = gridPane.getHeight();
        double rowHeight = totalHeight / numRows;
        return (int) (y / rowHeight);
    }

    private double getCellFontSize(GameParams params) {

        GridPane gridPane = params.getGridPane();
        int numColumns = gridPane.getColumnConstraints().size();
        double totalWidth = gridPane.getWidth();

        double columnWidth = totalWidth / numColumns;
        return columnWidth * 0.8;
    }

    public record ClickResult(int row, int col) {

    }

    private ClickResult getMouseClickResult(MouseEvent event) {

        int clickedRow = getRowIndex(event.getY());
        int clickedColumn = getColumnIndex(event.getX());
        return new ClickResult(clickedRow, clickedColumn);
    }

    // Универсальный метод для вывода символа на поле
    public void printSymbol(char symbol, Object move, GameParams params) {
        int row, col;

        // Определяем тип объекта и извлекаем координаты
        if (move instanceof int[] coordinates) {
            row = coordinates[0];
            col = coordinates[1];
        } else if (move instanceof ClickResult clickResult) {
            row = clickResult.row();
            col = clickResult.col();
        } else {
            throw new IllegalArgumentException("Unsupported move type");
        }

        GridPane gridPane = params.getGridPane();

        // Устанавливаем символ на поле
        final Text text = setSymbol(symbol, params);

        // Устанавливаем позицию текста в GridPane
        GridPane.setColumnIndex(text, col);
        GridPane.setRowIndex(text, row);
        GridPane.setHalignment(text, javafx.geometry.HPos.CENTER);

        // Добавляем текстовый элемент в GridPane
        gridPane.getChildren().add(text);

        // Пауза для анимации
        final PauseTransition pause = getPauseTransition(text);
        pause.play();
    }

    private static PauseTransition getPauseTransition(Text text) {
        PauseTransition pause = new PauseTransition(Duration.millis(1));

        // Устанавливаем действие, которое будет выполнено после паузы
        pause.setOnFinished(e -> {
            // Добавляем анимацию появления после паузы
            FadeTransition ft = new FadeTransition(Duration.millis(400), text);
            ft.setFromValue(0); // Начальное значение альфа-канала (прозрачность)
            ft.setToValue(1); // Конечное значение альфа-канала (полная видимость)
            ft.play();
        });
        return pause;
    }

    private Text setSymbol(char symbol, GameParams params) {

        // Создаем текстовый элемент для отображения символа
        Text text = new Text(String.valueOf(symbol));
        text.setFont(Font.font("Gill Sans MT", getCellFontSize(params)));

        // Выбираем цвет в зависимости от символа
        Paint color =
                (symbol == Constants.PLAYER_SYMBOL) ? Paint.valueOf(XColor) : Paint.valueOf(OColor);
        text.setFill(color);

        // Устанавливаем альфа-канал на 0 (текст полностью прозрачен)
        text.setOpacity(0);

        return text;
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

        if (startY == endY) {
            // Задаем новые координаты для начальной и конечной точек
            animateHLine(line, startX, endX, speed);
        }

        if (startX == endX) {
            // Задаем новые координаты для начальной и конечной точек
            animateVLine(line, startY, endY, speed);
        }
    }

    protected void animateVLine(Line line, double startY, double endY, Duration speed) {
        double newStartY =
                startY + (1.0 / 2) * (endY - startY); // Двигаем начальную точку влево от центра
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

        double newStartX =
                startX + (1.0 / 2) * (endX - startX); // Двигаем начальную точку влево от центра
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


    private void initializeLevelInfoLine() {
        // Вычисляем сумму wrappingWidth для обеих строк
        double totalWrappingWidth = staticText.getWrappingWidth() + dynamicText.getWrappingWidth();

        // Устанавливаем сумму wrappingWidth в качестве prefWidth для HBox
        hbox.setPrefWidth(totalWrappingWidth);

        // Центрируем HBox относительно AnchorPane
        hbox.setLayoutX((anchorPane.getPrefWidth() - hbox.getPrefWidth()) / 2);
    }

    private void initializeMenuBar() {
        difficultyLevels = new ToggleGroup();
        findAndInitializeDifficultyMenu(difficultyLevels);

    }

    private void findAndInitializeDifficultyMenu(ToggleGroup difficultyNewGame) {

        for (Menu menu : menuBar.getMenus()) {
            if ("Game".equals(menu.getText())) {
                findAndInitializeSubMenu(menu, difficultyNewGame);

                break;
            }
        }
    }

    private void findAndInitializeSubMenu(Menu menu, ToggleGroup difficultyNewGame) {

        for (MenuItem menuItem : menu.getItems()) {
            if (menuItem instanceof Menu && "Difficulty Level".equals(menuItem.getText())) {
                initializeDifficultyMenu((Menu) menuItem, difficultyNewGame);
                break;
            }
        }
    }

    private void initializeDifficultyMenu(Menu difficultyMenu, ToggleGroup difficultyNewGame) {

        for (MenuItem subMenuItem : difficultyMenu.getItems()) {
            if (subMenuItem instanceof RadioMenuItem) {
                initializeRadioMenuItem((RadioMenuItem) subMenuItem, difficultyNewGame);
            }
        }
    }

    private void initializeRadioMenuItem(RadioMenuItem radioMenuItem, ToggleGroup toggleGroup) {

        if ("AI".equals(radioMenuItem.getText())) {
            radioMenuItem.setSelected(true);
            updateLevelInfoLine(radioMenuItem);
        }
        radioMenuItem.setToggleGroup(toggleGroup);
        radioMenuItem.setOnAction(event -> updateLevelInfoLine(radioMenuItem));
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

    void resetMoveCounters() {
        moveCounter = 0; // Переменная для хранения счетчика ходов
        playerMovesCounter = 0; // Переменная для хранения счетчика ходов
        computerMovesCounter = 0; // Переменная для хранения счетчика ходов
    }

    public void setStage() {
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

    public int getCurrentGameTime() {

        // Получаем текущее время в миллисекундах
        long currentTime = System.currentTimeMillis();

        // Вычисляем разницу между текущим временем и началом игры в секундах
        return (int) ((currentTime - startTime) / 1000);
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

    private final StatWindow statWindow = new StatWindow();

    public void handleStatisticMenuItem() {
        //showStatWindow(anchorPane);
        statWindow.showWindow(anchorPane, startTime);
    }

//	private WindowManager windowManager = new WindowManagerImpl();
//
//	public void showStatWindow() {
//		windowManager.showStatWindow();
//	}
//
//	// Конструктор класса, инициализирующий переменную windowManager
//	public GameController(WindowManager windowManager) {
//		this.windowManager = windowManager;
//	}
//
//	public GameController() {
//	}

    //============= эксперимент с выводом окна статистики в том же FXML ===========

    @FXML
    private Text totalGamesLabel;

    @FXML
    private Text computerWinsLabel;

    @FXML
    private Text playerWinsLabel;

    @FXML
    private Text totalTimeLabel;

    @FXML
    private Group statGroup;

    public void showStatWindow(AnchorPane anchorPane) {
        // Получаем размеры объекта MenuBar
        MenuBar menuBar = (MenuBar) anchorPane.getScene().lookup("#menuBar");
        double menuBarHeight = menuBar.getHeight();

        // Получаем высоту заголовка окна
        double titleBarHeight =
                anchorPane.getScene().getWindow().getHeight() - anchorPane.getScene().getHeight();

        // Создаем группу для группировки прямоугольника и текста
        Group group = new Group();

        // Создаем прямоугольник с размерами AnchorPane и цветом таким же, как у AnchorPane
        Rectangle rectangle = new Rectangle(anchorPane.getWidth(),
                anchorPane.getHeight() - titleBarHeight - menuBarHeight);
        rectangle.setFill(anchorPane.getBackground().getFills().get(0).getFill());

        // Создаем текстовое поле для вывода значений
        Text text = new Text();
        text.setFont(Font.font("Gill Sans MT Condensed", FontWeight.BOLD, 36));
        text.setFill(Color.BLACK);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setText("Total Games Played: 5\n" +
                "Player Wins: 2\n" +
                "Computer Wins: 1\n" +
                "Total Time Played: 00:12:22");

        // Рассчитываем высоту текста
        double textHeight = text.getLayoutBounds().getHeight();

        // Устанавливаем положение текста
        text.setLayoutX((rectangle.getWidth() - text.getLayoutBounds().getWidth()) / 2);
        text.setLayoutY((rectangle.getHeight() - textHeight) / 2);

        // Добавляем прямоугольник и текстовое поле в группу
        group.getChildren().addAll(rectangle, text);

        // Располагаем группу по центру AnchorPane со смещением вниз на высоту окна и высоту MenuBar
        group.setLayoutX(anchorPane.getLayoutX());
        group.setLayoutY(anchorPane.getLayoutY() + titleBarHeight + menuBarHeight);

        // Добавляем группу на AnchorPane
        anchorPane.getChildren().add(group);

        // Устанавливаем обработчик события на клик мышки для закрытия прямоугольника
        group.setOnMouseClicked(event -> anchorPane.getChildren().remove(group));
    }


    public void handleAboutMenuItem() {
        AboutWindow.displayAboutDialog();
    }

    // ===================================== Эксперимент с внедрением слушателя ====================================================================
//
    private static GameController instance;

    // Метод для получения экземпляра класса GameController
    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }
//
//	@Override
//
//	public void propertyChange(PropertyChangeEvent evt) {
//		if ("isOpen".equals(evt.getPropertyName())) {
//			boolean isOpen = (boolean) evt.getNewValue();
//			if (isOpen) {
//				//System.out.println("GameController: Window is open");
//			} else {
//				//System.out.println("GameController: Window is closed");
//			}
//		}
//	}

    // ===================================== Конец эксперимента с внедрением слушателя ====================================================================

}


