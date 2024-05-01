module org.example.tiktaktoefx {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.logging;
    requires java.sql;

    opens tiktaktoefx20 to javafx.fxml;
    exports tiktaktoefx20;
}