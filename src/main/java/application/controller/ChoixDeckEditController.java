package application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ChoixDeckEditController {

    @FXML
    private Button backBtn;

    @FXML
    private GridPane gridDeck;

//    @FXML
//    void initialize() {
//        // Chargement des decks depuis le fichier JSON
//        JSONManipulator json = new JSONManipulator("src/main/resources/fr/ynryo/timeline/json/decks.json");
//        List<String> deckIds = json.getAllDeckIds();
//
//        try {
//            // Configuration pour disposer les decks dans le GridPane
//            int column = 0;
//            int row = 0;
//            int maxColumns = 3; // Basé sur les contraintes de colonnes dans le FXML
//
//            for (String deckId : deckIds) {
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/ynryo/timeline/deck-presentation.fxml"));
//
//                AnchorPane deckPresentation = loader.load();
//
//
//                // Récupération du contrôleur
//                DeckIconeController deckController = loader.getController();
//
//
//                // Configuration des données du deck
////                deckController.setDecklibController(this);
//                deckController.setTextNomDeck(json.getDeckName(deckId));
//                deckController.setTextNbCartes(String.valueOf(json.getCards(deckId).size()));
//
//                // Positionnement dans le GridPane
//                gridDeck.add(deckPresentation, column, row);
//
//                // Mise à jour des colonnes et rangées
//                column++;
//                if (column >= maxColumns) {
//                    column = 0;
//                    row++;
//                }
//            }
//        } catch (IOException e) {
//            System.err.println("Erreur lors du chargement des présentations de deck: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }

    @FXML
    void back(ActionEvent event) throws IOException {
        Stage stage = (Stage) backBtn.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fr/ynryo/timeline/menu-principal-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }

}
