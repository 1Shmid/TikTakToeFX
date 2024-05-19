package tiktaktoefx20.database;

import java.util.*;

/**
 * Класс хранит игровые ходы Class stores game moves
 */
public record GameMoveRecorder(int moveNumber, String player, int row, int col) {
	
	private static final List<Object[]> moves = new ArrayList<>();
	
	public static void recordMove(int moveNumber, String player, int row, int col) {
		Object[] move = {moveNumber, player, row, col};
		moves.add(move);
	}
	
	public static List<Object[]> getMoves() {
		return moves;
	}
}

