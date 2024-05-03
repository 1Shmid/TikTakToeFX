package tiktaktoefx20;

import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;
import tiktaktoefx20.database.*;

import java.io.*;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        int gameId = SQLiteDBManager.getGameIdFromDatabase(); // Получаем номер игры из базы данных
        String title = Constants.GAME_TITLE_PREFIX + gameId; // Формируем заголовок окна с номером игры

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("TTTFX.fxml"));
        Parent root = fxmlLoader.load();
        GameController controller = fxmlLoader.getController();
        controller.setStage(); // Передача объекта Stage в контроллер

        fxmlLoader.setController(new GameController());

        Scene scene = new Scene(root, 600, 600);
        stage.setTitle(title); // Устанавливаем заголовок окна с номером игры
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}