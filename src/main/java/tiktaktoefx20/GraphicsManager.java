package tiktaktoefx20;

import javafx.animation.*;
import javafx.geometry.*;
import javafx.scene.canvas.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.util.*;

import java.util.*;

public class GraphicsManager extends GameResultHandler {

    private final Canvas winningLineCanvas = new Canvas();

    protected static void animateVLine(Line line, double startY, double endY, Duration speed) {
        double newStartY = startY + (1.0 / 2) * (endY - startY); // Двигаем начальную точку влево от центра
        double newEndY = endY - (1.0 / 2) * (endY - startY); // Двигаем конечную точку вправо от центра

        line.setStartY(newStartY);
        line.setEndY(newEndY);

        // Создаем анимацию для изменения координаты X начальной точки
        Timeline startAnimation = new Timeline();
        startAnimation.getKeyFrames().add(
                new KeyFrame(speed, new KeyValue(line.startYProperty(), startY))
        );

        // Создаем анимацию для изменения координаты X конечной точки
        Timeline endAnimation = new Timeline();
        endAnimation.getKeyFrames().add(
                new KeyFrame(speed, new KeyValue(line.endYProperty(), endY))
        );

        // Запускаем анимации
        startAnimation.play();
        endAnimation.play();
    }

    protected static void animateHLine(Line line, double startX, double endX, Duration speed) {
        double newStartX = startX + (1.0 / 2) * (endX - startX); // Двигаем начальную точку влево от центра
        double newEndX = endX - (1.0 / 2) * (endX - startX); // Двигаем конечную точку вправо от центра

        line.setStartX(newStartX);
        line.setEndX(newEndX);

        // Создаем анимацию для изменения координаты X начальной точки
        Timeline startAnimation = new Timeline();
        startAnimation.getKeyFrames().add(
                new KeyFrame(speed, new KeyValue(line.startXProperty(), startX))
        );

        // Создаем анимацию для изменения координаты X конечной точки
        Timeline endAnimation = new Timeline();
        endAnimation.getKeyFrames().add(
                new KeyFrame(speed, new KeyValue(line.endXProperty(), endX))
        );

        // Запускаем анимации
        startAnimation.play();
        endAnimation.play();
    }

    private void setupCanvas(AnchorPane anchorPane) {
        if (!anchorPane.getChildren().contains(winningLineCanvas)) {
            winningLineCanvas.setWidth(anchorPane.getWidth());
            winningLineCanvas.setHeight(anchorPane.getHeight());
            anchorPane.getChildren().add(0, winningLineCanvas);
        }
    }

