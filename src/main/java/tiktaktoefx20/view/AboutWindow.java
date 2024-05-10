package tiktaktoefx20.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.*;

/**
 * Класс, отвечающий за содержимое окна "О программе". Class responsible for the content of the
 * "About" window.
 */

public class AboutWindow {

  public static void displayAboutDialog() {
    String overviewText = getOverviewTextFromReadme("README.md");

    String contentTextBuilder =
        "This application is an exciting game of Tic-Tac-Toe implemented in JavaFX.\n\n" +
            overviewText.trim() + // Убираем лишние пробелы в начале и конце
            "\n\n" +
            "The application features a convenient and intuitive interface to enjoy the game and have a great time.";

    Alert aboutDialog = new Alert(AlertType.INFORMATION);
    aboutDialog.setTitle("About");
    aboutDialog.setHeaderText("Welcome to Tic-Tac-Toe 2.0!");
    aboutDialog.setContentText(contentTextBuilder);

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
