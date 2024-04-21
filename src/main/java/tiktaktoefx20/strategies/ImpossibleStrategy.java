package tiktaktoefx20.strategies;

import tiktaktoefx20.*;
import tiktaktoefx20.database.*;

import java.util.Random;

public class ImpossibleStrategy implements MoveStrategy {
    @Override
    public int[] makeMove(char[][] gameField, String selectedLevel) {
        // Получаем данные о прошлых играх из базы данных
        int totalGames = SQLiteDBManager.getTotalGames();
        int totalWins = SQLiteDBManager.getTotalWins();
        int playerWins = SQLiteDBManager.getPlayerWins("Computer");
        int totalGameDuration = SQLiteDBManager.getTotalGameDuration();
        int[] shortestGame = SQLiteDBManager.getShortestGame();

        // Далее можно использовать эти данные для принятия оптимального решения компьютером
        // Например, на основе статистики побед и продолжительности игр можно разработать алгоритм принятия решения

        // Временный код для примера: делаем случайный ход
        return new EasyStrategy().makeMove(gameField, selectedLevel);
    }
}
