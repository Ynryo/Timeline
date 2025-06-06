package application.io;

import application.model.Card;

import java.util.ArrayList;
import java.util.List;

public abstract class CardLoader {

	private List<Card> cards;
	private String title;


	public CardLoader() {
		cards = new ArrayList<>();
	}

	public abstract void load(String nameDeck);

	public List<Card> getCards() {
		return cards;
	}

	public void addCard(Card card) {
		cards.add(card);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


}
