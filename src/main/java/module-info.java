module iut.s201.time {
    requires javafx.controls;
    requires javafx.fxml;


    opens iut.s201.time to javafx.fxml;
    exports iut.s201.time;
}