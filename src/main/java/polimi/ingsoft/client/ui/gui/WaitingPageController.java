package polimi.ingsoft.client.ui.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class WaitingPageController {
    private Stage stage;

    // Default constructor
    public WaitingPageController() {}

    // Constructor with stage parameter
    public WaitingPageController(Stage stage) {
        this.stage = stage;
    }
    public void start() throws Exception {
        // Load FXML file
        URL resourceUrl = getClass().getResource("/polimi/ingsoft/demo/graphics/WaitingPage.fxml");
        if (resourceUrl == null) {
            System.out.println("FXML file not found");
            return;
        }
        //System.out.println("FXML file found");
        Parent root = FXMLLoader.load(resourceUrl);

        /*FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), root);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();*/

        // Load CSS file
        URL cssUrl = getClass().getResource("/polimi/ingsoft/demo/graphics/css/ButtonStyle.css");
        if (cssUrl != null) {
            root.getStylesheets().add(cssUrl.toExternalForm());
            //System.out.println("CSS file found");
        } else {
            System.out.println("CSS file not found");
        }

        stage.getScene().setRoot(root);
    }

    public void cancelNewGame(ActionEvent actionEvent) throws IOException {

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        StartingPageController startingPageController = new StartingPageController(stage);
        try {
            startingPageController.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void startGame(ActionEvent actionEvent) throws IOException {

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        GamePageController gamePageController = new GamePageController(stage);
        try {
            gamePageController.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
