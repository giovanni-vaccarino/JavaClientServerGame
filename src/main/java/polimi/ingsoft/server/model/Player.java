package polimi.ingsoft.server.model;

import polimi.ingsoft.server.enumerations.PlayerColor;

import java.io.Serializable;

public class Player implements Serializable {
    private Board board;
    private PlayerColor color;

    private final PlayerHand hand;
    private QuestCard questCard;
    private final String nickname;

    public Player(PlayerHand hand, String nickname, PlayerColor color, Board board, QuestCard questCard) {
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

    public PlayerHand getHand() {
        return hand;
    }

    public void addToHand(MixedCard card) {
        this.hand.add(card);
    }

    public void removeFromHand(MixedCard card){
        this.hand.remove(card);
    }
}
