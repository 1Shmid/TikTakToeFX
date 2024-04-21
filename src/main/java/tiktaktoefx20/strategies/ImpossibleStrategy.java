package tiktaktoefx20.strategies;

import tiktaktoefx20.database.*;

import tiktaktoefx20.*;

import java.util.List;
import java.util.Random;

import java.util.ArrayList;
import java.util.List;

public class ImpossibleStrategy implements MoveStrategy {

    @Override
    public int[] makeMove(char[][] gameField, String selectedLevel) {
        // Получаем список предыдущих выигрышных состояний игрового поля из базы данных
        List<char[][]> winningGameStates = SQLiteDBManager.getWinningGameStates();
        // Создаем список для хранения возможных ходов
        List<int[]> possibleMoves = new ArrayList<>();

        // Сравниваем текущее игровое поле с каждым предыдущим выигрышным состоянием
        for (char[][] winningGameState : winningGameStates) {
            int[] move = compareGameField(gameField, winningGameState);
            // Если найден возможный ход, добавляем его в список
            if (move != null) {
                possibleMoves.add(move);
            }
        }

        // Если найдены возможные ходы, возвращаем первый из них
        if (!possibleMoves.isEmpty()) {
            return possibleMoves.get(0);
        }

        // Если ни один возможный ход не найден, возвращаем случайный ход
        return new HardStrategy().makeMove(gameField, selectedLevel);
    }

    // Метод для сравнения текущего игрового поля с предыдущим выигрышным состоянием и определения возможного хода

    /* Этот метод сравнивает каждую клетку текущего игрового поля с соответствующей клеткой предыдущего
    выигрышного состояния. Если значение клетки совпадает, увеличивается счетчик совпадений.
    Затем метод выбирает клетку с наибольшим числом совпадений и возвращает ее координаты
    как наиболее вероятный следующий ход.
     */
    private int[] compareGameField(char[][] gameField, char[][] winningGameState) {
        // Создаем переменные для хранения наиболее вероятного следующего хода
        int bestRow = -1;
        int bestCol = -1;
        int maxMatches = -1;

        // Проходим по всем клеткам текущего игрового поля
        for (int row = 0; row < gameField.length; row++) {
            for (int col = 0; col < gameField[0].length; col++) {
                // Если клетка пуста
                if (gameField[row][col] == Constants.EMPTY_SYMBOL) {
                    int matches = 0; // Переменная для подсчета совпадений с предыдущим выигрышным состоянием

                    // Сравниваем значение клетки текущего поля с соответствующей клеткой предыдущего выигрышного состояния
                    if (gameField[row][col] == winningGameState[row][col]) {
                        matches++; // Если значение совпадает, увеличиваем счетчик совпадений
                    }

                    // Если число совпадений больше текущего максимума, обновляем наиболее вероятный следующий ход
                    if (matches > maxMatches) {
                        maxMatches = matches;
                        bestRow = row;
                        bestCol = col;
                    }
                }
            }
        }

        // Если найден наиболее вероятный следующий ход, возвращаем его координаты
        if (maxMatches > 0) {
            return new int[]{bestRow, bestCol};
        }

        // Если ни один ход не найден, возвращаем null
        return null;
    }

}

