package polimi.ingsoft.client.ui.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class StartingPageController {
    private Stage stage;

    // Default constructor
    public StartingPageController() {}

    // Constructor with stage parameter
    public StartingPageController(Stage stage) {
        this.stage = stage;
    }
    public void start() throws Exception {
        // Load FXML file
        URL resourceUrl = getClass().getResource("/polimi/ingsoft/demo/graphics/StartingPage.fxml");
        if (resourceUrl == null) {
            System.out.println("FXML file not found");
            return;
        }
        //System.out.println("FXML file found");
        Parent root = FXMLLoader.load(resourceUrl);

        // Load CSS file
        URL cssUrl = getClass().getResource("/polimi/ingsoft/demo/graphics/css/GenericStyle.css");
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

    public void newGame(ActionEvent actionEvent) throws IOException {

        URL resource = getClass().getResource("/polimi/ingsoft/demo/graphics/NewGamePage.fxml");
        if (resource == null) {
            System.out.println("First choice fxml not found");
            return;
        }

        Parent root = FXMLLoader.load(resource);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        /*FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), root);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();*/

        stage.getScene().setRoot(root);

        NewGamePageController newGamePageController = new NewGamePageController(stage);
        try {
            newGamePageController.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
