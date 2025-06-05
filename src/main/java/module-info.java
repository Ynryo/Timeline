module com.example.timeline {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires com.google.gson;


    exports application;
    opens application to javafx.fxml;
    exports application.controller;
    opens application.controller to javafx.fxml;
    exports application.utils;
    opens application.utils to javafx.fxml;
}