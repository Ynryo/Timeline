package application.controller;

import application.MainApplication;
import application.model.MainGame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;

public class LeaveConfirmController {

    private static final String SAVE_FOLDER = "src/main/resources/com/example/timeline/save/";

    @FXML
    void quitCancel(ActionEvent event) {
        // ferme uniquement le stage actuel
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void quitConfirmation(ActionEvent event) throws IOException {
        // Récupérer les stages
        Stage modalStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Stage mainStage = (Stage) modalStage.getOwner();
        System.out.println(mainStage.getScene().getUserData() instanceof MainGame);
        System.out.println(mainStage.getScene().getUserData());
        System.out.println(mainStage.getScene() != null);

        // Récupérer la partie en cours depuis le GameController
        if (mainStage.getScene() != null && mainStage.getScene().getUserData() instanceof MainGame) {
            MainGame currentGame = (MainGame) mainStage.getScene().getUserData();
            System.out.println("kkk");

            if (currentGame != null && currentGame.getDeck() != null) {
                // Récupérer les informations du deck
                String deckName = currentGame.getDeck().getTitle();
                System.out.println("cccc");
                // Formater le nom de sauvegarde selon le format heure-minute-jour-mois-annee-deck
                LocalDateTime now = LocalDateTime.now();
                String saveName = String.format("%02d-%02d-%02d-%02d-%d-%s",
                        now.getHour(), now.getMinute(), now.getDayOfMonth(),
                        now.getMonthValue(), now.getYear(), deckName);

                // S'assurer que le dossier de sauvegarde existe
                File saveDir = new File(SAVE_FOLDER);
                if (!saveDir.exists()) {
                    saveDir.mkdirs();
                }

                // Sauvegarder la partie directement dans le dossier des saves
                String filePath = SAVE_FOLDER + saveName + ".bin";
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
                    oos.writeObject(currentGame);
                    System.out.println("Partie sauvegardée sous : " + filePath);
                } catch (IOException e) {
                    System.err.println("Erreur lors de la sauvegarde : " + e.getMessage());
                }
            }
        }
        
        // Retourner au menu principal
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/example/timeline/menu-principal-view.fxml"));
        modalStage.close();
        mainStage.setScene(new Scene(fxmlLoader.load()));
    }
}