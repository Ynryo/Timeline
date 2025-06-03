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

	public String getTitle() {
		return title;
	}
	
	
}
