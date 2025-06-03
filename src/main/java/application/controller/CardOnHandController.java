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
		mainController = gameController;
	}
	public void initView() {
		view.setTitleOnLabel(controlledCard.getTitle());
		view.setCardImage(ImageManager.getInstance().getImage(controlledCard.getUrlImage()));
	}
	public void setView(CardViewOnHand cardViewOnHand) {
		view = cardViewOnHand;
	}



}