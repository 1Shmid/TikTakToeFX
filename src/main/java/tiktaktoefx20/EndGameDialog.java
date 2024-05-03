package tiktaktoefx20;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

public class EndGameDialog {

    NewGame newGame = new NewGame();

    void show(char[][] gameField, String winnerSymbol, AnchorPane anchorPane, GridPane gridPane, String result) {
        // Загрузите FXML-файл для диалогового окна
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EndGameDialog.fxml"));
        EndGameDialogController controller = new EndGameDialogController();
        loader.setController(controller); // Устанавливаем контроллер
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Устанавливаем символ победителя и результат игры
        controller.setWinnerSymbol(winnerSymbol);
        controller.setResultText(result);

        // Получаем размеры окна игры
        Bounds gameBounds = anchorPane.localToScreen(anchorPane.getBoundsInLocal());

        // Получаем размеры объекта MenuBar
        double menuBarHeight = anchorPane.lookup("#menuBar").getBoundsInLocal().getHeight();

        // Создаем новое диалоговое окно
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED); // Устанавливаем стиль без заголовка

        // Устанавливаем размеры нового окна
        Scene dialogScene = new Scene(root);
        stage.setScene(dialogScene);
        controller.setStage(stage); // Устанавливаем Stage

        // Создаем паузу в 1 секунду перед показом окна
        PauseTransition pause = new PauseTransition(Duration.millis(300));
        pause.setOnFinished(event -> {
            // После завершения паузы, показываем диалоговое окно
            Platform.runLater(stage::showAndWait);
        });
        pause.play();

        // Устанавливаем обработчик события на отображение окна
        stage.setOnShown(event -> centerStage(stage, gameBounds, menuBarHeight));

        // Устанавливаем обработчик события на клик мышкой
        root.setOnMouseClicked(mouseEvent -> {
            stage.close();
            newGame.start(gameField, anchorPane, gridPane);
        });
    }

    // Метод для центрирования окна относительно другого окна с учетом высоты MenuBar
    private void centerStage(Stage stage, Bounds gameBounds, double menuBarHeight) {
        // Получаем размеры диалогового окна
        double dialogWidth = stage.getWidth();
        double dialogHeight = stage.getHeight();

        // Получаем размеры окна игры за вычетом высоты MenuBar
        double gameWidth = gameBounds.getWidth();
        double gameHeight = gameBounds.getHeight() - menuBarHeight;

        // Вычисляем координаты середины окна игры
        double gameCenterX = gameBounds.getMinX() + gameWidth / 2;
        double gameCenterY = gameBounds.getMinY() + gameHeight / 2;

        // Вычисляем новые координаты для центрирования диалогового окна
        double newDialogX = gameCenterX - dialogWidth / 2;
        double newDialogY = gameCenterY - dialogHeight / 2;

        // Устанавливаем новые координаты для диалогового окна
        stage.setX(newDialogX);
        stage.setY(newDialogY);

        // Создаем анимацию для плавного отображения окна
        startScaleAnimation(stage.getScene().getRoot());
    }


    void startScaleAnimation(Node node) {
        // Создаем анимацию масштабирования и изменения прозрачности
        ParallelTransition animation = createScaleAndFadeAnimation(node);

        // Запускаем анимацию
        animation.play();
    }

    private ParallelTransition createScaleAndFadeAnimation(Node node) {
        // Создаем анимацию изменения масштаба
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300), node);
        scaleTransition.setToX(1.0); // Конечный масштаб по оси X
        scaleTransition.setToY(1.0); // Конечный масштаб по оси Y

        // Создаем анимацию изменения прозрачности
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(300), node);
        fadeTransition.setFromValue(0.0); // Начальное значение прозрачности
        fadeTransition.setToValue(1.0); // Конечное значение прозрачности

        // Создаем параллельную анимацию для выполнения обеих анимаций одновременно
        ParallelTransition parallelTransition = new ParallelTransition(scaleTransition, fadeTransition);

        return parallelTransition;
    }

}
