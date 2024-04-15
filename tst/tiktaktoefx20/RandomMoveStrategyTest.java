package tiktaktoefx20;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RandomMoveStrategyTest {

    @Test
    public void testMakeMove() {
        // Создаем экземпляр игрового поля для тестирования
        int[][] gameField = {
                { 'X', 'O', 'X' },
                { 'O', ' ', 'O' },
                { ' ', ' ', 'X' }
        };

        // Создаем экземпляр стратегии случайного хода
        RandomMoveStrategy randomStrategy = new RandomMoveStrategy();

        // Выполняем ход с помощью стратегии
        int[] move = randomStrategy.makeMove(gameField, 'X');

        // Проверяем, что ход сделан в свободную клетку
        assertTrue(move[0] >= 0 && move[0] < gameField.length);
        assertTrue(move[1] >= 0 && move[1] < gameField[0].length);
        assertEquals(Constants.EMPTY_SYMBOL, gameField[move[0]][move[1]]);
    }
}