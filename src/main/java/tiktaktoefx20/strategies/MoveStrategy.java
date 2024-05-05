package tiktaktoefx20.strategies;

public interface MoveStrategy {
    int[] makeMove(char[][] gameField, String selectedLevel);
}