package tiktaktoefx20;

public interface MoveStrategy {
    int[] makeMove(int[][] gameField, char computerSymbol);
}