package application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DeckPresentationController {

    @FXML private Label TextNomDeck;
    @FXML private Label TextNbCartes;

    public void setNomDeck(String Nom){
        TextNomDeck.setText(Nom);
    }

    public void setNbCartes(String NBcartes){
        TextNbCartes.setText(NBcartes);
    }

}