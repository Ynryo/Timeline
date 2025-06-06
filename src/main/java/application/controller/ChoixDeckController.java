package application.controller;

import application.MainApplication;
import application.model.Coord;
import application.pojo.CollectionPOJO;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ChoixDeckController {
    @FXML
    private GridPane gridDeck;
    private Coord coordSelected;
    private Map<Coord, String> mapAnchorToController = new HashMap<>();


    @FXML
    void initialize()  {

        ObjectMapper mapper = new ObjectMapper();
        File file = new File("src/main/resources/com/example/timeline/json/decks.json");
        CollectionPOJO[] allDecks = null;
        try {
            allDecks = mapper.readValue(file, CollectionPOJO[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int column = 0;
        int row = 0;
        int maxColumns = 3;

        for (CollectionPOJO deck : allDecks){

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/timeline/deck-icone-view.fxml"));

            try {
                AnchorPane deckPresentation = loader.load();

                DeckIconeController controller = loader.getController();
                Coord coord = new Coord(row, column);
                //ajout au dictionnaire
                mapAnchorToController.put(coord,deck.name);

                controller.setTextNomDeck(deck.name);
                controller.setTextNbCartes(String.valueOf(deck.cards.length));

                gridDeck.add(deckPresentation, column, row);


            } catch (IOException e) {
                e.printStackTrace();
            }

            column++;
            if (column >= maxColumns) {
                column = 0;
                row++;

            }

        }
        gridDeck.setOnMouseClicked(event -> {
            Node clickedNode = event.getPickResult().getIntersectedNode();

            while (clickedNode != null && clickedNode != gridDeck && !(clickedNode instanceof AnchorPane)) {
                clickedNode = clickedNode.getParent();
            }

            if (clickedNode instanceof AnchorPane) {
                AnchorPane clickedDeck = (AnchorPane) clickedNode;
                Coord c = new Coord(GridPane.getRowIndex(clickedDeck),GridPane.getColumnIndex(clickedDeck));
                coordSelected = c;
            }
        });

    }


    @FXML
    void onPlayClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/example/timeline/game-view.fxml"));
        Parent root = fxmlLoader.load();
        GameController aGameController = fxmlLoader.getController();

        String s = mapAnchorToController.get(coordSelected);
        System.out.println(s);
        aGameController.setDeck(s);  // Passe le deck ici AVANT de créer la scène

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);

        stage.show();
    }

    public void back(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/timeline/menu-principal-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(fxmlLoader.load()));
    }
}