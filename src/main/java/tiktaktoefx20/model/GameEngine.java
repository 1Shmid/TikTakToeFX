package tiktaktoefx20.model;

import tiktaktoefx20.constants.Constants;
import java.util.*;
import tiktaktoefx20.controller.Context;
import tiktaktoefx20.controller.GameController;
import tiktaktoefx20.controller.GameController.ClickResult;
import tiktaktoefx20.controller.GameResultHandler;
import tiktaktoefx20.database.GameMove;
import tiktaktoefx20.strategies.AIStrategy;
import tiktaktoefx20.strategies.EasyStrategy;
import tiktaktoefx20.strategies.HardStrategy;
import tiktaktoefx20.strategies.Strategic;

/**
 * Вычисляет результаты игры и координаты выигрышных ячеек
 * <p>
 * Computes game results and winning coordinates
 */


public class GameEngine {
	
	private final Strategic easyStrategy = new EasyStrategy();
	private final Strategic hardStrategy = new HardStrategy();
	private final Strategic aiStrategy = new AIStrategy();
	
	// Создаем объекты обработчиков хода с разными стратегиями
	private final Context easyMoveHandler = new Context(easyStrategy);
	private final Context hardMoveHandler = new Context(hardStrategy);
	private final Context aiMoveHandler = new Context(aiStrategy);
	
	private final GameController gameController = new GameController();
	private final GameResultHandler gameResultHandler = new GameResultHandler();
	
	
	public void magicOn(ClickResult clickResult, GameParams params) {
		
		// Увеличиваем счетчик ходов
		int moveCounter = params.getMoveCounter() + 1;
		params.setMoveCounter(moveCounter);
		
		// Проверяем, что в ячейке нет символа, перед добавлением нового текста
		if (params.getGameField()[clickResult.row()][clickResult.col()] != Constants.EMPTY_SYMBOL) {
			// Ячейка уже занята, игнорируем клик
			return;
		}
		
		// Пришел первый клик и теперь нужно вывести символ.
		gameController.printSymbol(Constants.PLAYER_SYMBOL, clickResult, params);
		
		params.getGameField()[clickResult.row()][clickResult.col()] = Constants.PLAYER_SYMBOL;
		params.setGameField(params.getGameField()); // Обновляем gameField в объекте params
		
		// Увеличиваем счетчик ходов и счетчик ходов игрока
		int playerMovesCounter = params.getPlayerMovesCounter() + 1;
		params.setPlayerMovesCounter(playerMovesCounter);
		
		// Записываем ход игрока
		GameMove.addMove(moveCounter, "player", clickResult.row(), clickResult.col());
		
		// Обновляем состояние игры
		updateGameState(params);
	}
	
	private void updateGameState(GameParams params) {
		if (checkForWinOrDraw(params)) {
			params.setGameWinner(checkForWin(params) ? "The player" : "It's a draw");
			params.setWinningCells(GameEngine.winningCells);
			endGame(params);
		} else {
			performComputerMove(params);
			if (checkForWinOrDraw(params)) {
				params.setGameWinner(checkForWin(params) ? "The computer" : "It's a draw");
				params.setWinningCells(GameEngine.winningCells);
				endGame(params);
			}
		}
	}
	
	private void performComputerMove(GameParams params) {
		int[] computerMove = switch (params.getDifficultyLevel()) {
			case "EASY" -> easyMoveHandler.makeMove(params);
			case "HARD" -> hardMoveHandler.makeMove(params);
			case "AI" -> aiMoveHandler.makeMove(params);
			default -> easyMoveHandler.makeMove(params);
		};
		
		gameController.printSymbol(Constants.COMPUTER_SYMBOL, computerMove, params);
		
		// Обновляем gameField в params
		char[][] updatedGameField = Arrays.copyOf(params.getGameField(), params.getGameField().length);
		updatedGameField[computerMove[0]][computerMove[1]] = Constants.COMPUTER_SYMBOL;
		params.setGameField(updatedGameField);
		
		// Увеличиваем счетчик ходов
		params.setMoveCounter(params.getMoveCounter() + 1);
		// Увеличиваем счетчик ходов компьютера
		params.setComputerMovesCounter(params.getComputerMovesCounter() + 1);
		
		// Записываем ход компьютера
		GameMove.addMove(params.getMoveCounter(), "computer", computerMove[0], computerMove[1]);
	}
	
	private boolean checkForWinOrDraw(GameParams params) {
		return checkForWin(params) || checkForDraw(params);
	}
	
	private void endGame(GameParams params) {
		
		// Проверка, есть ли победитель
		boolean isWin = checkForWin(params);
		String gameWinner = isWin ? params.getGameWinner() : "It's a draw";
		
		// Обновление значений через сеттеры
		params.setGameWinner(gameWinner);
		params.setWinningCells(isWin ? params.getWinningCells() : null);
		params.setMoves(convertMovesToGameMovesList());
		
		// Передача обновленного объекта gameResultHandler
		gameResultHandler.endGame(params);
		
	}
	
