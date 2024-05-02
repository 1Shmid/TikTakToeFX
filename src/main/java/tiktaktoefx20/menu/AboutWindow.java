package tiktaktoefx20.menu;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.*;

public class AboutWindow {

    public static void displayAboutDialog() {
        String overviewText = getOverviewTextFromReadme("README.md");

        StringBuilder contentTextBuilder = new StringBuilder();
        contentTextBuilder.append("This application is an exciting game of Tic-Tac-Toe implemented in JavaFX.\n\n")
                .append(overviewText.trim()) // Убираем лишние пробелы в начале и конце
                .append("\n\n")
                .append("The application features a convenient and intuitive interface to enjoy the game and have a great time.");

        Alert aboutDialog = new Alert(AlertType.INFORMATION);
        aboutDialog.setTitle("About");
        aboutDialog.setHeaderText("Welcome to Tic-Tac-Toe 2.0!");
        aboutDialog.setContentText(contentTextBuilder.toString());

        // Убираем иконку
        aboutDialog.setGraphic(null);

        aboutDialog.showAndWait();
    }



    public static String getOverviewTextFromReadme(String readmeFilePath) {
        StringBuilder overviewText = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(readmeFilePath))) {
            boolean inOverviewBlock = false;
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("## Overview")) {
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