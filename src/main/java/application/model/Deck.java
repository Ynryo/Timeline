package application.model;

import application.io.CardLoader;
import application.io.JSONCardLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

    private String title;
    private List<Card> cards;

    public Deck(String deckId) {
        cards = new ArrayList<>();
        setup(deckId);
    }
    public void ajouterCarte(Card carte) {
        if (carte != null) {
            cards.add(carte);
        }
    }
    private void setup(String deckId) {
//        CardLoader loader = new JSONCardLoader(deckId);
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

    @Override
    public String toString() {
        return "Deck [title=" + title + ", number of cards=" + cards.size() + "]";
    }
}