	private List<GameMove> convertMovesToGameMovesList() {
		List<GameMove> gameMovesList = new ArrayList<>();
		for (Object[] move : GameMove.getMoves()) {
			int moveNumber = (int) move[0];
			String player = (String) move[1];
			int row = (int) move[2];
			int col = (int) move[3];
			gameMovesList.add(new GameMove(moveNumber, player, row, col));
		}
		return gameMovesList;
	}
	
	
	// Статическая переменная для хранения координат выигрышных ячеек
	public static List<int[]> winningCells = new ArrayList<>();
	
	// Проверка на победу
	
	public static boolean checkForWin(GameParams params) {
		return checkRowsForWin(params.getGameField()) ||
				checkColumnsForWin(params.getGameField()) ||
				checkDiagonalsForWin(params.getGameField());
	}
	
	public static boolean checkForDraw(GameParams params) {
		return !flattenGameField(params.getGameField()).contains(Constants.EMPTY_SYMBOL);
	}
	
	private static List<Character> flattenGameField(char[][] gameField) {
		List<Character> flattenedList = new ArrayList<>();
		for (char[] row : gameField) {
			for (char cell : row) {
				flattenedList.add(cell);
			}
		}
		return flattenedList;
	}
	
	// Определение координат выигрышных ячеек в строках
	static List<int[]> getRowsWinningCellsCoordinates(int row) {
		List<int[]> coordinates = new ArrayList<>();
		for (int j = 0; j < Constants.FIELD_SIZE; j++) {
			coordinates.add(new int[]{row, j});
		}
		return coordinates;
	}
	
	// Определение координат выигрышных ячеек в столбцах
	static List<int[]> getColumnWinningCellsCoordinates(int col) {
		List<int[]> coordinates = new ArrayList<>();
		for (int i = 0; i < Constants.FIELD_SIZE; i++) {
			coordinates.add(new int[]{i, col});
		}
		return coordinates;
	}
	
	// Определение координат выигрышных ячеек по диагоналям
	static List<int[]> getDiagonalWinningCellsCoordinates(char[][] gameField) {
		List<int[]> diagonalLeftRight = new ArrayList<>();
		List<int[]> diagonalRightLeft = new ArrayList<>();
		for (int i = 0; i < Constants.FIELD_SIZE; i++) {
			diagonalLeftRight.add(new int[]{i, i});
			diagonalRightLeft.add(new int[]{i, Constants.FIELD_SIZE - 1 - i});
		}
		// Выбираем одну диагональ в зависимости от символов в ее ячейках
		char symbol = gameField[0][0];
		return (symbol == gameField[1][1] && symbol == gameField[2][2]) ? diagonalLeftRight
				: diagonalRightLeft;
	}
	
	static boolean checkRowsForWin(char[][] gameField) {
		for (int i = 0; i < Constants.FIELD_SIZE; i++) {
			if (gameField[i][0] == gameField[i][1] &&
					gameField[i][0] == gameField[i][2] &&
					(gameField[i][0] == Constants.X_SYMBOL || gameField[i][0] == Constants.O_SYMBOL)) {
				//winningCells = getRowsWinningCellsCoordinates(gameField, i);
				winningCells.clear();
				winningCells.addAll(getRowsWinningCellsCoordinates(i));
				return true;
			}
		}
		return false;
	}
	
	static boolean checkColumnsForWin(char[][] gameField) {
		boolean columnWinFound = false; // Добавляем флаг для отслеживания нахождения выигрыша в столбце
		for (int i = 0; i < Constants.FIELD_SIZE; i++) {
			if (gameField[0][i] == gameField[1][i] &&
					gameField[0][i] == gameField[2][i] &&
					(gameField[0][i] == Constants.X_SYMBOL || gameField[0][i] == Constants.O_SYMBOL)) {
				winningCells = getColumnWinningCellsCoordinates(i);
				columnWinFound = true; // Устанавливаем флаг, что выигрыш в столбце найден
				break; // Прерываем цикл, так как выигрыш найден
			}
		}
		return columnWinFound; // Возвращаем флаг, показывающий, был ли найден выигрыш в столбце
	}
	
	// Проверка на победу по диагоналям
	static boolean checkDiagonalsForWin(char[][] gameField) {
		char symbol = gameField[0][0];
		
		if ((symbol == gameField[1][1] && symbol == gameField[2][2] &&
				(symbol == Constants.X_SYMBOL || symbol == Constants.O_SYMBOL))) {
			for (int i = 0; i < Constants.FIELD_SIZE; i++) {
				winningCells.add(new int[]{i, i});
				
				winningCells = getDiagonalWinningCellsCoordinates(gameField);
			}
			return true;
		}
		
		if ((gameField[0][2] == gameField[1][1] && gameField[0][2] == gameField[2][0]) &&
				(gameField[0][2] == Constants.X_SYMBOL || gameField[0][2] == Constants.O_SYMBOL)) {
			for (int i = 0; i < Constants.FIELD_SIZE; i++) {
				winningCells.add(new int[]{i, Constants.FIELD_SIZE - 1 - i});
				
				winningCells = getDiagonalWinningCellsCoordinates(gameField);
			}
			return true;
		}
		
		return false;
	}
}
