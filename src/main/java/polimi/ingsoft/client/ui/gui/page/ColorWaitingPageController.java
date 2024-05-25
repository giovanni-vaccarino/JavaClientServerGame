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

public class ColorWaitingPageController {
    public ColorWaitingPageController() {
        GUIsingleton.getInstance().setColorWaitingPageController(this);
    }

    public GUI getGui(){
        return GUIsingleton.getInstance().getGui();
    }

    public Stage getStage(){
        return GUIsingleton.getInstance().getStage();
    }

    public void start() throws Exception {
        // Load FXML file
        URL resourceUrl = getClass().getResource("/polimi/ingsoft/demo/graphics/ColorWaitingPage.fxml");
        if (resourceUrl == null) {
            System.out.println("FXML file not found");
            return;
        }
        //System.out.println("FXML file found");
        Parent root = FXMLLoader.load(resourceUrl);

        getStage().getScene().setRoot(root);
    }

    public void nextPage(ActionEvent actionEvent) throws IOException {

        InitialCardPageController initialCardPageController = new InitialCardPageController();
        try {
            initialCardPageController.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
