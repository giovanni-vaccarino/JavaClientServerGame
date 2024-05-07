package polimi.ingsoft.client.ui.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.ResourceBundle;

/*TO FIX:
- resourceCardDeck is null, set the background from java
- define a function that from (x,y) it returns the position in the ancor pane where x0,y0 is the initial card
- define buttons to see boards of the other players
- find a way to expand the board when it becomes too big for the table
 */

public class GamePageController /*implements Initializable*/{
    private Stage stage;
    @FXML
    private StackPane resourceCardDeck; // DA SISTEMARE --> DICE OGGETTO NULLO

    // Default constructor
    public GamePageController() {}

    // Constructor with stage parameter
    public GamePageController(Stage stage) {
        this.stage = stage;
    }
    public void start() throws Exception {
        // Load FXML file
        URL resourceUrl = getClass().getResource("/polimi/ingsoft/demo/graphics/GamePage.fxml");
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

        stage.getScene().setRoot(root);
    }

    /*@Override
    public void initialize(URL location, ResourceBundle resources) {
        String stackPaneId = "resourceCardDeck";

        if (resources.containsKey(stackPaneId)) {
            // Retrieve the StackPane from the resource bundle
            StackPane stackPane = (StackPane) resources.getObject(stackPaneId);

            // Now you have access to the retrieved StackPane and can perform any operations on it
            // For example, you can initialize it with an image
            String imageUrl = resources.getString("resourceCardDeckImage");
            background(imageUrl, stackPane);
        } else {
            // Handle the case where the StackPane with the specified ID is not found in the resources
            System.out.println("StackPane with ID " + stackPaneId + " not found in resources.");
        }
        //String imageUrl = resources.getString("/polimi/ingsoft/demo/graphics/img/card/backCard/resourceCard/backResourceCard(1).jpg");
    }

    private void background(String url, StackPane stackPane) {
        // Set background image for resourceCardDeck
        Image backgroundImage = new Image(url);
        ImageView backgroundImageView = new ImageView(backgroundImage);
        stackPane.getChildren().add(backgroundImageView);
    }*/

}
