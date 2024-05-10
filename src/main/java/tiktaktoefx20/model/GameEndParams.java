package tiktaktoefx20.model;

import java.util.*;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import tiktaktoefx20.database.*;

public record GameEndParams(String winningPlayer,
                            List<int[]> winningCells,
                            String selectedLevel,
                            List<GameMove> moves,
                            int moveCounter,
                            int playerMovesCounter,
                            int computerMovesCounter,
                            int gameTime,
                            AnchorPane anchorPane,
                            GridPane gridPane,
                            char[][] gameField,
                            Line bottomHLine,
                            Line rightVLine,
                            Line upHLine,
                            Line leftVLine
) {

}

