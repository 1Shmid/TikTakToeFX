package tiktaktoefx20.strategies;

import tiktaktoefx20.constants.Constants;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import tiktaktoefx20.model.GameParams;

import static tiktaktoefx20.database.SQLiteDBManager.getWinningGameStates;

/**
 * Класс, писпользует машинное обучение для принятия решений о следующем ходе на основе текущего
 * состояния игрового поля и выбранного уровня сложности
 * <p>
 * Class, uses machine learning to make decisions about the next move based on the current state of
 * the playing field and the selected difficulty level
 */

public class AIStrategy implements Strategic {
	
	@Override
	public int[] makeMove(GameParams params) {
		List<char[][]> winningGameStates = getWinningGameStates();
		List<int[]> possibleMoves = new ArrayList<>();
		
		for (char[][] winningGameState : winningGameStates) {
			for (int row = 0; row < params.getGameField().length; row++) {
				for (int col = 0; col < params.getGameField()[0].length; col++) {
					if (params.getGameField()[row][col] == Constants.EMPTY_SYMBOL
							&& params.getGameField()[row][col] == winningGameState[row][col]) {
						int matches = countMatches(params.getGameField(), winningGameState, row, col);
						if (matches > 0) {
							possibleMoves.add(new int[]{row, col, matches});
						}
					}
				}
			}
		}
		
		if (!possibleMoves.isEmpty()) {
			return chooseBestMove(possibleMoves);
		}
		
		return new HardStrategy().makeMove(params);
	}

//	public int[] makeMove(char[][] gameField, String selectedLevel) {
//		List<char[][]> winningGameStates = getWinningGameStates();
//		List<int[]> possibleMoves = new ArrayList<>();
//
//		for (char[][] winningGameState : winningGameStates) {
//			for (int row = 0; row < gameField.length; row++) {
//				for (int col = 0; col < gameField[0].length; col++) {
//					if (gameField[row][col] == Constants.EMPTY_SYMBOL
//							&& gameField[row][col] == winningGameState[row][col]) {
//						int matches = countMatches(gameField, winningGameState, row, col);
//						if (matches > 0) {
//							possibleMoves.add(new int[]{row, col, matches});
//						}
//					}
//				}
//			}
//		}
//
//		if (!possibleMoves.isEmpty()) {
//			return chooseBestMove(possibleMoves);
//		}
//
//		return new HardStrategy().makeMove(params);
//	}
	
	private int countMatches(char[][] currentGameState, char[][] winningGameState, int row, int col) {
		int matches = 0;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (isValidPosition(row + i, col + j, currentGameState.length, currentGameState[0].length)
						&&
						currentGameState[row + i][col + j] == winningGameState[row + i][col + j]) {
					matches++;
				}
			}
		}
		return matches;
	}
	
	private boolean isValidPosition(int row, int col, int numRows, int numCols) {
		return row >= 0 && row < numRows && col >= 0 && col < numCols;
	}
	
	private int[] chooseBestMove(List<int[]> possibleMoves) {
		if (possibleMoves.isEmpty()) {
			return null;
		}
		
		// Находим максимальный элемент по третьему элементу (предполагаем, что move[2] содержит приоритет)
		int[] bestMove = possibleMoves.stream()
				.max(Comparator.comparingInt(move -> move[2]))
				.orElse(null);
		
		// Возвращаем координаты лучшего хода (первые два элемента)
		return new int[]{bestMove[0], bestMove[1]};
	}
	
}
