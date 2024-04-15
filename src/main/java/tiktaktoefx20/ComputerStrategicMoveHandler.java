package tiktaktoefx20;

import tiktaktoefx20.strategies.*;


public class ComputerStrategicMoveHandler {
    private MoveStrategy moveStrategy;

    public ComputerStrategicMoveHandler(MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }

    public void setMoveStrategy(MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }

    public void makeMove(int[][] gameField, char computerSymbol) {
        int[] move;
        // Если текущая стратегия - блокировка ходов игрока, используем ее
        if (moveStrategy instanceof BlockOpponentMoveStrategy) {
            move = ((BlockOpponentMoveStrategy) moveStrategy).makeMove(gameField, computerSymbol);
        } else {
            // В противном случае используем текущую стратегию
            move = moveStrategy.makeMove(gameField, computerSymbol);
        }

        if (move != null) {
            int row = move[0];
            int col = move[1];
            gameField[row][col] = computerSymbol;
        } else {
            System.out.println("No valid move available.");
        }
    }
}