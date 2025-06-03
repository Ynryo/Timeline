package application.controller;

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

    private final List<Parent> gameCards = new ArrayList<>();
    private final Pane placeholder = new Pane();

    @FXML
    public void initialize() throws IOException {
        for (int i = 1900; i < 10+1900; i++) {
            Parent card = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/timeline/card.fxml")));
            card.setScaleX(1);
            card.setScaleY(1);
            Label label = (Label) ((Pane) card).getChildren().get(0);
            label.setText(String.valueOf(i + 1));
            makeDraggable(card);
            handZone.getChildren().add(card);
            gameCards.add(card);
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

                TranslateTransition transition = new TranslateTransition(Duration.millis(120), card);
                transition.setInterpolator(Interpolator.SPLINE(0.25, 0.1, 0.25, 1));
                transition.setToX(0);
                transition.setToY(0);
                transition.setOnFinished(evt -> {
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
                transition.play();

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
