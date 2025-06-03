package application.model;

public class MainGame {

	private static final int INITIAL_NB_CARDS = 4;
	private Player player1;
	private Deck deck;
	private  Card firstCard;

	// Constructeur
	
	public MainGame(String deckId) {
		super();
		setupGame(deckId);
	}
	
	// getters / setter
	
	public Hand getPlayerHand() {
		return player1.getHand();
	}
	
	public Player getPlayer1() {
		return player1;
	}

	public Deck getDeck() {
		return deck;
	}

	// logic

	private void setupGame(String deckId) {
		player1 = new Player("Joueur 1");
		deck = new Deck(deckId);
		firstCard =  deck.drawCard();

		for (int i = 0; i < INITIAL_NB_CARDS; i++) {
			player1.addInHandCard(deck.drawCard());
		}
	}

	public Card getFirstCard(){
		return firstCard;
	}
}
