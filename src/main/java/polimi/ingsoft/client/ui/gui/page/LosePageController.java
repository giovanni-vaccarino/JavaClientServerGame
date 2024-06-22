package polimi.ingsoft.client.ui.gui.page;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import polimi.ingsoft.client.ui.gui.GUI;
import polimi.ingsoft.client.ui.gui.GUIsingleton;

import java.net.URL;
import java.util.ResourceBundle;

public class LosePageController implements Initializable {
    @FXML Label winnerLabel;

    public LosePageController() {
        GUIsingleton.getInstance().setLosePageController(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        winnerLabel.setText("Winners: "+String.join(" / ", getGui().getUiModel().getGameState().getWinners()));
    }
    public GUI getGui(){
        return GUIsingleton.getInstance().getGui();
    }

    public Stage getStage(){
        return GUIsingleton.getInstance().getStage();
    }
    public void start() throws Exception {
        // Load FXML file
        URL resourceUrl = getClass().getResource("/polimi/ingsoft/demo/graphics/LosePage.fxml");
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

        getStage().getScene().setRoot(root);
    }

    public void nextPage(){

        StartingPageController startingPageController = new StartingPageController();
        try {
            startingPageController.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        getGui().clearUIModel();
    }
}
