package application.io;

import application.model.Card;


public class FAKECardLoader extends CardLoader {

	@Override
	public void load() {
		setTitle("Fake data");
		addCard(new Card("TITRE 1", 1950, 1, "https://upload.wikimedia.org/wikipedia/commons/a/a2/Person_Image_Placeholder.png"));
		addCard(new Card("TITRE 2", 1970, 2, "https://upload.wikimedia.org/wikipedia/commons/a/a2/Person_Image_Placeholder.png"));
		addCard(new Card("TITRE 3", 1980, 3, "https://upload.wikimedia.org/wikipedia/commons/a/a2/Person_Image_Placeholder.png"));
		addCard(new Card("TITRE 4", 1990, 4, "https://upload.wikimedia.org/wikipedia/commons/a/a2/Person_Image_Placeholder.png"));
		addCard(new Card("TITRE 5", 1990, 4, "https://upload.wikimedia.org/wikipedia/commons/a/a2/Person_Image_Placeholder.png"));
	}

}
