package tiktaktoefx20.drafts;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import java.util.Collections;

import java.util.HashMap;
import java.util.Map;


public class GameHistory {

    // Этот класс будет представлять историю игр
//    private List<GameRecord> gameRecords;
//    private List<String> currentGameMoves;
//    private char gameResult;
//
//    public GameHistory() {
//        this.gameRecords = new ArrayList<>();
//        currentGameMoves = new ArrayList<>();
//    }
//
//    // Метод для добавления записи об игре в историю
//    public void addGameRecord(GameRecord gameRecord) {
//        gameRecords.add(gameRecord);
//    }
//
//    // ----------------------------------------------
//
//
//
//    // Метод для сохранения игры в файл
//    public void saveGameToFile(String fileName) {
//        try (FileWriter writer = new FileWriter(fileName)) {
//            // Записываем все ходы игры
//            for (String move : currentGameMoves) {
//                writer.write(move + "\n");
//            }
//            // Записываем результат игры
//            writer.write("Game result: " + gameResult + "\n");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Метод для добавления хода игры и результата в массив
//    public void addMoveToCurrentGame(String move, char result) {
//        currentGameMoves.add(move);
//        gameResult = result;
//    }
//
//    // Метод для анализа наиболее часто используемых ходов во всех играх
//    public String getMostFrequentMove() {
//        Map<String, Integer> moveCounts = new HashMap<>();
//        for (GameRecord gameRecord : gameRecords) {
//            List<String> moves = gameRecord.getAllMoves();
//            for (String move : moves) {
//                moveCounts.put(move, moveCounts.getOrDefault(move, 0) + 1);
//            }
//        }
//        return Collections.max(moveCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
//    }
//
//    // Метод для анализа наиболее часто используемых ходов в ничейных партиях
//    public String getMostFrequentDrawMove() {
//        Map<String, Integer> drawMoveCounts = new HashMap<>();
//        for (GameRecord gameRecord : gameRecords) {
//            if (gameRecord.getWinner() == Constants.EMPTY_SYMBOL) {
//                List<String> moves = gameRecord.getAllMoves();
//                for (String move : moves) {
//                    drawMoveCounts.put(move, drawMoveCounts.getOrDefault(move, 0) + 1);
//                }
//            }
//        }
//        return Collections.max(drawMoveCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
//    }
//
//    // Метод для анализа наиболее часто используемых открытых ходов в победных партиях
//    public String getMostFrequentWinningOpenMove() {
//        Map<String, Integer> winningOpenMoveCounts = new HashMap<>();
//        for (GameRecord gameRecord : gameRecords) {
//            if (gameRecord.getWinner() == Constants.X_SYMBOL) {
//                List<String> moves = gameRecord.getAllMoves();
//                for (String move : moves) {
//                    if (!isBlockingMove(move)) {
//                        winningOpenMoveCounts.put(move, winningOpenMoveCounts.getOrDefault(move, 0) + 1);
//                    }
//                }
//            }
//        }
//        return Collections.max(winningOpenMoveCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
//    }
//
//    // Метод для определения, является ли ход блокирующим
//    private boolean isBlockingMove(String move) {
//        // Здесь ваша логика определения блокирующего хода
//        return false;
//    }
//
//    // Метод для анализа наиболее часто используемых блокирующих ходов в поражениях
//    public String getMostFrequentBlockingMove() {
//        Map<String, Integer> blockingMoveCounts = new HashMap<>();
//        for (GameRecord gameRecord : gameRecords) {
//            if (gameRecord.getWinner() == Constants.O_SYMBOL) {
//                List<String> moves = gameRecord.getAllMoves();
//                for (String move : moves) {
//                    blockingMoveCounts.put(move, blockingMoveCounts.getOrDefault(move, 0) + 1);
//                }
//            }
//        }
//        return Collections.max(blockingMoveCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
//    }
//
//    // Метод для анализа наиболее часто используемых ходов в победных партиях
//    public String getMostFrequentWinningMove() {
//        Map<String, Integer> winningMoveCounts = new HashMap<>();
//        for (GameRecord gameRecord : gameRecords) {
//            if (gameRecord.getWinner() == Constants.X_SYMBOL) {
//                List<String> moves = gameRecord.getAllMoves();
//                for (String move : moves) {
//                    winningMoveCounts.put(move, winningMoveCounts.getOrDefault(move, 0) + 1);
//                }
//            }
//        }
//        return Collections.max(winningMoveCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
//    }
//
//    // Метод для анализа наиболее эффективных стратегий в победных партиях
//    public String getMostEffectiveWinningStrategy() {
//        Map<String, Integer> winningStrategyCounts = new HashMap<>();
//        for (GameRecord gameRecord : gameRecords) {
//            if (gameRecord.getWinner() == Constants.X_SYMBOL) {
//                String strategy = gameRecord.getStrategy();
//                winningStrategyCounts.put(strategy, winningStrategyCounts.getOrDefault(strategy, 0) + 1);
//            }
//        }
//        return Collections.max(winningStrategyCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
//    }
//
//    // Метод для расчета процента побед
//    public double getWinPercentage() {
//        int totalGames = getTotalGameCount();
//        if (totalGames == 0) {
//            return 0.0;
//        }
//        return (double) getWinCount() / totalGames * 100;
//    }
//
//    // Метод для расчета процента поражений
//    public double getLossPercentage() {
//        int totalGames = getTotalGameCount();
//        if (totalGames == 0) {
//            return 0.0;
//        }
//        return (double) getLossCount() / totalGames * 100;
//    }
//
//    // Метод для расчета процента ничьих
//    public double getDrawPercentage() {
//        int totalGames = getTotalGameCount();
//        if (totalGames == 0) {
//            return 0.0;
//        }
//        return (double) getDrawCount() / totalGames * 100;
//    }
//
//    // Метод для получения количества побед
//    public int getWinCount() {
//        int winCount = 0;
//        for (GameRecord gameRecord : gameRecords) {
//            if (gameRecord.getWinner() == Constants.X_SYMBOL) {
//                winCount++;
//            }
//        }
//        return winCount;
//    }
//
//    // Метод для получения количества поражений
//    public int getLossCount() {
//        int lossCount = 0;
//        for (GameRecord gameRecord : gameRecords) {
//            if (gameRecord.getWinner() == Constants.O_SYMBOL) {
//                lossCount++;
//            }
//        }
//        return lossCount;
//    }
//
//    // Метод для получения количества ничьих
//    public int getDrawCount() {
//        int drawCount = 0;
//        for (GameRecord gameRecord : gameRecords) {
//            if (gameRecord.getWinner() == Constants.EMPTY_SYMBOL) {
//                drawCount++;
//            }
//        }
//        return drawCount;
//    }
//
//    // Метод для получения общего количества записей об играх
//    public int getTotalGameCount() {
//        return gameRecords.size();
//    }
//
//    // Метод для сохранения истории игр в файл
//    public void saveHistoryToFile(String fileName) {
//        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
//            outputStream.writeObject(gameRecords);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Метод для загрузки истории игр из файла
//    public void loadHistoryFromFile(String fileName) {
//        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
//            gameRecords = (List<GameRecord>) inputStream.readObject();
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Метод для анализа наиболее эффективных стратегий
//    public String getMostEffectiveStrategy() {
//        Map<String, Integer> strategyCounts = new HashMap<>();
//        for (GameRecord gameRecord : gameRecords) {
//            if (gameRecord.getWinner() != Constants.EMPTY_SYMBOL) {
//                String strategy = gameRecord.getStrategy();
//                strategyCounts.put(strategy, strategyCounts.getOrDefault(strategy, 0) + 1);
//            }
//        }
//        return Collections.max(strategyCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
//    }
//
//
//    // Метод для анализа наиболее часто используемых ходов во всех играх
//    public String getMostFrequentMove() {
//        Map<String, Integer> moveCounts = new HashMap<>();
//        for (GameRecord gameRecord : gameRecords) {
//            List<String> moves = gameRecord.getAllMoves();
//            for (String move : moves) {
//                moveCounts.put(move, moveCounts.getOrDefault(move, 0) + 1);
//            }
//        }
//        return Collections.max(moveCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
//    }
//
//    // Метод для анализа наиболее часто используемых ходов в ничейных партиях
//    public String getMostFrequentDrawMove() {
//        Map<String, Integer> drawMoveCounts = new HashMap<>();
//        for (GameRecord gameRecord : gameRecords) {
//            if (gameRecord.getWinner() == Constants.EMPTY_SYMBOL) {
//                List<String> drawMoves = gameRecord.getDrawMoves();
//                for (String drawMove : drawMoves) {
//                    drawMoveCounts.put(drawMove, drawMoveCounts.getOrDefault(drawMove, 0) + 1);
//                }
//            }
//        }
//        return Collections.max(drawMoveCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
//    }
//
//    // Метод для анализа наиболее эффективных ответных ходов
//    public String getMostEffectiveResponseMove() {
//        Map<String, Integer> responseMoveCounts = new HashMap<>();
//        for (GameRecord gameRecord : gameRecords) {
//            if (gameRecord.getWinner() != Constants.EMPTY_SYMBOL) {
//                List<String> responseMoves = gameRecord.getResponseMoves();
//                for (String responseMove : responseMoves) {
//                    responseMoveCounts.put(responseMove, responseMoveCounts.getOrDefault(responseMove, 0) + 1);
//                }
//            }
//        }
//        return Collections.max(responseMoveCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
//    }
//
//    // Метод для анализа наиболее эффективных блокировок
//    public String getMostEffectiveBlockMove() {
//        Map<String, Integer> blockMoveCounts = new HashMap<>();
//        for (GameRecord gameRecord : gameRecords) {
//            if (gameRecord.getWinner() == Constants.EMPTY_SYMBOL) {
//                List<String> blockMoves = gameRecord.getBlockMoves();
//                for (String blockMove : blockMoves) {
//                    blockMoveCounts.put(blockMove, blockMoveCounts.getOrDefault(blockMove, 0) + 1);
//                }
//            }
//        }
//        return Collections.max(blockMoveCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
//    }
//
//    // Метод для анализа наиболее эффективных ходов в середине игры
//    public String getMostEffectiveMiddleMove() {
//        Map<String, Integer> middleMoveCounts = new HashMap<>();
//        for (GameRecord gameRecord : gameRecords) {
//            if (gameRecord.getWinner() != Constants.EMPTY_SYMBOL && gameRecord.getMoveCount() > 3 && gameRecord.getMoveCount() <= Constants.FIELD_SIZE - 2) {
//                String middleMove = gameRecord.getMiddleMove();
//                middleMoveCounts.put(middleMove, middleMoveCounts.getOrDefault(middleMove, 0) + 1);
//            }
//        }
//        return Collections.max(middleMoveCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
//    }
//
//    // Метод для анализа наиболее эффективных конечных ходов
//    public String getMostEffectiveFinalMove() {
//        Map<String, Integer> finalMoveCounts = new HashMap<>();
//        for (GameRecord gameRecord : gameRecords) {
//            if (gameRecord.getWinner() != Constants.EMPTY_SYMBOL && gameRecord.getMoveCount() > Constants.FIELD_SIZE - 2) {
//                String finalMove = gameRecord.getFinalMove();
//                finalMoveCounts.put(finalMove, finalMoveCounts.getOrDefault(finalMove, 0) + 1);
//            }
//        }
//        return Collections.max(finalMoveCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
//    }
//
//
//    // Метод для анализа наиболее эффективных начальных ходов
//    public String getMostEffectiveInitialMove() {
//        Map<String, Integer> initialMoveCounts = new HashMap<>();
//        for (GameRecord gameRecord : gameRecords) {
//            if (gameRecord.getWinner() != Constants.EMPTY_SYMBOL && gameRecord.getMoveCount() <= 3) {
//                String initialMove = gameRecord.getInitialMove();
//                initialMoveCounts.put(initialMove, initialMoveCounts.getOrDefault(initialMove, 0) + 1);
//            }
//        }
//        return Collections.max(initialMoveCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
//    }
//
//    // Метод для анализа наиболее частых ошибок игроков
//    public String getMostFrequentMistake() {
//        Map<String, Integer> mistakeCounts = new HashMap<>();
//        for (GameRecord gameRecord : gameRecords) {
//            if (gameRecord.getWinner() == Constants.EMPTY_SYMBOL) {
//                List<String> mistakes = gameRecord.getMistakes();
//                for (String mistake : mistakes) {
//                    mistakeCounts.put(mistake, mistakeCounts.getOrDefault(mistake, 0) + 1);
//                }
//            }
//        }
//        return Collections.max(mistakeCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
//    }
//
//    // Метод для анализа наиболее часто используемых блокировок
//    public String getMostFrequentBlockStrategy() {
//        Map<String, Integer> blockStrategyCounts = new HashMap<>();
//        for (GameRecord gameRecord : gameRecords) {
//            if (gameRecord.getWinner() == Constants.EMPTY_SYMBOL) {
//                List<String> blockStrategy = gameRecord.getBlockStrategy();
//                blockStrategyCounts.put(blockStrategy.toString(), blockStrategyCounts.getOrDefault(blockStrategy.toString(), 0) + 1);
//            }
//        }
//        return Collections.max(blockStrategyCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
//    }
//
//    // Метод для анализа наиболее популярных стратегий победы
//    public String getMostFrequentWinningStrategy() {
//        Map<String, Integer> winningStrategyCounts = new HashMap<>();
//        for (GameRecord gameRecord : gameRecords) {
//            if (gameRecord.getWinner() != Constants.EMPTY_SYMBOL) {
//                List<String> winningStrategy = gameRecord.getWinningStrategy();
//                winningStrategyCounts.put(winningStrategy.toString(), winningStrategyCounts.getOrDefault(winningStrategy.toString(), 0) + 1);
//            }
//        }
//        return Collections.max(winningStrategyCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
//    }
//
//    // Метод для анализа наиболее популярных ходов в различных фазах игры
//    public Map<String, String> getMostFrequentMovesByGamePhase() {
//        Map<String, Integer> initialMoveCounts = new HashMap<>();
//        Map<String, Integer> middleMoveCounts = new HashMap<>();
//        Map<String, Integer> finalMoveCounts = new HashMap<>();
//
//        for (GameRecord gameRecord : gameRecords) {
//            List<String> initialMoves = gameRecord.getInitialMoves();
//            List<String> middleMoves = gameRecord.getMiddleMoves();
//            List<String> finalMoves = gameRecord.getFinalMoves();
//
//            updateMoveCounts(initialMoves, initialMoveCounts);
//            updateMoveCounts(middleMoves, middleMoveCounts);
//            updateMoveCounts(finalMoves, finalMoveCounts);
//        }
//
//        Map<String, String> mostFrequentMoves = new HashMap<>();
//        mostFrequentMoves.put("Initial", getMostFrequentMove(initialMoveCounts));
//        mostFrequentMoves.put("Middle", getMostFrequentMove(middleMoveCounts));
//        mostFrequentMoves.put("Final", getMostFrequentMove(finalMoveCounts));
//
//        return mostFrequentMoves;
//    }
//
//    private void updateMoveCounts(List<String> moves, Map<String, Integer> moveCounts) {
//        for (String move : moves) {
//            moveCounts.put(move, moveCounts.getOrDefault(move, 0) + 1);
//        }
//    }
//
//    private String getMostFrequentMove(Map<String, Integer> moveCounts) {
//        return Collections.max(moveCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
//    }
//
//    // Метод для анализа самого частого финального хода
//    public String getMostFrequentFinalMove() {
//        Map<String, Integer> finalMoveCounts = new HashMap<>();
//        for (GameRecord gameRecord : gameRecords) {
//            String finalMove = gameRecord.getFinalMove();
//            finalMoveCounts.put(finalMove, finalMoveCounts.getOrDefault(finalMove, 0) + 1);
//        }
//        return Collections.max(finalMoveCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
//    }
//
//    // Метод для анализа наиболее популярных начальных ходов
//    public String getMostFrequentInitialMove() {
//        Map<String, Integer> initialMoveCounts = new HashMap<>();
//        for (GameRecord gameRecord : gameRecords) {
//            String initialMove = gameRecord.getInitialMove();
//            initialMoveCounts.put(initialMove, initialMoveCounts.getOrDefault(initialMove, 0) + 1);
//        }
//        return Collections.max(initialMoveCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
//    }
//
//    // Метод для получения среднего числа ходов в игре
//    public double getAverageMoveCount() {
//        if (gameRecords.isEmpty()) {
//            return 0.0;
//        }
//
//        int totalMoveCount = 0;
//        for (GameRecord gameRecord : gameRecords) {
//            totalMoveCount += gameRecord.getMoveCount();
//        }
//
//        return (double) totalMoveCount / gameRecords.size();
//    }
//
//    // Метод для определения наиболее популярной комбинации ходов
//    public String getMostFrequentMoveCombination() {
//        Map<String, Integer> moveCombinationCounts = new HashMap<>();
//        for (GameRecord gameRecord : gameRecords) {
//            List<String> moveCombinations = gameRecord.getMoveCombinations();
//            for (String moveCombination : moveCombinations) {
//                moveCombinationCounts.put(moveCombination, moveCombinationCounts.getOrDefault(moveCombination, 0) + 1);
//            }
//        }
//        return Collections.max(moveCombinationCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
//    }
//
//    // Метод для получения самой длинной игры из истории
//    public GameRecord getLongestGame() {
//        if (gameRecords.isEmpty()) {
//            return null;
//        }
//
//        GameRecord longestGame = gameRecords.get(0);
//        for (GameRecord gameRecord : gameRecords) {
//            if (gameRecord.getDuration() > longestGame.getDuration()) {
//                longestGame = gameRecord;
//            }
//        }
//
//        return longestGame;
//    }
//
//    // Метод для получения средней продолжительности игр
//    public double getAverageGameDuration() {
//        if (gameRecords.isEmpty()) {
//            return 0.0;
//        }
//
//        long totalDuration = 0;
//        for (GameRecord gameRecord : gameRecords) {
//            totalDuration += gameRecord.getDuration();
//        }
//
//        return (double) totalDuration / gameRecords.size();
//    }
//
//    // Метод для получения статистики победителей игр
//    public Map<Character, Integer> getWinnerStatistics() {
//        Map<Character, Integer> winnerCounts = new HashMap<>();
//        for (GameRecord gameRecord : gameRecords) {
//            char winner = gameRecord.getWinner();
//            winnerCounts.put(winner, winnerCounts.getOrDefault(winner, 0) + 1);
//        }
//        return winnerCounts;
//    }
//
//    // Метод для получения наиболее часто встречающегося состояния игрового поля в истории
//    public char[][] getMostFrequentGameField() {
//        Map<String, Integer> gameFieldCounts = new HashMap<>();
//        for (GameRecord gameRecord : gameRecords) {
//            char[][] gameField = gameRecord.getGameField();
//            String gameFieldString = Arrays.deepToString(gameField);
//            gameFieldCounts.put(gameFieldString, gameFieldCounts.getOrDefault(gameFieldString, 0) + 1);
//        }
//        String mostFrequentGameFieldString = Collections.max(gameFieldCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
//        return Arrays.stream(mostFrequentGameFieldString.substring(1, mostFrequentGameFieldString.length() - 1).split(", "))
//                .map(s -> s.charAt(0))
//                .toArray(char[][]::new);
//    }
//
//    // Метод для получения последней игры из истории
//    public GameRecord getLastGame() {
//        if (!gameRecords.isEmpty()) {
//            return gameRecords.get(gameRecords.size() - 1);
//        } else {
//            System.err.println("История игр пуста");
//            return null;
//        }
//    }
//
//    // Метод для удаления конкретной игры из истории по индексу
//    public void removeGame(int index) {
//        if (index >= 0 && index < gameRecords.size()) {
//            gameRecords.remove(index);
//        } else {
//            System.err.println("Недопустимый индекс игры для удаления");
//        }
//    }
//
//    // Метод для получения списка всех игр из истории
//    public List<GameRecord> getAllGames() {
//        return Collections.unmodifiableList(gameRecords);
//    }
//
//    // Метод для получения количества игр в истории
//    public int getGameCount() {
//        return gameRecords.size();
//    }
//
//    // Метод для очистки истории игр
//    public void clearGameHistory() {
//        gameRecords.clear();
//    }
//
//    // Метод для сохранения истории игр в файл
//    public void saveGameHistoryToFile(String fileName) {
//        try (FileOutputStream fos = new FileOutputStream(fileName);
//             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
//            oos.writeObject(gameRecords);
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.err.println("Ошибка при сохранении истории игр в файл: " + e.getMessage());
//        }
//    }
//
//    // Метод для загрузки истории игр из файла
//    public void loadGameHistoryFromFile(String fileName) {
//        try (FileInputStream fis = new FileInputStream(fileName);
//             ObjectInputStream ois = new ObjectInputStream(fis)) {
//            gameRecords = (List<GameRecord>) ois.readObject();
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//            System.err.println("Ошибка при загрузке истории игр из файла: " + e.getMessage());
//        }
//    }
//
//    // Метод для поиска похожих игр
//    public List<GameRecord> findSimilarGames(char[][] gameField) {
//        List<GameRecord> similarGames = new ArrayList<>();
//        for (GameRecord gameRecord : gameRecords) {
//            if (areGameFieldsEqual(gameRecord.getGameField(), gameField)) {
//                similarGames.add(gameRecord);
//            }
//        }
//        return similarGames;
//    }
//
//    // Вспомогательный метод для сравнения игровых полей
//    private boolean areGameFieldsEqual(char[][] gameField1, char[][] gameField2) {
//        for (int i = 0; i < gameField1.length; i++) {
//            for (int j = 0; j < gameField1[i].length; j++) {
//                if (gameField1[i][j] != gameField2[i][j]) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }


}