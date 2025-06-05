package application.model;

public class MainGame {

	private static final int INITIAL_NB_CARDS = 4;
	private Player player1;
	private Player player2;
	private static int nbPlayers;
	private Deck deck;
	private  Card firstCard;

	// Constructeur
	
	public MainGame() {
		super();
		setupGame();
	}

	public static void setPlayersNumber(int nb) {
		nbPlayers = nb;

	}
	public static int getPlayersNumber() {
		return nbPlayers;
	}
	// getters / setter

	public Hand getPlayer1Hand() {
		return player1.getHand();
	}
	public Hand getPlayer2Hand() {
		return player2.getHand();  // Nouvelle méthode pour obtenir la main du joueur 2
	}
	public Player getPlayer1() {
		return player1;
	}

	public Deck getDeck() {
		return deck;
	}

	// logic

	private void setupGame() {
		player1 = new Player("Joueur 1");
		player2 = new Player("Joueur 2");

		deck = new Deck();
		firstCard = deck.drawCard();
		for (int i = 0; i < INITIAL_NB_CARDS; i++) {
			player1.addInHandCard(deck.drawCard());
			player2.addInHandCard(deck.drawCard());
		}
	}

	public Card getFirstCard(){
		return firstCard;
	}
}