    private void drawLine(Double startX, Double startY, Double endX, Double endY, Constants.Winner winner) {
        winningLineCanvas.getGraphicsContext2D().clearRect(0, 0, winningLineCanvas.getWidth(), winningLineCanvas.getHeight());

        double lineWidth = 5.0;
        winningLineCanvas.getGraphicsContext2D().setLineWidth(lineWidth);

        if (startX != null && startY != null && endX != null && endY != null) {
            // Устанавливаем цвет линии в зависимости от победителя
            winningLineCanvas.getGraphicsContext2D().setStroke(winner == Constants.Winner.PLAYER ? Color.web("#545454") : Color.WHITE);

            // Рисуем линию от начальной точки до текущей точки
            winningLineCanvas.getGraphicsContext2D().strokeLine(startX, startY, startX, startY);

            // Рассчитываем шаги изменения координат
            double stepX = (endX - startX) / 20;
            double stepY = (endY - startY) / 20;

            // Задаем начальные координаты линии
            final double[] currentX = {startX};
            final double[] currentY = {startY};

            // Анимируем рисование линии
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                int count = 0;
                @Override
                public void run() {
                    if (count < 20) {
                        currentX[0] += stepX;
                        currentY[0] += stepY;
                        winningLineCanvas.getGraphicsContext2D().clearRect(0, 0, winningLineCanvas.getWidth(), winningLineCanvas.getHeight());
                        winningLineCanvas.getGraphicsContext2D().strokeLine(startX, startY, currentX[0], currentY[0]);
                        count++;
                    } else {
                        // Останавливаем таймер после завершения анимации
                        timer.cancel();
                        timer.purge();
                    }
                }
            };
            timer.scheduleAtFixedRate(task, 0, 10);
        }
    }
    protected void drawWinningLine(List<int[]> winningCells, AnchorPane anchorPane, Constants.Winner winner, GridPane gridPane) {

        // Получаем размеры поля GridPane
        double gridPaneWidth = gridPane.getWidth();
        double gridPaneHeight = gridPane.getHeight();

        // Получаем размеры ячейки
        double cellWidth = gridPaneWidth / Constants.FIELD_SIZE;
        double cellHeight = gridPaneHeight / Constants.FIELD_SIZE;

        // Находим координаты центров начальной и конечной ячеек в системе координат GridPane
        int startRow = winningCells.get(0)[0];
        int startCol = winningCells.get(0)[1];
        int endRow = winningCells.get(2)[0];
        int endCol = winningCells.get(2)[1];

        double startCellCenterX = (startCol + 0.5) * cellWidth;
        double startCellCenterY = (startRow + 0.5) * cellHeight;
        double endCellCenterX = (endCol + 0.5) * cellWidth;
        double endCellCenterY = (endRow + 0.5) * cellHeight;

        // Преобразуем координаты центров ячеек из системы координат GridPane в систему координат AnchorPane
        double startX = gridPane.localToScene(startCellCenterX, startCellCenterY).getX();
        double startY = gridPane.localToScene(startCellCenterX, startCellCenterY).getY();
        double endX = gridPane.localToScene(endCellCenterX, endCellCenterY).getX();
        double endY = gridPane.localToScene(endCellCenterX, endCellCenterY).getY();

        // Установка размеров и добавление Canvas на сцену
        setupCanvas(anchorPane);

        // Рисуем линию на Canvas
        drawLine(startX, startY, endX, endY, winner);
    }

    static void animateShade(Rectangle shade, GridPane gridPane) {
        // Вызываем метод установки позиции
        centeringShade(shade, gridPane);

        // Создаем анимацию для масштабирования прямоугольника
        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setNode(shade);
        scaleTransition.setDuration(Duration.millis(300)); // Время анимации (1 секунда)
        scaleTransition.setToX(0.33); // Масштабирование по оси X в 1/3 от исходного размера
        scaleTransition.setToY(0.33); // Масштабирование по оси Y в 1/3 от исходного размера

        scaleTransition.setOnFinished(event -> {
            // Установка цвета прямоугольника на прозрачный
            shade.setFill(Color.TRANSPARENT);

            // Создаем анимацию для возврата прямоугольника к исходным размерам
            ScaleTransition reverseTransition = new ScaleTransition();
            reverseTransition.setNode(shade);
            reverseTransition.setDuration(Duration.millis(300)); // Время анимации (300 миллисекунд)
            reverseTransition.setToX(1); // Масштабирование по оси X до исходного размера
            reverseTransition.setToY(1); // Масштабирование по оси Y до исходного размера

            // Запуск анимации возврата
            reverseTransition.play();
        });

        // Запуск анимации
        scaleTransition.play();
    }



    private static void centeringShade(Rectangle shade, GridPane gridPane) {
        // Получаем размеры прямоугольника
        double shadeWidth = shade.getWidth();
        double shadeHeight = shade.getHeight();

        // Получаем координаты центра GridPane относительно AnchorPane
        Point2D centerCoordinates = getCenterCoordinates(gridPane);

        // Вычисляем координаты верхнего левого угла прямоугольника так, чтобы его центр совпадал с центром GridPane
        double topLeftX = centerCoordinates.getX() - shadeWidth / 2;
        double topLeftY = centerCoordinates.getY() - shadeHeight / 2;

        AnchorPane.setLeftAnchor(shade, topLeftX);
        AnchorPane.setTopAnchor(shade, topLeftY);
    }

    private static Point2D getCenterCoordinates(GridPane gridPane) {
        // Получаем координаты верхнего левого угла GridPane относительно AnchorPane
        double gridPaneLayoutX = gridPane.getLocalToParentTransform().getTx();
        double gridPaneLayoutY = gridPane.getLocalToParentTransform().getTy();

        // Получаем размеры GridPane
        double gridPaneWidth = gridPane.getWidth();
        double gridPaneHeight = gridPane.getHeight();

        // Вычисляем координаты центра GridPane относительно AnchorPane
        double centerX = gridPaneLayoutX + gridPaneWidth / 2;
        double centerY = gridPaneLayoutY + gridPaneHeight / 2;

        return new Point2D(centerX, centerY);
    }

    protected void animateLine(Line line) {

        // Получаем текущие координаты X начальной и конечной точек линии
        double startX = line.getStartX();
        double endX = line.getEndX();

        double startY = line.getStartY();
        double endY = line.getEndY();

        Duration speed = Duration.millis(500);

        if (startY == endY){
            // Задаем новые координаты для начальной и конечной точек
            animateHLine(line, startX, endX, speed);
        }

        if (startX == endX){
            // Задаем новые координаты для начальной и конечной точек
            animateVLine(line, startY, endY, speed);
        }
    }
}
