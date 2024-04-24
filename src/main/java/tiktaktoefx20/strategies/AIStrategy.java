package tiktaktoefx20.strategies;

import tiktaktoefx20.*;

import java.util.*;

import static tiktaktoefx20.database.SQLiteDBManager.getWinningGameStates;


public class AIStrategy implements MoveStrategy {

    @Override
    public int[] makeMove(char[][] gameField, String selectedLevel) {
        List<char[][]> winningGameStates = getWinningGameStates();
        List<int[]> possibleMoves = new ArrayList<>();

        for (char[][] winningGameState : winningGameStates) {
            int[] move = compareGameFields(gameField, winningGameState);
            if (move != null) {
                possibleMoves.add(move);
            }
        }

        if (!possibleMoves.isEmpty()) {
            return chooseBestMove(possibleMoves, gameField);
        }

        return new HardStrategy().makeMove(gameField, selectedLevel);
    }

    private int[] compareGameFields(char[][] currentGameState, char[][] winningGameState) {
        int bestRow = -1;
        int bestCol = -1;
        int maxMatches = -1;

        for (int row = 0; row < currentGameState.length; row++) {
            for (int col = 0; col < currentGameState[0].length; col++) {
                if (currentGameState[row][col] == Constants.EMPTY_SYMBOL && currentGameState[row][col] == winningGameState[row][col]) {
                    int matches = countMatches(currentGameState, winningGameState, row, col);
                    if (matches > maxMatches) {
                        maxMatches = matches;
                        bestRow = row;
                        bestCol = col;
                    }
                }
            }
        }

        return (maxMatches > 0) ? new int[]{bestRow, bestCol} : null;
    }

    private int countMatches(char[][] currentGameState, char[][] winningGameState, int row, int col) {
        int matches = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (isValidPosition(row + i, col + j, currentGameState.length, currentGameState[0].length) &&
                        currentGameState[row + i][col + j] == winningGameState[row + i][col + j]) {
                    matches++;
                }
            }
        }
        return matches;
    }

    private boolean isValidPosition(int row, int col, int numRows, int numCols) {
        return row >= 0 && row < numRows && col >= 0 && col < numCols;
    }

    private int[] chooseBestMove(List<int[]> possibleMoves, char[][] gameField) {
        sortMovesByPriority(possibleMoves, gameField); // Передаем оба аргумента
        return possibleMoves.get(0);
    }


    private void sortMovesByPriority(List<int[]> possibleMoves, char[][] gameField) {
        List<Map.Entry<int[], Integer>> entryList = new ArrayList<>();
        Map<int[], Integer> movePriorityMap = new HashMap<>();

        for (int[] move : possibleMoves) {
            int priority = calculatePriority(gameField, move);
            movePriorityMap.put(move, priority);
        }

        entryList.addAll(movePriorityMap.entrySet());
        entryList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        possibleMoves.clear();
        for (Map.Entry<int[], Integer> entry : entryList) {
            possibleMoves.add(entry.getKey());
        }
    }

    private int calculatePriority(char[][] gameField, int[] move) {
        int priority = 0;
        List<char[][]> winningGameStates = getWinningGameStates();

        for (char[][] winningGameState : winningGameStates) {
            int matches = compareWithWinningState(gameField, move, winningGameState);
            priority += matches;
        }

        return priority;
    }


    private int compareWithWinningState(char[][] gameField, int[] move, char[][] winningGameState) {
        int matches = 0;

        for (int row = 0; row < gameField.length; row++) {
            for (int col = 0; col < gameField[0].length; col++) {
                // Сравниваем каждую клетку текущего игрового поля с соответствующей клеткой выигрышного состояния
                if (gameField[row][col] == winningGameState[row][col]) {
                    matches++;
                }
            }
        }

        // Увеличиваем количество совпадений, если ход совпадает с выигрышным состоянием
        if (gameField[move[0]][move[1]] == winningGameState[move[0]][move[1]]) {
            matches++;
        }

        return matches;
    }

}
