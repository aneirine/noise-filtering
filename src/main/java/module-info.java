module com.example.noise2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;
    requires javafx.swing;


    opens com.example.noise2 to javafx.fxml;
    exports com.example.noise2;
    exports com.example.noise2.filters;
    opens com.example.noise2.filters to javafx.fxml;
    exports com.example.noise2.mvc;
    opens com.example.noise2.mvc to javafx.fxml;
}