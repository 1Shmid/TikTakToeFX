package tiktaktoefx20.menu;

import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;

import java.io.*;

public class StatDialogLoader {
    public static void showDialog(int gamesPlayed, int playerWins, int computerWins, String totalTime) {
        try {
            // Загрузка FXML-файла
            FXMLLoader loader = new FXMLLoader(StatDialogLoader.class.getResource("StatDialog.fxml"));
            Parent root = loader.load();

            // Получение контроллера
            StatDialogController controller = loader.getController();

            // Установка данных в контроллер
            controller.setData();

            // Создание сцены и установка корневого узла
            Scene scene = new Scene(root);

            // Создание и настройка диалогового окна
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("Statistics");
            dialogStage.setScene(scene);

            // Отображение диалогового окна
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
