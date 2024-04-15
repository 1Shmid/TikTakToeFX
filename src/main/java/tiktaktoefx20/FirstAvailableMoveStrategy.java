package tiktaktoefx20;

public class FirstAvailableMoveStrategy implements MoveStrategy {

    @Override
    public int[] makeMove(int[][] gameField, char computerSymbol) {
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[0].length; j++) {
                if (gameField[i][j] == Constants.EMPTY_SYMBOL) {
                    return new int[]{i, j};
                }
            }
        }
        // Если все ячейки заняты, возвращаем null
        return null;
    }
}