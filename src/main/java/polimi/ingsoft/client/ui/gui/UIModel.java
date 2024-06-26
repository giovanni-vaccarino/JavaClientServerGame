package polimi.ingsoft.client.ui.gui;

import polimi.ingsoft.server.common.Utils.MatchData;
import polimi.ingsoft.server.controller.GameState;
import polimi.ingsoft.server.controller.PlayerInitialSetting;
import polimi.ingsoft.server.enumerations.CLIENT_STATE;
import polimi.ingsoft.server.model.boards.Coordinates;
import polimi.ingsoft.server.model.boards.Board;
import polimi.ingsoft.server.model.boards.PlayedCard;
import polimi.ingsoft.server.model.cards.GoldCard;
import polimi.ingsoft.server.model.cards.MixedCard;
import polimi.ingsoft.server.model.cards.QuestCard;
import polimi.ingsoft.server.model.cards.ResourceCard;
import polimi.ingsoft.server.model.chat.Chat;
import polimi.ingsoft.server.model.chat.Message;
import polimi.ingsoft.server.model.publicboard.PlaceInPublicBoard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The UIModel class holds the data required for the GUI.
 * It includes match details, game state, player settings, public and private boards, player hands, and chat messages.
 */

public class UIModel {
    private Integer matchId;
    private List<MatchData> matchList;
    private CLIENT_STATE clientState;
    private GameState gameState;
    private PlayerInitialSetting playerInitialSetting;
    private PlaceInPublicBoard<ResourceCard> resourceCards;
    private PlaceInPublicBoard<GoldCard> goldCards;
    private PlaceInPublicBoard<QuestCard> questCards;
    private List<MixedCard> playerHand;
    private QuestCard personalQuestCard;
    private Map<String, Board> playerBoards;
    private Chat broadcastChat = new Chat();
    private Map<String, Chat> privateChat = new HashMap<>();

    /**
     * Gets the GUI instance.
     *
     * @return the GUI instance
     */
    public GUI getGui(){
        return GUIsingleton.getInstance().getGui();
    }

    /**
     * Gets the match ID.
     *
     * @return the match ID
     */
    public Integer getMatchId() {
        return matchId;
    }

    /**
     * Sets the match ID.
     *
     * @param matchId the match ID to set
     */
    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    /**
     * Gets the list of matches.
     *
     * @return the list of matches
     */
    public List<MatchData> getMatchList() {
        return matchList;
    }

    /**
     * Sets the list of matches.
     *
     * @param matchList the list of matches to set
     */
    public void setMatchList(List<MatchData> matchList) {
        this.matchList = matchList;
    }

    /**
     * Gets the client state.
     *
     * @return the client state
     */
    public CLIENT_STATE getClientState() {
        return clientState;
    }

    /**
     * Gets the broadcast chat.
     *
     * @return the broadcast chat
     */
    public Chat getBroadcastChat(){
        return broadcastChat;
    }

    /**
     * Gets the private chat.
     *
     * @return the private chat
     */
    public Map<String, Chat> getPrivateChat(){
        return privateChat;
    }

    /**
     * Sets the client state.
     *
     * @param clientState the client state to set
     */
    public void setClientState(CLIENT_STATE clientState) {
        this.clientState = clientState;
    }

    /**
     * Gets the game state.
     *
     * @return the game state
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Sets the game state.
     *
     * @param gameState the game state to set
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * Gets the player initial setting.
     *
     * @return the player initial setting
     */
    public PlayerInitialSetting getPlayerInitialSetting() {
        return playerInitialSetting;
    }

    /**
     * Sets the player initial setting.
     *
     * @param playerInitialSetting the player initial setting to set
     */
    public void setPlayerInitialSetting(PlayerInitialSetting playerInitialSetting) {
        this.playerInitialSetting = playerInitialSetting;

        List<MixedCard> playerHand = new ArrayList<>();
        for(int i=0; i<3; i++){
            playerHand.add(playerInitialSetting.getPlayerHand().get(i));
        }
        setPlayerHand(playerHand);

        if (playerInitialSetting.getQuestCard() != null){
            setPersonalQuestCard(playerInitialSetting.getQuestCard());
        }
    }

    /**
     * Gets the public board for resource cards.
     *
     * @return the resource card public board
     */
    public PlaceInPublicBoard<ResourceCard> getResourceCards() {
        return resourceCards;
    }

    /**
     * Gets the public board for gold cards.
     *
     * @return the gold card public board
     */
    public PlaceInPublicBoard<GoldCard> getGoldCards() {
        return goldCards;
    }

