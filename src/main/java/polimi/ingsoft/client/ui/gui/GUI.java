package polimi.ingsoft.client.ui.gui;

import jdk.dynalink.linker.GuardedInvocationTransformer;
import polimi.ingsoft.client.common.Client;
import polimi.ingsoft.client.ui.UI;
import polimi.ingsoft.client.ui.cli.MESSAGES;
import polimi.ingsoft.client.ui.gui.page.HomeController;
import polimi.ingsoft.server.controller.GameState;
import polimi.ingsoft.server.controller.PlayerInitialSetting;
import polimi.ingsoft.server.enumerations.CLIENT_STATE;
import polimi.ingsoft.server.enumerations.ERROR_MESSAGES;
import polimi.ingsoft.server.enumerations.GAME_PHASE;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class GUI extends UI{

    private HomeController homeController;
    private Integer matchId;
    private List<Integer> matchList;
    private CLIENT_STATE clientState;
    private GameState gameState;
    private PlayerInitialSetting playerInitialSetting;
    private boolean nextPageEnable = true;

    public GUI(Client client){
        super(client);
        GUIsingleton.getInstance().setGui(this);
    }

    @Override
    public void showWelcomeScreen() throws IOException {
        getClientMatches();
        homeController = new HomeController();
        HomeController.main(new String[]{});
    }

    @Override
    public void updateNickname() {
        GUIsingleton.getInstance().getNicknamePageController().nextPage();
    }

    @Override
    public void reportError(ERROR_MESSAGES errorMessage) {
        switch (errorMessage){
            case NICKNAME_NOT_AVAILABLE -> {
                GUIsingleton.getInstance().getNicknamePageController().showError(errorMessage);
            }
            case MATCH_IS_ALREADY_FULL -> {
                GUIsingleton.getInstance().getJoinGamePageController().showError(errorMessage);
            }
        }
    }

    public void createMatch(int numPlayers){
        try {
            clientState = CLIENT_STATE.NEWGAME;
            getClient().createMatch(getNickname(),numPlayers);
        } catch (IOException ignore) {
        }
    }

    public void joinMatch(Integer matchId) {
        try {
            clientState = CLIENT_STATE.JOINGAME;
            getClient().joinMatch(getNickname(), matchId);
        } catch (IOException ignore) {
        }
    }

    public void getClientMatches(){
        try {
            getClient().getMatches(this.getClient());
        } catch (IOException ignore) {
        }
    }

    public List<Integer> getMatchList(){
        return matchList;
    }

    @Override
    public void showUpdateMatchJoin() {
        switch (clientState){
            case NEWGAME:
                GUIsingleton.getInstance().getNewGamePageController().nextPage();
                break;
            case JOINGAME:
                GUIsingleton.getInstance().getJoinGamePageController().nextPage();
                break;
        }
    }

    @Override
    public void updateMatchesList(List<Integer> matches) {
        matchList = matches;
    }

    @Override
    public void showMatchCreate(Integer matchId) {
        this.matchId = matchId;
        try {
            getClient().joinMatch(getNickname(),matchId);
        } catch (IOException ignore) {
        }
    }

    @Override
    public void showUpdateGameState(GameState gameState) {
        this.gameState = gameState;

        updateView();
    }

    @Override
    public void showUpdateInitialSettings(PlayerInitialSetting playerInitialSetting) {
        this.playerInitialSetting=playerInitialSetting;
    }

    public void updateView(){
        switch (gameState.getGamePhase()){
            case INITIALIZATION -> {
                switch (gameState.getCurrentInitialStep()){
                    case COLOR -> {
                        if(nextPageEnable){
                            nextPageEnable=false;
                            nextPageWaiting();
                        }else{
                            GUIsingleton.getInstance().getColorPageController().showSuccess();
                        }
                    }
                }
            }
        }
    }

    public void nextPageWaiting(){
        GUIsingleton.getInstance().getWaitingPageController().nextPage();
    }

    @Override
    public void updatePlayersInLobby(List<String> nicknames) {

    }

}
