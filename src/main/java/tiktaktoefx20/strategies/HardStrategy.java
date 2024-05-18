package tiktaktoefx20.strategies;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import tiktaktoefx20.constants.Constants;
import tiktaktoefx20.model.GameEngine;
import tiktaktoefx20.model.GameParams;

/**
 * Класс, представляющий сложную реализацию стратегии.
 * <p>
 * A class representing the implementation of a easy strategy for a computer move.
 */

public class HardStrategy implements Strategic {
	
	private final Strategic easyStrategy = new EasyStrategy();
	
	@Override
	public int[] makeMove(GameParams params) {
		int[] move = findWinningMove(params);
		if (move != null) {
			return move;
		}
		
		move = blockPlayerWin(params);
		if (move != null) {
			return move;
		}
		
		move = occupyCorner(params.getGameField());
		if (move != null) {
			return move;
		}
		
		move = occupyCenter(params.getGameField());
		if (move != null) {
			return move;
		}
		
		// Если ни одно из условий не выполнено, делаем ход по простой стратегии
		return easyStrategy.makeMove(params);
	}
	
	
	public int[] findWinningMove(GameParams params) {
		// Поиск выигрышного хода
		char[][] gameField = params.getGameField();
		for (int row = 0; row < gameField.length; row++) {
			for (int col = 0; col < gameField[0].length; col++) {
				if (gameField[row][col] == Constants.EMPTY_SYMBOL) {
					gameField[row][col] = Constants.COMPUTER_SYMBOL;
					if (GameEngine.checkForWin(params)) {
						gameField[row][col] = Constants.EMPTY_SYMBOL; // Возвращаем ячейку в исходное состояние
						return new int[]{row, col};
					}
					gameField[row][col] = Constants.EMPTY_SYMBOL; // Возвращаем ячейку в исходное состояние
				}
			}
		}
		return null;
	}

//	private int[] findWinningMove(char[][] gameField) {
//		// Поиск выигрышного хода
//		for (int row = 0; row < gameField.length; row++) {
//			for (int col = 0; col < gameField[0].length; col++) {
//				if (gameField[row][col] == Constants.EMPTY_SYMBOL) {
//					gameField[row][col] = Constants.COMPUTER_SYMBOL;
//					if (GameEngine.checkForWin(gameField)) {
//						gameField[row][col] = Constants.EMPTY_SYMBOL; // Возвращаем ячейку в исходное состояние
//						return new int[]{row, col};
//					}
//					gameField[row][col] = Constants.EMPTY_SYMBOL; // Возвращаем ячейку в исходное состояние
//				}
//			}
//		}
//		return null;
//	}
	
	public int[] blockPlayerWin(GameParams params) {
		// Блокирование выигрыша игрока
		char[][] gameField = params.getGameField();
		for (int row = 0; row < gameField.length; row++) {
			for (int col = 0; col < gameField[0].length; col++) {
				if (gameField[row][col] == Constants.EMPTY_SYMBOL) {
					gameField[row][col] = Constants.PLAYER_SYMBOL; // Пытаемся сделать ход игрока
					if (GameEngine.checkForWin(params)) {
						gameField[row][col] = Constants.EMPTY_SYMBOL; // Возвращаем ячейку в исходное состояние
						return new int[]{row, col};
					}
					gameField[row][col] = Constants.EMPTY_SYMBOL; // Возвращаем ячейку в исходное состояние
				}
			}
		}
		return null;
	}

//	private int[] blockPlayerWin(char[][] gameField) {
//		// Блокирование выигрыша игрока
//		for (int row = 0; row < gameField.length; row++) {
//			for (int col = 0; col < gameField[0].length; col++) {
//				if (gameField[row][col] == Constants.EMPTY_SYMBOL) {
//					gameField[row][col] = Constants.PLAYER_SYMBOL; // Пытаемся сделать ход игрока
//					if (GameEngine.checkForWin(gameField)) {
//						gameField[row][col] = Constants.EMPTY_SYMBOL; // Возвращаем ячейку в исходное состояние
//						return new int[]{row, col};
//					}
//					gameField[row][col] = Constants.EMPTY_SYMBOL; // Возвращаем ячейку в исходное состояние
//				}
//			}
//		}
//		return null;
//	}
	
	private int[] occupyCenter(char[][] gameField) {
		// Занятие центральной клетки
		int centerRow = 1; // Индекс строки центральной клетки
		int centerCol = 1; // Индекс столбца центральной клетки
		if (gameField[centerRow][centerCol] == Constants.EMPTY_SYMBOL) {
			return new int[]{centerRow, centerCol};
		}
		return null;
	}
	
	private int[] occupyCorner(char[][] gameField) {
		// Занятие свободного угла
		List<int[]> emptyCorners = new ArrayList<>();
		if (gameField[0][0] == Constants.EMPTY_SYMBOL) {
			emptyCorners.add(new int[]{0, 0});
		}
		if (gameField[0][2] == Constants.EMPTY_SYMBOL) {
			emptyCorners.add(new int[]{0, 2});
		}
		if (gameField[2][0] == Constants.EMPTY_SYMBOL) {
			emptyCorners.add(new int[]{2, 0});
		}
		if (gameField[2][2] == Constants.EMPTY_SYMBOL) {
			emptyCorners.add(new int[]{2, 2});
		}
		if (!emptyCorners.isEmpty()) {
			Random random = new Random();
			return emptyCorners.get(random.nextInt(emptyCorners.size()));
		}
		return null;
	}
}
