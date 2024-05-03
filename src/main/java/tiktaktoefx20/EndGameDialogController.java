package tiktaktoefx20;

import javafx.fxml.FXML;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.*;

import static tiktaktoefx20.Constants.*;

public class EndGameDialogController {

    @FXML
    private Label resultLabel;

    @FXML
    private Label winnerLabel;

    private Stage stage;

    public void setWinnerSymbol(String winnerSymbol) {
        if (winnerSymbol.equals("The player")) {
            setSymbolText("X", Color.valueOf(XColor)); // Устанавливаем символ "X" при победе игрока
        } else if (winnerSymbol.equals("The computer")) {
            setSymbolText("O", Color.valueOf(OColor)); // Устанавливаем символ "O" при победе компьютера
        } else {
            Text xText = createSymbolText(String.valueOf(Constants.PLAYER_SYMBOL), Color.valueOf(XColor));
            xText.setFont(winnerLabel.getFont());
            Text oText = createSymbolText(String.valueOf(Constants.COMPUTER_SYMBOL), Color.valueOf(OColor));
            oText.setFont(winnerLabel.getFont());
            TextFlow flow = new TextFlow(xText, oText);
            winnerLabel.setGraphic(flow); // Устанавливаем символ "ХO" при ничьей
            winnerLabel.setAlignment(Pos.CENTER);
        }
    }

    private void setSymbolText(String symbol, Color color) {
        winnerLabel.setText(symbol);
        winnerLabel.setTextFill(color);
        winnerLabel.setAlignment(Pos.CENTER);
    }

    private Text createSymbolText(String symbol, Color color) {
        Text text = new Text(symbol);
        text.setFill(color);
        return text;
    }

    public void setResultText(String resultText) {
        resultLabel.setText(resultText.contains("wins") ? "WINNER!" : "DRAW!");
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
