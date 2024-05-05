module org.example.tiktaktoefx {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.logging;
    requires java.sql;
    requires java.desktop;

    opens tiktaktoefx20 to javafx.fxml;
    exports tiktaktoefx20;
    exports tiktaktoefx20.menu;
    opens tiktaktoefx20.menu to javafx.fxml;
}