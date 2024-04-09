package org.example.tiktaktoefx;

import java.net.URL;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class HelloController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    private char nowSym = 'X'; // убрать в класс Constants не получится
    private char gameField[][] = new char[3][3]; // создание массива для хранения получаемых в процессе игры значений от кнопки

    private boolean gameOver = false;
    @FXML
    void btnClick(ActionEvent event) {

        //((Button)event.getSource()).setText(String.valueOf(nowSym)); - быдлокод

        Button clickedButton = (Button) event.getSource(); // получили клик

       if (gameOver || clickedButton.getText() != "") return; // если игра окончена - выходим и на нажатие больше не реагируем

       clickedButton.setText(String.valueOf(nowSym)); // нарисовали символ

        // получаем координаты кнопки  - индекс ячейки,
        // и если возвращает null присваиваем 0 - здесь это глюк метода get и таким образом мы это исправляем

        int rowIndex = GridPane.getRowIndex(clickedButton) == null ? 0 : GridPane.getRowIndex(clickedButton);
        int columnIndex = GridPane.getColumnIndex(clickedButton) == null ? 0 : GridPane.getColumnIndex(clickedButton);

        gameField[rowIndex][columnIndex] = nowSym; // записываем в массив кликнутую кнопку и символ на ней

        // Проверяем победителя в строке
        if (gameField[0][0] == gameField[0][1] &&
                gameField[0][0] == gameField[0][2] &&
                (gameField[0][0] == 'X' ||
                gameField[0][0] == 'O')) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Победил " + clickedButton.getText(), ButtonType.OK);

            alert.showAndWait();

            gameOver = true;
        }
        else if (gameField[1][0] == gameField[1][1] &&
                gameField[1][0] == gameField[1][2] &&
                (gameField[1][0] == 'X' ||
                gameField[1][0] == 'O')) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Победил " + clickedButton.getText(), ButtonType.OK);
            alert.showAndWait();

            gameOver = true;
        }
        else if (gameField[2][0] == gameField[2][1] &&
                gameField[2][0] == gameField[2][2] &&
                (gameField[2][0] == 'X' ||
                gameField[2][0] == 'O')) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Победил " + clickedButton.getText(), ButtonType.OK);
            alert.showAndWait();

            gameOver = true;
        }
        // Проверяем победителя в строке
        else if (gameField[0][0] == gameField[1][0] &&
                gameField[0][0] == gameField[2][0] &&
                (gameField[0][0] == 'X' ||
                gameField[0][0] == 'O')) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Победил " + clickedButton.getText(), ButtonType.OK);
            alert.showAndWait();

            gameOver = true;
        }
        else if (gameField[0][1] == gameField[1][1] &&
                gameField[0][1] == gameField[2][1] &&
                (gameField[0][1] == 'X' ||
                gameField[0][1] == 'O')) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Победил " + clickedButton.getText(), ButtonType.OK);
            alert.showAndWait();

            gameOver = true;
        }
        else if (gameField[0][2] == gameField[1][2] &&
                gameField[0][2] == gameField[2][2] &&
                (gameField[0][2] == 'X' ||
                gameField[0][2] == 'O')) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Победил " + clickedButton.getText(), ButtonType.OK);
            alert.showAndWait();

            gameOver = true;
        }
        // Проверяем победителя в диагоналях
        else if (gameField[0][0] == gameField[1][1] &&
                gameField[0][0] == gameField[2][2] &&
                (gameField[0][0] == 'X' ||
                gameField[0][0] == 'O')) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Победил " + clickedButton.getText(), ButtonType.OK);
            alert.showAndWait();

            gameOver = true;
        }
        else if (gameField[2][2] == gameField[1][1] &&
                gameField[2][2] == gameField[0][2] &&
                (gameField[2][2] == 'X' ||
                gameField[2][2] == 'O')) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Победил " + clickedButton.getText(), ButtonType.OK);
            alert.showAndWait();

            gameOver = true;
        }

        nowSym = nowSym == 'X' ? 'O' : 'X';

    }

    @FXML
    void initialize() {

    }

}
