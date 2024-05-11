package tiktaktoefx20.controller;

import javafx.fxml.*;
import javafx.scene.text.*;
import tiktaktoefx20.database.*;

import java.util.*;

public class StatWindowController {
	
	private static final int SECONDS_IN_HOUR = 3600;
	private static final int SECONDS_IN_MINUTE = 60;
	
	@FXML
	private Text totalGamesLabel;
	
	@FXML
	private Text computerWinsLabel;
	
	@FXML
	private Text playerWinsLabel;
	
	@FXML
	private Text totalTimeLabel;

//	@FXML
//	private void initialize() {
//		//setData();
//	}
	
	private void updateTotalTime(long startTime) {
		GameController gameController = GameController.getInstance();
		int totalGameDuration = SQLiteDBManager.getTotalGameDuration();
		final int[] currentGameDuration = {gameController.getCurrentGameTime(startTime)};
		
		// Выводим полученные данные в окне
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				currentGameDuration[0]++;
				
				totalTimeLabel.setText(formatTime(totalGameDuration + currentGameDuration[0]));
			}
		}, 0, 1000); // Обновляем каждую секунду
	}
	
	private void updateCompWins() {
		// Получение количества побед компьютера
		int computerWins = SQLiteDBManager.getCountWins("Computer");
		computerWinsLabel.setText(String.valueOf(computerWins));
	}
	
	private void updatePlayerWins() {
		// Получение количества побед игрока
		int playerWins = SQLiteDBManager.getCountWins("Player");
		
		// Выводим полученные данные в окне
		playerWinsLabel.setText(String.valueOf(playerWins));
	}
	
	private void updateTotalGames() {
		// Получаем номер игры из базы данных; -1 ибо текущая игра ещё не завершена
		int gameId = SQLiteDBManager.getGameIdFromDatabase() - 1;
		
		// Выводим полученные данные в окне
		totalGamesLabel.setText(String.valueOf(gameId));
	}
	
	private static String formatTime(int totalSeconds) {
		int hours = totalSeconds / SECONDS_IN_HOUR;
		int minutes = (totalSeconds % SECONDS_IN_HOUR) / SECONDS_IN_MINUTE;
		int seconds = totalSeconds % SECONDS_IN_MINUTE;
		return String.format("%02d:%02d:%02d", hours, minutes, seconds);
	}
	
	public void setData(long startTime) {
		
		updateTotalGames();
		
		updatePlayerWins();
		
		updateCompWins();
		
		updateTotalTime(startTime);
	}
	
	Timer timer = new Timer();
	
	public void stopTimer() {
		if (timer != null) {
			timer.cancel();
			timer.purge();
		}
	}
}