package tiktaktoefx20.controller;

import tiktaktoefx20.model.GameEndParams;
import tiktaktoefx20.model.GameEngine;
import tiktaktoefx20.view.GameResultWindow;
import tiktaktoefx20.constants.Constants;
import javafx.fxml.*;
import tiktaktoefx20.database.*;

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
      Constants.Winner winner =
          params.winningPlayer().equals("The player") ? Constants.Winner.PLAYER
              : Constants.Winner.COMPUTER;

      // Рисуем линию победы
      GraphicsManager graphicsManager = new GraphicsManager();
      graphicsManager.drawWinningLine(params.winningCells(), params.anchorPane(), winner,
          params.gridPane());

      return params.winningPlayer() + " wins!";
    } else if (GameEngine.checkForDraw(params.gameField())) {
      return "It's a draw!!";
    } else {
      return "";
    }
  }

  private void recordGameResult(GameEndParams params, String result) {
    // Записываем результат игры
    GameRecorder gameRecorder = new GameRecorder(params.moves(), params.moveCounter(),
        params.playerMovesCounter(),
        params.computerMovesCounter(), result, params.gameTime(), params.selectedLevel());
    gameRecorder.recordGame();
  }

}

