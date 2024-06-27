package polimi.ingsoft.server.controller;

import polimi.ingsoft.server.model.player.PlayerColor;
import polimi.ingsoft.server.model.cards.InitialCard;
import polimi.ingsoft.server.model.cards.QuestCard;
import polimi.ingsoft.server.model.decks.PlayerHand;

import java.io.Serializable;

/**
 * This class represents the initial settings for a player in the game.
 */
public class PlayerInitialSetting implements Serializable, Cloneable {

    private final String nickname;

    private boolean isInitialFaceUp;

    private InitialCard initialCard;

    private PlayerHand playerHand;

    private QuestCard questCard;

    private QuestCard firstChoosableQuestCard;

    private QuestCard secondChoosableQuestCard;

    private PlayerColor color;

    public PlayerInitialSetting(String nickname){
        this.nickname = nickname;
    }

    /**
     * Constructs a PlayerInitialSetting with the specified details.
     *
     * @param nickname          the nickname of the player
     * @param playerHand        the initial player hand
     * @param firstQuestCard    the first choosable quest card
     * @param secondQuestCard   the second choosable quest card
     * @param initialCard       the initial card of the player
     */
    public PlayerInitialSetting(String nickname,
                                PlayerHand playerHand,
                                QuestCard firstQuestCard,
                                QuestCard secondQuestCard,
                                InitialCard initialCard){
        this.nickname = nickname;
        this.playerHand = playerHand;
        this.firstChoosableQuestCard = firstQuestCard;
        this.secondChoosableQuestCard = secondQuestCard;
        this.initialCard = initialCard;
    }

    public QuestCard getFirstChoosableQuestCard() {
        return firstChoosableQuestCard;
    }

    public QuestCard getSecondChoosableQuestCard() {
        return secondChoosableQuestCard;
    }

    public void setIsInitialFaceUp(boolean isInitialFaceUp) {
        this.isInitialFaceUp = isInitialFaceUp;
    }

    public void setQuestCard(QuestCard questCard){
        this.questCard = questCard;
    }

    public void setColor(PlayerColor color){
        this.color = color;
    }

    public String getNickname(){
        return this.nickname;
    }

    public PlayerColor getColor(){
        return this.color;
    }

    public Boolean getIsInitialFaceUp(){
        return this.isInitialFaceUp;
    }

    public QuestCard getQuestCard(){
        return this.questCard;
    }

    public PlayerHand getPlayerHand(){
        return this.playerHand;
    }

    public InitialCard getInitialCard(){
        return this.initialCard;
    }

    @Override
    public PlayerInitialSetting clone() {
        try {
            PlayerInitialSetting clone = (PlayerInitialSetting) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
