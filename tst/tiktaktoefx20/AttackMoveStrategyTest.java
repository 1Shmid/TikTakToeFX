package tiktaktoefx20;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AttackMoveStrategyTest {

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

        // Создаем экземпляр стратегии атаки
        AttackMoveStrategy attackStrategy = new AttackMoveStrategy(gameLogic);

        // Выполняем ход с помощью стратегии
        int[] move = attackStrategy.makeMove(gameField, 'X');

        // Проверяем, что ход сделан в свободную клетку
        assertEquals(1, move[0]);
        assertEquals(1, move[1]);
    }
}
