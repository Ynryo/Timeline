package application.controller;

import application.model.Deck;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class DeckLibController {

    @FXML
    private GridPane gridDeck;

    public void initialize() {

        JSONManipulator json = new JSONManipulator("src/main/resources/com/example/timeline/json/decks.json");
        System.out.println("Nombre de decks : " + json.getAllDeckIds().size());
        for (String deckId : json.getAllDeckIds()) {
            Deck deck = (Deck) json.getDeckInfo(deckId);
        }

//                if (deck != null) {
//                    System.out.println("Deck : " + deck.getNom() + " | Cartes : " + deck.getNombreCartes());
//
//                    int row = i / 3;
//                    int col = i % 3;
//
//                    try {
//                        FXMLLoader loader = new FXMLLoader(getClass().getResource("deck-presentation.fxml"));
//                        Node card = loader.load();
//                        DeckPresentationController controller = loader.getController();
//
//                        // Envoi des données réelles
//                        controller.setNomDeck(deck.getNom());
//                        controller.setNbCartes(String.valueOf(deck.getNombreCartes()));
//
//                        GridPane.setHalignment(card, HPos.CENTER);
//                        GridPane.setValignment(card, VPos.CENTER);
//                        GridPane.setHgrow(card, Priority.NEVER);
//                        GridPane.setVgrow(card, Priority.NEVER);
//                        GridPane.setMargin(card, new Insets(50, 50, 50, 50));
//
//                        gridDeck.add(card, col, row);
//
//                    } catch (IOException e) {
//                        System.err.println("Erreur lors du chargement de deck-presentation.fxml : " + e.getMessage());
//                    }
//                }
            }
        }
//    }
//}
