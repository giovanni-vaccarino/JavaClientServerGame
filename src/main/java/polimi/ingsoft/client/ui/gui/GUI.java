package polimi.ingsoft.client.ui.gui;

import polimi.ingsoft.client.common.Client;
import polimi.ingsoft.client.ui.UI;
import polimi.ingsoft.client.ui.cli.ClientPublicBoard;
import polimi.ingsoft.client.ui.gui.page.HomeController;
import polimi.ingsoft.server.controller.GameState;
import polimi.ingsoft.server.controller.PlayerInitialSetting;
import polimi.ingsoft.server.enumerations.CLIENT_STATE;
import polimi.ingsoft.server.enumerations.ERROR_MESSAGES;
import polimi.ingsoft.server.enumerations.Resource;
import polimi.ingsoft.server.model.*;

import java.io.IOException;
import java.util.List;

public class GUI extends UI{

    private HomeController homeController;

    private boolean nextColorPageEnable = true;
    private boolean nextInitialCardPageEnable = true;
    private boolean nextQuestCardPageEnable = true;
    private boolean nextGamePageEnable = true;

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
            case COLOR_ALREADY_PICKED -> {
                GUIsingleton.getInstance().getColorPageController().showError(errorMessage);
            }
        }
    }

    public void createMatch(int numPlayers){
        try {
            getUiModel().setClientState(CLIENT_STATE.NEWGAME);
            getClient().createMatch(getNickname(),numPlayers);
        } catch (IOException ignore) {
        }
    }

    public void joinMatch(Integer matchId) {
        try {
            getUiModel().setClientState(CLIENT_STATE.JOINGAME);
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
        return getUiModel().getMatchList();
    }

    @Override
    public void showUpdateMatchJoin() {
        switch (getUiModel().getClientState()){
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
        getUiModel().setMatchList(matches);
    }

    @Override
    public void showMatchCreate(Integer matchId) {
        getUiModel().setMatchId(matchId);
        try {
            getClient().joinMatch(getNickname(),matchId);
        } catch (IOException ignore) {
        }
    }

    @Override
    public void showUpdateGameState(GameState gameState) {
        getUiModel().setGameState(gameState);

        updateView();
    }

    @Override
    public void showUpdateInitialSettings(PlayerInitialSetting playerInitialSetting) {
        getUiModel().setPlayerInitialSetting(playerInitialSetting);
    }


    public String getInitialCard(){
        return getUiModel().getPlayerInitialSetting().getInitialCard().getID();
        //return "InitialCard(5)";
    }

    public void updateView(){
        switch (getUiModel().getGameState().getGamePhase()){
            case INITIALIZATION -> {
                switch (getUiModel().getGameState().getCurrentInitialStep()){
                    case COLOR -> {
                        if(nextColorPageEnable){
                            nextColorPageEnable =false;
                            GUIsingleton.getInstance().getWaitingPageController().nextPage();
                        }else{
                            /*if (playerInitialSetting != null){
                                if(playerInitialSetting.getColor() != null){
                                    GUIsingleton.getInstance().getColorPageController().showSuccess();
                                }
                            }*/
                        }
                    }
                    case FACE_INITIAL -> {
                        if(nextInitialCardPageEnable){
                            nextInitialCardPageEnable =false;
                            GUIsingleton.getInstance().getColorPageController().nextPage();
                        }
                    }
                    case QUEST_CARD -> {
                        if(nextQuestCardPageEnable){
                            nextQuestCardPageEnable = false;
                            GUIsingleton.getInstance().getInitialCardPageController().nextPage();
                        }

                    }
                }
            }
            case PLAY -> {
                if(nextGamePageEnable){
                    nextGamePageEnable = false;
                    GUIsingleton.getInstance().getQuestCardPageController().nextPage();
                }
            }
        }
    }

    @Override
    public void updatePlayersInLobby(List<String> nicknames) {

    }
    @Override
    public void createPublicBoard(PlaceInPublicBoard<ResourceCard> resourceCards, PlaceInPublicBoard<GoldCard> goldCards, PlaceInPublicBoard<QuestCard> questCards){
        super.createPublicBoard(resourceCards, goldCards, questCards);
        GUIsingleton.getInstance().getLoadingGamePageController().nextPage();
    }

    public PlaceInPublicBoard<ResourceCard> getResourceCardPublicBoard(){
        return getUiModel().getResourceCards();
    }

    public PlaceInPublicBoard<GoldCard> getGoldCardPublicBoard(){
        return getUiModel().getGoldCards();
    }

    public PlaceInPublicBoard<QuestCard> getQuestCardPublicBoard(){
        return getUiModel().getQuestCards();
    }

}
