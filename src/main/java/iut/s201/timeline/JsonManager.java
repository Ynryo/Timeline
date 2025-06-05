package iut.s201.timeline;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JsonManager {
    private static final String FILE_NAME = "cartes.json";

    // Sauvegarde un deck complet (nom + cartes) dans un fichier JSON
    public void sauvegarderDeck(Deck deck) {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            writer.write("{\n");
            writer.write("  \"nom\": \"" + echapperJson(deck.getNom()) + "\",\n");
            writer.write("  \"cartes\": [\n");

            List<Card> cartes = deck.getCartes();
            for (int i = 0; i < cartes.size(); i++) {
                Card carte = cartes.get(i);
                writer.write("    {\n");
                writer.write("      \"nom\": \"" + echapperJson(carte.getNom()) + "\",\n");
                writer.write("      \"date\": \"" + echapperJson(carte.getDate()) + "\",\n");
                writer.write("      \"url\": \"" + echapperJson(carte.getUrl()) + "\",\n");
                writer.write("      \"description\": \"" + echapperJson(carte.getDescription()) + "\"\n");
                writer.write("    }");

                // Ajouter une virgule sauf pour le dernier élément
                if (i < cartes.size() - 1) {
                    writer.write(",");
                }
                writer.write("\n");
            }

            writer.write("  ]\n");
            writer.write("}");
            System.out.println("Deck sauvegardé dans " + FILE_NAME);

        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

    // Charge un deck complet depuis un fichier JSON
    public Deck chargerDeck() {
        Deck deck = new Deck();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String ligne;
            Card carteEnCours = null;
            boolean dansTableauCartes = false;

            while ((ligne = reader.readLine()) != null) {
                ligne = ligne.trim();

                // Lecture du nom du deck
                if (ligne.startsWith("\"nom\":") && !dansTableauCartes) {
                    String nomDeck = extraireValeur(ligne);
                    deck.setNom(nomDeck);
                }
                // Début du tableau des cartes
                else if (ligne.startsWith("\"cartes\":")) {
                    dansTableauCartes = true;
                }
                // Début d'une nouvelle carte
                else if (ligne.equals("{") && dansTableauCartes) {
                    carteEnCours = new Card();
                }
                // Fin d'une carte
                else if ((ligne.equals("}") || ligne.equals("},")) && dansTableauCartes) {
                    if (carteEnCours != null) {
                        deck.ajouterCarte(carteEnCours);
                        carteEnCours = null;
                    }
                }
                // Propriétés des cartes
                else if (dansTableauCartes && carteEnCours != null) {
                    if (ligne.startsWith("\"nom\":")) {
                        String nom = extraireValeur(ligne);
                        carteEnCours.setNom(nom);
                    } else if (ligne.startsWith("\"date\":")) {
                        String date = extraireValeur(ligne);
                        carteEnCours.setDate(date);
                    } else if (ligne.startsWith("\"url\":")) {
                        String url = extraireValeur(ligne);
                        carteEnCours.setUrl(url);
                    } else if (ligne.startsWith("\"description\":")) {
                        String description = extraireValeur(ligne);
                        carteEnCours.setDescription(description);
                    }
                }
            }

            System.out.println("Deck chargé depuis " + FILE_NAME + " - Nom: " + deck.getNom() + ", Cartes: " + deck.getNombreCartes());

        } catch (FileNotFoundException e) {
            System.out.println("Fichier " + FILE_NAME + " non trouvé, création d'un deck vide");
            deck.setNom("Nouveau Deck");
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement : " + e.getMessage());
        }

        return deck;
    }

    // Méthodes de compatibilité avec l'ancien système
    public void sauvegarderCartes(List<Card> cartes) {
        Deck deck = new Deck("Deck Sans Nom");
        deck.setCartes(cartes);
        sauvegarderDeck(deck);
    }

    public List<Card> chargerCartes() {
        Deck deck = chargerDeck();
        return deck.getCartes();
    }

    public void ajouterCarte(Card nouvelleCarte) {
        Deck deck = chargerDeck();
        deck.ajouterCarte(nouvelleCarte);
        sauvegarderDeck(deck);
    }

    public void supprimerCarte(String nomCarte) {
        Deck deck = chargerDeck();
        boolean removed = deck.getCartes().removeIf(carte -> carte.getNom().equals(nomCarte));

        if (removed) {
            sauvegarderDeck(deck);
            System.out.println("Carte supprimée avec succès: " + nomCarte);
        } else {
            System.out.println("Aucune carte trouvée avec le nom: " + nomCarte);
        }
    }

    // Nouvelle méthode pour mettre à jour le nom du deck
    public void mettreAJourNomDeck(String nouveauNom) {
        Deck deck = chargerDeck();
        deck.setNom(nouveauNom);
        sauvegarderDeck(deck);
    }

    // Extrait la valeur d'une ligne JSON (entre guillemets)
    private String extraireValeur(String ligne) {
        // Trouve le premier guillemet après les deux points
        int debut = ligne.indexOf('"', ligne.indexOf(':')) + 1;

        // Trouve le dernier guillemet avant la virgule ou la fin
        int fin = ligne.lastIndexOf('"');

        if (debut > 0 && fin > debut) {
            return desarechapperJson(ligne.substring(debut, fin));
        }
        return "";
    }

    // Échappe les caractères spéciaux pour JSON
    private String echapperJson(String texte) {
        if (texte == null) {
            return "";
        }
        return texte.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    // Déséchappe les caractères spéciaux du JSON
    private String desarechapperJson(String texte) {
        if (texte == null) {
            return "";
        }
        return texte.replace("\\\"", "\"")
                .replace("\\\\", "\\")
                .replace("\\n", "\n")
                .replace("\\r", "\r")
                .replace("\\t", "\t");
    }
}