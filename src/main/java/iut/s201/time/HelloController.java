package iut.s201.time;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class HelloController {
    @FXML
    private Label timeLabel;

    @FXML
    private TextField minField;

    @FXML
    private TextField secField;
    
    @FXML
    private Label cooldownLabel;
    
    @FXML
    private Button applyButton;
    
    private Timeline timeline;
    private IntegerProperty timeSeconds = new SimpleIntegerProperty();
    
    @FXML
    public void initialize() {
        // Ajouter des écouteurs pour mettre à jour le label quand les champs changent
        minField.textProperty().addListener((observable, oldValue, newValue) -> updateTimeLabel());
        secField.textProperty().addListener((observable, oldValue, newValue) -> updateTimeLabel());
    }
    
    private void updateTimeLabel() {
        String minutes = minField.getText();
        String seconds = secField.getText();
        
        // Vérifier si les champs ne sont pas vides
        if (!minutes.isEmpty() || !seconds.isEmpty()) {
            timeLabel.setText(String.format("%02d:%02d", 
                minutes.isEmpty() ? 0 : Integer.parseInt(minutes),
                seconds.isEmpty() ? 0 : Integer.parseInt(seconds)));
        } else {
            timeLabel.setText("00:00");
        }
    }
    
    @FXML
    private void handleApplyButton() {
        // Arrêter le compte à rebours en cours s'il y en a un
        if (timeline != null) {
            timeline.stop();
        }
        
        // Récupérer les minutes et secondes
        int minutes = minField.getText().isEmpty() ? 0 : Integer.parseInt(minField.getText());
        int seconds = secField.getText().isEmpty() ? 0 : Integer.parseInt(secField.getText());
        
        // Calculer le temps total en secondes
        timeSeconds.set(minutes * 60 + seconds);
        
        // Mettre à jour le label du compte à rebours
        updateCooldownLabel();
        
        // Créer une timeline pour le compte à rebours
        timeline = new Timeline(
            new KeyFrame(Duration.seconds(1), e -> {
                timeSeconds.set(timeSeconds.get() - 1);
                updateCooldownLabel();
                
                // Arrêter le compte à rebours quand il atteint 0
                if (timeSeconds.get() <= 0) {
                    timeline.stop();
                }
            })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    
    private void updateCooldownLabel() {
        int minutes = timeSeconds.get() / 60;
        int seconds = timeSeconds.get() % 60;
        cooldownLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }
}