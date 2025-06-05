module iut.s201.timeline {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens iut.s201.timeline to javafx.fxml;
    exports iut.s201.timeline;
}