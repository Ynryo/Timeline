package application.controller;

import application.MainApplication;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class MenuPrincipalController {
    @FXML
    private VBox mainVBox;
    @FXML
    private VBox nouvellePartieVBox;
    @FXML
    private Button buttonQuitter;
    @FXML
    void onNouvellePartieClicked(ActionEvent event) {
        mainVBox.setVisible(false);
        nouvellePartieVBox.setVisible(true);
    }
    @FXML
    void onNouvellePartieRetourClicked(ActionEvent event) {
        mainVBox.setVisible(true);
        nouvellePartieVBox.setVisible(false);
    }
    @FXML
    void on1PlayerClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/example/timeline/deck-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void on2PlayerClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/example/timeline/deck-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void handleQuitter() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.initOwner(buttonQuitter.getScene().getWindow());
        alert.setTitle("Confirmation");
        alert.setHeaderText("Êtes-vous sûr de vouloir quitter ?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Platform.exit();
        } else {
            alert.close();
        }
    }
    @FXML
    void onContinuerClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/example/timeline/continue-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }
}