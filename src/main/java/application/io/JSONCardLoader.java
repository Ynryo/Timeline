package application.io;

import application.controller.JSONManipulator;
import application.model.Card;


public class JSONCardLoader extends CardLoader {

	private static final String PATH = "data.json";
	private String deck;

	public JSONCardLoader(String deck) {
		super();
		this.deck = deck;
	}

	@Override
	public void load() {
		System.out.println("rentre dans load");
		JSONManipulator json = new JSONManipulator("src/main/resources/com/example/timeline/json/decks.json");
		System.out.println(json.getCards(deck));
		for (int i =0; i<json.getCards(deck).size(); i++) {

			Card tmpCard = new Card(json.getCardTitle(deck, i), json.getCardYear(deck, i), i, json.getCardImageURL(deck, i));
			System.out.println(tmpCard);
			addCard(tmpCard);

			System.out.println("tmpCard = " + tmpCard);
//			i++;
		}
	
//		ObjectMapper objectMapper = new ObjectMapper();
//		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//
//		CollectionPOJO result;
//		try {
//			File file = new File(PATH);
//			result = objectMapper.readValue(file, CollectionPOJO.class);
//
//			setTitle(result.name);
//
//			int pos = 0;
//			for (CardPOJO cardP: result.cards) {
//				addCard(new Card(cardP, pos++));
//			}
//
//		} catch (JsonProcessingException e) {
//			System.err.println("Probleme avec le json");
//		} catch (IOException e) {
//			System.err.println("Probleme avec le fichier des données");
//			e.printStackTrace();
//		}
		
	}

}
