package tiktaktoefx20.view;

import javafx.animation.*;
import javafx.application.*;
import javafx.fxml.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.util.*;

import java.io.*;
import tiktaktoefx20.controller.StatWindowController;

/**
 * Класс, отвечающий за показ окна статистики.
 * <p>
 * Class responsible for the show of the statistics window.
 */

public class StatWindow {

//	private WindowManager windowManager; // Поле для хранения экземпляра WindowManager
//
//	// Конструктор класса StatWindow, принимающий экземпляр WindowManager
//	public StatWindow(WindowManager windowManager) {
//		this.windowManager = windowManager;
//	}
	
	/**
	 * Отображает диалоговое окно со статистикой. Displays the dialog window with statistics.
	 *
	 * @param anchorPane Pane, на котором нужно отобразить диалоговое окно. The pane on which the
	 *                   dialog window should be displayed.
	 */
//	public void showWindow(AnchorPane anchorPane) {
//		// Загрузка FXML-файла для диалогового окна и его отображение через WindowManager
//		windowManager.showStatWindow();
//
//	}
	public void showWindow(AnchorPane anchorPane, long startTime) {
		
		// Загрузите FXML-файл для диалогового окна
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/tiktaktoefx20/StatDialog.fxml"));
		Parent root;
		try {
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		StatWindowController controller = loader.getController();
		
		controller.setData(startTime);
		
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
			// Получаем контроллер окна
			//StatWindowController controller = loader.getController();
			// Закрываем окно
			stage.close();
			controller.stopTimer();
		});
	}
	
	public void centerStage(Stage stage, Bounds gameBounds, double menuBarHeight) {
		
		double parentHeaderHeight = 27;
		
		//double menuBarHeight = 27;
		
		// Получаем размеры диалогового окна
		double dialogWidth = stage.getWidth();
		double dialogHeight = stage.getHeight();
		
		// Получаем размеры окна игры за вычетом высоты MenuBar
		double gameWidth = gameBounds.getWidth();
		double gameHeight = gameBounds.getHeight() - menuBarHeight;
		
		// Вычисляем координаты середины окна игры
		double gameCenterX = gameBounds.getMinX() + gameWidth / 2;
		double gameCenterY = gameBounds.getMinY() + parentHeaderHeight + gameHeight / 2;
		
		// Вычисляем новые координаты для центрирования диалогового окна
		double newDialogX = gameCenterX - dialogWidth / 2;
		double newDialogY = gameCenterY - dialogHeight / 2;
		
		// Устанавливаем новые координаты для диалогового окна
		stage.setX(newDialogX);
		stage.setY(newDialogY);
	}
}
