package polimi.ingsoft.client.ui.gui;

import polimi.ingsoft.client.common.Client;
import polimi.ingsoft.client.ui.UI;
import polimi.ingsoft.client.ui.gui.page.HomeController;
import polimi.ingsoft.client.ui.gui.utils.PlaceCardUtils;
import polimi.ingsoft.server.common.Utils.MatchData;
import polimi.ingsoft.server.controller.GameState;
import polimi.ingsoft.server.controller.PlayerInitialSetting;
import polimi.ingsoft.server.enumerations.CLIENT_STATE;
import polimi.ingsoft.server.enumerations.ERROR_MESSAGES;
import polimi.ingsoft.server.enumerations.TYPE_HAND_CARD;
import polimi.ingsoft.server.model.boards.Coordinates;
import polimi.ingsoft.server.model.boards.Board;
import polimi.ingsoft.server.model.boards.PlayedCard;
import polimi.ingsoft.server.model.cards.GoldCard;
import polimi.ingsoft.server.model.cards.MixedCard;
import polimi.ingsoft.server.model.cards.QuestCard;
import polimi.ingsoft.server.model.cards.ResourceCard;
import polimi.ingsoft.server.model.chat.Message;
import polimi.ingsoft.server.model.decks.PlayerHand;
import polimi.ingsoft.server.model.publicboard.PlaceInPublicBoard;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * The GUI class represents the graphical user interface for the client.
 * It extends the UI class and provides methods to interact with the GUI components.
 */

public class GUI extends UI{

    private HomeController homeController;

    private boolean nextColorPageEnable = true;
    private boolean nextInitialCardPageEnable = true;
    private boolean nextQuestCardPageEnable = true;
    private boolean nextGamePageEnable = true;

    private final UIModel uiModel;

    /**
     * Constructor for the GUI class.
     *
     * @param client the client instance
     */
    public GUI(Client client){
        super(client);
        GUIsingleton.getInstance().setGui(this);
        uiModel = new UIModel();
    }

