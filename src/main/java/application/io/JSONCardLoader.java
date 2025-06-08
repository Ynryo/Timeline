package application.io;

import application.model.Card;
import application.pojo.CardPOJO;
import application.pojo.CollectionPOJO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;


public class JSONCardLoader extends CardLoader {

	private static final String PATH = "src/main/resources/fr/ynryo/timeline/json/decks.json";

	@Override
	public void load(String nameDeck) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		try {
			File file = new File(PATH);

			CollectionPOJO[] allDecks = objectMapper.readValue(file, CollectionPOJO[].class);

			CollectionPOJO result = null;
			for (CollectionPOJO deck : allDecks) {
				if (deck.name.equalsIgnoreCase(nameDeck)) {
					result = deck;
					break;
				}
			}

			if (result == null) {
				System.err.println("Deck non trouvé : " + nameDeck);
				return;
			}

			setTitle(result.name);

			int pos = 0;
			for (CardPOJO cardP : result.cards) {
				addCard(new Card(cardP));
			}

		} catch (JsonProcessingException e) {
			System.err.println("Probleme avec le json");
		} catch (IOException e) {
			System.err.println("Probleme avec le fichier des données");
			e.printStackTrace();
		}
	}

}
