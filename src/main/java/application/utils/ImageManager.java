package application.util;

import javafx.scene.image.Image;

public class ImageManager {

	private static ImageManager instance;
	
	public ImageManager() {
	}

	public Image getImage(String urlImage) {
		// TODO: verifier si pas deja récupérée dans le cache
		
		System.out.println("Fetch image from URL " + urlImage);
		Image newImage = new Image(urlImage);
		// TODO conserver l'image en cache 
		return newImage;
	}

	public static ImageManager getInstance() {
		if (instance == null)
			instance = new ImageManager();
		return instance;
	}
	
}
