package tiktaktoefx20.controller;

import tiktaktoefx20.constants.Constants;
import javafx.fxml.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;

import static tiktaktoefx20.constants.Constants.*;


/**
 * Класс отвечает за управление окном результатов игры.
 * <p>
 * The class is responsible for controlling the game results window.
 */


public class GameResultWindowController {

  @FXML
  private Label resultLabel;

  @FXML
  private Label winnerLabel;

  public void setWinnerSymbol(String winnerSymbol) {
    if (winnerSymbol.equals("The player")) {
      setSymbolText("X", Color.valueOf(XColor)); // Устанавливаем символ "X" при победе игрока
    } else if (winnerSymbol.equals("The computer")) {
      setSymbolText("O", Color.valueOf(OColor)); // Устанавливаем символ "O" при победе компьютера
    } else {
      Text xText = createSymbolText(String.valueOf(Constants.PLAYER_SYMBOL), Color.valueOf(XColor));
      xText.setFont(winnerLabel.getFont());
      Text oText = createSymbolText(String.valueOf(Constants.COMPUTER_SYMBOL),
          Color.valueOf(OColor));
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

  public void setStage() {
  }
}

//    private GameController gameController;
//
//    // Метод для регистрации GameController в качестве наблюдателя
//    public void registerObserver(GameController gameController) {
//        this.gameController = gameController;
//        gameController.registerObserver(this); // Регистрируем текущий объект как наблюдателя
//    }

//    @Override
//    public void updateDialogState(boolean isOpen) {
//
//        System.out.println("GameResultWindowController @Override updateDialogState:" + isOpen);

//        // Здесь вызываем метод GameController
//        if (!isOpen && gameController != null) {
//            gameController.notifyObservers(false);
//        }

//    @Override
//    public void registerObserver() {
//        observers.add(observer);
//    }
//
//    @Override
//    public void removeObserver() {
//
//    }
//
//    @Override
//    public void notifyObservers() {



