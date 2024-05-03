package tiktaktoefx20.database;

import java.util.*;

/**
 * @param moveNumber Этот класс отвечает за хранение ходов
 */
public record GameMove(int moveNumber, String player, int row, int col) {

    private static final List<Object[]> moves = new ArrayList<>();

    public static void addMove(int moveNumber, String player, int row, int col) {
        Object[] move = {moveNumber, player, row, col};
        moves.add(move);
    }

    public static List<Object[]> getMoves() {
        return moves;
    }
}

