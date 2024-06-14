package polimi.ingsoft.client.ui.gui.page;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.control.RadioButton;
import polimi.ingsoft.client.ui.gui.GUI;
import polimi.ingsoft.client.ui.gui.GUIsingleton;

import java.io.IOException;
import java.net.URL;

public class NewGamePageController {
    private int numberPlayers;

    @FXML
    private RadioButton twoPlayersRadioButton;

    @FXML
    private RadioButton threePlayersRadioButton;

    @FXML
    private RadioButton fourPlayersRadioButton;

    public NewGamePageController() {GUIsingleton.getInstance().setNewGamePageController(this);}

    public GUI getGui(){
        return GUIsingleton.getInstance().getGui();
    }

    public Stage getStage(){
        return GUIsingleton.getInstance().getStage();
    }
    public void start() throws Exception {

        // Load FXML file
        URL resourceUrl = getClass().getResource("/polimi/ingsoft/demo/graphics/NewGamePage.fxml");
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

    public void setNumberPlayers(ActionEvent actionEvent) {
        if(twoPlayersRadioButton.isSelected()){

            threePlayersRadioButton.setVisible(false);
            fourPlayersRadioButton.setVisible(false);
            numberPlayers=2;

        } else if (threePlayersRadioButton.isSelected()) {

            twoPlayersRadioButton.setVisible(false);
            fourPlayersRadioButton.setVisible(false);
            numberPlayers=3;

        } else if (fourPlayersRadioButton.isSelected()) {

            threePlayersRadioButton.setVisible(false);
            twoPlayersRadioButton.setVisible(false);
            numberPlayers=4;
        }

        GUIsingleton.getInstance().getGui().createMatch(numberPlayers); // do not use this.gui
    }

    public void nextPage(){
        WaitingPageController waitingPageController = new WaitingPageController();
        try {
            waitingPageController.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void backPage(ActionEvent actionEvent) throws IOException {

        StartingPageController startingPageController = new StartingPageController();
        try {
            startingPageController.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

