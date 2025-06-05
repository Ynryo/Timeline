package application.controller;

import application.model.Card;
import application.model.Deck;
import application.model.Hand;
import application.model.MainGame;
import application.views.CardViewOnHand;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GameController {
    @FXML
    private HBox dropZone;
    @FXML
    private HBox handZone;
    @FXML
    private StackPane drawZone;
    @FXML
    private Pane dragLayer;
    @FXML
    private Label titreDeck;
    @FXML
    private Label pointLabel;
    @FXML
    private Label victoryLabel;
    private boolean isAnimationInProgress = false;
    private MainGame model;
    private int points;
    private boolean perfect;
    private final Pane placeholder = new Pane();
    private final List<CardViewOnHand> timelineCards = new ArrayList<>();
    private String selectedDeck;

    public GameController() {
        super();
    }

    @FXML
    public void initialize() throws IOException {

        initUI();
        model = new MainGame(selectedDeck);
        initUIFromModel();

    }

    public void setSelectedDeck(String selectedDeck) {
        this.selectedDeck = selectedDeck;
    }

    private void initUI() {
        handZone.getChildren().clear();
    }

    private void initUIFromModel() {
        dropZone.setPrefWidth(641);
        dropZone.setMaxWidth(641);
        points = 0;
        perfect = true;
        victoryLabel.setVisible(false);
        pointLabel.setText("Points : " + points);
        titreDeck.setText(model.getDeck().getTitle());
        handZone.getChildren().clear();
        displayPlayerHand();
        displayFirstCard();
        displayDraw();
    }

    private void displayPlayerHand() {
        Hand hand = model.getPlayerHand();

        for (Card aCard : hand.getCards()) {
            CardViewOnHand view = createViewCard(aCard, this);
            view.setScaleX(1.2);
            view.setScaleY(1.2);
            handZone.getChildren().add(view);

        }
    }
    @FXML
    void onQuitterClicked(ActionEvent event) {
        //TODO: save bin
    }

    @FXML
    void onReglesClicked(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/timeline/regle.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Règles du jeu");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void displayDraw() {
        Deck deck = model.getDeck();
        while (!deck.isDrawEmpty()) {
            Card card = deck.drawCard();
            CardViewOnHand view = createViewCard(card, this);
            view.setScaleX(1.2);
            view.setScaleY(1.2);
            drawZone.getChildren().add(view);
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/timeline/unknown-card-view.fxml"));
            Node unknownCardView = loader.load();
            unknownCardView.setScaleX(1.2);
            unknownCardView.setScaleY(1.2);


            drawZone.getChildren().add(unknownCardView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayFirstCard() {
        CardViewOnHand view = createViewCard(model.getFirstCard(), this);
        view.revealDate();
        view.setScaleX(1.2);
        view.setScaleY(1.2);
        dropZone.getChildren().add(view);
        timelineCards.add(view);

    }

    private CardViewOnHand createViewCard(Card aCard, GameController aGameController) {
        CardOnHandController controller = new CardOnHandController(aCard, this);
        CardViewOnHand view = new CardViewOnHand(controller);
        if (aGameController.model.getPlayerHand().containsCard(aCard) && aCard != aGameController.model.getFirstCard()) {
            makeDraggable(view);
        }
        return view;
    }


    private void makeDraggable(CardViewOnHand card) {


        final double[] offset = new double[2];

        placeholder.setPrefSize(50, 90);
        placeholder.setMinSize(50, 90);
        placeholder.setMaxSize(50, 90);
        placeholder.setStyle("-fx-background-color: rgba(0, 0, 0, 0.2); -fx-background-radius: 10;");

        card.setOnMousePressed(e -> {

            System.out.println("test");
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
            Bounds adjustedDropBounds = new BoundingBox(dropBounds.getMinX(), dropBounds.getMinY() + 30, dropBounds.getWidth(), dropBounds.getHeight() - 60);

            if (adjustedDropBounds.intersects(cardBounds)) {
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
            Bounds adjustedDropBounds = new BoundingBox(dropBounds.getMinX(), dropBounds.getMinY() + 30, dropBounds.getWidth(), dropBounds.getHeight() - 60
            );
            boolean droppedInHBox = adjustedDropBounds.intersects(nodeBounds);
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
                card.revealDate();


                int Index = Math.min(insertIndex, timelineCards.size());
                timelineCards.add(Index, card);

                System.out.println("Dates:");
                for (CardViewOnHand cardDisplay : timelineCards) {
                    System.out.println(cardDisplay.getDate());
                }


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

                    checkPlacement(card, Index);
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
        card.setOnMouseEntered(e -> {
            card.setCursor(Cursor.HAND);
        });
    }

    public boolean isTimelineSorted() {
        for (int i = 1; i < timelineCards.size(); i++) {
            int prevDate = Integer.parseInt(timelineCards.get(i - 1).getDate());
            int currentDate = Integer.parseInt(timelineCards.get(i).getDate());

            if (currentDate < prevDate) {
                return false;
            }
        }
        return true;
    }

    private void animateVictory() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(800));
        fadeTransition.setNode(victoryLabel);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        TranslateTransition ttransition = new TranslateTransition();
        ttransition.setDuration(Duration.millis(800));
        ttransition.setInterpolator(Interpolator.SPLINE(0, 0.1, 0.25, 1));
        ttransition.setNode(victoryLabel);
        ttransition.setByY(80);
        victoryLabel.setVisible(true);
        fadeTransition.play();
        ttransition.play();
    }

    private void animateScoreIncrease(String pts, String color) {
        Label scoreIncreaseLabel = new Label(pts);
        scoreIncreaseLabel.setStyle("-fx-font-size: 30px;-fx-text-fill:" + color + "; -fx-font-family: 'Koulen'");
        Pane container = (Pane) pointLabel.getParent();
        scoreIncreaseLabel.setLayoutX(pointLabel.getLayoutX() + pointLabel.getWidth() / 2 - 10);
        scoreIncreaseLabel.setLayoutY(pointLabel.getLayoutY() + 10);
        scoreIncreaseLabel.setLayoutX(pointLabel.getLayoutX() + 120);

        container.getChildren().add(scoreIncreaseLabel);
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), scoreIncreaseLabel);
        translateTransition.setByY(-40);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), scoreIncreaseLabel);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);

        translateTransition.setOnFinished(e -> {
            container.getChildren().remove(scoreIncreaseLabel);
        });

        translateTransition.play();
        fadeTransition.play();
    }

    private void checkPlacement(CardViewOnHand card, int Index) {
        if (isTimelineSorted()) {
            colorOutline(card, "#88ff00");

            if (handZone.getChildren().size() == 0) {
                if (perfect) {
                    points += 7;
                    pointLabel.setText("Points : " + points);
                    animateScoreIncrease("+2 +5", "#ffaa00");
                } else {
                    points += 2;
                    pointLabel.setText("Points : " + points);
                    animateScoreIncrease("+2", "#7bff00");
                }
                animateVictory();

            } else {
                points++;
                pointLabel.setText("Points : " + points);
                animateScoreIncrease("+1", "#7bff00");
            }
        } else {
            colorOutline(card, "#ff0004");
            points--;
            pointLabel.setText("Points : " + points);
            animateScoreIncrease("-1", "#ff0011");
            perfect = false;
            PauseTransition pause = new PauseTransition(Duration.millis(1000));
            PauseTransition pause2 = new PauseTransition(Duration.millis(500));

            pause.setOnFinished(event -> {
                moveCardToCorrectPosition(card);
                pause2.play();
            });
            pause2.setOnFinished(event -> {
                drawCardInHand();
            });
            pause.play();

        }
    }

    private void moveCardToCorrectPosition(CardViewOnHand card) {
        int cardDate = Integer.parseInt(card.getDate());
        int insertIndex = -1;

        for (int i = 0; i < timelineCards.size(); i++) {
            int currentCardDate = Integer.parseInt(timelineCards.get(i).getDate());
            if (cardDate < currentCardDate) {
                insertIndex = i;
                break;
            }
        }
        if (insertIndex == -1) {
            insertIndex = timelineCards.size();
        }

        int currentIndex = timelineCards.indexOf(card);
        if (currentIndex == insertIndex) {
            return;
        }
        timelineCards.remove(card);
        if (insertIndex <= timelineCards.size()) {
            timelineCards.add(insertIndex, card);
        } else {
            timelineCards.add(card);
        }
        dropZone.getChildren().remove(card);
        dropZone.getChildren().add(card);
        dropZone.getChildren().clear();
        Collections.sort(timelineCards, Comparator.comparingInt(a -> Integer.parseInt(a.getDate())));

        for (CardViewOnHand cardView : timelineCards) {
            dropZone.getChildren().add(cardView);
        }
        colorOutline(card, "#ffff00");
        updateCardOverlapping();
    }

    private void drawCardInHand() {
        if (drawZone.getChildren().isEmpty()) return;
        CardViewOnHand cardToDraw = (CardViewOnHand) drawZone.getChildren().get(0);

        Bounds sceneBefore = cardToDraw.localToScene(cardToDraw.getBoundsInLocal());

        Bounds handZoneBounds = handZone.localToScene(handZone.getBoundsInLocal());
        double targetX = handZoneBounds.getMinX() + (handZone.getWidth() / 2) - (cardToDraw.getWidth() / 2);  // Center the card horizontally
        double targetY = handZoneBounds.getMinY() + (handZone.getHeight() / 2) - (cardToDraw.getHeight() / 2);  // Center the card vertically

        dragLayer.getChildren().add(cardToDraw);
        cardToDraw.setLayoutX(sceneBefore.getMinX());
        cardToDraw.setLayoutY(sceneBefore.getMinY());
        TranslateTransition tt = new TranslateTransition(Duration.millis(300), cardToDraw);
        tt.setInterpolator(Interpolator.SPLINE(0.25, 0.1, 0.25, 1));  // Smooth transition effect
        tt.setToX(targetX - sceneBefore.getMinX());
        tt.setToY(targetY - sceneBefore.getMinY());
        tt.setOnFinished(evt -> {
            dragLayer.getChildren().remove(cardToDraw);
            handZone.getChildren().add(cardToDraw);
            cardToDraw.setTranslateX(0);
            cardToDraw.setTranslateY(0);
            updateCardOverlapping();
            makeDraggable(cardToDraw);
        });
        tt.play();

    }

    private static void colorOutline(CardViewOnHand card, String color) {
        Timeline timeline = new Timeline();

        Duration duration = Duration.millis(1000);

        int frame = 20;
        for (int i = 0; i <= frame; i++) {
            double t = i / (double) frame;
            Color interpolated = Color.web(color).interpolate(Color.BLACK, t);
            String hex = String.format("#%02x%02x%02x",
                    (int) (interpolated.getRed() * 255),
                    (int) (interpolated.getGreen() * 255),
                    (int) (interpolated.getBlue() * 255)
            );
            KeyFrame keyFrame = new KeyFrame(
                    duration.multiply(t),
                    evt -> card.getCardOutline().setStyle(card.getCardOutline().getStyle() + "-fx-border-color: " + hex + ";")
            );
            timeline.getKeyFrames().add(keyFrame);
        }
        timeline.play();
    }

    private void updateCardOverlapping() {
        int total = dropZone.getChildren().size();
        int placeholderIndex = dropZone.getChildren().indexOf(placeholder);
        double overlap = Math.min(40, total * 5);
        double totalWidth = 0;
        for (int i = 0; i < total; i++) {
            Node node = dropZone.getChildren().get(i);
            double horizontalOffset = -i * overlap;

            if (placeholderIndex != -1) {
                if (i < placeholderIndex) {
                    horizontalOffset -= overlap + 20;
                } else if (i > placeholderIndex) {
                    horizontalOffset += overlap + 20;
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
