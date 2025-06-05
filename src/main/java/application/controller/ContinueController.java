package application.controller;

import application.MainApplication;
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
import java.net.URL;
import java.util.Arrays;

public class ContinueController {
    @FXML
    private VBox containerVBox;

    public void initialize() {
        try {
            URL url = getClass().getResource("/com/example/timeline/save");
            if (url == null) {
                System.out.println("Dossier save introuvable");
                return;
            }

            File folder = new File(url.toURI());
            if (folder.exists() && folder.isDirectory()) {
                File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".bin"));
                System.out.println("files  : " + Arrays.toString(files));

                if (files != null) {
                    for (File file : files) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/timeline/save-view.fxml"));
                        Parent fileItem = loader.load();

                        SaveViewController controller = loader.getController();
                        controller.setData(file.getName());

                        containerVBox.getChildren().add(fileItem);
                    }
                }
            } else {
                System.out.println("Le dossier n'existe pas ou n'est pas un rÃ©pertoire");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    void onRetourClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/example/timeline/menu-principal-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}