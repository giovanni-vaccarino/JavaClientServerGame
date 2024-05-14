package polimi.ingsoft.server.model;

import polimi.ingsoft.server.enumerations.PlayerColors;
import polimi.ingsoft.server.model.*;

import java.io.Serializable;

public class Player implements Serializable {
    private Board board;
    private PlayerColors color;
    private final PlayerHand<MixedCard> hand;
    private QuestCard questCard;
    private final String nickname;

    public Player(PlayerHand<MixedCard> hand, InitialCard initialCard, String nickname) {
        this.nickname = nickname;
        this.hand = hand;
    }

    public Player(PlayerHand<MixedCard> hand, String nickname, PlayerColors color, Board board, QuestCard questCard) {
        this.nickname = nickname;
        this.color = color;
        this.hand = hand;
        this.board = board;
        this.questCard = questCard;
    }

    public Board getBoard() {
        return this.board;
    }

    public String getNickname(){return this.nickname;}

    public void setQuestCard(QuestCard questCard){this.questCard = questCard;}

}
