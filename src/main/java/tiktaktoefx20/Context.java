package tiktaktoefx20;


import tiktaktoefx20.strategies.*;

public class Context {
    private final MoveStrategy moveStrategy;

    public Context(MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }

    public int[] makeMove(char[][] gameField, String selectedLevel) {
            return moveStrategy.makeMove(gameField, selectedLevel);
    }

}