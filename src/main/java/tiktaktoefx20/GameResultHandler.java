package tiktaktoefx20;

import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import tiktaktoefx20.database.*;

import java.util.*;

public class GameResultHandler {

    private int gameNumber = 1; // Инициализируем начальное значение счетчика игр

    @FXML
    protected GridPane gridPane;

    @FXML
    public void endGame(char[][] gameField, String winnerSymbol, List<GameMove> moves, int totalMoves, int playerMoves, int computerMoves, int duration) {

        // Проверяем условия победы или ничьи
        String result = "";
        if (GameEngine.checkForWin(gameField)) {
            result = winnerSymbol + " wins!";
        } else if (GameEngine.checkForDraw(gameField)) {
            result = "It's a draw!!";
        }

        // Создаем объект Game и записываем игру в базу данных
        Game game = new Game(moves, totalMoves, playerMoves, computerMoves, result, duration);
        game.recordGame();
        gameNumber++; // Увеличиваем счетчик игр после завершения текущей игры

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

        // Если пользователь выбрал "Новая игра", начинаем новую игру
        if (resultButton.isPresent() && resultButton.get() == newGameButton) {

            startNewGame(gameField);

        } else {
            // Иначе закрываем приложение
            Platform.exit();
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
    }
}
