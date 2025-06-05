package application.controller;

import application.model.Card;
import application.util.ImageManager;
import application.views.CardViewOnHand;

public class CardOnHandController {

	private application.controller.GameController mainController;
	private CardViewOnHand view;
	private Card controlledCard;

	public CardOnHandController(Card aCard, application.controller.GameController gameController) {
		this.controlledCard = aCard;
		this.mainController = gameController;
	}

	// Initialiser la vue de la carte
	public void initView() {
		view.setTitleOnLabel(controlledCard.getTitle());
		view.setCardImage(ImageManager.getInstance().getImage(controlledCard.getUrlImage()));
	}

	// Assigner une vue à ce contrôleur
	public void setView(CardViewOnHand cardViewOnHand) {
		view = cardViewOnHand;
	}

	// Accéder à l'objet Card associé à cette vue
	public Card getCard() {
		return this.controlledCard;  // Retourner l'objet Card associé à ce contrôleur
	}

	// Retourner la date de la carte depuis le contrôleur
	public String getDateFromController() {
		return controlledCard.getDate();
	}
}
