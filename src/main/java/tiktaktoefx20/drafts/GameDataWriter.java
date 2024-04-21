package tiktaktoefx20.drafts;

import java.io.*;
import java.util.*;

public class GameDataWriter {

    public void writeSingleMove(int[] move, String[][] gameField, String result) {
        try (FileOutputStream fos = new FileOutputStream("game_data.ser", true);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            // Записываем ход и состояние поля
            oos.writeObject(move); // Записываем один ход
            oos.writeObject(gameField); // Записываем состояние поля
            oos.writeObject(result); // Записываем результат игры
            System.out.println("Move and game state have been written to file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeGameData(List<int[]> moves, List<String[][]> gameStates, List<String> results) {
        try (FileOutputStream fos = new FileOutputStream("game_data.ser");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            // Записываем список ходов, состояний поля и результатов игры
            oos.writeObject(moves); // Записываем список ходов
            oos.writeObject(gameStates); // Записываем список состояний поля
            oos.writeObject(results); // Записываем список результатов игры
            System.out.println("Game data has been written to file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}