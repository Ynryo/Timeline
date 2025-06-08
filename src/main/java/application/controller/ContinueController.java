package application.controller;

import application.MainApplication;
import application.utils.SaveManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ContinueController {
    @FXML private VBox containerVBox;
    private List<String> saves;

    @FXML
    public void initialize() throws IOException {
        SaveManager saveManager = new SaveManager();
        saves = saveManager.listSaves();
        if (saves == null) {
            System.out.println("No save found in the save folder.");
            return;
        }
        containerVBox.getChildren().clear();
        System.out.println("saves = " + saves.size());
        for (String save : saves) {
            if (save.endsWith(".bin")) {
                File file = new File(save);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/ynryo/timeline/save-view.fxml"));
                Parent fileItem = loader.load();
                SaveViewController controller = loader.getController();
                controller.setData(file.getName());
                controller.setController(this);
                containerVBox.getChildren().add(fileItem);
            }
        }
    }

    @FXML
    void onRetourClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/fr/ynryo/timeline/menu-principal-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}