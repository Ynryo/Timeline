package application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class DeckPresentationController {

    @FXML
    private Label TextNbCartes;

    @FXML
    private Label TextNomDeck;

    @FXML
    private ImageView firstCardImg;

    private String selected;
    private DeckLibController controller;


    public void setNomDeck(String Nom){
        TextNomDeck.setText(Nom);
    }

    public void setNbCartes(String NBcartes){
        TextNbCartes.setText(NBcartes);
    }

    public void setImgView(String URLImage) {
        Image image = new Image(URLImage);
        firstCardImg.setImage(image);
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }
    public void setDecklibController(DeckLibController controller) {
        this.controller = controller;
    }

    @FXML
    void onClicked(MouseEvent event) throws IOException {
        controller.setSelectedClicked(selected);
//        deck_presentation.setEffect(null);
//        deck_presentation.setScaleX(1.1);
//        deck_presentation.setScaleY(1.1);

    }

}