package application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;



public class DeckIconeController {

    @FXML
    private Label TextNbCartes;

    @FXML
    private Label TextNomDeck;

    public void setTextNomDeck(String textNomDeck) {
        TextNomDeck.setText(textNomDeck);
    }

    public void setTextNbCartes(String textNbCartes) {
        TextNbCartes.setText(textNbCartes);
    }


}
