package polimi.ingsoft.client.ui.gui.page;

import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import polimi.ingsoft.client.ui.gui.GUI;
import polimi.ingsoft.client.ui.gui.GUIsingleton;

import java.io.IOException;
import java.net.URL;


public class ConnectionPageController{
    private Stage stage;
    private static GUI gui;
    private NicknamePageController nicknamePageController;

    public ConnectionPageController(){

    }
    public ConnectionPageController(Stage stage) {
        this.stage = stage;
        this.gui = GUIsingleton.getInstance().getGui();
    }

    // transition to set the FadeTransition or not
    public void start(boolean transition) throws Exception {
        // Load FXML file
        URL resourceUrl = getClass().getResource("/polimi/ingsoft/demo/graphics/ConnectionPage.fxml");
        if (resourceUrl == null) {
            System.out.println("FXML file not found");
            return;
        }
        //System.out.println("FXML file found");
        Parent root = FXMLLoader.load(resourceUrl);

        if(transition){
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), root);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
            fadeTransition.play();
        }

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

    public void nextPageRMI(ActionEvent actionEvent) throws IOException {

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        nicknamePageController = new NicknamePageController(stage);

        try {
            nicknamePageController.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void nextPageSocket(ActionEvent actionEvent) throws IOException {

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        nicknamePageController = new NicknamePageController(stage);

        try {
            nicknamePageController.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
