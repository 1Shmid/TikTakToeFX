package tiktaktoefx20.model;

import java.util.*;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import tiktaktoefx20.database.*;

/**
 * Класс, представляющий набор параметров для окончания игры, необходимых другим классам и методам.
 * Class representing a set of game ending parameters needed by other classes and methods.
 */

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

