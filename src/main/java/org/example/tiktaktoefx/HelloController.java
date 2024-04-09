package org.example.tiktaktoefx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class HelloController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    private char nowSym = 'X'; // убрать в класс Constants не получится
    private char gameField[][] = new char[3][3]; // создание массива для хранения получаемых в процессе игры значений от кнопки
    @FXML
    void btnClick(ActionEvent event) {

        //((Button)event.getSource()).setText(String.valueOf(nowSym)); - быдлокод

        Button clickedButton = (Button) event.getSource();





        clickedButton.setText(String.valueOf(nowSym));

        nowSym = nowSym == 'X' ? 'O' : 'X';


    }

    @FXML
    void initialize() {

    }

}
