package tiktaktoefx20.drafts;

import java.io.*;
import java.util.*;

public class GameDataReader {

    public static List<int[]> readGameData() {
        List<int[]> moves = null;
        try (FileInputStream fis = new FileInputStream("game_data.ser");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            moves = (List<int[]>) ois.readObject(); // Читаем список ходов
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return moves;
    }

    public static List<String[][]> readGameStates() {
        List<String[][]> gameStates = null;
        try (FileInputStream fis = new FileInputStream("game_data.ser");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            ois.readObject(); // Пропускаем список ходов
            gameStates = (List<String[][]>) ois.readObject(); // Читаем список состояний поля
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return gameStates;
    }

    public static List<String> readResults() {
        List<String> results = null;
        try (FileInputStream fis = new FileInputStream("game_data.ser");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            ois.readObject(); // Пропускаем список ходов
            ois.readObject(); // Пропускаем список состояний поля
            results = (List<String>) ois.readObject(); // Читаем список результатов игры
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return results;
    }
}
