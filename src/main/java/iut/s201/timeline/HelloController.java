package iut.s201.timeline;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.List;

public class HelloController {

    @FXML private TextField NameField, dateField, urlField, titleDeckField;
    @FXML private TextArea descArea;
    @FXML private GridPane gridPane;
    @FXML private VBox previewCard;
    @FXML private Button addCardButton; // Référence vers le bouton "Ajouter carte"

    private CardController previewCardController;

    private int cardCount = 0;
    private JsonManager jsonManager;
    private Deck deckActuel;
    private Timeline saveTimer; // Timer pour éviter les sauvegardes trop fréquentes

    // Variables pour gérer la modification
    private boolean modeModification = false;
    private Card carteEnCoursDeModification = null;

    @FXML
    public void initialize() {
        jsonManager = new JsonManager();
        chargerDeckExistant();
        setupPreviewCard();
        setupFieldListeners();

        // Ajouter un listener avec délai pour sauvegarder automatiquement le nom du deck
        if (titleDeckField != null) {
            titleDeckField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (deckActuel != null && newValue != null) {
                    deckActuel.setNom(newValue.trim());

                    // Annuler le timer précédent s'il existe
                    if (saveTimer != null) {
                        saveTimer.stop();
                    }

                    // Créer un nouveau timer avec un délai de 500ms
                    saveTimer = new Timeline(new KeyFrame(Duration.millis(500), e -> {
                        if (!newValue.trim().isEmpty()) {
                            jsonManager.mettreAJourNomDeck(newValue.trim());
                        }
                    }));
                    saveTimer.play();
                }
            });
        }
    }

    // Configure la carte de prévisualisation
    private void setupPreviewCard() {
        if (previewCard != null) {
            try {
                // Charger le contrôleur de la carte de prévisualisation
                FXMLLoader loader = new FXMLLoader(getClass().getResource("card.fxml"));
                Parent cardContent = loader.load();

                // Vider le contenu existant de previewCard et ajouter le nouveau contenu
                previewCard.getChildren().clear();
                previewCard.getChildren().add(cardContent);

                previewCardController = loader.getController();
                if (previewCardController != null) {
                    // Marquer cette carte comme une carte de prévisualisation
                    previewCardController.setAsPreviewCard(true);
                    // Initialiser avec des données par défaut ou vides
                    updatePreviewCard();
                }
            } catch (IOException e) {
                System.err.println("Erreur lors du chargement de la carte de prévisualisation : " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // Configure les listeners pour mettre à jour la carte de prévisualisation en temps réel
    private void setupFieldListeners() {
        if (NameField != null) {
            NameField.textProperty().addListener((observable, oldValue, newValue) -> updatePreviewCard());
        }
        if (dateField != null) {
            dateField.textProperty().addListener((observable, oldValue, newValue) -> updatePreviewCard());
        }
        if (urlField != null) {
            urlField.textProperty().addListener((observable, oldValue, newValue) -> updatePreviewCard());
        }
        if (descArea != null) {
            descArea.textProperty().addListener((observable, oldValue, newValue) -> updatePreviewCard());
        }
    }

    // Met à jour la carte de prévisualisation avec les données des champs en temps réel
    private void updatePreviewCard() {
        if (previewCardController != null) {
            String nom = NameField != null ? NameField.getText() : "";
            String date = dateField != null ? dateField.getText() : "";
            String url = urlField != null ? urlField.getText() : "";
            String description = descArea != null ? descArea.getText() : "";

            // Utiliser des valeurs par défaut si les champs sont vides
            String displayNom = nom.trim().isEmpty() ? "Nom de la carte" : nom;
            String displayDate = date.trim().isEmpty() ? "Année" : date;
            String displayDescription = description.trim().isEmpty() ? "Description de la carte" : description;

            // Mettre à jour la carte de prévisualisation avec les données actuelles
            previewCardController.setCardData(displayNom, displayDate, displayDescription, url);

            System.out.println("Carte de prévisualisation mise à jour : " + displayNom + " (" + displayDate + ")");
        }
    }

    // Nouvelle méthode pour remplir les champs avec les données d'une carte à modifier
    public void remplirChampsPourModification(Card carte) {
        if (carte != null) {
            // Passer en mode modification
            modeModification = true;
            carteEnCoursDeModification = carte;

            // Remplir les champs avec les données de la carte
            if (NameField != null) {
                NameField.setText(carte.getNom() != null ? carte.getNom() : "");
            }
            if (dateField != null) {
                dateField.setText(carte.getDate() != null ? carte.getDate() : "");
            }
            if (urlField != null) {
                urlField.setText(carte.getUrl() != null ? carte.getUrl() : "");
            }
            if (descArea != null) {
                descArea.setText(carte.getDescription() != null ? carte.getDescription() : "");
            }

            // Changer le texte du bouton pour indiquer qu'on est en mode modification
            updateAddButtonText();

            System.out.println("Mode modification activé pour la carte : " + carte.getNom());
        }
    }

    // Met à jour le texte du bouton selon le mode
    private void updateAddButtonText() {
        // Cette méthode nécessiterait d'avoir une référence au bouton
        // Pour l'instant, on peut juste imprimer un message
        if (modeModification) {
            System.out.println("Mode modification : Le bouton devrait afficher 'MODIFIER CARTE'");
        } else {
            System.out.println("Mode ajout : Le bouton devrait afficher 'AJOUTER CARTE'");
        }
    }

    private boolean checkEmptyAndMark(Control field, String value, String message) {
        if (value.trim().isEmpty()) {
            System.out.println(message);
            if (field instanceof TextField) {
                ((TextField) field).setPromptText("Obligatoire");
            } else if (field instanceof TextArea) {
                ((TextArea) field).setPromptText("Obligatoire");
            }
            field.setStyle("-fx-prompt-text-fill: red;");
            return true;
        } else {
            return false;
        }
    }

        @FXML
    public void addCard(ActionEvent event) throws IOException {
        String nom = NameField.getText();
        String date = dateField.getText();
        String url = urlField.getText();
        String description = descArea.getText();

        // Vérifier que les champs obligatoires sont remplis
        boolean hasError = false;
        hasError |= checkEmptyAndMark(NameField, nom, "Le nom est obligatoire");
        hasError |= checkEmptyAndMark(dateField, date, "La date est obligatoire");
        hasError |= checkEmptyAndMark(urlField, url, "L'url est obligatoire");
        hasError |= checkEmptyAndMark(descArea, description, "La description est obligatoire");

        if (hasError) {
            // On arrête ici si au moins un champ est vide
            return;
        }

        if (modeModification && carteEnCoursDeModification != null) {
            // Mode modification : mettre à jour la carte existante
            modifierCarteExistante(nom, date, url, description);
        } else {
            // Mode ajout : créer une nouvelle carte
            ajouterNouvelleCarte(nom, date, url, description);
        }

        clearFields();
        // Sortir du mode modification
        modeModification = false;
        carteEnCoursDeModification = null;
        updateAddButtonText();
    }

    // Méthode pour modifier une carte existante
    private void modifierCarteExistante(String nom, String date, String url, String description) {
        // Mettre à jour les données de la carte
        carteEnCoursDeModification.setNom(nom);
        carteEnCoursDeModification.setDate(date);
        carteEnCoursDeModification.setUrl(url);
        carteEnCoursDeModification.setDescription(description);

        // Sauvegarder le deck
        jsonManager.sauvegarderDeck(deckActuel);

        // Mettre à jour l'affichage de toutes les cartes
        rafraichirAffichageCartes();

        System.out.println("Carte modifiée avec succès : " + nom);
    }

    // Méthode pour ajouter une nouvelle carte
    private void ajouterNouvelleCarte(String nom, String date, String url, String description) throws IOException {
        // Créer un objet Card avec les données
        Card nouvelleCarte = new Card(nom, date, url, description);
        deckActuel.ajouterCarte(nouvelleCarte);

        // Sauvegarder le deck complet dans le fichier JSON
        jsonManager.sauvegarderDeck(deckActuel);
        System.out.println("Carte sauvegardée : " + nouvelleCarte.toString());

        // Charger le FXML de la carte
        FXMLLoader loader = new FXMLLoader(getClass().getResource("card-view.fxml"));
        Parent card = loader.load();

        // Modifier la taille si c'est un Region (VBox, HBox, Pane, etc.)
        if (card instanceof Region) {
            Region region = (Region) card;
            region.setPrefSize(125, 200); // largeur, hauteur
            region.setMinSize(125, 200);
            region.setMaxSize(125, 200);
        }

        // Obtenir le contrôleur de la carte et définir les données
        CardController cardController = loader.getController();
        if (cardController != null) {
            cardController.setCardData(nom, date, description, url);
            cardController.setJsonManager(jsonManager);
            cardController.setCarteAssociee(nouvelleCarte); // Associer la carte au contrôleur
            cardController.setMainController(this); // Passer la référence du contrôleur principal

            // Définir l'action de suppression
            cardController.setOnDeleteAction(() -> {
                deckActuel.supprimerCarte(nouvelleCarte);
                jsonManager.sauvegarderDeck(deckActuel);
                gridPane.getChildren().remove(card);
                cardCount--;
                reorganizeCards();
            });
        }

        // Calcul automatique de la ligne et colonne selon le nombre de cartes
        int col = cardCount % 2; // Colonne : 0 ou 1 (2 colonnes au total)
        int row = cardCount / 2; // Ligne : augmente tous les 2 éléments

        System.out.println("Ajout de la carte " + (cardCount + 1) + " à la position [" + row + "," + col + "]");
        gridPane.add(card, col, row);

        cardCount++;
        System.out.println("Nombre total de cartes: " + cardCount);
    }

    // Méthode pour rafraîchir l'affichage de toutes les cartes
    private void rafraichirAffichageCartes() {
        // Vider le GridPane
        gridPane.getChildren().clear();
        cardCount = 0;

        // Recharger toutes les cartes
        try {
            List<Card> cartes = deckActuel.getCartes();
            for (Card carte : cartes) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("card-view.fxml"));
                Parent cardView = loader.load();

                // Modifier la taille
                if (cardView instanceof Region) {
                    Region region = (Region) cardView;
                    region.setPrefSize(125, 200);
                    region.setMinSize(125, 200);
                    region.setMaxSize(125, 200);
                }

                // Obtenir le contrôleur et définir les données
                CardController cardController = loader.getController();
                if (cardController != null) {
                    cardController.setCardData(
                            carte.getNom(),
                            carte.getDate(),
                            carte.getDescription(),
                            carte.getUrl()
                    );

                    cardController.setJsonManager(jsonManager);
                    cardController.setCarteAssociee(carte); // Associer la carte au contrôleur
                    cardController.setMainController(this); // Passer la référence du contrôleur principal

                    // Définir l'action de suppression
                    cardController.setOnDeleteAction(() -> {
                        deckActuel.supprimerCarte(carte);
                        jsonManager.sauvegarderDeck(deckActuel);
                        gridPane.getChildren().remove(cardView);
                        cardCount--;
                        reorganizeCards();
                    });
                }

                // Calculer la position
                int col = cardCount % 2;
                int row = cardCount / 2;

                gridPane.add(cardView, col, row);
                cardCount++;
            }
        } catch (IOException e) {
            System.err.println("Erreur lors du rafraîchissement de l'affichage : " + e.getMessage());
        }
    }

    // Charge le deck existant depuis le fichier JSON au démarrage
    private void chargerDeckExistant() {
        try {
            deckActuel = jsonManager.chargerDeck();

            // Mettre à jour le champ titre avec le nom du deck
            if (titleDeckField != null && deckActuel.getNom() != null && !deckActuel.getNom().isEmpty()) {
                titleDeckField.setText(deckActuel.getNom());
            }

            // Charger les cartes dans l'interface
            List<Card> cartes = deckActuel.getCartes();
            for (Card carte : cartes) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("card-view.fxml"));
                Parent cardView = loader.load();

                // Modifier la taille
                if (cardView instanceof Region) {
                    Region region = (Region) cardView;
                    region.setPrefSize(125, 200);
                    region.setMinSize(125, 200);
                    region.setMaxSize(125, 200);
                }

                // Obtenir le contrôleur et définir les données
                CardController cardController = loader.getController();
                if (cardController != null) {
                    cardController.setCardData(
                            carte.getNom(),
                            carte.getDate(),
                            carte.getDescription(),
                            carte.getUrl()
                    );

                    // Passer le JsonManager au contrôleur de carte
                    cardController.setJsonManager(jsonManager);
                    cardController.setCarteAssociee(carte); // Associer la carte au contrôleur
                    cardController.setMainController(this); // Passer la référence du contrôleur principal

                    // Définir l'action de suppression
                    cardController.setOnDeleteAction(() -> {
                        deckActuel.supprimerCarte(carte);
                        jsonManager.sauvegarderDeck(deckActuel);
                        gridPane.getChildren().remove(cardView);
                        cardCount--;
                        reorganizeCards();
                    });
                }

                // Calculer la position
                int col = cardCount % 2;
                int row = cardCount / 2;

                gridPane.add(cardView, col, row);
                cardCount++;
            }

            System.out.println("Chargé le deck '" + deckActuel.getNom() + "' avec " + cartes.size() + " cartes");

        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du deck : " + e.getMessage());
            // Créer un nouveau deck en cas d'erreur
            deckActuel = new Deck("Nouveau Deck");
        }
    }

    private void clearFields() {
        if (NameField != null) {
            NameField.clear();
        }
        if (dateField != null) {
            dateField.clear();
        }
        if (urlField != null) {
            urlField.clear();
        }
        if (descArea != null) {
            descArea.clear();
        }
        // Mettre à jour la carte de prévisualisation après avoir vidé les champs
        updatePreviewCard();
    }

    @FXML
    public void close() {
        Stage stage = (Stage) NameField.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void saveCard() {
        // Sauvegarder le deck complet
        if (deckActuel != null) {
            jsonManager.sauvegarderDeck(deckActuel);
            System.out.println("Deck '" + deckActuel.getNom() + "' sauvegardé avec " + deckActuel.getNombreCartes() + " cartes");
        }
        Stage stage = (Stage) NameField.getScene().getWindow();
        stage.close();
    }

    // Réorganise les cartes dans le GridPane après une suppression
    private void reorganizeCards() {
        int count = 0;

        // Créer une copie de la liste des enfants pour éviter les problèmes de concurrence
        java.util.List<javafx.scene.Node> cards = new java.util.ArrayList<>(gridPane.getChildren());

        gridPane.getChildren().clear();

        // Réajouter toutes les cartes dans l'ordre
        for (javafx.scene.Node card : cards) {
            int col = count % 2;
            int row = count / 2;
            gridPane.add(card, col, row);
            count++;
        }
        cardCount = count;
    }
}