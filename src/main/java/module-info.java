module org.example.tiktaktoefx {
  requires javafx.controls;
  requires javafx.fxml;
  requires com.dlsc.formsfx;
  requires java.logging;
  requires java.sql;
  requires java.desktop;

  opens tiktaktoefx20 to javafx.fxml;
  exports tiktaktoefx20;
  exports tiktaktoefx20.strategies;
  exports tiktaktoefx20.constants;
  exports tiktaktoefx20.controller;
  opens tiktaktoefx20.controller to javafx.fxml;
  exports tiktaktoefx20.view;
  opens tiktaktoefx20.view to javafx.fxml;
  exports tiktaktoefx20.model;
  opens tiktaktoefx20.model to javafx.fxml;
}
