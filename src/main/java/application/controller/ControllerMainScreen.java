package application.controller;


import application.model.Card;
import application.model.Hand;
import application.model.MainGame;
import application.views.CardViewOnHand;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;



public class ControllerMainScreen {

	@FXML
	private Label titreDeck;

	@FXML
	private HBox playerHand;

	private MainGame model;
	
	private Card selectedCard;

	public ControllerMainScreen() {
		super();
	}

	public void initialization() {
		initUI();

		model = new MainGame();

		initUIFromModel();
	}

	private void initUI() {
		playerHand.getChildren().clear();
		selectedCard = null;
	}

	private void initUIFromModel() {
		titreDeck.setText(model.getDeck().getTitle());
		playerHand.getChildren().clear();
		displayPlayerHand();		
	}

	private void refresh() {
		Platform.runLater(() -> {
			initUIFromModel();
		});
	}

	private void displayPlayerHand() {
		Hand hand = model.getPlayerHand();

		for (Card aCard : hand.getCards()) {
			CardOnHandController controller = new CardOnHandController(aCard, this);
			CardViewOnHand view = new CardViewOnHand(controller, aCard.equals(selectedCard));

			playerHand.getChildren().add(view);
		}
	}

	public void newGameAction() {
		initialization();
	}

	public void setCardSelected(Card controlledCard) {
		selectedCard = controlledCard;
		refresh();
	}

}

