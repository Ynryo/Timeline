//package application.controller;
//
//import application.model.Card;
//import javafx.fxml.FXML;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.layout.VBox;
//import javafx.scene.shape.Rectangle;
//import javafx.scene.text.Font;
//import javafx.scene.text.FontWeight;
//import javafx.scene.text.Text;
//
//public class CardController {
//
//    // Constantes pour la configuration
//    private static final double DEFAULT_FONT_SIZE = 14.0;
//    private static final double MIN_FONT_SIZE = 8.0;
//    private static final double FONT_STEP = 0.5;
//    private static final double LABEL_MARGIN = 20.0;
//    private static final double CORNER_RADIUS = 30.0;
//    private static final double CLIP_HEIGHT_OFFSET = 15.0;
//
//    // Éléments FXML
//    @FXML private Label titleLabel, dateLabel;
//    @FXML private VBox backgroundCard;
//    @FXML private Button deleteButton;
//
//    // Callback pour la suppression
//    private Runnable onDeleteAction;
////    private JSONManipulator jsonManager;
//    private boolean isPreviewCard = false; // Nouveau flag pour identifier les cartes de prévisualisation
//
//    // Nouvelle propriété pour stocker la référence à la carte associée
//    private Card carteAssociee;
//
//    // Référence vers le contrôleur principal pour accéder aux champs de texte
////    private EditDeckController mainController;
//
//    @FXML
//    public void initialize() {
//        setupDeleteButton();
//        setupTitleLabel();
//        setupBackgroundCard();
//    }
//
//    public void setJsonManager(JSONManipulator jsonManager) {
//        this.jsonManager = jsonManager;
//    }
//
//    // Nouvelle méthode pour marquer cette carte comme une carte de prévisualisation
//    public void setAsPreviewCard(boolean isPreview) {
//        this.isPreviewCard = isPreview;
//    }
//
//    // Nouvelle méthode pour définir la carte associée
//    public void setCarteAssociee(Card carte) {
//        this.carteAssociee = carte;
//    }
//
//    // Nouvelle méthode pour définir la référence vers le contrôleur principal
////    public void setMainController(EditDeckController controller) {
////        this.mainController = controller;
////    }
//
//    // Configure le bouton de suppression - CORRIGÉ
//    private void setupDeleteButton() {
//        if (deleteButton != null) {
//            deleteButton.setOnAction(event -> {
//                if (onDeleteAction != null) {
//                    onDeleteAction.run();
//                }
//            });
//        }
//    }
//
//    // Configure le label du titre avec ajustement automatique de la police
//    private void setupTitleLabel() {
//        if (titleLabel != null) {
//            titleLabel.widthProperty().addListener((obs, oldVal, newVal) -> adjustFontSize());
//            titleLabel.textProperty().addListener((obs, oldVal, newVal) -> adjustFontSize());
//            adjustFontSize(); // Ajustement initial
//        }
//    }
//
//    // Configure le conteneur principal avec les coins arrondis
//    private void setupBackgroundCard() {
//        if (backgroundCard != null) {
//            backgroundCard.widthProperty().addListener((obs, oldVal, newVal) -> updateRoundedClip());
//            backgroundCard.heightProperty().addListener((obs, oldVal, newVal) -> updateRoundedClip());
//        }
//    }
//
//    // Met à jour le clipping pour créer les coins arrondis
//    private void updateRoundedClip() {
//        if (backgroundCard == null || !hasValidDimensions()) {
//            return;
//        }
//
//        double width = backgroundCard.getWidth();
//        double height = backgroundCard.getHeight();
//
//        Rectangle clip = createRoundedClip(width, height);
//        backgroundCard.setClip(clip);
//    }
//
//    // Vérifie si les dimensions du conteneur sont valides
//    private boolean hasValidDimensions() {
//        return backgroundCard.getWidth() > 0 && backgroundCard.getHeight() > 0;
//    }
//
//    // Crée un rectangle de clipping avec coins arrondis
//    private Rectangle createRoundedClip(double width, double height) {
//        Rectangle clip = new Rectangle();
//        clip.setWidth(width);
//
//        // Si c'est une carte de prévisualisation, on garde la hauteur normale (tous les angles arrondis)
//        // Sinon, on ajoute l'offset pour arrondir seulement le haut
//        if (isPreviewCard) {
//            clip.setHeight(height);
//        } else {
//            clip.setHeight(height + CLIP_HEIGHT_OFFSET);
//        }
//
//        clip.setArcWidth(CORNER_RADIUS);
//        clip.setArcHeight(CORNER_RADIUS);
//        clip.setX(0);
//        clip.setY(0);
//        return clip;
//    }
//
//    // Ajuste automatiquement la taille de la police du titre
//    private void adjustFontSize() {
//        if (!isValidTitleLabel()) {
//            return;
//        }
//
//        String text = titleLabel.getText();
//        double availableWidth = titleLabel.getWidth() - LABEL_MARGIN;
//        double optimalFontSize = calculateOptimalFontSize(text, availableWidth);
//
//        applyFontStyle(optimalFontSize);
//    }
//
//    // Vérifie si le label du titre est valide
//    private boolean isValidTitleLabel() {
//        return titleLabel != null &&
//                titleLabel.getText() != null &&
//                !titleLabel.getText().trim().isEmpty();
//    }
//
//    // Calcule la taille de police optimale pour le texte donné
//    private double calculateOptimalFontSize(String text, double availableWidth) {
//        double fontSize = DEFAULT_FONT_SIZE;
//
//        while (fontSize > MIN_FONT_SIZE) {
//            if (textFitsInWidth(text, fontSize, availableWidth)) {
//                break;
//            }
//            fontSize -= FONT_STEP;
//        }
//
//        return fontSize;
//    }
//
//    // Vérifie si le texte tient dans la largeur disponible avec la police donnée
//    private boolean textFitsInWidth(String text, double fontSize, double availableWidth) {
//        Font testFont = Font.font("Arial", FontWeight.BOLD, fontSize);
//        Text textNode = new Text(text);
//        textNode.setFont(testFont);
//        double textWidth = textNode.getLayoutBounds().getWidth();
//
//        return textWidth <= availableWidth;
//    }
//
//    // Applique le style de police au label du titre
//    private void applyFontStyle(double fontSize) {
//        String style = String.format(
//                "-fx-text-fill: white; " +
//                        "-fx-font-weight: bold; " +
//                        "-fx-font-size: %.1fpx;",
//                fontSize
//        );
//        titleLabel.setStyle(style);
//    }
//
//    // Définit l'action à exécuter lors de la suppression
//    public void setOnDeleteAction(Runnable action) {
//        this.onDeleteAction = action;
//    }
//
//    // Met à jour les données de la carte
//    public void setCardData(String title, String date, String description, String imageUrl) {
//        updateLabels(title, date);
//        updateBackground(imageUrl);
//    }
//
//    // Met à jour les labels de titre et de year
//    private void updateLabels(String title, String date) {
//        if (titleLabel != null) {
//            titleLabel.setText(title);
//        }
//        if (dateLabel != null) {
//            dateLabel.setText(date);
//        }
//    }
//
//    // Met à jour l'arrière-plan de la carte
//    private void updateBackground(String imageUrl) {
//        if (isValidImageUrl(imageUrl)) {
//            applyImageBackground(imageUrl);
//        } else {
//            applyDefaultBackground();
//        }
//    }
//
//    // Vérifie si l'URL de l'image est valide
//    private boolean isValidImageUrl(String imageUrl) {
//        return imageUrl != null && !imageUrl.trim().isEmpty();
//    }
//
//    // Applique une image comme arrière-plan
//    private void applyImageBackground(String imageUrl) {
//        try {
//            String imageStyle = String.format(
//                    "-fx-background-image: url('%s'); " +
//                            "-fx-background-size: cover; " +
//                            "-fx-background-position: center; " +
//                            "-fx-background-repeat: no-repeat;",
//                    imageUrl
//            );
//            backgroundCard.setStyle(imageStyle);
//            System.out.println("Image appliquée avec succès : " + imageUrl);
//
//        } catch (Exception e) {
//            System.err.println("Erreur lors du chargement de l'image: " + e.getMessage());
//            applyDefaultBackground();
//        }
//    }
//
//    // Lorsque Bouton modifier est cliqué, remplir les champs avec les données de la carte
////    @FXML
////    public void modifierCarte() {
////        if (mainController != null && carteAssociee != null) {
////            // Remplir les champs de texte avec les données de la carte
////            mainController.remplirChampsPourModification(carteAssociee);
////            System.out.println("Carte en cours de modification : " + carteAssociee.getNom());
////        } else {
////            System.err.println("Erreur : Impossible de modifier la carte - références manquantes");
////        }
////    }
//
//    // Applique l'arrière-plan par défaut
//    private void applyDefaultBackground() {
//        backgroundCard.setStyle(
//                "-fx-background-color: linear-gradient(to bottom, #000000, #1c1c1c);"
//        );
//    }
//}