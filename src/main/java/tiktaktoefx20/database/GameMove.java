package tiktaktoefx20.database;

import java.sql.*;

import static tiktaktoefx20.database.SQLiteDBManager.DB_URL;

public class GameMove {
    private int moveNumber;
    private String player;
    private String coordinates;

    public GameMove(int moveNumber, String player, String coordinates) {
        this.moveNumber = moveNumber;
        this.player = player;
        this.coordinates = coordinates;
    }


    private static final String INSERT_MOVE_SQL = "INSERT INTO moves (game_id, player, row, col) VALUES (?, ?, ?, ?)";

    // Метод для записи хода игрока или компьютера в базу данных
    public static void recordGameMove(int moveNumber, String player, int row, int col) {
        // Проверяем наличие таблицы game_moves
        checkOrCreateGameMovesTable();

        // SQL-запрос для записи хода в таблицу game_moves
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

    // Метод для проверки наличия таблицы game_moves и ее создания, если она отсутствует
    private static void checkOrCreateGameMovesTable() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet tables = meta.getTables(null, null, "game_moves", null);
            if (!tables.next()) {
                // Таблица не существует, создаем ее
                Statement statement = conn.createStatement();
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS game_moves (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "move_number INTEGER," +
                        "player TEXT," +
                        "row INTEGER," +
                        "col INTEGER" +
                        ")");
                statement.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    // Геттеры и сеттеры


}