package tiktaktoefx20;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

public class StatDialogController {


    @FXML
    private Text computerWinsLabel;

    @FXML
    private Text playerWinsLabel;

    @FXML
    private Text totalGamesLabel;

    @FXML
    private Text totalTimeLabel;

//    @FXML
//    private void initialize() {
//        setData();
//    }
    public void setStage() {
    }

    String gamesPlayed;
    String playerWins;
    String computerWins;
    String totalTime;

    // Метод для установки данных в метки окна
    public void setData() {

//        totalGamesLabel.setText(gamesPlayed);
//        playerWinsLabel.setText(playerWins);
//        computerWinsLabel.setText(computerWins);
//        totalTimeLabel.setText(totalTime);
    }


}
