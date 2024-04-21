package tiktaktoefx20;

import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import tiktaktoefx20.database.*;

import java.sql.*;

import java.util.*;

import static tiktaktoefx20.GameEngine.checkForDraw;
import static tiktaktoefx20.GameEngine.checkForWin;
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
    public void endGame(char[][] gameField, String winnerSymbol, List<GameMove> moves, int totalMoves, int playerMoves, int computerMoves, int duration) {

        // Проверяем условия победы или ничьи
        String result = "";
        if (checkForWin(gameField)) {
            result = winnerSymbol + " wins!";
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


        // Обновляем результат и продолжительность игры
//        totalMoves = moves.size();
        //result = winnerSymbol.equals("The player") ? "The player wins!" : "It's a draw";
//        result = checkForWin(gameField) ? "The player" : (checkForDraw(gameField) ? "It's a draw" : "The computer");
//        System.out.println("The result is: " + result);

        // Создаем объект игры и записываем ее в базу данных
        Game game = new Game(moves, totalMoves, playerMoves, computerMoves, result, duration);
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

        // Выводим содержание таблиц
        printDatabaseContents();
    }

    private void printDatabaseContents() {
        // Выводим содержание таблицы game_moves
        System.out.println("Contents of game_moves table:");
        printTableContents("game_moves");

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

