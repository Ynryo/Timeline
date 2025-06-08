package application.utils;

import application.controller.GameController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;

public class SaveManager {

    private static final String SAVE_FOLDER = System.getProperty("user.home") + "AppData\\Local\\.timeline\\save\\";
    private static final String NUM_SAVE_FILE = "ressources/save/numSave.txt";

    static {
        File folder = new File(SAVE_FOLDER);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }


    public static void save(GameController game, String deckName) {
        String filePath = SAVE_FOLDER + getSaveName(deckName) + ".bin";
        System.out.println("filePath = " + filePath);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(game);
            System.out.println("Partie sauvegardee sous : " + filePath);
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

    private static String getSaveName(String deckId) {
        return String.format(
                "%02d-%02d-%02d-%02d-%4d-%s",
                LocalDateTime.now().getHour(),
                LocalDateTime.now().getMinute(),
                LocalDateTime.now().getDayOfMonth(),
                LocalDateTime.now().getMonthValue(),
                LocalDateTime.now().getYear(),
                deckId
        );
    }

//    public static MainGame load(String saveName) {
//        String filePath = SAVE_FOLDER + saveName + ".dat";
//        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
//            MainGame game = (MainGame) ois.readObject();
//
//            // Assurez-vous que le deck est correctement récupéré à partir de la sauvegarde
//            Deck deck = game.getDeck(); // Vérifiez que le deck est bien chargé
//            System.out.println("Nom du deck chargé : " + deck.getTitle());
//
//            GameManager.setNbJoueur(game.getNbJoueur());
//            GameManager.setTimedMode(game.isTimedMode());
//            GameManager.setTimeLimitSeconds(game.getTimeLimitSeconds());
//            GameManager.setDeck(deck); // Assurez-vous que le deck est correctement passé à GameManager
//            GameManager.setCurrentGame(game);
//
//            System.out.println("Partie chargée depuis : " + filePath);
//            return game;
//        } catch (IOException | ClassNotFoundException e) {
//            System.err.println("Erreur lors du chargement : " + e.getMessage());
//            return null;
//        }
//    }


    public static boolean deleteSave(String saveName) {
        File file = new File(SAVE_FOLDER + saveName + ".bin");
        return file.delete();
    }

    public static String[] listSaves() {
        File folder = new File(SAVE_FOLDER);
        System.out.println(folder.getName());
        return folder.list((dir, name) -> name.endsWith(".bin"));
    }
}