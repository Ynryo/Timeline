package application.controller;

import application.MainApplication;
import application.model.MainGame;
import application.utils.SaveManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LeaveConfirmController {

    private MainGame mainGame;
    private String selectedDeck;

    @FXML
    void quitCancel(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void quitConfirmation(ActionEvent event) throws IOException {
        Stage modalStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Stage mainStage = (Stage) modalStage.getOwner();

        SaveManager saveManager = new SaveManager();
        saveManager.save(mainGame, selectedDeck);

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/example/timeline/menu-principal-view.fxml"));
        modalStage.close();
        mainStage.setScene(new Scene(fxmlLoader.load()));
    }

    void setDatasForSave(MainGame mainGame, String selectedDeck) {
        this.mainGame = mainGame;
        this.selectedDeck = selectedDeck;
    }

}