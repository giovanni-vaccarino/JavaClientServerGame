package polimi.ingsoft.client.ui.gui.page;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;
import polimi.ingsoft.client.ui.gui.GUI;
import polimi.ingsoft.client.ui.gui.GUIsingleton;

import java.io.IOException;
import java.net.URL;

public class WaitingPageController {
    public WaitingPageController() {
        GUIsingleton.getInstance().setWaitingPageController(this);
    }

    public GUI getGui(){
        return GUIsingleton.getInstance().getGui();
    }

    public Stage getStage(){
        return GUIsingleton.getInstance().getStage();
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

        getStage().getScene().setRoot(root);
    }

    public void cancelNewGame(ActionEvent actionEvent) throws IOException {

        /*StartingPageController startingPageController = new StartingPageController();
        try {
            startingPageController.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/
    }

    public void startGame(ActionEvent actionEvent) throws IOException {
        nextPage();
    }

    public void nextPage(){
        ColorPageController colorPageController = new ColorPageController(getStage());
        try {
            colorPageController.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
