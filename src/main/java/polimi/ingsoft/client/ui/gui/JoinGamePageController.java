package polimi.ingsoft.client.ui.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class JoinGamePageController {
    private Stage stage;
    private String nickname;
    private int numberPlayers;
    @FXML
    private TextField nicknameInput;

    @FXML
    SplitMenuButton gameList;

    // Default constructor
    public JoinGamePageController() {}

    // Constructor with stage parameter
    public JoinGamePageController(Stage stage) {
        this.stage = stage;
    }

    public void initializeGames(){
        List<String> items = List.of("Game 1", "Game 2", "Game 3"); // DA SETTARE
        setGameList(items);
    }

    public void setGameList(List<String> games){
        for (String game : games) {
            MenuItem menuItem = new MenuItem(game);
            gameList.getItems().add(menuItem);
        }
    }

    public void start() throws Exception {

        // Load FXML file
        URL resourceUrl = getClass().getResource("/polimi/ingsoft/demo/graphics/JoinGamePage.fxml");
        if (resourceUrl == null) {
            System.out.println("FXML file not found");
            return;
        }
        //System.out.println("FXML file found");
        Parent root = FXMLLoader.load(resourceUrl);

        // Load CSS file - DO I NEED THIS?
        URL cssUrl = getClass().getResource("/polimi/ingsoft/demo/graphics/css/ButtonStyle.css");
        if (cssUrl != null) {
            root.getStylesheets().add(cssUrl.toExternalForm());
            //System.out.println("CSS file found");
        } else {
            System.out.println("CSS file not found");
        }
        stage.getScene().setRoot(root);


    } // !!! POTREBBE ESTENDERE UNA SOVRACLASSE CHE DEFINISCE IL METODO START E GLI PASSA IL FILE FXML IN INPUT

    // 2 PAGINE FXML: NewGamePage e ContinueNewGamePage
    // metodi/variabili associati alla seconda pagina hanno come prefisso "continue"

    public void backPage(ActionEvent actionEvent) throws IOException {

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        StartingPageController startingPageController = new StartingPageController(stage);
        try {
            startingPageController.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void continueBackPage(ActionEvent actionEvent) throws IOException {

        // ATTENZIONE: perdo salvataggio nickname (se torno indietro)

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        JoinGamePageController newJoinPageController = new JoinGamePageController(stage);
        try {
            newJoinPageController.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void continueJoinGame(ActionEvent actionEvent) throws IOException {

        if(!nicknameInput.getText().trim().equals("")) {// DEFINISCI E CAMBIA CON FUNZIONE VALIDATION_NAME
            setNickname();

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            URL resourceUrl = getClass().getResource("/polimi/ingsoft/demo/graphics/ContinueJoinGamePage.fxml");
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
        }else{
            nicknameInput.setStyle("-fx-background-color: #d34813;");;
        }
    }

    public void setNickname() {
        nickname = nicknameInput.getText().trim();
        System.out.println(nickname);
    }
}

