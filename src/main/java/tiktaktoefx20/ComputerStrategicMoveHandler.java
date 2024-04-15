package tiktaktoefx20;

import javafx.scene.control.*;


public class ComputerStrategicMoveHandler {
    private MoveStrategy moveStrategy;

    public ComputerStrategicMoveHandler(MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }

    public void setMoveStrategy(MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }

    public void makeMove(int[][] gameField, char computerSymbol) {
        int[] move = moveStrategy.makeMove(gameField, computerSymbol);
        if (move != null) {
            int row = move[0];
            int col = move[1];
            gameField[row][col] = computerSymbol;
        } else {
            System.out.println("No valid move available.");
        }
    }
}