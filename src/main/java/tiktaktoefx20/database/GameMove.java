package tiktaktoefx20.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static tiktaktoefx20.database.SQLiteDBManager.DB_URL;

public class GameMove {

    /*
    Этот класс отвечает за хранение ходов
     */
    private int moveNumber;
    private String player;
    private int row;
    private int col;
    private static List<Object[]> moves = new ArrayList<>();

    public GameMove(int moveNumber, String player, int row, int col) {
        this.moveNumber = moveNumber;
        this.player = player;
        this.row = row;
        this.col = col;
    }

    public static void addMove(int moveNumber, String player, int row, int col) {
        Object[] move = {moveNumber, player, row, col};
        moves.add(move);
    }

    public static List<Object[]> getMoves() {
        return moves;
    }

    public void clearMoves() {
        moves.clear();
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    public String getPlayer() {
        return player;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }


}








//    private int moveNumber;
//    private String player;
//    private int row;
//    private int col;
//
//    public GameMove(int moveNumber, String player, int row, int col) {
//        this.moveNumber = moveNumber;
//        this.player = player;
//        this.row = row;
//        this.col = col;
//    }
//
//
//
//    // Метод для записи хода игрока или компьютера в базу данных
//    public static void recordMove(int moveNumber, String player, int row, int col) {
//        // Проверяем наличие таблицы game_moves
//        checkOrCreateGameMovesTable();
//
//        // SQL-запрос для записи хода в таблицу game_moves
//        String sql = "INSERT INTO game_moves (move_number, player, row, col) VALUES (?, ?, ?, ?)";
//
//        try (Connection conn = DriverManager.getConnection(DB_URL);
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//            pstmt.setInt(1, moveNumber);
//            pstmt.setString(2, player);
//            pstmt.setInt(3, row);
//            pstmt.setInt(4, col);
//
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    // Метод для проверки наличия таблицы game_moves и ее создания, если она отсутствует
//    private static void checkOrCreateGameMovesTable() {
//        try (Connection conn = DriverManager.getConnection(DB_URL)) {
//            DatabaseMetaData meta = conn.getMetaData();
//            ResultSet tables = meta.getTables(null, null, "game_moves", null);
//            if (!tables.next()) {
//                // Таблица не существует, создаем ее
//                Statement statement = conn.createStatement();
//                statement.executeUpdate("CREATE TABLE IF NOT EXISTS game_moves (" +
//                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
//                        "move_number INTEGER," +
//                        "player TEXT," +
//                        "row INTEGER," +
//                        "col INTEGER" +
//                        ")");
//                statement.close();
//            }
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//
//    public static List<GameMove> readGameMoves() {
//        List<GameMove> gameMoves = new ArrayList<>();
//
//        // SQL запрос для выборки всех ходов из таблицы game_moves
//        String sql = "SELECT * FROM game_moves";
//
//        try (Connection conn = DriverManager.getConnection(DB_URL);
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(sql)) {
//
//            // Читаем результаты запроса и создаем объекты GameMove
//            while (rs.next()) {
//                int moveNumber = rs.getInt("move_number");
//                String player = rs.getString("player");
//                int row = rs.getInt("row");
//                int col = rs.getInt("col");
//
//                GameMove gameMove = new GameMove(moveNumber, player, row, col);
//                gameMoves.add(gameMove);
//
//                // Выводим информацию о каждом ходе
//                System.out.println("Move Number: " + moveNumber);
//                System.out.println("Player: " + player);
//                System.out.println("Row: " + row);
//                System.out.println("Column: " + col);
//                System.out.println("-------------------");
//            }
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//
//        return gameMoves;
//    }
//
//    // Геттеры и сеттеры
//
//
//    public int getMoveNumber() {
//        return moveNumber;
//    }
//
//    public String getPlayer() {
//        return player;
//    }
//
//    public int getRow() {
//        return row;
//    }
//
//    public int getCol() {
//        return col;
//    }
