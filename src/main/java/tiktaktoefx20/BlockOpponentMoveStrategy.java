package tiktaktoefx20;

public class BlockOpponentMoveStrategy implements MoveStrategy {

    @Override
    public int[] makeMove(int[][] gameField, char computerSymbol) {
        // Проверяем каждую клетку игрового поля
        for (int row = 0; row < gameField.length; row++) {
            for (int col = 0; col < gameField[0].length; col++) {
                if (gameField[row][col] == Constants.EMPTY_SYMBOL) {
                    // Создаем временную копию поля для проверки хода
                    int[][] tempField = copyGameField(gameField);
                    // Симулируем ход игрока в текущую клетку
                    tempField[row][col] = Constants.PLAYER_SYMBOL; // Предполагаем, что символ игрока - это Constants.PLAYER_SYMBOL
                    // Проверяем, выиграл ли игрок
                    if (isWinningMove(tempField, Constants.PLAYER_SYMBOL)) {
                        // Если да, возвращаем координаты этой клетки для блокировки
                        return new int[]{row, col};
                    }
                }
            }
        }
        // Если не найдено выигрышного хода игрока, возвращаем null
        return null;
    }

    // Метод для создания копии игрового поля
    private int[][] copyGameField(int[][] gameField) {
        int[][] copy = new int[gameField.length][gameField[0].length];
        for (int i = 0; i < gameField.length; i++) {
            System.arraycopy(gameField[i], 0, copy[i], 0, gameField[0].length);
        }
        return copy;
    }

    // Метод для проверки, является ли ход выигрышным для заданного символа
    private boolean isWinningMove(int[][] gameField, char symbol) {
        // Реализуйте логику проверки выигрыша для заданного символа
        // Это может быть аналогичная проверка, которая используется в вашем текущем коде для проверки победы
        // Например, проверка строк, столбцов и диагоналей на наличие трех одинаковых символов
        // Если найден выигрыш, вернуть true, иначе false
        return false;
    }
}