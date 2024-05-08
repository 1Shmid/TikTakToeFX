package tiktaktoefx20;

import javafx.fxml.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import tiktaktoefx20.database.*;

import java.util.*;

public class GameResultHandler {

    private final int gameNumber = 1; // Инициализируем начальное значение счетчика игр
    GameResultWindow gameResultWindow = new GameResultWindow();

    @FXML
    public void endGame(GameEndParams params) {

        final String result = gameResult(params);
        recordGameResult(params, result);
        gameResultWindow.show(params, result);
    }

    private String gameResult(GameEndParams params) {
        if (GameEngine.checkForWin(params.gameField())) {
            Constants.Winner winner = params.winningPlayer().equals("The player") ? Constants.Winner.PLAYER : Constants.Winner.COMPUTER;

            // Рисуем линию победы
            GraphicsManager graphicsManager = new GraphicsManager();
            graphicsManager.drawWinningLine(params.winningCells(), params.anchorPane(), winner, params.gridPane());

            return params.winningPlayer() + " wins!";
        } else if (GameEngine.checkForDraw(params.gameField())) {
            return "It's a draw!!";
        } else {
            return "";
        }
    }

    private void recordGameResult(GameEndParams params, String result) {
        // Записываем результат игры
        Game game = new Game(params.moves(), params.moveCounter(), params.playerMovesCounter(), params.computerMovesCounter(), result, params.gameTime(), params.selectedLevel());
        game.recordGame();
    }

}

