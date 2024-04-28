package tiktaktoefx20;

import javafx.animation.*;
import javafx.application.*;
import javafx.fxml.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.*;
import javafx.util.*;
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

    private GameController gameController;
    private Canvas winningLineCanvas = new Canvas(); // Объявляем поле для хранения объекта Canvas с нарисованной линией

    @FXML
    protected GridPane gridPane;

    public GameResultHandler() {
        // Конструктор по умолчанию
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    @FXML
    public void endGame(List<int[]> winningCells, char[][] gameField, String winnerSymbol, List<GameMove> moves, int totalMoves, int playerMoves, int computerMoves, int duration, String selectedLevel, AnchorPane anchorPane) {
        
        final String result = gameResult(winningCells, gameField, winnerSymbol, anchorPane);

        if (showEndGameDialog(gameField, winnerSymbol, anchorPane, result)) return;

        recordGameResult(moves, totalMoves, playerMoves, computerMoves, duration, selectedLevel, result);
    }

    private String gameResult(List<int[]> winningCells, char[][] gameField, String winnerSymbol, AnchorPane anchorPane) {
        Constants.Winner winner;
        String result = "";

        if (checkForWin(gameField)) {
            result = winnerSymbol + " wins!";
            winner = winnerSymbol.equals("The player") ? Constants.Winner.PLAYER : Constants.Winner.COMPUTER;

            // Рисуем линию победы
            GraphicsManager graphicsManager = new GraphicsManager();
            graphicsManager.drawWinningLine(winningCells, anchorPane, winner, gridPane); 

        } else if (checkForDraw(gameField)) {
            result = "It's a draw!!";
        }
        return result;
    }

    private void recordGameResult(List<GameMove> moves, int totalMoves, int playerMoves, int computerMoves, int duration, String selectedLevel, String result) {
        // Записываем результат игры
        Game game = new Game(moves, totalMoves, playerMoves, computerMoves, result, duration, selectedLevel);
        game.recordGame();
        gameNumber++;
    }

    private boolean showEndGameDialog(char[][] gameField, String winnerSymbol, AnchorPane anchorPane, String result) {
        // Загрузите FXML-файл для диалогового окна
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EndGameDialog.fxml"));
        EndGameDialogController controller = new EndGameDialogController();
        loader.setController(controller); // Устанавливаем контроллер
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }

        // Устанавливаем символ победителя и результат игры
        controller.setWinnerSymbol(winnerSymbol);
        controller.setResultText(result);

        // Получаем размеры окна игры
        Bounds gameBounds = anchorPane.localToScreen(anchorPane.getBoundsInLocal());

        // Создаем новое диалоговое окно
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED); // Устанавливаем стиль без заголовка

        // Устанавливаем размеры нового окна
        Scene dialogScene = new Scene(root);
        stage.setScene(dialogScene);
        controller.setStage(stage); // Устанавливаем Stage

        // Создаем паузу в 1 секунду перед показом окна
        PauseTransition pause = new PauseTransition(Duration.millis(300));
        pause.setOnFinished(event -> {
            // После завершения паузы, показываем диалоговое окно
            Platform.runLater(stage::showAndWait);
        });
        pause.play();

        // Устанавливаем обработчик события на отображение окна
        stage.setOnShown(event -> centerStage(stage, gameBounds));

        // Устанавливаем обработчик события на клик мышкой
        root.setOnMouseClicked(event -> {
            stage.close();
            startNewGame(gameField, anchorPane);
        });
        return false;
    }

    // Метод для центрирования окна относительно другого окна
    private void centerStage(Stage stage, Bounds gameBounds) {
        // Получаем размеры диалогового окна
        double dialogWidth = stage.getWidth();
        double dialogHeight = stage.getHeight();

        // Получаем размеры окна игры
        double gameWidth = gameBounds.getWidth();
        double gameHeight = gameBounds.getHeight();

        // Вычисляем координаты середины окна игры
        double gameCenterX = gameBounds.getMinX() + gameWidth / 2;
        double gameCenterY = gameBounds.getMinY() + gameHeight / 2;

        // Вычисляем новые координаты для центрирования диалогового окна
        double newDialogX = gameCenterX - dialogWidth / 2;
        double newDialogY = gameCenterY - dialogHeight / 2;

        // Устанавливаем новые координаты для диалогового окна
        stage.setX(newDialogX);
        stage.setY(newDialogY);
    }

    protected void startNewGame(char[][] gameField, AnchorPane anchorPane) {

        clearCanvas(anchorPane);

        clearGridPaine();

        clearGameField(gameField);

        // Обнуляем счетчики ходов
        GameController.resetMoveCounters();

        // Включаем таймер игры
        GameController.startGameTimer();

        // Получаем новый номер игры из базы данных
        int newGameId = SQLiteDBManager.getGameIdFromDatabase();

        // Обновляем заголовок окна с новым номером игры
        setNewTitleGameNumber(newGameId);

    }

    private void setNewTitleGameNumber(int newGameId) {
        String newTitle = Constants.GAME_TITLE_PREFIX + newGameId;
        Stage stage = (Stage) gridPane.getScene().getWindow();
        stage.setTitle(newTitle);
    }

    private static void clearGameField(char[][] gameField) {
        // Обнуляем состояние полей массива игры
        for (int i = 0; i < Constants.FIELD_SIZE; i++) {
            for (int j = 0; j < Constants.FIELD_SIZE; j++) {
                gameField[i][j] = Constants.EMPTY_SYMBOL;
            }
        }
    }

    private void clearGridPaine() {
        // Очищаем игровое поле и включаем все кнопки
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Button button) {
                button.setText("");
                button.setDisable(false);
            }
        }
    }

    private static void clearCanvas(AnchorPane anchorPane) {
        // Проход по всем элементам AnchorPane
        anchorPane.getChildren().forEach(node -> {
            // Проверка, является ли текущий элемент Canvas
            if (node instanceof Canvas) {
                // Если элемент - Canvas, очистить его
                ((Canvas) node).getGraphicsContext2D().clearRect(0, 0, ((Canvas) node).getWidth(), ((Canvas) node).getHeight());
            }
        });
    }
}

