package polimi.ingsoft.client.ui.gui;

import javafx.stage.Stage;
import polimi.ingsoft.client.ui.gui.page.ConnectionPageController;
import polimi.ingsoft.client.ui.gui.page.NewGamePageController;
import polimi.ingsoft.client.ui.gui.page.NicknamePageController;

public class GUIsingleton {
    private static GUIsingleton instance;

    private ConnectionPageController connectionPageController;
    private NicknamePageController nicknamePageController;
    private NewGamePageController newGamePageController;
    private Stage stage;
    private GUI gui;

    private GUIsingleton() {
        // Private constructor to prevent instantiation
    }

    public static GUIsingleton getInstance() {
        if (instance == null) {
            instance = new GUIsingleton();
        }
        return instance;
    }

    public ConnectionPageController getConnectionPageController() {
        return connectionPageController;
    }

    public void setConnectionPageController(ConnectionPageController connectionPageController) {
        this.connectionPageController = connectionPageController;
    }

    public NicknamePageController getNicknamePageController() {
        return nicknamePageController;
    }

    public void setNicknamePageController(NicknamePageController nicknamePageController) {
        this.nicknamePageController = nicknamePageController;
    }

    public GUI getGui(){
        return gui;
    }

    public void setGui(GUI gui){
        this.gui=gui;
    }

    public NewGamePageController getNewGamePageController(){
        return newGamePageController;
    }

    public void setNewGamePageController(NewGamePageController newGamePageController){
        this.newGamePageController=newGamePageController;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
