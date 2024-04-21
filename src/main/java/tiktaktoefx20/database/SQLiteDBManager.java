package tiktaktoefx20.database;

import java.sql.*;
import java.util.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static tiktaktoefx20.database.GameMove.*;


public class SQLiteDBManager {

    static final String DB_URL = "jdbc:sqlite:game_database.db";

    public static void addGame(List<GameMove> moves, int totalMoves, int playerMoves, int computerMoves, String result, int duration) {
        createGamesTable();
        createGameMovesTable();

        for (GameMove move : moves) {
            recordMove(move.getMoveNumber(), move.getPlayer(), move.getRow(), move.getCol());
        }

        String sql = "INSERT INTO games (total_moves, player_moves, computer_moves, result, duration) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, totalMoves);
            pstmt.setInt(2, playerMoves);
            pstmt.setInt(3, computerMoves);
            pstmt.setString(4, result);
            pstmt.setInt(5, duration);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Метод для создания таблицы games, если она не существует
    private static void createGamesTable() {
        String sql = "CREATE TABLE IF NOT EXISTS games (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "total_moves INTEGER," +
                "player_moves INTEGER," +
                "computer_moves INTEGER," +
                "result TEXT," +
                "duration INTEGER" +
                ")";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Метод для создания таблицы game_moves, если она не существует
    private static void createGameMovesTable() {
        String sql = "CREATE TABLE IF NOT EXISTS game_moves (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "move_number INTEGER," +
                "player TEXT," +
                "row INTEGER," +
                "col INTEGER" +
                ")";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Метод для записи хода в таблицу game_moves
    private static void recordMove(int moveNumber, String player, int row, int col) {
        String sql = "INSERT INTO game_moves (move_number, player, row, col) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, moveNumber);
            pstmt.setString(2, player);
            pstmt.setInt(3, row);
            pstmt.setInt(4, col);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
