package tiktaktoefx20.view;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.beans.*;
import java.io.IOException;
import tiktaktoefx20.model.GameEndParams;
import tiktaktoefx20.controller.GameController;
import tiktaktoefx20.controller.GameResultWindowController;
import tiktaktoefx20.controller.NewGameSetter;

/**
 * Класс, отвечающий за содержимое окна с результатами игры. Class responsible for the content of
 * the game results window.
 */

public class GameResultWindow {

  private final NewGameSetter newGameSetter = new NewGameSetter();

  private boolean isOpen;
  private final PropertyChangeSupport support;

  private Parent getParent(GameResultWindowController controller) {
    // Загружаем FXML-файл для диалогового окна
    FXMLLoader loader = new FXMLLoader(
        getClass().getResource("/tiktaktoefx20/GameResultWindow.fxml"));

    loader.setController(controller); // Устанавливаем контроллер

    Parent root;
    try {
      root = loader.load();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
    return root;
  }

  public void show(GameEndParams params, String result) {
    GameController gameController = GameController.getInstance();

    GameResultWindowController controller = new GameResultWindowController();

    updateWindowState(setValue(true));

    addPropertyChangeListener(evt -> {
      gameController.propertyChange(evt);
    });

    final Parent root = getParent(controller);
    if (root == null) {
      return;
    }

    setupWindow(params, result, controller);

    final Stage stage = createStage(root, controller);

    setupStage(params, stage);

    createPauseTransition(params, stage);

    addPropertyChangeListener(evt -> {
      gameController.propertyChange(evt);
    });

    setupRootClickHandler(params, root, stage);
  }

  private void setupRootClickHandler(GameEndParams params, Parent root, Stage stage) {
    root.setOnMouseClicked(mouseEvent -> {
      updateWindowState(false);
      newGameSetter.startNewGame(params.gridPane(), params.bottomHLine(), params.rightVLine(),
          params.upHLine(),
          params.leftVLine());
      stage.close();
    });
  }

  private void createPauseTransition(GameEndParams params, Stage stage) {
    // Создаем паузу в 1 секунду перед показом окна
    PauseTransition pause = new PauseTransition(Duration.millis(300));
    pause.setOnFinished(event -> {
      // После завершения паузы, показываем диалоговое окно
      Platform.runLater(stage::showAndWait);
      newGameSetter.cleanGameResult(params.gameField(), params.anchorPane(), params.gridPane());
    });
    pause.play();
  }

  private void setupStage(GameEndParams params, Stage stage) {
    // Получаем размеры окна игры
    Bounds gameBounds = params.anchorPane().localToScreen(params.anchorPane().getBoundsInLocal());

    // Получаем размеры объекта MenuBar
    double menuBarHeight = params.anchorPane().lookup("#menuBar").getBoundsInLocal().getHeight();

    // Устанавливаем обработчик события на отображение окна
    stage.setOnShown(event -> centerStage(stage, gameBounds, menuBarHeight));
  }

  private static Stage createStage(Parent root, GameResultWindowController controller) {
    // Создаем новое диалоговое окно
    Stage stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.initStyle(StageStyle.UNDECORATED); // Устанавливаем стиль без заголовка

    // Устанавливаем размеры нового окна
    Scene dialogScene = new Scene(root);
    stage.setScene(dialogScene);
    controller.setStage(); // Устанавливаем Stage
    return stage;
  }

  private static void setupWindow(GameEndParams params, String result,
      GameResultWindowController controller) {
    // Устанавливаем символ победителя и результат игры
    controller.setWinnerSymbol(params.winningPlayer());
    controller.setResultText(result);
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
    return new ParallelTransition(scaleTransition, fadeTransition);
  }

  public GameResultWindow() {
    support = new PropertyChangeSupport(this);
  }

  public boolean getValue() {
    return isOpen;
  }

  public boolean setValue(boolean newValue) {
    boolean oldValue = isOpen;
    isOpen = newValue;
    support.firePropertyChange("isOpen", oldValue, newValue);
    return isOpen;
  }

  public void addPropertyChangeListener(PropertyChangeListener listener) {
    support.addPropertyChangeListener(listener);
  }

  public void removePropertyChangeListener(PropertyChangeListener listener) {
    support.removePropertyChangeListener(listener);
  }

  public void updateWindowState(boolean isOpen) {

    support.firePropertyChange("isOpen", this.isOpen,
        isOpen); // String propertyName, boolean oldValue, boolean newValue

    this.isOpen = isOpen;

  }

}
