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

	private static final String PATH = "data.json";

	@Override
	public void load() {
	
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		CollectionPOJO result;
		try {
			File file = new File(PATH);
			result = objectMapper.readValue(file, CollectionPOJO.class);
			
			setTitle(result.name);
			
			int pos = 0;
			for (CardPOJO cardP: result.cards) {
				addCard(new Card(cardP, pos++));
			}
			
		} catch (JsonProcessingException e) {
			System.err.println("Probleme avec le json");
		} catch (IOException e) {
			System.err.println("Probleme avec le fichier des données");
			e.printStackTrace();
		}
		
	}

}
