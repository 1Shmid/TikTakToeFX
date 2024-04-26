package tiktaktoefx20;

import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.*;
import tiktaktoefx20.database.*;

import java.sql.*;

import java.util.*;

import static com.sun.javafx.sg.prism.NGCanvas.LINE_WIDTH;
import static tiktaktoefx20.GameEngine.*;
import static tiktaktoefx20.database.SQLiteDBManager.DB_URL;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.Node;
import javafx.application.Platform;
import java.sql.*;

public class GameResultHandler {

    private int gameNumber = 1; // Инициализируем начальное значение счетчика игр

    @FXML
    protected GridPane gridPane;


    private GameController gameController;

    public GameResultHandler() {
        // Конструктор по умолчанию
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    @FXML
    public void endGame(List<int[]> winningCells, char[][] gameField, String winnerSymbol, List<GameMove> moves, int totalMoves, int playerMoves, int computerMoves, int duration, String selectedLevel, AnchorPane anchorPane) {

        // Проверяем условия победы или ничьи
        String result = "";
        if (checkForWin(gameField)) {
            result = winnerSymbol + " wins!";

            System.out.println("endGame  Победила диагональ: " + Arrays.toString(winningCells.get(0)) + ", " +
                    Arrays.toString(winningCells.get(1)) + ", " +
                    Arrays.toString(winningCells.get(2)));

            drawWinningLine(winningCells, anchorPane); // Рисуем линию победы

            printGridAndCellCoordinates();


        } else if (checkForDraw(gameField)) {
            result = "It's a draw!!";
        }

        // Создаем новое диалоговое окно
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Game result");
        alert.setHeaderText(null);
        alert.setContentText(result);

        // Создаем кнопки для новой игры и выхода
        ButtonType newGameButton = new ButtonType("New Game", ButtonBar.ButtonData.YES);
        ButtonType exitButton = new ButtonType("Exit", ButtonBar.ButtonData.NO);

        // Устанавливаем кнопки в диалоговом окне
        alert.getButtonTypes().setAll(newGameButton, exitButton);


        // Ожидаем действия пользователя
        Optional<ButtonType> resultButton = alert.showAndWait();

        Game game = new Game(moves, totalMoves, playerMoves, computerMoves, result, duration, selectedLevel); // Добавляем уровень сложности в конструктор
        game.recordGame();
        gameNumber++; // Увеличиваем счетчик игр после завершения текущей игры

        // Если пользователь выбрал "Новая игра", начинаем новую игру
        if (resultButton.isPresent() && resultButton.get() == newGameButton) {

            startNewGame(gameField);
        } else {
            // Иначе закрываем приложение
            Platform.exit();
        }
    }




    private Canvas winningLineCanvas = new Canvas(); // Объявляем поле для хранения объекта Canvas с нарисованной линией

    // Метод для рисования линии на Canvas
    // Метод для рисования линии на Canvas
    private void drawWinningLine(List<int[]> winningCells, AnchorPane anchorPane) {

        System.out.println("drawWinningLine  Победила диагональ: " + Arrays.toString(winningCells.get(0)) + ", " +
                Arrays.toString(winningCells.get(1)) + ", " +
                Arrays.toString(winningCells.get(2)));

        // Получаем размеры AnchorPane
        double anchorPaneWidth = anchorPane.getWidth();
        double anchorPaneHeight = anchorPane.getHeight();

        // Получаем размеры поля GridPane
        double gridPaneWidth = gridPane.getWidth();
        double gridPaneHeight = gridPane.getHeight();

        // Получаем размеры ячейки
        double cellWidth = gridPaneWidth / Constants.FIELD_SIZE;
        double cellHeight = gridPaneHeight / Constants.FIELD_SIZE;

        // Находим координаты центров начальной и конечной ячеек в системе координат GridPane
        int startRow = winningCells.get(0)[0];
        int startCol = winningCells.get(0)[1];
        int endRow = winningCells.get(2)[0];
        int endCol = winningCells.get(2)[1];

        double startCellCenterX = (startCol + 0.5) * cellWidth;
        double startCellCenterY = (startRow + 0.5) * cellHeight;
        double endCellCenterX = (endCol + 0.5) * cellWidth;
        double endCellCenterY = (endRow + 0.5) * cellHeight;

        // Преобразуем координаты центров ячеек из системы координат GridPane в систему координат AnchorPane
        double startX = gridPane.localToScene(startCellCenterX, startCellCenterY).getX();
        double startY = gridPane.localToScene(startCellCenterX, startCellCenterY).getY();
        double endX = gridPane.localToScene(endCellCenterX, endCellCenterY).getX();
        double endY = gridPane.localToScene(endCellCenterX, endCellCenterY).getY();

        // Выводим координаты и размеры для проверки
        System.out.println("// Находим центры начальной и конечной ячеек");


        System.out.println("startRow  width: " + startRow);
        System.out.println("startCol  width: " + startCol);
        System.out.println("endRow  width: " + endRow);
        System.out.println("endCol  endCol: " + endCol);
        System.out.println("AnchorPane width: " + anchorPaneWidth);
        System.out.println("AnchorPane height: " + anchorPaneHeight);
        System.out.println("GridPane width: " + gridPaneWidth);
        System.out.println("GridPane height: " + gridPaneHeight);
        System.out.println("Cell width: " + cellWidth);
        System.out.println("Cell height: " + cellHeight);
        System.out.println("Start X: " + startX);
        System.out.println("Start Y: " + startY);
        System.out.println("End X: " + endX);
        System.out.println("End Y: " + endY);

        // Установка размеров и добавление Canvas на сцену
        setupCanvas(anchorPane);

        // Рисуем линию на Canvas
        drawLine(startX, startY, endX, endY);
    }


    // Метод для установки размеров и добавления Canvas на сцену
    private void setupCanvas(AnchorPane anchorPane) {
        if (!anchorPane.getChildren().contains(winningLineCanvas)) {
            winningLineCanvas.setWidth(anchorPane.getWidth());
            winningLineCanvas.setHeight(anchorPane.getHeight());
            anchorPane.getChildren().add(0, winningLineCanvas); // Добавляем Canvas в начало списка дочерних элементов AnchorPane
        }
    }


    // Метод для рисования линии на Canvas
    private void drawLine(double startX, double startY, double endX, double endY) {
        GraphicsContext gc = winningLineCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, winningLineCanvas.getWidth(), winningLineCanvas.getHeight()); // Очищаем Canvas
        gc.setStroke(Color.RED); // Цвет линии
        gc.setLineWidth(LINE_WIDTH); // Ширина линии
        gc.strokeLine(startX, startY, endX, endY);
    }



