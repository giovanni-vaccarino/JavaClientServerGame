package polimi.ingsoft.client.ui.gui.page;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.stage.Stage;
import polimi.ingsoft.client.ui.gui.GUI;
import polimi.ingsoft.client.ui.gui.GUIsingleton;
import polimi.ingsoft.server.enumerations.ERROR_MESSAGES;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static polimi.ingsoft.server.enumerations.ERROR_MESSAGES.MATCH_DOES_NOT_EXIST;

public class JoinGamePageController implements Initializable {
    private int gameId;
    private boolean selected;
    @FXML
    SplitMenuButton gameList;
    @FXML
    Button errButtonFull;
    @FXML
    Button errButtonNoSel;

    // Default constructor
    public JoinGamePageController() {
        GUIsingleton.getInstance().setJoinGamePageController(this);
    }

    public GUI getGui(){
        return GUIsingleton.getInstance().getGui();
    }

    public Stage getStage(){
        return GUIsingleton.getInstance().getStage();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errButtonFull.setVisible(false);
        errButtonNoSel.setVisible(false);
        List<Integer> items = getGui().getMatchList();
        resetGame();
        setGameList(items);
    }
    public void resetGame(){
        gameList.setText("Games");
        selected = false;
        gameList.setStyle("-fx-background-color: white;");
    }
    public void setGame(Integer s){
        gameList.setText(s.toString());
        gameId = s;
    }

    public void setGameList(List<Integer> games) {
        gameList.getItems().clear();
        for (Integer game : games) {
            MenuItem menuItem = new MenuItem(game.toString());
            menuItem.setOnAction(e -> handleMenuItemAction(game));
            gameList.getItems().add(menuItem);
        }
    }

    private void handleMenuItemAction(Integer s) {
        if(!selected){
            selected = true;
            errButtonFull.setVisible(false);
            errButtonNoSel.setVisible(false);
        }
        setGame(s);
    }

    public void updateGames(){
        resetGame();
        setGameList(getGui().getMatchList());
    }

    public void selectGame(ActionEvent actionEvent) throws IOException {

        if(selected){
            getGui().joinMatch(gameId);
        }else{
            showError(MATCH_DOES_NOT_EXIST);
        }
    }

    public void showError(ERROR_MESSAGES errorMessages){
        gameList.setStyle("-fx-background-color: #d34813;");
        //gameList.setText("Select a game");// ADD ERROR WINDOWS

        switch (errorMessages){
            case MATCH_IS_ALREADY_FULL -> {
                errButtonFull.setVisible(true);
            }
            case MATCH_DOES_NOT_EXIST -> {
                errButtonNoSel.setVisible(true);
            }
        }
    }

    public void nextPage(){
        WaitingPageController waitingPageController = new WaitingPageController(getStage());
        try {
            waitingPageController.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
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
        getStage().getScene().setRoot(root);


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

