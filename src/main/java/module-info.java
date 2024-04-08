module org.example.tiktaktoefx {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens org.example.tiktaktoefx to javafx.fxml;
    exports org.example.tiktaktoefx;
}