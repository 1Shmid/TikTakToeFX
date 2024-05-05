package tiktaktoefx20;

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


public class NewGame {


    protected void start(char[][] gameField, AnchorPane anchorPane, GridPane gridPane, Rectangle shade) {

        clearCanvas(anchorPane);
        clearGridPaine(gridPane);
        clearGameField(gameField);
        GameController.resetMoveCounters();
        GameController.startGameTimer();
        int newGameId = SQLiteDBManager.getGameIdFromDatabase();
        setNewTitleGameNumber(newGameId, gridPane);
    }


    private void setNewTitleGameNumber(int newGameId, GridPane gridPane) {

        String newTitle = Constants.GAME_TITLE_PREFIX + newGameId;
        Stage stage = (Stage) gridPane.getScene().getWindow();
        stage.setTitle(newTitle);
    }

    private void clearGridPaine(GridPane gridPane) {

        // Очищаем игровое поле и включаем все кнопки
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Button button) {
                button.setText("");
                button.setDisable(false);
            }
        }
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
