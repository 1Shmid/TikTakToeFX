package tiktaktoefx20;

import javafx.fxml.FXML;
import javafx.geometry.*;
import javafx.scene.control.Label;
import javafx.stage.*;

public class EndGameDialogController {

    @FXML
    private Label winnerLabel;

    @FXML
    private Label resultLabel;
    private Stage stage;

    public void initialize() {
        // Устанавливаем выравнивание текста по центру для обоих Label
        winnerLabel.setAlignment(Pos.CENTER);
        resultLabel.setAlignment(Pos.CENTER);
    }

    public void setWinnerSymbol(String winnerSymbol) {
        if (winnerSymbol.equals("The player")) {
            winnerLabel.setText("X"); // Если победил игрок, устанавливаем "X" в winnerLabel
        } else if (winnerSymbol.equals("The computer")) {
            winnerLabel.setText("O"); // Если победил компьютер, устанавливаем "O" в winnerLabel
        } else {
            winnerLabel.setText("XO"); // В случае ничьей, устанавливаем "XO" в winnerLabel
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
