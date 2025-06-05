package iut.s201.timeline;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    private String nom;
    private List<Card> cartes;

    // Constructeur par défaut
    public Deck() {
        this.cartes = new ArrayList<>();
    }

    // Constructeur avec nom
    public Deck(String nom) {
        this.nom = nom;
        this.cartes = new ArrayList<>();
    }

    // Getters et Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Card> getCartes() {
        return cartes;
    }

    public void setCartes(List<Card> cartes) {
        this.cartes = cartes != null ? cartes : new ArrayList<>();
    }

    // Méthodes utilitaires
    public void ajouterCarte(Card carte) {
        if (carte != null) {
            cartes.add(carte);
        }
    }

    public void supprimerCarte(Card carte) {
        cartes.remove(carte);
    }

    public int getNombreCartes() {
        return cartes.size();
    }

    @Override
    public String toString() {
        return "Deck{" +
                "nom='" + nom + '\'' +
                ", nombreCartes=" + cartes.size() +
                '}';
    }
}