package application.model;

import application.pojo.CardPOJO;

public class Card {

	private String title;
	private String date;
	private int position;
	private String urlImage;
	
	public Card(String title, String date, int position, String urlImage) {
		super();
		this.title = title;
		this.date = date;
		this.position = position;
		this.urlImage = urlImage;
	}

	public Card(CardPOJO cardP, int position) {
		super();
		this.title = cardP.name;
		this.date = cardP.date;
		this.position = position;
		this.urlImage = cardP.url;
	}

	public String getTitle() {
		return title;
	}

	public String getDate() {
		return date;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public int getPosition() {
		return position;
	}

	@Override
	public String toString() {
		return "Card [title=" + title + ", date=" + date + ", position=" + position + "]";
	}

	
}
