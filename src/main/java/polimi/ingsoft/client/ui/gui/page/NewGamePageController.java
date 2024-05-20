package polimi.ingsoft.client.ui.gui.page;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.control.RadioButton;

import java.io.IOException;
import java.net.URL;

public class NewGamePageController {
    private Stage stage;
    private int numberPlayers;

    @FXML
    private RadioButton twoPlayersRadioButton;

    @FXML
    private RadioButton threePlayersRadioButton;

    @FXML
    private RadioButton fourPlayersRadioButton;

    // Default constructor
    public NewGamePageController() {}

    // Constructor with stage parameter
    public NewGamePageController(Stage stage) {
        this.stage = stage;
    }

    public void start() throws Exception {

        // Load FXML file
        URL resourceUrl = getClass().getResource("/polimi/ingsoft/demo/graphics/NewGamePage.fxml");
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

    public void backPage(ActionEvent actionEvent) throws IOException {

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        StartingPageController startingPageController = new StartingPageController(stage);
        try {
            startingPageController.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setNumberPlayers(ActionEvent actionEvent) {
        if(twoPlayersRadioButton.isSelected()){

            numberPlayers=2;

        } else if (threePlayersRadioButton.isSelected()) {

            numberPlayers=3;

        } else if (fourPlayersRadioButton.isSelected()) {

            numberPlayers=4;
        }


        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        WaitingPageController waitingPageController = new WaitingPageController(stage);
        try {
            waitingPageController.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println(numberPlayers);
    }
}

