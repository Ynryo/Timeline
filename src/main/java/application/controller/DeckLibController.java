package application.controller;

import application.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class DeckLibController {

    @FXML
    private GridPane gridDeck;
    private String selected = null;

    public void initialize() {
        // Chargement des decks depuis le fichier JSON
        JSONManipulator json = new JSONManipulator("src/main/resources/com/example/timeline/json/decks.json");
        List<String> deckIds = json.getAllDeckIds();
        
        try {
            // Configuration pour disposer les decks dans le GridPane
            int column = 0;
            int row = 0;
            int maxColumns = 3; // Basé sur les contraintes de colonnes dans le FXML
            
            for (String deckId : deckIds) {
                // Chargement du fichier FXML de présentation de deck
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/timeline/deck-presentation.fxml"));
                AnchorPane deckPresentation = loader.load();
                
                // Récupération du contrôleur
                DeckPresentationController deckController = loader.getController();
                
                // Configuration des données du deck
                deckController.setNomDeck(json.getDeckName(deckId));
                deckController.setNbCartes(String.valueOf(json.getCards(deckId).size()));
                deckController.setSelected(deckId);
                
                // Positionnement dans le GridPane
                gridDeck.add(deckPresentation, column, row);
                
                // Mise à jour des colonnes et rangées
                column++;
                if (column >= maxColumns) {
                    column = 0;
                    row++;
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des présentations de deck: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void onPlayClicked(ActionEvent event) throws IOException {
        System.out.println("selected = " + selected);
        if (selected != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/example/timeline/game-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            GameController controller = fxmlLoader.getController();
            controller.setSelectedDeck(selected);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }

    }

    public void setSelectedClicked(String selected) {
        this.selected = selected;
        System.out.println("selected = " + this.selected);
    }
}