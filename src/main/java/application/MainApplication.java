package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    private static final String PROJECT_NAME = "Timeline - The Game";
    private static final String PROJECT_STRUCTURE = "/fr/ynryo/timeline/";

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(PROJECT_STRUCTURE + "menu-principal-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(PROJECT_NAME);
        stage.setResizable(false);
        stage.getIcons().add(new Image(getClass().getResourceAsStream( PROJECT_STRUCTURE + "favicon/favicon.png")));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}