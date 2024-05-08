package polimi.ingsoft.server;

import polimi.ingsoft.server.model.*;

import java.io.Serializable;

public class Player implements Serializable {
    private Board board;
    private final PlayerHand<MixedCard> hand;
    private final InitialCard initialCard;
    private QuestCard questCard;
    private final String nickname;

    public Player(PlayerHand<MixedCard> hand, InitialCard initialCard, String nickname) {
        this.initialCard = initialCard;
        this.nickname = nickname;
        this.hand = hand;
    }

    public Board getBoard() {
        return this.board;
    }

    public String getNickname(){return this.nickname;}

    public InitialCard getInitialCard(){return this.initialCard;}

    public void setQuestCard(QuestCard questCard){this.questCard = questCard;}

    public void setBoard(Boolean isFaceUp){
        this.board = new Board(this.initialCard, isFaceUp);
    }
}
