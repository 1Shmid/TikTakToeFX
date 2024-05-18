package tiktaktoefx20.model;

import java.util.*;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import tiktaktoefx20.database.*;

/**
 * Класс, представляющий набор параметров для окончания игры, необходимых другим классам и методам.
 * Class representing a set of game ending parameters needed by other classes and methods.
 */

public class GameParams {
	
	private String gameWinner;
	private List<int[]> winningCells;
	private String difficultyLevel;
	private List<GameMove> moves;
	private int moveCounter;
	private int playerMovesCounter;
	private int computerMovesCounter;
	private int gameTime;
	private AnchorPane anchorPane;
	private GridPane gridPane;
	private char[][] gameField;
	private Line bottomHLine;
	private Line rightVLine;
	private Line upHLine;
	private Line leftVLine;
	
	public GameParams(String gameWinner,
			List<int[]> winningCells,
			String difficultyLevel,
			List<GameMove> moves,
			int moveCounter,
			int playerMovesCounter,
			int computerMovesCounter,
			int gameTime,
			AnchorPane anchorPane,
			GridPane gridPane,
			char[][] gameField,
			Line bottomHLine,
			Line rightVLine,
			Line upHLine,
			Line leftVLine) {
		
		this.gameWinner = gameWinner;
		this.winningCells = winningCells;
		this.difficultyLevel = difficultyLevel;
		this.moves = moves;
		this.moveCounter = moveCounter;
		this.playerMovesCounter = playerMovesCounter;
		this.computerMovesCounter = computerMovesCounter;
		this.gameTime = gameTime;
		this.anchorPane = anchorPane;
		this.gridPane = gridPane;
		this.gameField = gameField;
		this.bottomHLine = bottomHLine;
		this.rightVLine = rightVLine;
		this.upHLine = upHLine;
		this.leftVLine = leftVLine;
	}
	
	// Getters and setters
	public String getGameWinner() {
		return gameWinner;
	}
	
	public void setGameWinner(String gameWinner) {
		this.gameWinner = gameWinner;
	}
	
	public List<int[]> getWinningCells() {
		return winningCells;
	}
	
	public void setWinningCells(List<int[]> winningCells) {
		this.winningCells = winningCells;
	}
	
	public String getDifficultyLevel() {
		return difficultyLevel;
	}
	
	public void setDifficultyLevel(String difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}
	
	public List<GameMove> getMoves() {
		return moves;
	}
	
	
	public void setMoves(List<GameMove> moves) {
		this.moves = moves;
	}
	
	public int getMoveCounter() {
		return moveCounter;
	}
	
	public void setMoveCounter(int moveCounter) {
		this.moveCounter = moveCounter;
	}
	
	public int getPlayerMovesCounter() {
		return playerMovesCounter;
	}
	
	public void setPlayerMovesCounter(int playerMovesCounter) {
		this.playerMovesCounter = playerMovesCounter;
	}
	
	public int getComputerMovesCounter() {
		return computerMovesCounter;
	}
	
	public void setComputerMovesCounter(int computerMovesCounter) {
		this.computerMovesCounter = computerMovesCounter;
	}
	
	public int getGameTime() {
		return gameTime;
	}
	
	public void setGameTime(int gameTime) {
		this.gameTime = gameTime;
	}
	
	public AnchorPane getAnchorPane() {
		return anchorPane;
	}
	
	public void setAnchorPane(AnchorPane anchorPane) {
		this.anchorPane = anchorPane;
	}
	
	public GridPane getGridPane() {
		return gridPane;
	}
	
	public void setGridPane(GridPane gridPane) {
		this.gridPane = gridPane;
	}
	
	public char[][] getGameField() {
		return gameField;
	}
	
	public void setGameField(char[][] gameField) {
		this.gameField = gameField;
	}
	
	public Line getBottomHLine() {
		return bottomHLine;
	}
	
	public void setBottomHLine(Line bottomHLine) {
		this.bottomHLine = bottomHLine;
	}
	
	public Line getRightVLine() {
		return rightVLine;
	}
	
	public void setRightVLine(Line rightVLine) {
		this.rightVLine = rightVLine;
	}
	
	public Line getUpHLine() {
		return upHLine;
	}
	
	public void setUpHLine(Line upHLine) {
		this.upHLine = upHLine;
	}
	
	public Line getLeftVLine() {
		return leftVLine;
	}
	
	public void setLeftVLine(Line leftVLine) {
		this.leftVLine = leftVLine;
	}
	
	@Override
	public String toString() {
		return "GameParams{" +
				"gameWinner='" + gameWinner + '\'' +
				", winningCells=" + winningCells +
				", difficultyLevel='" + difficultyLevel + '\'' +
				", moves=" + moves +
				", moveCounter=" + moveCounter +
				", playerMovesCounter=" + playerMovesCounter +
				", computerMovesCounter=" + computerMovesCounter +
				", gameTime=" + gameTime +
				", anchorPane=" + anchorPane +
				", gridPane=" + gridPane +
				", bottomHLine=" + bottomHLine +
				", rightVLine=" + rightVLine +
				", upHLine=" + upHLine +
				", leftVLine=" + leftVLine +
				'}';
	}
	
	
}


