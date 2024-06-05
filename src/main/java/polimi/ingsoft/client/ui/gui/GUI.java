package polimi.ingsoft.client.ui.gui;

import polimi.ingsoft.client.common.Client;
import polimi.ingsoft.client.ui.UI;
import polimi.ingsoft.client.ui.gui.page.HomeController;
import polimi.ingsoft.client.ui.gui.utils.PlaceCardUtils;
import polimi.ingsoft.server.controller.GameState;
import polimi.ingsoft.server.controller.PlayerInitialSetting;
import polimi.ingsoft.server.enumerations.CLIENT_STATE;
import polimi.ingsoft.server.enumerations.ERROR_MESSAGES;
import polimi.ingsoft.server.enumerations.TYPE_HAND_CARD;
import polimi.ingsoft.server.model.*;
import polimi.ingsoft.server.model.Coordinates;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GUI extends UI{

    private HomeController homeController;

    private boolean nextColorPageEnable = true;
    private boolean nextInitialCardPageEnable = true;
    private boolean nextQuestCardPageEnable = true;
    private boolean nextGamePageEnable = true;

    private final UIModel uiModel;

    public GUI(Client client){
        super(client);
        GUIsingleton.getInstance().setGui(this);
        uiModel = new UIModel();
    }

    public UIModel getUiModel() {
        return uiModel;
    }

    @Override
    public void showWelcomeScreen() throws IOException {
        homeController = new HomeController();
        HomeController.main(new String[]{});
    }

    @Override
    public void updateNickname() {
        GUIsingleton.getInstance().getNicknamePageController().nextPage();
        getClientMatches();
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

            case WRONG_PLAYER_TURN, NOT_ENOUGH_RESOURCES, COORDINATE_NOT_VALID -> {
                javafx.application.Platform.runLater(() -> GUIsingleton.getInstance().getGamePageController().showError(errorMessage));
            }
        }
    }

    public void createMatch(int numPlayers){
        try {
            getUiModel().setClientState(CLIENT_STATE.NEWGAME);
            getServer().createMatch(getNickname(),numPlayers);
        } catch (IOException ignore) {
        }
    }

    public void joinMatch(Integer matchId) {
        try {
            getUiModel().setClientState(CLIENT_STATE.JOINGAME);
            getServer().joinMatch(getNickname(), matchId);
        } catch (IOException ignore) {
        }
    }

    public void getClientMatches(){
        try {
            getServer().getMatches(this.getNickname());
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
            getServer().joinMatch(getNickname(),matchId);
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
        PlaceCardUtils.initializeFaceCards();
        getUiModel().setPlayerInitialSetting(playerInitialSetting);
    }

    @Override
    public void setPlayerBoards(Map<String, Board> playerBoard){
        getUiModel().setPlayerBoards(playerBoard);
        GUIsingleton.getInstance().getLoadingGamePageController().nextPage();
    }


    public String getInitialCard(){
        return getUiModel().getPlayerInitialSetting().getInitialCard().getID();
        //return "InitialCard(5)";
    }

    public void updateView(){
        System.out.println("Mi è arrivato un updateGameState con phase: " + getUiModel().getGameState().getGamePhase());
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
                }else {
                    javafx.application.Platform.runLater(() -> GUIsingleton.getInstance().getGamePageController().setCurrentPlayerName());
                }
            }
        }
    }

    @Override
    public void updatePlayersInLobby(List<String> nicknames) {

    }
    @Override
    public void createPublicBoard(PlaceInPublicBoard<ResourceCard> resourceCards, PlaceInPublicBoard<GoldCard> goldCards, PlaceInPublicBoard<QuestCard> questCards){
        getUiModel().setResourceCards(resourceCards);
        getUiModel().setGoldCards(goldCards);
        getUiModel().setQuestCards(questCards);
    }

    @Override
    public void updatePublicBoard(TYPE_HAND_CARD deckType, PlaceInPublicBoard<?> placeInPublicBoard){
        System.out.println("STO RICEVENDO UN AGGIORNAMENTO DI PUBLIC BOARD DI TIPO: "+ deckType);

        if (deckType == TYPE_HAND_CARD.RESOURCE) {
            getUiModel().setPlaceInPublicBoardResource((PlaceInPublicBoard<ResourceCard>) placeInPublicBoard);
        } else {
            getUiModel().setPlaceInPublicBoardGold((PlaceInPublicBoard<GoldCard>)placeInPublicBoard);
        }
        javafx.application.Platform.runLater(() -> GUIsingleton.getInstance().getGamePageController().updatePublicBoard());
    }

    @Override
    public void updatePlayerBoard(String nickname, Coordinates coordinates, PlayedCard playedCard, Integer score){
        System.out.println("REFRESH BOARD IN GUI");
        getUiModel().updatePlayerBoard(nickname, coordinates, playedCard, score);
        javafx.application.Platform.runLater(() -> GUIsingleton.getInstance().getGamePageController().updateBoard());
        javafx.application.Platform.runLater(() -> GUIsingleton.getInstance().getGamePageController().setScore());
    }

    @Override
    public void updatePlayerHand(PlayerHand playerHand){
        System.out.println(playerHand.getCards().toString());
        getUiModel().setPlayerHand(playerHand.getCards());
    }

    @Override
    public void updateBroadcastChat(Message message){
        uiModel.addBroadcastMessage(message);
        javafx.application.Platform.runLater(() -> GUIsingleton.getInstance().getGamePageController().updateChat());
    }

    @Override
    public void updatePrivateChat(String receiver, Message message){
        uiModel.addPrivateMessage(receiver, message);
        javafx.application.Platform.runLater(() -> GUIsingleton.getInstance().getGamePageController().updateChat());
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

    public List<MixedCard> getPlayerHand(){
        return getUiModel().getPlayerHand();
    }

    public QuestCard getFirstChoosableQuestCard(){
        return getUiModel().getPlayerInitialSetting().getFirstChoosableQuestCard();
    }

    public QuestCard getSecondChoosableQuestCard(){
        return getUiModel().getPlayerInitialSetting().getSecondChoosableQuestCard();
    }

    public QuestCard getPersonalQuestCard(){
        return getUiModel().getPersonalQuestCard();
    }

    public void drawPublicBoard(TYPE_HAND_CARD typeHandCard, PlaceInPublicBoard.Slots slots){
        System.out.println("TYPE: "+typeHandCard+"/ SLOT: "+slots.toString()+"/ NICKNAME: "+getNickname());
        try {
            getMatchServer().drawCard(getNickname(),typeHandCard,slots);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendBroadCastMessage(String message){
        try {
            getMatchServer().sendBroadcastMessage(getNickname(),message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendPrivateMessage(String receiver, String message){
        try {
            getMatchServer().sendPrivateMessage(getNickname(),receiver,message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
