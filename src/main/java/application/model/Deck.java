package application.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import application.io.CardLoader;
import application.io.FAKECardLoader;

public class Deck {

    private String title;
    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();

        setup();
    }
    public void ajouterCarte(Card carte) {
        if (carte != null) {
            cards.add(carte);
        }
    }
    private void setup() {
        CardLoader loader = new FAKECardLoader();
        loader.load();
        cards = loader.getCards();
        title = loader.getTitle();
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        if (cards.isEmpty()) {
            return null;
        }
        return cards.remove(0);
    }

    public boolean hasMoreCards() {
        return !cards.isEmpty();
    }
    public String getNom() {
        return title;
    }

    public void setNom(String nom) {
        this.title = nom;
    }
    public List<Card> getCards() {
        return cards;
    }

    public String getTitle() {
        return title;
    }


}