    /**
     * Gets the UI model.
     *
     * @return the UI model
     */
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
                javafx.application.Platform.runLater(() -> GUIsingleton.getInstance().getNicknamePageController().showError(errorMessage));
            }

            case MATCH_IS_ALREADY_FULL -> {
                javafx.application.Platform.runLater(() -> GUIsingleton.getInstance().getJoinGamePageController().showError(errorMessage));
            }

            case COLOR_ALREADY_PICKED -> {
                javafx.application.Platform.runLater(() -> GUIsingleton.getInstance().getColorPageController().showError(errorMessage));
            }

            case WRONG_GAME_PHASE, WRONG_PLAYER_TURN, NOT_ENOUGH_RESOURCES, COORDINATE_NOT_VALID -> {
                javafx.application.Platform.runLater(() -> GUIsingleton.getInstance().getGamePageController().showError(errorMessage));
            }
        }
    }

    /**
     * Creates a new match with the specified number of players.
     *
     * @param numPlayers the number of players
     */
    public void createMatch(int numPlayers){
        try {
            getUiModel().setClientState(CLIENT_STATE.NEWGAME);
            getServer().createMatch(getNickname(),numPlayers);
        } catch (IOException ignore) {
        }
    }

    /**
     * Joins an existing match with the specified match ID.
     *
     * @param matchId the match ID
     */
    public void joinMatch(Integer matchId) {
        try {
            getUiModel().setClientState(CLIENT_STATE.JOINGAME);
            getServer().joinMatch(getNickname(), matchId);
        } catch (IOException ignore) {
        }
    }

    /**
     * Retrieves the list of matches for the client.
     */
    public void getClientMatches(){
        try {
            getServer().getMatches(this.getNickname());
        } catch (IOException ignore) {
        }
    }

    /**
     * Gets the list of matches.
     *
     * @return the list of matches
     */
    public List<MatchData> getMatchList(){
        return getUiModel().getMatchList();
    }

    @Override
    public void showUpdateMatchJoin() {
        switch (getUiModel().getClientState()){
            case NEWGAME:
                javafx.application.Platform.runLater(() -> GUIsingleton.getInstance().getNewGamePageController().nextPage());
                break;
            case JOINGAME:
                javafx.application.Platform.runLater(() -> GUIsingleton.getInstance().getJoinGamePageController().nextPage());
                break;
        }
    }

    @Override
    public void updateMatchesList(List<MatchData> matches) {
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
    }

    /**
     * Gets the initial card.
     *
     * @return the initial card ID
     */
    public String getInitialCard(){
        return getUiModel().getPlayerInitialSetting().getInitialCard().getID();
    }

    /**
     * Updates the view based on the current game state.
     */
    public void updateView(){
        //System.out.println("Mi è arrivato un updateGameState con phase: " + getUiModel().getGameState().getGamePhase());
        switch (getUiModel().getGameState().getGamePhase()){
            case INITIALIZATION -> {
                switch (getUiModel().getGameState().getCurrentInitialStep()){
                    case COLOR -> {
                        if(nextColorPageEnable){
                            nextColorPageEnable =false;
                            javafx.application.Platform.runLater(() ->GUIsingleton.getInstance().getWaitingPageController().nextPage());
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
                            javafx.application.Platform.runLater(() ->GUIsingleton.getInstance().getColorPageController().nextPage());
                        }
                    }
                    case QUEST_CARD -> {
                        if(nextQuestCardPageEnable){
                            nextQuestCardPageEnable = false;
                            javafx.application.Platform.runLater(() ->GUIsingleton.getInstance().getInitialCardPageController().nextPage());
                        }

                    }
                }
            }

            case PLAY -> {
                if(nextGamePageEnable){
                    nextGamePageEnable = false;
                    javafx.application.Platform.runLater(() ->GUIsingleton.getInstance().getQuestCardPageController().nextPage());
                    javafx.application.Platform.runLater(() ->GUIsingleton.getInstance().getLoadingGamePageController().nextPage());
                    javafx.application.Platform.runLater(() -> GUIsingleton.getInstance().getGamePageController().setCurrentPlayerName());
                }else {
                    javafx.application.Platform.runLater(() -> GUIsingleton.getInstance().getGamePageController().setCurrentPlayerName());
                }
            }

            case LAST_ROUND -> {
                javafx.application.Platform.runLater(() -> GUIsingleton.getInstance().getGamePageController().setCurrentPlayerName());
                javafx.application.Platform.runLater(() -> GUIsingleton.getInstance().getGamePageController().showLastRound());
            }

            case END -> {
                javafx.application.Platform.runLater(() -> GUIsingleton.getInstance().getGamePageController().nextPage());
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
        //System.out.println("STO RICEVENDO UN AGGIORNAMENTO DI PUBLIC BOARD DI TIPO: "+ deckType);

        if (deckType == TYPE_HAND_CARD.RESOURCE) {
            getUiModel().setPlaceInPublicBoardResource((PlaceInPublicBoard<ResourceCard>) placeInPublicBoard);
        } else {
            getUiModel().setPlaceInPublicBoardGold((PlaceInPublicBoard<GoldCard>)placeInPublicBoard);
        }
        javafx.application.Platform.runLater(() -> GUIsingleton.getInstance().getGamePageController().updatePublicBoard());
    }

    @Override
    public void updatePlayerBoard(String nickname, Coordinates coordinates, PlayedCard playedCard, Integer score){
        //System.out.println("REFRESH BOARD IN GUI");
        getUiModel().updatePlayerBoard(nickname, coordinates, playedCard, score);
        javafx.application.Platform.runLater(() -> GUIsingleton.getInstance().getGamePageController().updateBoard(nickname));
        javafx.application.Platform.runLater(() -> GUIsingleton.getInstance().getGamePageController().setScore());
    }

    @Override
    public void updatePlayerHand(PlayerHand playerHand){
        //System.out.println(playerHand.getCards().toString());
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

    @Override
    public void setRejoinMatchModel(PlayerInitialSetting playerInitialSetting,
                                    GameState gameState,
                                    PlaceInPublicBoard<ResourceCard> resource,
                                    PlaceInPublicBoard<GoldCard> gold,
                                    PlaceInPublicBoard<QuestCard> quest,
                                    Map<String, Board> boards,
                                    PlayerHand playerHand) {
        nextGamePageEnable  = false;
        getUiModel().setGameState(gameState);
        getUiModel().setPlaceInPublicBoardResource(resource);
        getUiModel().setPlaceInPublicBoardGold(gold);
        getUiModel().setQuestCards(quest);
        getUiModel().setPlayerBoards(boards);
        getUiModel().setPlayerHand(playerHand.getCards());
        getUiModel().setPlayerInitialSetting(playerInitialSetting);

        javafx.application.Platform.runLater(() -> GUIsingleton.getInstance().getNicknamePageController().startGame());
        if(GUIsingleton.getInstance().getStartingPageController() != null){
            javafx.application.Platform.runLater(() -> GUIsingleton.getInstance().getStartingPageController().startGame());
        }
    }

    /**
     * Gets the public board for resource cards.
     *
     * @return the resource card public board
     */
    public PlaceInPublicBoard<ResourceCard> getResourceCardPublicBoard(){
        return getUiModel().getResourceCards();
    }

    /**
     * Gets the public board for gold cards.
     *
     * @return the gold card public board
     */
    public PlaceInPublicBoard<GoldCard> getGoldCardPublicBoard(){
        return getUiModel().getGoldCards();
    }

    /**
     * Gets the public board for quest cards.
     *
     * @return the quest card public board
     */
    public PlaceInPublicBoard<QuestCard> getQuestCardPublicBoard(){
        return getUiModel().getQuestCards();
    }

    /**
     * Gets the player's hand of mixed cards.
     *
     * @return the player's hand
     */
    public List<MixedCard> getPlayerHand(){
        return getUiModel().getPlayerHand();
    }

    /**
     * Gets the first choosable quest card.
     *
     * @return the first choosable quest card
     */
    public QuestCard getFirstChoosableQuestCard(){
        return getUiModel().getPlayerInitialSetting().getFirstChoosableQuestCard();
    }

    /**
     * Gets the second choosable quest card.
     *
     * @return the second choosable quest card
     */
    public QuestCard getSecondChoosableQuestCard(){
        return getUiModel().getPlayerInitialSetting().getSecondChoosableQuestCard();
    }

    /**
     * Gets the player's personal quest card.
     *
     * @return the personal quest card
     */
    public QuestCard getPersonalQuestCard(){
        return getUiModel().getPersonalQuestCard();
    }

    /**
     * Draws a card from the public board.
     *
     * @param typeHandCard the type of hand card
     * @param slots        the slots on the public board
     */
    public void drawPublicBoard(TYPE_HAND_CARD typeHandCard, PlaceInPublicBoard.Slots slots){
        //System.out.println("TYPE: "+typeHandCard+"/ SLOT: "+slots.toString()+"/ NICKNAME: "+getNickname());
        try {
            getMatchServer().drawCard(getNickname(),typeHandCard,slots);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sends a broadcast message.
     *
     * @param message the message to be sent
     */
    public void sendBroadCastMessage(String message){
        try {
            getMatchServer().sendBroadcastMessage(getNickname(),message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sends a private message to a specific player.
     *
     * @param receiver the receiver's nickname
     * @param message  the message to be sent
     */
    public void sendPrivateMessage(String receiver, String message){
        try {
            getMatchServer().sendPrivateMessage(getNickname(),receiver,message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Clears the UI model and enables new game.
     */
    public void clearUIModel(){
        uiModel.clear();

        nextColorPageEnable = true;
        nextInitialCardPageEnable = true;
        nextQuestCardPageEnable = true;
        nextGamePageEnable = true;

        try{
            getClient().setMatchServer(null);
        } catch (IOException ignore){}
    }
}
