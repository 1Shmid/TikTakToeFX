package tiktaktoefx20;

import javafx.application.*;
import javafx.scene.canvas.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.stage.*;
import tiktaktoefx20.database.*;
import javafx.concurrent.Task;


public class NewGame {

    protected void cleanGameResult(char[][] gameField, AnchorPane anchorPane, GridPane gridPane){

        GameController gameController = new GameController();

        Task<Void> backgroundTask = new Task<>() {
            @Override
            protected Void call() {

                Platform.runLater(() -> clearCanvas(anchorPane));
                Platform.runLater(() -> gameController.clearGridPane(gridPane));
                clearGameField(gameField);
                gameController.resetMoveCounters();
                return null;
            }
        };

        Thread thread = new Thread(backgroundTask);
        thread.start();

    }

    protected void start(GridPane gridPane, Line bottomHLine, Line rightVLine, Line upHLine,Line leftVLine) {

        GameController gameController = GameController.getInstance();

        gameController.startGameTimer();
        int newGameId = SQLiteDBManager.getGameIdFromDatabase();
        setNewTitleGameNumber(newGameId, gridPane);

        gameController.animateLine(bottomHLine);
        gameController.animateLine(rightVLine);
        gameController.animateLine(upHLine);
        gameController.animateLine(leftVLine);
    }


    private void setNewTitleGameNumber(int newGameId, GridPane gridPane) {

        String newTitle = Constants.GAME_TITLE_PREFIX + newGameId;
        Stage stage = (Stage) gridPane.getScene().getWindow();
        stage.setTitle(newTitle);
    }

    private void clearGameField(char[][] gameField) {
        // Обнуляем состояние полей массива игры
        for (int i = 0; i < Constants.FIELD_SIZE; i++) {
            for (int j = 0; j < Constants.FIELD_SIZE; j++) {
                gameField[i][j] = Constants.EMPTY_SYMBOL;
            }
        }
    }

    private void clearCanvas(AnchorPane anchorPane) {
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
