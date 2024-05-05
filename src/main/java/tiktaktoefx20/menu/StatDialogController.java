package tiktaktoefx20.menu;

import javafx.fxml.*;
import javafx.scene.control.*;

public class StatDialogController {

    @FXML
    private Label gamesPlayedLabel;

    @FXML
    private Label playerWinsLabel;

    @FXML
    private Label computerWinsLabel;

    @FXML
    private Label totalTimeLabel;

    // Метод для установки данных в метки окна
    public void setData(int gamesPlayed, int playerWins, int computerWins, String totalTime) {
        gamesPlayedLabel.setText("Games Played: " + gamesPlayed);
        playerWinsLabel.setText("Player Wins: " + playerWins);
        computerWinsLabel.setText("Computer Wins: " + computerWins);
        totalTimeLabel.setText("Total Time: " + totalTime);
    }
}
