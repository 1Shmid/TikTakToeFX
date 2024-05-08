package tiktaktoefx20;

import java.util.*;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;

public class GameEndParams {
    public String getWinningPlayer() {
        return winningPlayer;
    }

    public List<int[]> getWinningCells() {
        return winningCells;
    }

    public String getSelectedLevel() {
        return selectedLevel;
    }

    public int getMoveCounter() {
        return moveCounter;
    }

    public int getPlayerMovesCounter() {
        return playerMovesCounter;
    }

    public int getComputerMovesCounter() {
        return computerMovesCounter;
    }

    public int getGameTime() {
        return gameTime;
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public Line getBottomHLine() {
        return bottomHLine;
    }

    public Line getRightVLine() {
        return rightVLine;
    }

    public Line getUpHLine() {
        return upHLine;
    }

    public Line getLeftVLine() {
        return leftVLine;
    }

    private final String winningPlayer;
    private final List<int[]> winningCells;
    private final String selectedLevel;
    private final int moveCounter;
    private final int playerMovesCounter;
    private final int computerMovesCounter;
    private final int gameTime;
    private final AnchorPane anchorPane;
    private final GridPane gridPane;
    private final Line bottomHLine;
    private final Line rightVLine;
    private final Line upHLine;
    private final Line leftVLine;

    public GameEndParams(String winningPlayer, List<int[]> winningCells, String selectedLevel, int moveCounter, int playerMovesCounter, int computerMovesCounter, int gameTime, AnchorPane anchorPane, GridPane gridPane, Line bottomHLine, Line rightVLine, Line upHLine, Line leftVLine) {
        this.winningPlayer = winningPlayer;
        this.winningCells = winningCells;
        this.selectedLevel = selectedLevel;
        this.moveCounter = moveCounter;
        this.playerMovesCounter = playerMovesCounter;
        this.computerMovesCounter = computerMovesCounter;
        this.gameTime = gameTime;
        this.anchorPane = anchorPane;
        this.gridPane = gridPane;
        this.bottomHLine = bottomHLine;
        this.rightVLine = rightVLine;
        this.upHLine = upHLine;
        this.leftVLine = leftVLine;
    }

    // Геттеры для всех параметров
}

