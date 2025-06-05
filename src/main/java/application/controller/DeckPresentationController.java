package application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class DeckPresentationController {

    @FXML
    private Label TextNbCartes;

    @FXML
    private Label TextNomDeck;

    @FXML
    private AnchorPane deck_presentation;
    private String selected;
    private DeckLibController controller;


    public void setNomDeck(String Nom){
        TextNomDeck.setText(Nom);
    }

    public void setNbCartes(String NBcartes){
        TextNbCartes.setText(NBcartes);
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    @FXML
    void onClicked(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/example/timeline/deck-view.fxml"));
        Parent root = fxmlLoader.load();
        DeckLibController controller = fxmlLoader.getController();
        controller.setSelectedClicked(selected);
//        deck_presentation.setEffect(null);
//        deck_presentation.setScaleX(1.1);
//        deck_presentation.setScaleY(1.1);

    }

}