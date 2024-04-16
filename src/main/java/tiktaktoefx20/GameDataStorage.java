package tiktaktoefx20;
import java.io.*;
import java.util.List;

public class GameDataStorage {
    private static final String FILENAME = "game_data.ser";

//    // Метод для сохранения данных об играх на диск
//    public static void saveGameData(List<GameData> gameDataList) {
//        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
//            oos.writeObject(gameDataList);
//            System.out.println("Game data saved successfully.");
//        } catch (IOException e) {
//            System.err.println("Error saving game data: " + e.getMessage());
//        }
//    }
//
//    // Метод для загрузки данных об играх с диска
//    public static List<GameData> loadGameData() {
//        List<GameData> gameDataList = null;
//        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILENAME))) {
//            gameDataList = (List<GameData>) ois.readObject();
//            System.out.println("Game data loaded successfully.");
//        } catch (IOException | ClassNotFoundException e) {
//            System.err.println("Error loading game data: " + e.getMessage());
//        }
//        return gameDataList;
//    }
}

