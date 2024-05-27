package polimi.ingsoft.client.ui.gui;

import polimi.ingsoft.server.controller.GameState;
import polimi.ingsoft.server.controller.PlayerInitialSetting;
import polimi.ingsoft.server.enumerations.CLIENT_STATE;
import polimi.ingsoft.server.model.GoldCard;
import polimi.ingsoft.server.model.PlaceInPublicBoard;
import polimi.ingsoft.server.model.QuestCard;
import polimi.ingsoft.server.model.ResourceCard;

import java.util.List;

public class UIModel {
    private Integer matchId;
    private List<Integer> matchList;
    private CLIENT_STATE clientState;
    private GameState gameState;
    private PlayerInitialSetting playerInitialSetting;
    private PlaceInPublicBoard<ResourceCard> resourceCards;
    private PlaceInPublicBoard<GoldCard> goldCards;
    private PlaceInPublicBoard<QuestCard> questCards;

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
}
