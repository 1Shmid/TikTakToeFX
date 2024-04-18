package tiktaktoefx20.database;

import tiktaktoefx20.*;

import java.sql.*;

public class DatabaseManager {
    private Connection connection;

    // Метод для подключения к базе данных SQLite
    public void connect(String dbName) throws SQLException {
        String url = "jdbc:sqlite:" + dbName;
        connection = DriverManager.getConnection(url);
        System.out.println("Connected to SQLite database.");
    }

    // Метод для создания таблицы в базе данных
    public void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS GameRecords (\n"
                + "    gameNumber INTEGER PRIMARY KEY,\n"
                + "    player VARCHAR(50),\n"
                + "    coordinates VARCHAR(50),\n"
                + "    result CHAR(1),\n"
                + "    moveCount INTEGER,\n"
                + "    durationSeconds INTEGER\n"
                + ");";
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
            System.out.println("Table created successfully.");
        }
    }

    // Метод для добавления записи об игре в таблицу
    public void insertGameRecord(GameRecord gameRecord) throws SQLException {
        String sql = "INSERT INTO GameRecords (gameNumber, player, coordinates, result, moveCount, durationSeconds) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, gameRecord.getGameNumber());
            preparedStatement.setString(2, gameRecord.getPlayer());
            preparedStatement.setString(3, gameRecord.getCoordinates());
            preparedStatement.setString(4, String.valueOf(gameRecord.getResult()));
            preparedStatement.setInt(5, gameRecord.getMoveCount());
            preparedStatement.setInt(6, gameRecord.getDurationSeconds());
            preparedStatement.executeUpdate();
            System.out.println("Game record inserted successfully.");
        }
    }

    // Другие методы для работы с базой данных (например, получение всех записей, удаление записей и т.д.)
}