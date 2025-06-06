package application.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.File;
import java.net.URL;

public class SaveViewController {

    @FXML
    private Label NomLabel;

    private String fileName;

    @FXML
    private Label dateHeureLabel;

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
            NomLabel.setText(nomDeck);
            dateHeureLabel.setText(dateHeure);
        } else {
            // Format inattendu, on met tout dans un label pour ne rien perdre
            NomLabel.setText(name);
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
    public void onClickDelete() {
        if (fileName == null) {
            System.out.println("Erreur : nom de fichier non défini");
            return;
        }

        try {
            // Construire le chemin vers le fichier
            URL url = getClass().getResource("/com/example/timeline/save/" + fileName);
            if (url != null) {
                File file = new File(url.toURI());

                if (file.exists()) {
                    boolean deleted = file.delete();
                    if (deleted) {
                        System.out.println("Fichier supprimé : " + fileName);

                        // Supprimer cet élément de l'interface
                        Node parent = NomLabel.getParent(); // L'AnchorPane
                        if (parent.getParent() instanceof VBox) {
                            VBox container = (VBox) parent.getParent();
                            container.getChildren().remove(parent);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
