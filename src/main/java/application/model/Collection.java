package application.model;

import java.util.ArrayList;
import java.util.List;

public class Collection {

    private static List<Deck> decks = new ArrayList<>();;

    public static void addDeck(Deck deck) {
        decks.add(deck);
    }

    public static List<Deck> getDecks() {
        return decks;
    }

    public static void removeDeck(Deck deck) {
        decks.remove(deck);
    }
}
