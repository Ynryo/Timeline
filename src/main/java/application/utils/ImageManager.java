package application.utils;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class ImageManager {

	private static ImageManager instance;
	private static Map<String, Image> cache = new HashMap<>();

	public ImageManager() {}

	public Image getImage(String urlImage) {
		if (cache.containsKey(urlImage)) {
			return cache.get(urlImage);
		} else {
			System.out.println("Fetch image from URL " + urlImage);
			Image newImage = new Image(urlImage);
			cache.put(urlImage, newImage);
			return newImage;
		}
	}

	public static ImageManager getInstance() {
		if (instance == null)
			instance = new ImageManager();
		return instance;
	}

}
