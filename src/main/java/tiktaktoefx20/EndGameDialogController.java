package tiktaktoefx20;

import javafx.fxml.FXML;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.*;

public class EndGameDialogController {

    @FXML
    private Text XText;

    @FXML
    private Text OText;

    @FXML
    private Label resultLabel;

    @FXML
    private Label winnerLabel;

    private Stage stage;
    public void setWinnerTexts(Text XText, Text OText) {
        this.XText = XText;
        this.OText = OText;
    }

    public void initialize() {
        // Инициализируем шрифт для текста
        Font font = new Font("Gill Sans MT", 96);

        // Применяем шрифт к текстовым объектам
        XText.setFont(font);
        OText.setFont(font);
    }

    public void setWinnerSymbol(String winnerSymbol) {
        if (winnerSymbol.equals("The player")) {
            winnerLabel.setText("X");
            winnerLabel.setTextFill(Color.BLACK); // Черный цвет для символа "X"
            winnerLabel.setAlignment(Pos.CENTER);
        } else if (winnerSymbol.equals("The computer")) {
            winnerLabel.setText("O");
            winnerLabel.setTextFill(Color.WHITE); // Белый цвет для символа "O"
            winnerLabel.setAlignment(Pos.CENTER);
        } else {
            XText.setText("X");
            OText.setText("O");
            XText.setFill(Color.BLACK); // Черный цвет для символа "X"
            OText.setFill(Color.WHITE); // Белый цвет для символа "O"

            Text xText = new Text(XText.getText());
            xText.setFill(Color.BLACK);
            xText.setFont(XText.getFont()); // Используем шрифт из XText

            Text oText = new Text(OText.getText());
            oText.setFill(Color.WHITE);
            oText.setFont(OText.getFont()); // Используем шрифт из OText


            TextFlow flow = new TextFlow(xText, oText);
            winnerLabel.setGraphic(flow);


        }
    }



    public void setResultText(String resultText) {
        if (resultText.contains("wins")) {
            resultLabel.setText("WINNER!"); // Устанавливаем "WINNER!" в resultLabel
        } else {
            resultLabel.setText("DRAW!"); // Устанавливаем "DRAW!" в resultLabel
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }


    // Метод для расположения окна по центру AnchorPane
    public void centerOnAnchorPane(double anchorPaneWidth, double anchorPaneHeight) {
        double dialogWidth = stage.getWidth();
        double dialogHeight = stage.getHeight();

        double x = (anchorPaneWidth - dialogWidth) / 2;
        double y = (anchorPaneHeight - dialogHeight) / 2;

        stage.setX(x);
        stage.setY(y);
    }
}
