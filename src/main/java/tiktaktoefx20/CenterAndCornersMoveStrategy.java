package tiktaktoefx20;

public class CenterAndCornersMoveStrategy implements MoveStrategy {

    private TTTGameLogic gameLogic;

    public CenterAndCornersMoveStrategy(TTTGameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    @Override
    public int[] makeMove(int[][] gameField, char computerSymbol) {
        // Проверяем, свободна ли центральная клетка
        if (gameField[1][1] == Constants.EMPTY_SYMBOL) {
            return new int[]{1, 1}; // Занимаем центральную клетку
        } else {
            // Занимаем угловую клетку (выбираем первую попавшуюся свободную)
            for (int i = 0; i < gameField.length; i += 2) {
                for (int j = 0; j < gameField[0].length; j += 2) {
                    if (gameField[i][j] == Constants.EMPTY_SYMBOL) {
                        return new int[]{i, j};
                    }
                }
            }
        }
        // Если центральная и угловые клетки заняты, возвращаем null
        return null;
    }
}
