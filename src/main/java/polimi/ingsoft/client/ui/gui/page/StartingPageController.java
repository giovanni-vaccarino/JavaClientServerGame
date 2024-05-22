package polimi.ingsoft.client.ui.gui.page;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;
import polimi.ingsoft.client.ui.gui.GUIsingleton;
import polimi.ingsoft.client.ui.gui.page.JoinGamePageController;
import polimi.ingsoft.client.ui.gui.page.NewGamePageController;
import polimi.ingsoft.client.ui.gui.page.NicknamePageController;

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
        URL cssUrl = getClass().getResource("/polimi/ingsoft/demo/graphics/css/ButtonStyle.css");
        if (cssUrl != null) {
            root.getStylesheets().add(cssUrl.toExternalForm());
            //System.out.println("CSS file found");
        } else {
            System.out.println("CSS file not found");
        }
        stage.getScene().setRoot(root);
    }

    public void newGame(ActionEvent actionEvent) throws IOException {

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        NewGamePageController newGamePageController = new NewGamePageController(stage);
        GUIsingleton.getInstance().setNewGamePageController(newGamePageController);
        try {
            newGamePageController.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void joinGame(ActionEvent actionEvent) throws IOException {

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        JoinGamePageController joinGamePageController = new JoinGamePageController(stage);
        try {
            joinGamePageController.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void backPage(ActionEvent actionEvent) throws IOException {

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        NicknamePageController nicknamePageController = new NicknamePageController(stage);
        try {
            nicknamePageController.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        /*ConnectionPageController connectionPageController = new ConnectionPageController(stage);
        try {
            connectionPageController.start(false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/
    }
}
