package tiktaktoefx20;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CenterAndCornersMoveStrategyTest {

    @Test
    public void testMakeMove() {
        // Создаем экземпляр игрового поля для тестирования
        int[][] gameField = {
                { 'X', 'O', 'X' },
                { 'O', ' ', 'O' },
                { ' ', ' ', 'X' }
        };

        // Создаем экземпляр TTTGameLogic
        TTTGameLogic gameLogic = new TTTGameLogic();

        // Создаем экземпляр стратегии для центра и углов
        CenterAndCornersMoveStrategy centerAndCornersStrategy = new CenterAndCornersMoveStrategy(gameLogic);

        // Выполняем ход с помощью стратегии
        int[] move = centerAndCornersStrategy.makeMove(gameField, 'X');

        // Проверяем, что ход сделан в центральную клетку
        assertEquals(1, move[0]);
        assertEquals(1, move[1]);
    }
}
