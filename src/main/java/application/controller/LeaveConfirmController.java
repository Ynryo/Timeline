package application.controller;

import application.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LeaveConfirmController {

    @FXML
    void quitCancel(ActionEvent event) {
        // ferme uniquement le stage actuel
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void quitConfirmation(ActionEvent event) throws IOException {
        Stage modalStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Stage mainStage = (Stage) modalStage.getOwner();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/example/timeline/menu-principal-view.fxml"));
        modalStage.close();
        mainStage.setScene(new Scene(fxmlLoader.load()));
    }
}