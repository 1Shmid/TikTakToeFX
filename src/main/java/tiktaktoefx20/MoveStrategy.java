package tiktaktoefx20;

public interface MoveStrategy {
    int[] makeMove(char[][] gameField, String selectedLevel);
}