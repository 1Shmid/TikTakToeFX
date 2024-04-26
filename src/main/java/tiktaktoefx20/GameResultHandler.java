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

import java.io.*;
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
    private Canvas winningLineCanvas = new Canvas(); // Объявляем поле для хранения объекта Canvas с нарисованной линией

    public GameResultHandler() {
        // Конструктор по умолчанию
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    @FXML
    public void endGame(List<int[]> winningCells, char[][] gameField, String winnerSymbol, List<GameMove> moves, int totalMoves, int playerMoves, int computerMoves, int duration, String selectedLevel, AnchorPane anchorPane) {
        Constants.Winner winner;
        String result = "";

        if (checkForWin(gameField)) {
            result = winnerSymbol + " wins!";
            winner = winnerSymbol.equals("The player") ? Constants.Winner.PLAYER : Constants.Winner.COMPUTER;
            drawWinningLine(winningCells, anchorPane, winner); // Рисуем линию победы
        } else if (checkForDraw(gameField)) {
            result = "It's a draw!!";
        }

        // Загрузите FXML-файл для диалогового окна
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EndGameDialog.fxml"));
        EndGameDialogController controller = new EndGameDialogController();
        loader.setController(controller); // Устанавливаем контроллер
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Устанавливаем символ победителя и результат игры
        controller.setWinnerSymbol(winnerSymbol);
        controller.setResultText(result);

        // Создайте новое диалоговое окно
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED); // Устанавливаем стиль без заголовка
        stage.setScene(new Scene(root));
        controller.setStage(stage); // Устанавливаем Stage

        // Устанавливаем обработчик события на клик мышкой
        root.setOnMouseClicked(event -> {
            stage.close();
            startNewGame(gameField, anchorPane);
        });

        // Показываем диалоговое окно
        stage.showAndWait();

        Game game = new Game(moves, totalMoves, playerMoves, computerMoves, result, duration, selectedLevel);
        game.recordGame();
        gameNumber++;
    }



    // Метод для рисования линии на Canvas
    private void drawWinningLine(List<int[]> winningCells, AnchorPane anchorPane, Constants.Winner winner) {

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

        // Установка размеров и добавление Canvas на сцену
        setupCanvas(anchorPane);

        // Рисуем линию на Canvas
        drawLine(startX, startY, endX, endY, winner);
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
    private void drawLine(double startX, double startY, double endX, double endY, Constants.Winner winner) {
        GraphicsContext gc = winningLineCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, winningLineCanvas.getWidth(), winningLineCanvas.getHeight()); // Очищаем Canvas
        if (winner == Constants.Winner.PLAYER) {
            gc.setStroke(Color.web("#545454"));// Цвет линии для победы игрока
        } else {
            gc.setStroke(Color.WHITE); // Цвет линии для победы компьютера
        }
        gc.setLineWidth(LINE_WIDTH); // Ширина линии
        gc.strokeLine(startX, startY, endX, endY);
    }


    protected void startNewGame(char[][] gameField, AnchorPane anchorPane) {

        // Проход по всем элементам AnchorPane
        anchorPane.getChildren().forEach(node -> {
            // Проверка, является ли текущий элемент Canvas
            if (node instanceof Canvas) {
                // Если элемент - Canvas, очистить его
                ((Canvas) node).getGraphicsContext2D().clearRect(0, 0, ((Canvas) node).getWidth(), ((Canvas) node).getHeight());
            }
        });


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

    }


}

