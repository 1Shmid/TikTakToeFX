package tiktaktoefx20;

import javafx.geometry.*;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.*;

public class GraphicsManager extends GameResultHandler {

    // Метод для отрисовки линии на игровом поле
    public static void drawLineOnGridPane(GridPane gridPane, List<int[]> winningCells) {
        // Проверяем, что у нас есть выигрышные ячейки и GridPane
        if (winningCells == null || winningCells.isEmpty() || gridPane == null) {
            return;
        }

        // Получаем размеры ячеек в GridPane
        double cellWidth = gridPane.getWidth() / Constants.FIELD_SIZE;
        double cellHeight = gridPane.getHeight() / Constants.FIELD_SIZE;

        // Получаем координаты центров выигрышных ячеек
        List<Point2D> cellCenters = new ArrayList<>();
        for (int[] cell : winningCells) {
            double centerX = (cell[1] + 0.5) * cellWidth;
            double centerY = (cell[0] + 0.5) * cellHeight;
            cellCenters.add(new Point2D(centerX, centerY));
        }

        // Создаем линию между центрами выигрышных ячеек
        if (cellCenters.size() >= 2) {
            Point2D startPoint = cellCenters.get(0);
            Point2D endPoint = cellCenters.get(1);
            Line line = new Line(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
            gridPane.getChildren().add(line);
        }
    }
}
