package tiktaktoefx20.database;

import java.sql.*;
import java.util.*;



public class SQLiteDBManager {

    protected static final String DB_URL = "jdbc:sqlite:tiktaktoe.db";
    private static Connection connection;

    public SQLiteDBManager(String dbName) {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTables() {
        try {
            Statement statement = connection.createStatement();
            // Создаем таблицу для записей об играх
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS GameRecords (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "gameNumber INTEGER," +
                    "player TEXT," +
                    "coordinates TEXT," +
                    "moveNumber INTEGER" +
                    ")");
            // Создаем таблицу для хранения данных о каждой игре
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Games (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "gameNumber INTEGER," +
                    "moves TEXT," +
                    "totalMoves INTEGER," +
                    "playerMoves INTEGER," +
                    "computerMoves INTEGER," +
                    "result TEXT," +
                    "duration INTEGER" +
                    ")");
            // Создаем таблицу для общей статистики всех игр
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS GameStats (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "totalGames INTEGER," +
                    "totalDuration INTEGER," +
                    "longestGame INTEGER," +
                    "shortestGame INTEGER," +
                    "totalWins INTEGER," +
                    "playerWins INTEGER," +
                    "computerWins INTEGER" +
                    ")");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addMove(String player, String coordinates) {
        // Метод для добавления записи о ходе в таблицу Moves
        String sql = "INSERT INTO Moves (Player, Coordinates) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, player);
            pstmt.setString(2, coordinates);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addGame(String moves, int totalMoves, int playerMoves, int computerMoves,
                               String result, int durationSeconds) {
        // Метод для добавления записи об игре в таблицу Games
        String sql = "INSERT INTO Games (Moves, TotalMoves, PlayerMoves, ComputerMoves, Result, DurationSeconds) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, moves);
            pstmt.setInt(2, totalMoves);
            pstmt.setInt(3, playerMoves);
            pstmt.setInt(4, computerMoves);
            pstmt.setString(5, result);
            pstmt.setInt(6, durationSeconds);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getMoves() {
        // Метод для чтения всех ходов из таблицы Moves
        List<String> movesList = new ArrayList<>();
        String sql = "SELECT Player, Coordinates FROM Moves";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String player = rs.getString("Player");
                String coordinates = rs.getString("Coordinates");
                movesList.add("Player: " + player + ", Coordinates: " + coordinates);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movesList;
    }
}