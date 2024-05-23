package polimi.ingsoft.client.ui.gui;

import javafx.stage.Stage;
import polimi.ingsoft.client.ui.gui.page.*;

public class GUIsingleton {
    private static GUIsingleton instance;

    private ConnectionPageController connectionPageController;
    private NicknamePageController nicknamePageController;
    private StartingPageController startingPageController;
    private NewGamePageController newGamePageController;
    private JoinGamePageController joinGamePageController;
    private WaitingPageController waitingPageController;
    private ColorPageController colorPageController;
    private ColorWaitingPageController colorWaitingPageController;
    private InitialCardPageController initialCardPageController;
    private InitialCardWaitingPageController initialCardWaitingPageController;
    private QuestCardPageController questCardPageController;
    private QuestCardWaitingPageController questCardWaitingPageController;
    private GamePageController gamePageController;
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

    public StartingPageController getStartingPageController() {
        return startingPageController;
    }

    public void setStartingPageController(StartingPageController startingPageController) {
        this.startingPageController = startingPageController;
    }

    public JoinGamePageController getJoinGamePageController() {
        return joinGamePageController;
    }

    public void setJoinGamePageController(JoinGamePageController joinGamePageController) {
        this.joinGamePageController = joinGamePageController;
    }

    public WaitingPageController getWaitingPageController() {
        return waitingPageController;
    }

    public void setWaitingPageController(WaitingPageController waitingPageController) {
        this.waitingPageController = waitingPageController;
    }

    public ColorPageController getColorPageController() {
        return colorPageController;
    }

    public void setColorPageController(ColorPageController colorPageController) {
        this.colorPageController = colorPageController;
    }

    public ColorWaitingPageController getColorWaitingPageController() {
        return colorWaitingPageController;
    }

    public void setColorWaitingPageController(ColorWaitingPageController colorWaitingPageController) {
        this.colorWaitingPageController = colorWaitingPageController;
    }

    public InitialCardPageController getInitialCardPageController() {
        return initialCardPageController;
    }

    public void setInitialCardPageController(InitialCardPageController initialCardPageController) {
        this.initialCardPageController = initialCardPageController;
    }

    public InitialCardWaitingPageController getInitialCardWaitingPageController() {
        return initialCardWaitingPageController;
    }

    public void setInitialCardWaitingPageController(InitialCardWaitingPageController initialCardWaitingPageController) {
        this.initialCardWaitingPageController = initialCardWaitingPageController;
    }

    public QuestCardPageController getQuestCardPageController() {
        return questCardPageController;
    }

    public void setQuestCardPageController(QuestCardPageController questCardPageController) {
        this.questCardPageController = questCardPageController;
    }

    public QuestCardWaitingPageController getQuestCardWaitingPageController() {
        return questCardWaitingPageController;
    }

    public void setQuestCardWaitingPageController(QuestCardWaitingPageController questCardWaitingPageController) {
        this.questCardWaitingPageController = questCardWaitingPageController;
    }

    public GamePageController getGamePageController() {
        return gamePageController;
    }

    public void setGamePageController(GamePageController gamePageController) {
        this.gamePageController = gamePageController;
    }
}
