package polimi.ingsoft.client.ui.gui.page;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;
import polimi.ingsoft.client.ui.gui.GUI;
import polimi.ingsoft.client.ui.gui.GUIsingleton;
import polimi.ingsoft.client.ui.gui.page.JoinGamePageController;
import polimi.ingsoft.client.ui.gui.page.NewGamePageController;
import polimi.ingsoft.client.ui.gui.page.NicknamePageController;

import java.io.IOException;
import java.net.URL;

public class StartingPageController {

    private boolean nextPage = false;
    // Default constructor
    public StartingPageController() {GUIsingleton.getInstance().setStartingPageController(this);}

    public GUI getGui(){
        return GUIsingleton.getInstance().getGui();
    }

    public Stage getStage(){
        return GUIsingleton.getInstance().getStage();
    }

    public void start() throws Exception {
        // Load FXML file
        URL resourceUrl = getClass().getResource("/polimi/ingsoft/demo/graphics/StartingPage.fxml");
        if (resourceUrl == null) {
            System.out.println("FXML file not found");
            return;
        }
        Parent root = FXMLLoader.load(resourceUrl);

        // Load CSS file
        URL cssUrl = getClass().getResource("/polimi/ingsoft/demo/graphics/css/ButtonStyle.css");
        if (cssUrl != null) {
            root.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.out.println("CSS file not found");
        }
        getStage().getScene().setRoot(root);
    }

    public void newGame(ActionEvent actionEvent) throws IOException {
        nextPage=true;

        NewGamePageController newGamePageController = new NewGamePageController();
        try {
            newGamePageController.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void joinGame(ActionEvent actionEvent) throws IOException {
        nextPage=true;

        JoinGamePageController joinGamePageController = new JoinGamePageController();
        try {
            joinGamePageController.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void startGame(){
        if(!nextPage){
            GamePageController gamePageController = new GamePageController();
            try {
                gamePageController.start();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
