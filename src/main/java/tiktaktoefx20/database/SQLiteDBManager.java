package tiktaktoefx20.database;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLiteDBManager {

    public static final String DB_URL = "jdbc:sqlite:TTTFX 2.0.db";
    private static final Logger LOGGER = Logger.getLogger(SQLiteDBManager.class.getName());

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void addGame(List<GameMove> moves, int totalMoves, int playerMoves, int computerMoves, String result, int duration, String level) {
        createTables();

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO games (total_moves, player_moves, computer_moves, result, duration, level, game_state) VALUES (?, ?, ?, ?, ?, ?, ?)")) {

            conn.setAutoCommit(false);

            for (GameMove move : moves) {
                recordMove(conn, move.moveNumber(), move.player(), move.row(), move.col());
            }

            pstmt.setInt(1, totalMoves);
            pstmt.setInt(2, playerMoves);
            pstmt.setInt(3, computerMoves);
            pstmt.setString(4, result);
            pstmt.setInt(5, duration);
            pstmt.setString(6, level);
            pstmt.setString(7, serializeGameState(moves)); // Serialize game state and store it

            pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding game to database", e);
        }
    }



    private static void recordMove(Connection conn, int moveNumber, String player, int row, int col) {
        try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO moves (move_number, player, row, col) VALUES (?, ?, ?, ?)")) {
            pstmt.setInt(1, moveNumber);
            pstmt.setString(2, player);
            pstmt.setInt(3, row);
            pstmt.setInt(4, col);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error recording move in database", e);
        }
    }

    private static void createTables() {
        String gamesTableSQL = "CREATE TABLE IF NOT EXISTS games (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "total_moves INTEGER," +
                "player_moves INTEGER," +
                "computer_moves INTEGER," +
                "result TEXT," +
                "duration INTEGER," +
                "level TEXT," +
                "game_state TEXT" + // Добавляем колонку для хранения состояния игры
                ")";

        String gameMovesTableSQL = "CREATE TABLE IF NOT EXISTS moves (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "move_number INTEGER," +
                "player TEXT," +
                "row INTEGER," +
                "col INTEGER" +
                ")";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(gamesTableSQL);
            stmt.executeUpdate(gameMovesTableSQL);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error creating tables", e);
        }
    }


    private static String serializeGameState(List<GameMove> moves) {
        StringBuilder serializedState = new StringBuilder();
        for (GameMove move : moves) {
            serializedState.append(move.player());
            serializedState.append(move.row());
            serializedState.append(move.col());
        }
        return serializedState.toString();
    }


    public static int getTotalGames() {
        String sql = "SELECT COUNT(*) FROM games";
        int totalGames = 0;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                totalGames = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting total games", e);
        }

        return totalGames;
    }

    public static int getLongestGameDuration() {
        String sql = "SELECT MAX(duration) FROM games";
        int longestDuration = 0;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                longestDuration = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting longest game duration", e);
        }

        return longestDuration;
    }


    // Общее игровое время
    public static int getTotalGameDuration() {
        String sql = "SELECT SUM(duration) FROM games";
        int totalDuration = 0;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                totalDuration = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting total game duration", e);
        }

        return totalDuration;
    }

    // Самая короткая игра (в секундах и ходах)
    public static int[] getShortestGame() {
        String sql = "SELECT MIN(duration), MIN(total_moves) FROM games";
        int shortestDuration = 0;
        int shortestMoves = 0;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                shortestDuration = rs.getInt(1);
                shortestMoves = rs.getInt(2);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting shortest game", e);
        }

        return new int[]{shortestDuration, shortestMoves};
    }

    // Количество побед всего
    public static int getTotalWins() {
        String sql = "SELECT COUNT(*) FROM games WHERE result = 'Win'";
        int totalWins = 0;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                totalWins = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting total wins", e);
        }

        return totalWins;
    }

    // Количество побед каждого игрока
    public static int getPlayerWins(String player) {
        String sql = "SELECT COUNT(*) FROM games WHERE result = 'Win' AND player = ?";
        int playerWins = 0;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, player);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                playerWins = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting player wins", e);
        }

        return playerWins;
    }

    // Получение всех выигрышных состояний из базы данных
    public static List<char[][]> getWinningGameStates() {
        List<char[][]> winningStates = new ArrayList<>();

        String sql = "SELECT game_state FROM games WHERE result LIKE '%wins%'";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String serializedState = rs.getString("game_state");
                char[][] gameField = deserializeGameState(serializedState);
                winningStates.add(gameField);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting winning game states", e);
        }

        return winningStates;
    }


    // Десериализация игрового состояния из строки в двумерный массив
    private static char[][] deserializeGameState(String serializedState) {
        char[][] gameField = new char[3][3];
        int index = 0;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                gameField[row][col] = serializedState.charAt(index++);
            }
        }
        return gameField;
    }

    // Метод для получения номера игры из базы данных
    public static int getGameIdFromDatabase() {
        String sql = "SELECT MAX(id) FROM games"; // Получаем максимальный идентификатор игры
        int gameId = 0;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            // Проверяем наличие таблицы "games" в базе данных
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet tableResultSet = meta.getTables(null, null, "games", null);
            if (tableResultSet.next()) { // Если таблица существует
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    gameId = rs.getInt(1); // Получаем значение максимального идентификатора
                }
            }
            else {
                LOGGER.log(Level.WARNING, "Table 'games' does not exist in the database."); // Логируем предупреждение
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting game ID from database", e);
        }

        return gameId;
    }



}
