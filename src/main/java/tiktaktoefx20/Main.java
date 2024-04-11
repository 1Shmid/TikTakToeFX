package tiktaktoefx20;

import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

import java.io.*;
import java.util.*;

public class Main extends Application {



    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("TTTFX.fxml"));
        Parent root = fxmlLoader.load();
        GameController controller = fxmlLoader.getController();
        controller.setStage(stage); // Передача объекта Stage в контроллер

        Scene scene = new Scene(root, 862, 400);
        stage.setTitle("TikTakToeFX");
        stage.setScene(scene);
        stage.show();
    }





    public static void main(String[] args) {
        launch();
    }
}