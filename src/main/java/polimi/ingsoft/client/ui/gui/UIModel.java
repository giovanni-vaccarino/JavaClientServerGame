package polimi.ingsoft.client.ui.gui;

import polimi.ingsoft.server.controller.GameState;
import polimi.ingsoft.server.controller.PlayerInitialSetting;
import polimi.ingsoft.server.enumerations.CLIENT_STATE;
import polimi.ingsoft.server.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UIModel {
    private Integer matchId;
    private List<Integer> matchList;
    private CLIENT_STATE clientState;
    private GameState gameState;
    private PlayerInitialSetting playerInitialSetting;
    private PlaceInPublicBoard<ResourceCard> resourceCards;
    private PlaceInPublicBoard<GoldCard> goldCards;
    private PlaceInPublicBoard<QuestCard> questCards;
    private List<MixedCard> playerHand;
    private QuestCard personalQuestCard;
    private Map<String, Board> playerBoards;

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    public List<Integer> getMatchList() {
        return matchList;
    }

    public void setMatchList(List<Integer> matchList) {
        this.matchList = matchList;
    }

    public CLIENT_STATE getClientState() {
        return clientState;
    }

    public void setClientState(CLIENT_STATE clientState) {
        this.clientState = clientState;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public PlayerInitialSetting getPlayerInitialSetting() {
        return playerInitialSetting;
    }

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

    public PlaceInPublicBoard<ResourceCard> getResourceCards() {
        return resourceCards;
    }

    public void setResourceCards(PlaceInPublicBoard<ResourceCard> resourceCards) {
        this.resourceCards = resourceCards;
    }

    public PlaceInPublicBoard<GoldCard> getGoldCards() {
        return goldCards;
    }

    public void setGoldCards(PlaceInPublicBoard<GoldCard> goldCards) {
        this.goldCards = goldCards;
    }

    public PlaceInPublicBoard<QuestCard> getQuestCards() {
        return questCards;
    }

    public void setQuestCards(PlaceInPublicBoard<QuestCard> questCards) {
        this.questCards = questCards;
    }

    public List<MixedCard> getPlayerHand() {
        return playerHand;
    }

    public void setPlayerHand(List<MixedCard> playerHand) {
        this.playerHand = playerHand;
    }

    public QuestCard getPersonalQuestCard() {
        return personalQuestCard;
    }

    public void setPersonalQuestCard(QuestCard personalQuestCard) {
        this.personalQuestCard = personalQuestCard;
    }

    public Map<String, Board> getPlayerBoards() {
        return playerBoards;
    }

    public void setPlayerBoards(Map<String, Board> playerBoards) {
        this.playerBoards = playerBoards;
    }
}
