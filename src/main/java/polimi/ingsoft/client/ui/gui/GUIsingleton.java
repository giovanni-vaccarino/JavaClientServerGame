package polimi.ingsoft.client.ui.gui;

import javafx.stage.Stage;
import polimi.ingsoft.client.ui.gui.page.*;

public class GUIsingleton {
    private static GUIsingleton instance;

    private NicknamePageController nicknamePageController;
    private StartingPageController startingPageController;
    private NewGamePageController newGamePageController;
    private JoinGamePageController joinGamePageController;
    private WaitingPageController waitingPageController;
    private ColorPageController colorPageController;
    private InitialCardPageController initialCardPageController;
    private QuestCardPageController questCardPageController;
    private GamePageController gamePageController;
    private LoadingGamePageController loadingGamePageController;
    private WinPageController winPageController;
    private LosePageController losePageController;
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

    public InitialCardPageController getInitialCardPageController() {
        return initialCardPageController;
    }

    public void setInitialCardPageController(InitialCardPageController initialCardPageController) {
        this.initialCardPageController = initialCardPageController;
    }

    public QuestCardPageController getQuestCardPageController() {
        return questCardPageController;
    }

    public void setQuestCardPageController(QuestCardPageController questCardPageController) {
        this.questCardPageController = questCardPageController;
    }

    public GamePageController getGamePageController() {
        return gamePageController;
    }

    public void setGamePageController(GamePageController gamePageController) {
        this.gamePageController = gamePageController;
    }

    public LoadingGamePageController getLoadingGamePageController() {
        return loadingGamePageController;
    }

    public void setLoadingGamePageController(LoadingGamePageController loadingGamePageController) {
        this.loadingGamePageController = loadingGamePageController;
    }

    public WinPageController getWinPageController() {
        return winPageController;
    }

    public void setWinPageController(WinPageController winPageController) {
        this.winPageController = winPageController;
    }

    public LosePageController getLosePageController() {
        return losePageController;
    }

    public void setLosePageController(LosePageController losePageController) {
        this.losePageController = losePageController;
    }
}
