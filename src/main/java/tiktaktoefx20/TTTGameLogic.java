package tiktaktoefx20;

public class TTTGameLogic {
    // Ваша текущая реализация класса игры

    public boolean isWinningMove(int[][] gameField, int row, int col, char symbol) {
        // Проверяем выигрыш по горизонтали
        if (gameField[row][(col + 1) % 3] == symbol && gameField[row][(col + 2) % 3] == symbol) {
            return true;
        }
        // Проверяем выигрыш по вертикали
        if (gameField[(row + 1) % 3][col] == symbol && gameField[(row + 2) % 3][col] == symbol) {
            return true;
        }
        // Проверяем выигрыш по диагонали (если клетка находится на диагонали)
        if (row == col && gameField[(row + 1) % 3][(col + 1) % 3] == symbol && gameField[(row + 2) % 3][(col + 2) % 3] == symbol) {
            return true;
        }
        // Проверяем выигрыш по антидиагонали (если клетка находится на антидиагонали)
        if (row + col == 2 && gameField[(row + 1) % 3][(col + 2) % 3] == symbol && gameField[(row + 2) % 3][(col + 1) % 3] == symbol) {
            return true;
        }
        return false;
    }
}