    private void removeWinningLine(GridPane gridPane, List<int[]> winningCells) {
        // Создаем новый объект Canvas
        Canvas canvas = new Canvas(gridPane.getWidth(), gridPane.getHeight());
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Добавляем проверку наличия существующей линии и удаляем ее, если она есть
        if (winningLineCanvas != null) {
            gridPane.getChildren().remove(winningLineCanvas);
        }
        // Сохраняем ссылку на новый Canvas в поле winningLineCanvas
        winningLineCanvas = canvas;
    }

    private void printGridAndCellCoordinates() {
        // Получаем координаты поля
        double gridPaneSceneX = gridPane.localToScene(gridPane.getBoundsInLocal()).getMinX();
        double gridPaneSceneY = gridPane.localToScene(gridPane.getBoundsInLocal()).getMinY();
        double gridPaneWidth = gridPane.getWidth();
        double gridPaneHeight = gridPane.getHeight();

        System.out.println("GridPane coordinates:");
        System.out.println("Scene X: " + gridPaneSceneX);
        System.out.println("Scene Y: " + gridPaneSceneY);
        System.out.println("Width: " + gridPaneWidth);
        System.out.println("Height: " + gridPaneHeight);

        // Получаем координаты каждой ячейки
        double cellWidth = gridPaneWidth / Constants.FIELD_SIZE;
        double cellHeight = gridPaneHeight / Constants.FIELD_SIZE;

        System.out.println("\nCell coordinates:");
        for (int i = 0; i < Constants.FIELD_SIZE; i++) {
            for (int j = 0; j < Constants.FIELD_SIZE; j++) {
                double cellSceneX = gridPaneSceneX + j * cellWidth;
                double cellSceneY = gridPaneSceneY + i * cellHeight;
                System.out.println("Cell [" + i + ", " + j + "]:");
                System.out.println("Scene X: " + cellSceneX);
                System.out.println("Scene Y: " + cellSceneY);
            }
        }
    }


    protected void startNewGame(char[][] gameField) {
        // Очищаем игровое поле и включаем все кнопки
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Button button) {
                button.setText("");
                button.setDisable(false);
            }
        }

        // Обнуляем состояние полей массива игры
        for (int i = 0; i < Constants.FIELD_SIZE; i++) {
            for (int j = 0; j < Constants.FIELD_SIZE; j++) {
                gameField[i][j] = Constants.EMPTY_SYMBOL;
            }
        }
        // Обнуляем счетчики ходов
        GameController.resetMoveCounters();

        // Включаем таймер игры
        GameController.startGameTimer();

        // Получаем новый номер игры из базы данных
        int newGameId = SQLiteDBManager.getGameIdFromDatabase();

        // Обновляем заголовок окна с новым номером игры
        String newTitle = Constants.GAME_TITLE_PREFIX + newGameId;
        Stage stage = (Stage) gridPane.getScene().getWindow();
        stage.setTitle(newTitle);

        // Выводим содержание таблиц
        // printDatabaseContents();
    }


    private void printDatabaseContents() {
        // Выводим содержание таблицы game_moves
        System.out.println("Contents of moves table:");
        printTableContents("moves");

        // Выводим содержание таблицы games
        System.out.println("Contents of games table:");
        printTableContents("games");
    }

    private void printTableContents(String tableName) {
        String sql = "SELECT * FROM " + tableName;
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Выводим заголовки столбцов
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(rsmd.getColumnName(i) + "\t");
            }
            System.out.println();

            // Выводим содержимое таблицы
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rs.getString(i) + "\t");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

