package polimi.ingsoft.client.ui.gui.page;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import polimi.ingsoft.client.ui.gui.GUI;
import polimi.ingsoft.client.ui.gui.GUIsingleton;
import polimi.ingsoft.server.enumerations.ERROR_MESSAGES;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NicknamePageController implements Initializable {
    private boolean nextPage = false;
    private String nickname;
    @FXML
    private TextField nicknameInput;
    @FXML
    Button errButton;

    public NicknamePageController() {
        GUIsingleton.getInstance().setNicknamePageController(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errButton.setVisible(false);

        nicknameInput.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    setNickname();
                    break;
                default:
                    break;
            }
        });
    }

    public GUI getGui(){
        return GUIsingleton.getInstance().getGui();
    }

    public Stage getStage(){
        return GUIsingleton.getInstance().getStage();
    }

    public void start() throws Exception {
        // Load FXML file
        URL resourceUrl = getClass().getResource("/polimi/ingsoft/demo/graphics/NicknamePage.fxml");
        if (resourceUrl == null) {
            System.out.println("FXML file not found");
            return;
        }
        Parent root = FXMLLoader.load(resourceUrl);

        /*FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), root);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();*/

        // Load CSS file
        URL cssUrl = getClass().getResource("/polimi/ingsoft/demo/graphics/css/ButtonStyle.css");
        if (cssUrl != null) {
            root.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.out.println("CSS file not found");
        }

        getStage().getScene().setRoot(root);
    }

    public void validateNickname(ActionEvent actionEvent) throws IOException {
        setNickname();
    }

    public void setNickname() {
        nickname = nicknameInput.getText().trim();
        getGui().setNickname(nickname);
    }

    public void showError(ERROR_MESSAGES errorMessage) {
        errButton.setVisible(true);
    }


    public void nextPage(){
        nextPage=true;
        errButton.setVisible(false);

        StartingPageController startingPageController = new StartingPageController();
        try {
            startingPageController.start();
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
