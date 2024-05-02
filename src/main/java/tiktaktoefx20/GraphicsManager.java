package tiktaktoefx20;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.*;

public class GraphicsManager extends GameResultHandler {

    private final Canvas winningLineCanvas = new Canvas();

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
}