    /**
     * Gets the public board for quest cards.
     *
     * @return the quest card public board
     */
    public PlaceInPublicBoard<QuestCard> getQuestCards() {
        return questCards;
    }

    /**
     * Gets the player's hand of mixed cards.
     *
     * @return the player's hand
     */
    public List<MixedCard> getPlayerHand() {
        return playerHand;
    }

    /**
     * Gets the player's personal quest card.
     *
     * @return the personal quest card
     */
    public QuestCard getPersonalQuestCard() {
        return personalQuestCard;
    }

    /**
     * Gets the player boards.
     *
     * @return the player boards
     */
    public Map<String, Board> getPlayerBoards() {
        return playerBoards;
    }

    /**
     * Sets the public board for resource cards.
     *
     * @param placeInPublicBoard the public board to set
     */
    public void setPlaceInPublicBoardResource(PlaceInPublicBoard<ResourceCard> placeInPublicBoard){
        this.resourceCards = placeInPublicBoard;
    }

    /**
     * Sets the resource cards.
     *
     * @param resourceCards the resource cards to set
     */
    public void setResourceCards(PlaceInPublicBoard<ResourceCard> resourceCards) {
        this.resourceCards = resourceCards;
    }

    /**
     * Sets the public board for gold cards.
     *
     * @param placeInPublicBoard the public board to set
     */
    public void setPlaceInPublicBoardGold(PlaceInPublicBoard<GoldCard> placeInPublicBoard){
        this.goldCards = placeInPublicBoard;
    }

    /**
     * Sets the gold cards.
     *
     * @param goldCards the gold cards to set
     */
    public void setGoldCards(PlaceInPublicBoard<GoldCard> goldCards) {
        this.goldCards = goldCards;
    }

    /**
     * Sets the quest cards.
     *
     * @param questCards the quest cards to set
     */
    public void setQuestCards(PlaceInPublicBoard<QuestCard> questCards) {
        this.questCards = questCards;
    }

    /**
     * Sets the player's hand of mixed cards.
     *
     * @param playerHand the player's hand to set
     */
    public void setPlayerHand(List<MixedCard> playerHand) {
        System.out.println("Adesso hai " + playerHand.size() + " carte in mano");
        this.playerHand = playerHand;
    }

    /**
     * Sets the player's personal quest card.
     *
     * @param personalQuestCard the personal quest card to set
     */
    public void setPersonalQuestCard(QuestCard personalQuestCard) {
        this.personalQuestCard = personalQuestCard;
    }

    /**
     * Sets the player boards.
     *
     * @param playerBoards the player boards to set
     */
    public void setPlayerBoards(Map<String, Board> playerBoards) {
        this.playerBoards = playerBoards;
        for(String player: playerBoards.keySet()){
            if(!player.equals(getGui().getNickname())){
                privateChat.put(player, new Chat());
            }
        }
    }

    /**
     * Updates the player board with new coordinates, played card, and score.
     *
     * @param nickname   the player's nickname
     * @param coordinates the coordinates of the played card
     * @param playedCard  the played card
     * @param score       the new score
     */
    public void updatePlayerBoard(String nickname, Coordinates coordinates, PlayedCard playedCard, Integer score){
        Board playerBoard = playerBoards.get(nickname);

        playerBoard.getCards().put(coordinates, playedCard);
        playerBoard.updatePoints(score - playerBoard.getScore());

        if(playedCard == null){
            System.out.println("Played Card NULL");
        }else {
            System.out.println("Played Card NON NULL "+coordinates.getX()+":"+coordinates.getY());
        }
    }

    /**
     * Adds a broadcast message to the chat.
     *
     * @param message the message to add
     */
    public void addBroadcastMessage(Message message){
        broadcastChat.addMessage(message.getSender(), message.getText());
    }

    /**
     * Adds a private message to the chat.
     *
     * @param receiver the receiver of the message
     * @param message  the message to add
     */
    public void addPrivateMessage(String receiver, Message message){
        privateChat.get(receiver).addMessage(message.getSender(), message.getText());
    }

    /**
     * Clears the UI model, resetting all fields to their initial states.
     */
    public void clear(){
        matchId = null;
        matchList = null;
        clientState = null;
        gameState = null;
        playerInitialSetting = null;
        resourceCards = null;
        goldCards = null;
        questCards = null;
        playerHand = null;
        personalQuestCard = null;
        playerBoards = null;
        privateChat.clear();
        broadcastChat = new Chat();
    }

}
