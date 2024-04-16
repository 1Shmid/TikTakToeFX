package tiktaktoefx20;


public class ComputerStrategicMoveHandler {
    private final MoveStrategy moveStrategy;

    public ComputerStrategicMoveHandler(MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }

    public int[] makeMove(char[][] gameField, String selectedLevel) {
            return moveStrategy.makeMove(gameField, selectedLevel);
    }

}