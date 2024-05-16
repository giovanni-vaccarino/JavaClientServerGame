package polimi.ingsoft.client.ui.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class JoinGamePageController implements Initializable {
    private Stage stage;
    private String game;
    private boolean selected;
    @FXML
    SplitMenuButton gameList;
    @FXML
    Button errButton;

    // Default constructor
    public JoinGamePageController() {}

    // Constructor with stage parameter
    public JoinGamePageController(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errButton.setVisible(false);
        List<String> items = List.of("Game 1", "Game 2", "Game 3"); // CALL MODEL
        resetGame();
        setGameList(items);
    }
    public void resetGame(){
        setGame("Games");
        selected = false;
        gameList.setStyle("-fx-background-color: white;");
    }
    public void setGame(String s){
        game=String.valueOf(s);
        gameList.setText(game);
    }

    public void setGameList(List<String> games) {
        gameList.getItems().clear();
        for (String game : games) {
            MenuItem menuItem = new MenuItem(game);
            menuItem.setOnAction(e -> handleMenuItemAction(game));
            gameList.getItems().add(menuItem);
        }
    }

    private void handleMenuItemAction(String s) {
        if(!selected){
            selected = true;
        }
        setGame(s);
    }

    public void refreshGames(){
        resetGame();
        List<String> items = List.of("Game 1", "Game 2");  // REFRESH LIST
        setGameList(items);
    }

    public void nextPage(ActionEvent actionEvent) throws IOException {

        if(selected){
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            WaitingPageController waitingPageController = new WaitingPageController(stage);
            try {
                waitingPageController.start();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else{
            gameList.setStyle("-fx-background-color: #d34813;");
            gameList.setText("Select a game");// ADD ERROR WINDOWS
            errButton.setVisible(true);
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
}

