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
    private Label resultLabel;

    @FXML
    private Label winnerLabel;

    private Stage stage;

    public void setWinnerSymbol(String winnerSymbol) {
        if (winnerSymbol.equals("The player")) {
            winnerLabel.setText("X");
            winnerLabel.setTextFill(Color.web("#545454")); // Черный цвет для символа "X"
            winnerLabel.setAlignment(Pos.CENTER);
        } else if (winnerSymbol.equals("The computer")) {
            winnerLabel.setText("O");
            winnerLabel.setTextFill(Color.WHITE); // Белый цвет для символа "O"
            winnerLabel.setAlignment(Pos.CENTER);
        } else {
            Text xText = new Text(String.valueOf(Constants.PLAYER_SYMBOL));
            xText.setFill(Color.web("#545454"));
            xText.setFont(winnerLabel.getFont());

            Text oText = new Text(String.valueOf(Constants.COMPUTER_SYMBOL));
            oText.setFill(Color.WHITE);
            oText.setFont(winnerLabel.getFont());

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
