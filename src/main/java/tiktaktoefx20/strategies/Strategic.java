package tiktaktoefx20.strategies;

import tiktaktoefx20.model.GameParams;

/**
 * Класс определяет интерфейс стратегии
 * <p>
 * Defines the strategy interface
 */

public interface Strategic {
	
	int[] makeMove(GameParams params);
}