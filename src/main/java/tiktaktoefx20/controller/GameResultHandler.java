package tiktaktoefx20.controller;

import tiktaktoefx20.model.GameParams;
import tiktaktoefx20.model.GameEngine;
import tiktaktoefx20.view.GameResultWindow;
import tiktaktoefx20.constants.Constants;
import javafx.fxml.*;
import tiktaktoefx20.database.*;

/**
 * Класс, который записывает результаты игры в базу данных и вызывает окно с результатами. Class
 * that records game results in the database and opens a window with the results.
 */

public class GameResultHandler {
	
	private final int gameNumber = 1; // Инициализируем начальное значение счетчика игр
	GameResultWindow gameResultWindow = new GameResultWindow();
	
	@FXML
	public void endGame(GameParams params) {
		
		recordGameResult(params);
		gameResultWindow.show(params);
		
	}
	
	private String gameWinner(GameParams params) {
		if (GameEngine.checkForWin(params.gameField())) {
			Constants.Winner winner =
					params.gameWinner().equals("The player") ? Constants.Winner.PLAYER
							: Constants.Winner.COMPUTER;
			
			// Рисуем линию победы
			GraphicsManager graphicsManager = new GraphicsManager();
			graphicsManager.drawWinningLine(params.winningCells(), params.anchorPane(), winner,
					params.gridPane());
			
			return params.gameWinner() + " wins!";
		} else if (GameEngine.checkForDraw(params.gameField())) {
			return "It's a draw!!";
		} else {
			return "";
		}
	}
	
	private void recordGameResult(GameParams params) {
		// Записываем результат игры
		GameRecorder gameRecorder = new GameRecorder(params.moves(),
				params.moveCounter(),
				params.playerMovesCounter(),
				params.computerMovesCounter(),
				params.gameWinner(),
				params.gameTime(),
				params.difficultyLevel());
		
		System.out.println("params.gameWinner(): " + params.gameWinner());
		gameRecorder.recordGame();
	}
	
}

