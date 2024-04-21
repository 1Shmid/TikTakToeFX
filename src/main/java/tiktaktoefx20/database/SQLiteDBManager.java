package tiktaktoefx20.database;

import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLiteDBManager {

    public static final String DB_URL = "jdbc:sqlite:TTTFX 2.0.db";
    private static final Logger LOGGER = Logger.getLogger(SQLiteDBManager.class.getName());

    private static Connection connection = null;

    private static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL);
        }
        return connection;
    }

    public static void addGame(List<GameMove> moves, int totalMoves, int playerMoves, int computerMoves, String result, int duration, String level) {
        createGamesTable();
        createGameMovesTable();

        int lastMoveNumber = 0; // Хранит номер последнего хода
        for (GameMove move : moves) {
            recordMove(++lastMoveNumber, move.getPlayer(), move.getRow(), move.getCol());
        }

        String sql = "INSERT INTO games (total_moves, player_moves, computer_moves, result, duration, level) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, totalMoves);
            pstmt.setInt(2, playerMoves);
            pstmt.setInt(3, computerMoves);
            pstmt.setString(4, result);
            pstmt.setInt(5, duration);
            pstmt.setString(6, level);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding game to database", e);
        }
    }

    private static void recordMove(int moveNumber, String player, int row, int col) {
        String sql = "INSERT INTO game_moves (move_number, player, row, col) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, moveNumber);
            pstmt.setString(2, player);
            pstmt.setInt(3, row);
            pstmt.setInt(4, col);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error recording move in database", e);
        }
    }

    private static void createGamesTable() {
        String sql = "CREATE TABLE IF NOT EXISTS games (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "total_moves INTEGER," +
                "player_moves INTEGER," +
                "computer_moves INTEGER," +
                "result TEXT," +
                "duration INTEGER," +
                "level TEXT" +
                ")";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error creating games table", e);
        }
    }

    private static void createGameMovesTable() {
        String sql = "CREATE TABLE IF NOT EXISTS game_moves (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "move_number INTEGER," +
                "player TEXT," +
                "row INTEGER," +
                "col INTEGER" +
                ")";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error creating game_moves table", e);
        }
    }
}
