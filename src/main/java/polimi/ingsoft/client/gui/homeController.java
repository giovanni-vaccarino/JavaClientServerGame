package polimi.ingsoft.client.gui;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;

public class homeController extends Application {
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL resourceUrl = getClass().getResource("/StartingScene.fxml");
        if (resourceUrl == null) {
            System.out.println("Starting scene fxml not found");
            return;
        }
        Parent root = FXMLLoader.load(resourceUrl);
        stage.setTitle("Codex");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
