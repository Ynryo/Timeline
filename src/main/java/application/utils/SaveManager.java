package application.utils;

import application.model.MainGame;

import java.io.*;

public class SaveManager {

    private static final String SAVE_FOLDER = "ressources/saves/";
    private static final String NUM_SAVE_FILE = "ressources/saves/numSave.txt";

    static {
        File folder = new File(SAVE_FOLDER);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    public static int getAndIncrementSaveNumber() {
        int num = 1;
        File numFile = new File(NUM_SAVE_FILE);

        if (numFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(numFile))) {
                String line = br.readLine();
                if (line != null) {
                    num = Integer.parseInt(line.trim());
                }
            } catch (IOException | NumberFormatException e) {
                System.err.println("Erreur lecture numSave.txt : " + e.getMessage());
                num = 1;
            }
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(numFile, false))) {
            bw.write(String.valueOf(num + 1));
        } catch (IOException e) {
            System.err.println("Erreur ecriture numSave.txt : " + e.getMessage());
        }

        return num;
    }


    public static void save(MainGame game, String saveName) {
        String filePath = SAVE_FOLDER + saveName + ".dat";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(game);
            System.out.println("Partie sauvegardee sous : " + filePath);
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde : " + e.getMessage());
        }
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
        File file = new File(SAVE_FOLDER + saveName + ".dat");
        return file.delete();
    }

    public static String[] listSaves() {
        File folder = new File(SAVE_FOLDER);
        System.out.println(folder.getName());
        return folder.list((dir, name) -> name.endsWith(".dat"));
    }
}