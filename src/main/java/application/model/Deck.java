package application.model;

import application.io.CardLoader;
import application.io.FAKECardLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

    private String title;
    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();

        setup();
    }

    private void setup() {
        CardLoader loader = new FAKECardLoader();
        loader.load();
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