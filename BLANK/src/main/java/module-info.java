module org.example.blank {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.blank to javafx.fxml;
    exports org.example.blank;
}