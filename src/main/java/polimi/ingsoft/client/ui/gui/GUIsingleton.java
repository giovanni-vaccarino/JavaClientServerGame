package polimi.ingsoft.client.ui.gui;

import javafx.stage.Stage;
import polimi.ingsoft.client.ui.gui.page.*;

/**
 * Singleton class to manage the GUI components and controllers.
 */
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

    /**
     * Private constructor to prevent instantiation.
     */
    private GUIsingleton() {
        // Private constructor to prevent instantiation
    }

    /**
     * Gets the singleton instance of GUIsingleton.
     *
     * @return the singleton instance
     */
    public static GUIsingleton getInstance() {
        if (instance == null) {
            instance = new GUIsingleton();
        }
        return instance;
    }

    /**
     * Gets the NicknamePageController.
     *
     * @return the NicknamePageController
     */
    public NicknamePageController getNicknamePageController() {
        return nicknamePageController;
    }

    /**
     * Sets the NicknamePageController.
     *
     * @param nicknamePageController the NicknamePageController to set
     */
    public void setNicknamePageController(NicknamePageController nicknamePageController) {
        this.nicknamePageController = nicknamePageController;
    }

    /**
     * Gets the GUI instance.
     *
     * @return the GUI instance
     */
    public GUI getGui(){
        return gui;
    }

    /**
     * Sets the GUI instance.
     *
     * @param gui the GUI instance to set
     */
    public void setGui(GUI gui){
        this.gui=gui;
    }

    /**
     * Gets the NewGamePageController.
     *
     * @return the NewGamePageController
     */
    public NewGamePageController getNewGamePageController(){
        return newGamePageController;
    }

    /**
     * Sets the NewGamePageController.
     *
     * @param newGamePageController the NewGamePageController to set
     */
    public void setNewGamePageController(NewGamePageController newGamePageController){
        this.newGamePageController=newGamePageController;
    }

    /**
     * Gets the Stage.
     *
     * @return the Stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Sets the Stage.
     *
     * @param stage the Stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Gets the StartingPageController.
     *
     * @return the StartingPageController
     */
    public StartingPageController getStartingPageController() {
        return startingPageController;
    }

    /**
     * Sets the StartingPageController.
     *
     * @param startingPageController the StartingPageController to set
     */
    public void setStartingPageController(StartingPageController startingPageController) {
        this.startingPageController = startingPageController;
    }

    /**
     * Gets the JoinGamePageController.
     *
     * @return the JoinGamePageController
     */
    public JoinGamePageController getJoinGamePageController() {
        return joinGamePageController;
    }

    /**
     * Sets the JoinGamePageController.
     *
     * @param joinGamePageController the JoinGamePageController to set
     */
    public void setJoinGamePageController(JoinGamePageController joinGamePageController) {
        this.joinGamePageController = joinGamePageController;
    }

    /**
     * Gets the WaitingPageController.
     *
     * @return the WaitingPageController
     */
    public WaitingPageController getWaitingPageController() {
        return waitingPageController;
    }

    /**
     * Sets the WaitingPageController.
     *
     * @param waitingPageController the WaitingPageController to set
     */
    public void setWaitingPageController(WaitingPageController waitingPageController) {
        this.waitingPageController = waitingPageController;
    }

    /**
     * Gets the ColorPageController.
     *
     * @return the ColorPageController
     */
    public ColorPageController getColorPageController() {
        return colorPageController;
    }

    /**
     * Sets the ColorPageController.
     *
     * @param colorPageController the ColorPageController to set
     */
    public void setColorPageController(ColorPageController colorPageController) {
        this.colorPageController = colorPageController;
    }

    /**
     * Gets the InitialCardPageController.
     *
     * @return the InitialCardPageController
     */
    public InitialCardPageController getInitialCardPageController() {
        return initialCardPageController;
    }

    /**
     * Sets the InitialCardPageController.
     *
     * @param initialCardPageController the InitialCardPageController to set
     */
    public void setInitialCardPageController(InitialCardPageController initialCardPageController) {
        this.initialCardPageController = initialCardPageController;
    }

    /**
     * Gets the QuestCardPageController.
     *
     * @return the QuestCardPageController
     */
    public QuestCardPageController getQuestCardPageController() {
        return questCardPageController;
    }

    /**
     * Sets the QuestCardPageController.
     *
     * @param questCardPageController the QuestCardPageController to set
     */
    public void setQuestCardPageController(QuestCardPageController questCardPageController) {
        this.questCardPageController = questCardPageController;
    }

    /**
     * Gets the GamePageController.
     *
     * @return the GamePageController
     */
    public GamePageController getGamePageController() {
        return gamePageController;
    }

    /**
     * Sets the GamePageController.
     *
     * @param gamePageController the GamePageController to set
     */
    public void setGamePageController(GamePageController gamePageController) {
        this.gamePageController = gamePageController;
    }

    /**
     * Gets the LoadingGamePageController.
     *
     * @return the LoadingGamePageController
     */
    public LoadingGamePageController getLoadingGamePageController() {
        return loadingGamePageController;
    }

    /**
     * Sets the LoadingGamePageController.
     *
     * @param loadingGamePageController the LoadingGamePageController to set
     */
    public void setLoadingGamePageController(LoadingGamePageController loadingGamePageController) {
        this.loadingGamePageController = loadingGamePageController;
    }

    /**
     * Gets the WinPageController.
     *
     * @return the WinPageController
     */
    public WinPageController getWinPageController() {
        return winPageController;
    }

    /**
     * Sets the WinPageController.
     *
     * @param winPageController the WinPageController to set
     */
    public void setWinPageController(WinPageController winPageController) {
        this.winPageController = winPageController;
    }

    /**
     * Gets the LosePageController.
     *
     * @return the LosePageController
     */
    public LosePageController getLosePageController() {
        return losePageController;
    }

    /**
     * Sets the LosePageController.
     *
     * @param losePageController the LosePageController to set
     */
    public void setLosePageController(LosePageController losePageController) {
        this.losePageController = losePageController;
    }
}
