package application.views;

import application.controller.CardOnHandController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class CardViewOnHand extends VBox {

	private CardOnHandController controller;

	private ImageView cardImage;
	private Label cardTitle;
	private Label cardDate;
	private Pane cardOutline;
	private Parent root ;

	public CardViewOnHand(CardOnHandController controller) {
		super();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/ynryo/timeline/card-view.fxml"));
		try {
			this.controller = controller;
			root = loader.load();

			controller.setView(this);
			cardTitle = ((Label) root.lookup("#card_title"));
			cardDate = ((Label) root.lookup("#card_date"));
			cardImage = ((ImageView) root.lookup("#card_img"));
			cardOutline = ((Pane) root.lookup("#cardOutline"));
			this.setAlignment(Pos.CENTER);

			this.getChildren().add(root);
			controller.initView();


		} catch (IOException e) {
			System.err.println("Problem while loading the card fxml");
			e.printStackTrace();        }
	}


	public void setTitleOnLabel(String text) {
		cardTitle.setText(text);
	}
	public void setDateOnLabel(String text) {
		cardDate.setText(text);
	}
	public void revealDate(){
		setDateOnLabel(controller.getDateFromController());
	}
	public String getDate(){
		return controller.getDateFromController();
	}
	public Pane getCardOutline(){
		return cardOutline;
	}

	public void setCardImage(Image image) {
		cardImage.setImage(image);
	}

	public Node getVBoxCard(){
		Node node  = (Node) this ;
		return node;
	}

}
