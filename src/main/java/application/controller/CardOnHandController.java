package application.controller;

import application.model.Card;
import application.util.ImageManager;
import application.views.CardViewOnHand;

public class CardOnHandController {

	private application.controller.ControllerMainScreen mainController;
	private CardViewOnHand view;
	private Card controlledCard;


	public CardOnHandController(Card aCard, application.controller.ControllerMainScreen controllerMainScreen) {
		this.controlledCard = aCard;
		mainController = controllerMainScreen;
	}

	public void initView() {
		view.setTitle(controlledCard.getTitle());
		view.setCardImage(ImageManager.getInstance().getImage(controlledCard.getUrlImage())); 
	}

	public void selectAction() {
		mainController.setCardSelected(controlledCard);
	}

	public void setView(CardViewOnHand cardViewOnHand) {
		view = cardViewOnHand;
	}
	
	
	
}
