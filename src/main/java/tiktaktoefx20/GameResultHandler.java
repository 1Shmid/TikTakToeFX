package tiktaktoefx20;

import javafx.animation.*;
import javafx.fxml.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.util.*;
import tiktaktoefx20.database.*;

import java.io.*;

import java.util.*;

import static tiktaktoefx20.GameEngine.*;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.Node;
import javafx.application.Platform;

public class GameResultHandler {

    private int gameNumber = 1; // Инициализируем начальное значение счетчика игр

    private final Canvas winningLineCanvas = new Canvas(); // Объявляем поле для хранения объекта Canvas с нарисованной линией

    @FXML
    protected GridPane gridPane;

    public GameResultHandler() {
    }

    public void setGameController(GameController gameController) {
    }

    @FXML
    public void endGame(List<int[]> winningCells, char[][] gameField, String winnerSymbol, List<GameMove> moves, int totalMoves, int playerMoves, int computerMoves, int duration, String selectedLevel, AnchorPane anchorPane) {
        
        final String result = gameResult(winningCells, gameField, winnerSymbol, anchorPane);

        // Задержка перед показом диалогового окна
        PauseTransition pause = new PauseTransition(Duration.millis(110)); // Время анимации линии
        pause.setOnFinished(event -> showEndGameDialog(gameField, winnerSymbol, anchorPane, result));
        pause.play();

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

    private void showEndGameDialog(char[][] gameField, String winnerSymbol, AnchorPane anchorPane, String result) {
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

        // Получаем размеры окна игры
        Bounds gameBounds = anchorPane.localToScreen(anchorPane.getBoundsInLocal());

        // Получаем размеры объекта MenuBar
        double menuBarHeight = anchorPane.lookup("#menuBar").getBoundsInLocal().getHeight();

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
        stage.setOnShown(event -> centerStage(stage, gameBounds, menuBarHeight));

        // Устанавливаем обработчик события на клик мышкой
        root.setOnMouseClicked(event -> {
            stage.close();
            startNewGame(gameField, anchorPane);
        });
    }

    // Метод для центрирования окна относительно другого окна с учетом высоты MenuBar
    private void centerStage(Stage stage, Bounds gameBounds, double menuBarHeight) {
        // Получаем размеры диалогового окна
        double dialogWidth = stage.getWidth();
        double dialogHeight = stage.getHeight();

        // Получаем размеры окна игры за вычетом высоты MenuBar
        double gameWidth = gameBounds.getWidth();
        double gameHeight = gameBounds.getHeight() - menuBarHeight;

        // Вычисляем координаты середины окна игры
        double gameCenterX = gameBounds.getMinX() + gameWidth / 2;
        double gameCenterY = gameBounds.getMinY() + gameHeight / 2;

        // Вычисляем новые координаты для центрирования диалогового окна
        double newDialogX = gameCenterX - dialogWidth / 2;
        double newDialogY = gameCenterY - dialogHeight / 2;

        // Устанавливаем новые координаты для диалогового окна
        stage.setX(newDialogX);
        stage.setY(newDialogY);

        // Создаем анимацию для плавного отображения окна
        startScaleAnimation(stage.getScene().getRoot());
    }


    private void startScaleAnimation(Node node) {
        // Создаем анимацию масштабирования и изменения прозрачности
        ParallelTransition animation = createScaleAndFadeAnimation(node);

        // Запускаем анимацию
        animation.play();
    }

    private ParallelTransition createScaleAndFadeAnimation(Node node) {
        // Создаем анимацию изменения масштаба
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300), node);
        scaleTransition.setToX(1.0); // Конечный масштаб по оси X
        scaleTransition.setToY(1.0); // Конечный масштаб по оси Y

        // Создаем анимацию изменения прозрачности
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(300), node);
        fadeTransition.setFromValue(0.0); // Начальное значение прозрачности
        fadeTransition.setToValue(1.0); // Конечное значение прозрачности

        // Создаем параллельную анимацию для выполнения обеих анимаций одновременно
        ParallelTransition parallelTransition = new ParallelTransition(scaleTransition, fadeTransition);

        return parallelTransition;
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

