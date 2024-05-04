package polimi.ingsoft.client.ui.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
        System.out.println("FXML file found");
        Parent root = FXMLLoader.load(resourceUrl);

        // Load CSS file
        URL cssUrl = getClass().getResource("/polimi/ingsoft/demo/graphics/fonts/KodeMonoFont.css");
        if (cssUrl != null) {
            //root.getStylesheets().add(cssUrl.toExternalForm());
            root.getStylesheets().add(cssUrl.toExternalForm());
            System.out.println("CSS file found");
        } else {
            System.out.println("CSS file not found");
        }

        // Set up the stage
        //stage.setTitle("Connection Page");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
