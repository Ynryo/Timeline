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

    private void setup(String deckId) {
//        CardLoader loader = new JSONCardLoader(deckId);
        CardLoader loader = new JSONCardLoader();
        loader.load(deckId);
        cards = loader.getCards();
        title = loader.getTitle();
        Collections.shuffle(cards);
    }
    public boolean isDrawEmpty() { return cards.isEmpty(); }
    public Card drawCard() {
        if (cards.isEmpty()) {
            return null;
        }
        return cards.remove(0);
    }

    public boolean hasMoreCards() {
        return !cards.isEmpty();
    }

    public String getTitle() {
        return title;
    }
}
