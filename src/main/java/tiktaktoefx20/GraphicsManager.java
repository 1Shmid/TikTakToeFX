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

    private void drawLine(double startX, double startY, double endX, double endY, Constants.Winner winner) {
        // Очищаем Canvas
        winningLineCanvas.getGraphicsContext2D().clearRect(0, 0, winningLineCanvas.getWidth(), winningLineCanvas.getHeight());

        // Устанавливаем цвет линии в зависимости от победителя
        if (winner == Constants.Winner.PLAYER) {
            winningLineCanvas.getGraphicsContext2D().setStroke(Color.web("#545454"));
        } else {
            winningLineCanvas.getGraphicsContext2D().setStroke(Color.WHITE);
        }

        // Устанавливаем ширину линии и рисуем её
        double lineWidth = 5.0; // Пример ширины линии
        winningLineCanvas.getGraphicsContext2D().setLineWidth(lineWidth);
        winningLineCanvas.getGraphicsContext2D().strokeLine(startX, startY, endX, endY);
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
