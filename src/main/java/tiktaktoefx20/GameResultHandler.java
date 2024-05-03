package tiktaktoefx20;

import javafx.fxml.*;
import javafx.scene.layout.*;
import tiktaktoefx20.database.*;

import java.util.*;

import static tiktaktoefx20.GameEngine.*;

public class GameResultHandler {

    private int gameNumber = 1; // Инициализируем начальное значение счетчика игр

    @FXML
    protected GridPane gridPane;

    EndGameDialog endGameDialog = new EndGameDialog();

    public GameResultHandler() {
    }

    public void setGameController() {
    }

    @FXML
    public void endGame(List<int[]> winningCells, char[][] gameField, String winnerSymbol, List<GameMove> moves, int totalMoves, int playerMoves, int computerMoves, int duration, String selectedLevel, AnchorPane anchorPane) {
        
        final String result = gameResult(winningCells, gameField, winnerSymbol, anchorPane);

        recordGameResult(moves, totalMoves, playerMoves, computerMoves, duration, selectedLevel, result);

        endGameDialog.show(gameField, winnerSymbol, anchorPane, gridPane, result);
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
}

