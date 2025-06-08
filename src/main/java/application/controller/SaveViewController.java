package application.controller;

import application.utils.SaveManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class SaveViewController {

    @FXML private Label saveName;
    @FXML private Label dateHeureLabel;
    @FXML private Button deleteButton;
    @FXML private Button loadButton;
    private String fileName;
    private ContinueController controller;


    public void setData(String name) {
        this.fileName = name;

        // Exemple d'entrée : "15-52-05-06-2025-chaise"
        name = name.substring(0, name.length() - 4); // pour enlever le .bin

        // On split la chaîne sur le tiret avant le nom
        // La chaîne est "heure-year-nom", on veut séparer à partir du dernier '-'
        int index = name.lastIndexOf('-');
        if (index != -1) {
            String dateHeure = name.substring(0, index); // "15-52-05-06-2025"
            dateHeure = formatDateHeure(dateHeure);
            String nomDeck = name.substring(index + 1); // "chaise"

            // Mettre dans les labels (supposons que NomLabel et dateHeurLabel sont des Label)
            saveName.setText(nomDeck);
            dateHeureLabel.setText(dateHeure);
        } else {
            // Format inattendu, on met tout dans un label pour ne rien perdre
            saveName.setText(name);
            dateHeureLabel.setText("");
        }
    }

    public String formatDateHeure(String raw) {
        // raw = "15-52-05-06-2025"
        String[] parts = raw.split("-");

        if (parts.length != 5) {
            throw new IllegalArgumentException("Format invalide : " + raw);
        }

        String heure = parts[0];
        String minute = parts[1];
        String jour = parts[2];
        String mois = parts[3];
        String annee = parts[4];

        return "Le " + jour + "/" + mois + "/" + annee + " à " + heure + "h" + minute;
    }

    @FXML
    public void onClickDelete() throws IOException {
        if (fileName == null) {
            System.out.println("Erreur : nom de fichier non défini");
            return;
        }

        SaveManager saveManager = new SaveManager();
        saveManager.deleteSave(fileName);
        controller.initialize();
    }

    @FXML
    void loadGame(ActionEvent event) {
        SaveManager saveManager = new SaveManager();
        saveManager.load(fileName, event);
    }

    public void setController(ContinueController controller) {
        this.controller = controller;
    }
}
