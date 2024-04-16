package tiktaktoefx20;

import java.util.*;

public class TTTGameLogic {

    // Проверка на победу
    public static boolean checkForWinS(char[][] gameField) {
        System.out.println("Game field before checkForWinS" + Arrays.deepToString(gameField));

        // Проверка победы по строкам
        for (int i = 0; i < Constants.FIELD_SIZE; i++) {
            if (gameField[i][0] == gameField[i][1] &&
                    gameField[i][0] == gameField[i][2] &&
                    (gameField[i][0] == Constants.X_SYMBOL || gameField[i][0] == Constants.O_SYMBOL)) {
                System.out.println("Winning condition met: row " + i);
                return true;
            }
        }

        // Проверка победы по столбцам
        for (int i = 0; i < Constants.FIELD_SIZE; i++) {
            if (gameField[0][i] == gameField[1][i] &&
                    gameField[0][i] == gameField[2][i] &&
                    (gameField[0][i] == Constants.X_SYMBOL || gameField[0][i] == Constants.O_SYMBOL)) {
                System.out.println("Winning condition met: column " + i);
                return true;
            }
        }

        // Проверка победы по диагоналям
        boolean winResult =  (gameField[0][0] == gameField[1][1] && gameField[0][0] == gameField[2][2] &&
                (gameField[0][0] == Constants.X_SYMBOL || gameField[0][0] == Constants.O_SYMBOL)) ||
                (gameField[0][2] == gameField[1][1] && gameField[0][2] == gameField[2][0]) &&
                        (gameField[0][2] == Constants.X_SYMBOL || gameField[0][2] == Constants.O_SYMBOL);

        if (winResult) {
            System.out.println("Winning condition met: diagonal");
        }
        return winResult;
    }

    // Проверка на ничью
    public static boolean checkForDrawS(char[][] gameField) {

        System.out.println("Game field before checkForDrawS" + Arrays.deepToString(gameField));

        if (checkForWinS(gameField)) {
            // Если уже есть победитель, то игра не закончена в ничью
            return false;
        }


        for (int i = 0; i < Constants.FIELD_SIZE; i++) {
            for (int j = 0; j < Constants.FIELD_SIZE; j++) {
                if (gameField[i][j] == Constants.EMPTY_SYMBOL) {
                    // Найдена пустая ячейка, игра не закончилась вничью
                    return false;
                }
            }
        }
        return true; // Все ячейки заполнены, ничья
    }




    public static boolean isWinningMove(char[][] gameField, int row, int col, char symbol) {
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