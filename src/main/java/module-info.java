module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.swing;
    requires javafx.media;
    requires org.slf4j; // For SLF4J logging

    // Opens the package to JavaFX for reflection
    opens org.example.demo to javafx.fxml;

    // Export the package so that it can be used by other modules
    exports org.example.demo;
}
