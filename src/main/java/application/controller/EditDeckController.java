//package application.controller;
//
//import application.model.Card;
//import application.model.Coord;
//import application.model.Deck;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.TextField;
//import javafx.scene.control.TextArea;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.GridPane;
//import javafx.scene.control.Button;
//import javafx.event.ActionEvent;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class EditDeckController {
// TODO: Modifier avec la bonne methode

//    @FXML
//    private GridPane gridCards;
//    @FXML
//    private TextField nameField, dateField, urlField, titleDeckField;
//    @FXML
//    private TextArea descArea;
//    @FXML
//    private Button addCardButton;
//
//    private Deck deckActuel;
//    private Map<Coord, Card> coordToCard = new HashMap<>();
//    private Coord selectedCoord = null;
//    private int maxColumns = 2;
//    private Map<application.model.Coord, String> mapAnchorToController = new HashMap<>();
//    Coord coordSelected;
//    private String nomDeckAEditer;
//
//    @FXML
//    void onEditDeckClicked(ActionEvent event) throws IOException {
//        String nomDeckSelectionne = mapAnchorToController.get(coordSelected);
//
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("edit-deck-view.fxml"));
//        Parent root = loader.load();
//        EditDeckController editDeckController = loader.getController();
//        editDeckController.setNomDeckAEditer(nomDeckSelectionne);
//
//        Scene scene = new Scene(root);
//        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    public void setNomDeckAEditer(String nomDeck) {
//        this.nomDeckAEditer = nomDeck;
//    }
//
//    @FXML
//    public void initialize() {
//        // Charger le deck existant (à adapter selon ton système)
//        deckActuel = ...; // Charge ton deck ici
//
//        // Afficher toutes les cartes dans la grille
//        afficherCartesDansGrille();
//
//        // Listener pour sélectionner une carte dans la grille
//        gridCards.setOnMouseClicked(event -> {
//            javafx.scene.Node clickedNode = event.getPickResult().getIntersectedNode();
//            while (clickedNode != null && clickedNode != gridCards && !(clickedNode instanceof AnchorPane)) {
//                clickedNode = clickedNode.getParent();
//            }
//            if (clickedNode instanceof AnchorPane) {
//                AnchorPane clickedCard = (AnchorPane) clickedNode;
//                Coord c = new Coord(GridPane.getRowIndex(clickedCard), GridPane.getColumnIndex(clickedCard));
//                selectedCoord = c;
//                Card card = coordToCard.get(c);
//                remplirChampsPourModification(card);
//            }
//        });
//    }
//
//    private void afficherCartesDansGrille() {
//        gridCards.getChildren().clear();
//        coordToCard.clear();
//        List<Card> cartes = deckActuel.getCartes();
//        int column = 0;
//        int row = 0;
//        for (Card card : cartes) {
//            try {
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("/chemin/vers/ton/card-view.fxml"));
//                AnchorPane cardPane = loader.load();
//                CardController controller = loader.getController();
//                controller.setCardData(card.getNom(), card.getDate(), card.getDescription(), card.getUrl());
//                // Ajoute la suppression sur le bouton de la carte
//                controller.setOnDeleteAction(() -> {
//                    deckActuel.supprimerCarte(card);
//                    afficherCartesDansGrille();
//                });
//
//                Coord coord = new Coord(row, column);
//                coordToCard.put(coord, card);
//                gridCards.add(cardPane, column, row);
//
//                column++;
//                if (column >= maxColumns) {
//                    column = 0;
//                    row++;
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @FXML
//    void onAddOrEditCard(ActionEvent event) {
//        String nom = nameField.getText();
//        String year = dateField.getText();
//        String url = urlField.getText();
//        String description = descArea.getText();
//
//        // Vérifie les champs obligatoires ici...
//
//        if (selectedCoord != null && coordToCard.containsKey(selectedCoord)) {
//            // Mode édition
//            Card card = coordToCard.get(selectedCoord);
//            card.setNom(nom);
//            card.setDate(year);
//            card.setUrl(url);
//            card.setDescription(description);
//        } else {
//            // Mode ajout
//            Card nouvelleCarte = new Card(nom, year, url, description);
//            deckActuel.ajouterCarte(nouvelleCarte);
//        }
//        afficherCartesDansGrille();
//        clearFields();
//        selectedCoord = null;
//    }
//
//    private void remplirChampsPourModification(Card card) {
//        if (card != null) {
//            nameField.setText(card.getNom());
//            dateField.setText(card.getDate());
//            urlField.setText(card.getUrl());
//            descArea.setText(card.getDescription());
//            addCardButton.setText("Modifier la carte");
//        }
//    }
//
//    private void clearFields() {
//        nameField.clear();
//        dateField.clear();
//        urlField.clear();
//        descArea.clear();
//        addCardButton.setText("Ajouter une carte");
//    }
//
//    // Classe interne pour gérer les coordonnées
//    private static class Coord {
//        private final int row;
//        private final int column;
//        public Coord(int row, int column) {
//            this.row = row;
//            this.column = column;
//        }
//        // equals() et hashCode() à override pour HashMap !
//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (!(o instanceof Coord)) return false;
//            Coord coord = (Coord) o;
//            return row == coord.row && column == coord.column;
//        }
//        @Override
//        public int hashCode() {
//            return 31 * row + column;
//        }
//    }
//}
