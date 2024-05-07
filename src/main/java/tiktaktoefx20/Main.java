package tiktaktoefx20;

import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;
import tiktaktoefx20.database.*;

import java.beans.*;
import java.io.*;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        GameResultWindow gameResultWindow = new GameResultWindow();

        int gameId = SQLiteDBManager.getGameIdFromDatabase(); // Получаем номер игры из базы данных
        String title = Constants.GAME_TITLE_PREFIX + gameId; // Формируем заголовок окна с номером игры

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("TTTFX.fxml"));
        Parent root = fxmlLoader.load();
        GameController controller = fxmlLoader.getController();
        controller.setStage(); // Передача объекта Stage в контроллер

        //GameController gameController = new GameController();

        //fxmlLoader.setController(controller);

        gameResultWindow.addPropertyChangeListener(controller);
        System.out.println("GameController успешно зарегистрирован как слушатель");


//        gameResultWindow.addPropertyChangeListener(evt -> {
//            System.out.println("GameController успешно зарегистрирован как слушатель");
//            controller.propertyChange(evt);
//        });

        Scene scene = new Scene(root, 600, 600);
        stage.setTitle(title); // Устанавливаем заголовок окна с номером игры
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}