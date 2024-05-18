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
		
		gameWinner(params);
		recordGameResult(params);
		gameResultWindow.show(params);
		
	}
	
	private void gameWinner(GameParams params) {
		if (GameEngine.checkForWin(params.getGameField())) {
			Constants.Winner winner =
					"The player".equals(params.getGameWinner()) ? Constants.Winner.PLAYER
							: Constants.Winner.COMPUTER;
			
			// Рисуем линию победы
			if (params.getWinningCells() != null) {
				GraphicsManager graphicsManager = new GraphicsManager();
				graphicsManager.drawWinningLine(params.getWinningCells(), params.getAnchorPane(), winner,
						params.getGridPane());
			}
			
		} else if (GameEngine.checkForDraw(params.getGameField())) {
		} else {
		}
	}
	
	private void recordGameResult(GameParams params) {
		// Записываем результат игры
		GameRecorder gameRecorder = new GameRecorder(
				params.getMoves(),
				params.getMoveCounter(),
				params.getPlayerMovesCounter(),
				params.getComputerMovesCounter(),
				params.getGameWinner(),
				params.getGameTime(),
				params.getDifficultyLevel()
		);
		
		System.out.println("params.getGameWinner(): " + params.getGameWinner());
		gameRecorder.recordGame();
	}
	
}

