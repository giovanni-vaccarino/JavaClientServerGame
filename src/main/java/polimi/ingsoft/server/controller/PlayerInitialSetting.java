package polimi.ingsoft.server.controller;

import polimi.ingsoft.server.enumerations.PlayerColors;
import polimi.ingsoft.server.model.InitialCard;
import polimi.ingsoft.server.model.MixedCard;
import polimi.ingsoft.server.model.PlayerHand;
import polimi.ingsoft.server.model.QuestCard;

public class PlayerInitialSetting {

    private final String nickname;

    private boolean isInitialFaceUp;

    private InitialCard initialCard;

    private PlayerHand<MixedCard> playerHand = new PlayerHand<>();

    private QuestCard questCard;

    private PlayerColors color;

    public PlayerInitialSetting(String nickname){
        this.nickname = nickname;
    }

    public void setIsInitialFaceUp(boolean isInitialFaceUp) {
        this.isInitialFaceUp = isInitialFaceUp;
    }

    public void setQuestCard(QuestCard questCard){
        this.questCard = questCard;
    }

    public void setColor(PlayerColors color){
        this.color = color;
    }

    public String getNickname(){
        return this.nickname;
    }

    public PlayerColors getColor(){
        return this.color;
    }

    public Boolean getIsInitialFaceUp(){
        return this.isInitialFaceUp;
    }

    public QuestCard getQuestCard(){
        return this.questCard;
    }

    public PlayerHand<MixedCard> getPlayerHand(){
        return this.playerHand;
    }

    public InitialCard getInitialCard(){
        return this.initialCard;
    }
}
