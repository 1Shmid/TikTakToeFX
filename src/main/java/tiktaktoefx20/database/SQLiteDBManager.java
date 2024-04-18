package tiktaktoefx20.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteDBManager {
    private Connection connection;

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

    public Connection getConnection() {
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
}