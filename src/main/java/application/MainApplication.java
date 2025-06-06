package application;

import application.model.MainGame;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    private MainGame mainGame;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/example/timeline/menu-principal-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("MenuPrincipal");
        stage.setResizable(false);
        
        // Stocker l'instance de MainApplication dans le userData du Stage
        stage.setUserData(this);
        
        stage.setScene(scene);
        stage.show();
    }

    public MainGame getMainGame() {
        return mainGame;
    }

    public void setMainGame(MainGame mainGame) {
        this.mainGame = mainGame;
    }

    public static void main(String[] args) {
        launch();
    }
}