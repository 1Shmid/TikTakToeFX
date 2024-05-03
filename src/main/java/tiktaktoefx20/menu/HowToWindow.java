package tiktaktoefx20.menu;

import javafx.scene.control.Alert;

import java.io.*;

public class HowToWindow {

    public static void displayHowToDialog() {
        String overviewText = getOverviewTextFromReadme("README.md");

        String contentTextBuilder = overviewText.trim(); // Убираем лишние пробелы в начале и конце;

        Alert howDialog = new Alert(Alert.AlertType.INFORMATION);
        howDialog.setTitle("How to Play");
        // aboutDialog.setHeaderText("Welcome to Tic-Tac-Toe 2.0!");
        howDialog.setContentText(contentTextBuilder);

        // Убираем иконку и текст
        howDialog.setHeaderText(null);
        howDialog.setGraphic(null);

        howDialog.showAndWait();
    }

    public static String getOverviewTextFromReadme(String readmeFilePath) {
        StringBuilder overviewText = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(readmeFilePath))) {
            boolean inOverviewBlock = false;
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("## How to Play")) {
                    inOverviewBlock = true;
                    continue;
                }

                if (inOverviewBlock) {
                    if (line.startsWith("##")) {
                        break; // Покидаем блок Overview при обнаружении следующего заголовка
                    }

                    overviewText.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return overviewText.toString();
    }
}

