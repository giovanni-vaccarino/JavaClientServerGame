package polimi.ingsoft.client.ui.gui;

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


import java.io.IOException;
import java.net.URL;

public class connectionPageController{
    private Stage stage;

    // Default constructor
    public connectionPageController() {}

    // Constructor with stage parameter
    public connectionPageController(Stage stage) {
        this.stage = stage;
    }
    public void start() throws Exception {
        // Load FXML file
        URL resourceUrl = getClass().getResource("/polimi/ingsoft/demo/graphics/connectionPage.fxml");
        if (resourceUrl == null) {
            System.out.println("FXML file not found");
            return;
        }
        //System.out.println("FXML file found");
        Parent root = FXMLLoader.load(resourceUrl);

        // Load CSS file
        URL cssUrl = getClass().getResource("/polimi/ingsoft/demo/graphics/css/genericStyle.css");
        if (cssUrl != null) {
            root.getStylesheets().add(cssUrl.toExternalForm());
            //System.out.println("CSS file found");
        } else {
            System.out.println("CSS file not found");
        }

        // Set up the stage
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void nextPage(ActionEvent actionEvent) throws IOException {

        URL resource = getClass().getResource("/polimi/ingsoft/demo/graphics/startingPage.fxml");
        if (resource == null) {
            System.out.println("First choice fxml not found");
            return;
        }

        Parent root = FXMLLoader.load(resource);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), root);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();

        stage.getScene().setRoot(root);
    }
}
