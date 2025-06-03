package application.controller;

import application.model.Card;
import application.model.Hand;
import application.model.MainGame;
import application.views.CardViewOnHand;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameController {
    @FXML
    private HBox dropZone;
    @FXML
    private HBox handZone;
    @FXML
    private Pane dragLayer;
    @FXML
    private Label titreDeck;

    private MainGame model;


    private final Pane placeholder = new Pane();

    public GameController() {
        super();
    }

    @FXML
    public void initialize() throws IOException {

        initUI();
        model = new MainGame();
        initUIFromModel();
    }

    private void initUI() {
        handZone.getChildren().clear();
    }

    private void initUIFromModel() {
        titreDeck.setText(model.getDeck().getTitle());
        handZone.getChildren().clear();
        displayPlayerHand();
    }

    private void displayPlayerHand() {
        Hand hand = model.getPlayerHand();

        for (Card aCard : hand.getCards()) {
            CardOnHandController controller = new CardOnHandController(aCard, this);
            CardViewOnHand view = new CardViewOnHand(controller);
            makeDraggable(view.getVBoxCard());

            handZone.getChildren().add(view);
        }
    }


    private void makeDraggable(Node card) {
        final double[] offset = new double[2];

        placeholder.setPrefSize(50, 90);
        placeholder.setMinSize(50, 90);
        placeholder.setMaxSize(50, 90);
        placeholder.setStyle("-fx-background-color: rgba(0, 0, 0, 0.2); -fx-background-radius: 10;");

        card.setOnMousePressed(e -> {
            Bounds bounds = card.localToScene(card.getBoundsInLocal());
            offset[0] = e.getSceneX() - bounds.getMinX();
            offset[1] = e.getSceneY() - bounds.getMinY();
            int initialIndex = handZone.getChildren().indexOf(card);
            card.setUserData(new Object[]{bounds.getMinX(), bounds.getMinY(), initialIndex});

            ((Pane) card.getParent()).getChildren().remove(card);
            dragLayer.getChildren().add(card);
            card.setLayoutX(bounds.getMinX());
            card.setLayoutY(bounds.getMinY());
            card.setCursor(Cursor.CLOSED_HAND);
        });

        card.setOnMouseDragged(e -> {
            card.setLayoutX(e.getSceneX() - offset[0]);
            card.setLayoutY(e.getSceneY() - offset[1]);

            Bounds cardBounds = card.localToScene(card.getBoundsInLocal());
            Bounds dropBounds = dropZone.localToScene(dropZone.getBoundsInLocal());
            if (dropBounds.intersects(cardBounds)) {
                double centerX = cardBounds.getMinX() + cardBounds.getWidth() / 2;
                int insertIndex = 0;
                for (Node child : dropZone.getChildren()) {
                    if (child != placeholder && child != card) {
                        Bounds cb = child.localToScene(child.getBoundsInLocal());
                        double childCenter = cb.getMinX() + cb.getWidth() / 2;
                        if (centerX > childCenter) {
                            insertIndex++;
                        }
                    }
                }
                dropZone.getChildren().remove(placeholder);
                dropZone.getChildren().add(insertIndex, placeholder);
            } else {
                dropZone.getChildren().remove(placeholder);
            }
            updateCardOverlapping();
        });

        card.setOnMouseReleased(e -> {
            card.setCursor(Cursor.HAND);
            card.setMouseTransparent(true);
            dropZone.getChildren().remove(placeholder);

            Bounds nodeBounds = card.localToScene(card.getBoundsInLocal());
            Bounds dropBounds = dropZone.localToScene(dropZone.getBoundsInLocal());
            boolean droppedInHBox = dropBounds.intersects(nodeBounds);

            if (droppedInHBox) {
                Bounds sceneBefore = card.localToScene(card.getBoundsInLocal());
                dragLayer.getChildren().remove(card);
                double centerX = sceneBefore.getMinX() + sceneBefore.getWidth() / 2;

                int insertIndex = 0;
                for (Node child : dropZone.getChildren()) {
                    if (child != placeholder) {
                        Bounds cb = child.localToScene(child.getBoundsInLocal());
                        if (centerX > cb.getMinX() + cb.getWidth() / 2) {
                            insertIndex++;
                        }
                    }
                }
                dropZone.getChildren().add(insertIndex, card);
                dropZone.applyCss();
                dropZone.layout();

                Bounds sceneAfter = card.localToScene(card.getBoundsInLocal());
                double dx = sceneBefore.getMinX() - sceneAfter.getMinX();
                double dy = sceneBefore.getMinY() - sceneAfter.getMinY();
                card.setTranslateX(dx);
                card.setTranslateY(dy);

                TranslateTransition tt = new TranslateTransition(Duration.millis(120), card);
                tt.setInterpolator(Interpolator.SPLINE(0.25, 0.1, 0.25, 1));
                tt.setToX(0);
                tt.setToY(0);
                tt.setOnFinished(evt -> {
                    card.setOnMousePressed(null);
                    card.setOnMouseDragged(null);
                    card.setOnMouseReleased(null);
                    card.setOnMouseEntered(null);
                    card.setCursor(Cursor.DEFAULT);
                    card.setTranslateX(0);
                    card.setTranslateY(0);
                    card.setMouseTransparent(false);
                    updateCardOverlapping();
                });
                tt.play();

            } else {
                Object[] data = (Object[]) card.getUserData();
                double initX = (double) data[0];
                double initY = (double) data[1];
                int initIdx = (int) data[2];

                TranslateTransition transition = new TranslateTransition(Duration.millis(250), card);
                transition.setInterpolator(Interpolator.SPLINE(0.25, 0.1, 0.25, 1));
                transition.setToX(initX - card.getLayoutX());
                transition.setToY(initY - card.getLayoutY());
                transition.setOnFinished(event -> {
                    dragLayer.getChildren().remove(card);
                    if (initIdx >= 0 && initIdx <= handZone.getChildren().size()) {
                        handZone.getChildren().add(initIdx, card);
                    } else {
                        handZone.getChildren().add(card);
                    }
                    card.setTranslateX(0);
                    card.setTranslateY(0);
                    card.setMouseTransparent(false);
                    updateCardOverlapping();
                });
                transition.play();
            }
        });
        card.setOnMouseEntered(e -> card.setCursor(Cursor.HAND));
    }

    private void updateCardOverlapping() {
        int total = dropZone.getChildren().size();
        int placeholderIndex = dropZone.getChildren().indexOf(placeholder);
        double overlap = Math.min(40, total * 2.5);
        double totalWidth = 0;
        for (int i = 0; i < total; i++) {
            Node node = dropZone.getChildren().get(i);
            double horizontalOffset = -i * overlap;

            if (placeholderIndex != -1) {
                if (i < placeholderIndex) {
                    horizontalOffset -= overlap +10;
                } else if (i > placeholderIndex) {
                    horizontalOffset += overlap +10;
                }
            }
            node.setTranslateX(horizontalOffset);
            totalWidth = Math.max(totalWidth, Math.abs(horizontalOffset));
        }
        for (Node node : dropZone.getChildren()) {
            node.setTranslateX(node.getTranslateX() + totalWidth / 2);
        }
    }

}
