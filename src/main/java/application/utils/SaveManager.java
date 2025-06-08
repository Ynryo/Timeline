package application.utils;

import application.MainApplication;
import application.controller.GameController;
import application.model.Deck;
import application.model.MainGame;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SaveManager {

    private static final String SAVE_FOLDER = System.getProperty("user.home") + "\\AppData\\Local\\.timeline\\save\\";
    private static final String SAVE_FILE_EXTENSION = ".bin";

    public void save(MainGame game, String deckName) {
        String filePath = SAVE_FOLDER + getSaveName(deckName) + SAVE_FILE_EXTENSION;
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(game);
            System.out.println("Partie sauvegardée sous : " + filePath);
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

    public void load(String saveName, ActionEvent event) {
        String filePath = SAVE_FOLDER + saveName;
        System.out.println("load filePath = " + filePath);
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            MainGame game = (MainGame) ois.readObject();

            Deck deck = game.getDeck();
            System.out.println("Nom du deck chargé : " + deck.getTitle());

            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/example/timeline/game-view.fxml"));
            Parent root = fxmlLoader.load();
            GameController gameController = fxmlLoader.getController();
            gameController.loadGameFromSave(getDeckName(saveName), game);

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

            System.out.println("Partie chargée depuis : " + filePath);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erreur lors du chargement : " + e.getMessage());
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void deleteSave(String saveName) {
        File file = new File(SAVE_FOLDER + saveName);
        file.delete();
    }

    //format = hh-mm-dd-MM-yyyy-deckId
    private String getSaveName(String deckId) {
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

    public List<String> listSaves() {
        File folder = new File(SAVE_FOLDER);
        List<String> filesNames = new ArrayList<>();
        if (!folder.exists()) {
            return new ArrayList<>();
        }
        File[] files = folder.listFiles();
        if (files == null) {
            return new ArrayList<>();
        }
        for (File file : files) {
            filesNames.add(file.getName());
        }
        Collections.reverse(filesNames);
        return filesNames;
    }

    private String getDeckName(String saveName) {
        return String.format(saveName.split("-")[5].split("\\.")[0]);
    }
}