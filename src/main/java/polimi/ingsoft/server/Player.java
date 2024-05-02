package polimi.ingsoft.server;

import polimi.ingsoft.server.model.*;

import java.io.Serializable;

public class Player implements Serializable {
    private Board board;
    private final PlayerHand<MixedCard> hand;
    private InitialCard initialCard;
    private final String nickname;

    public Player(PlayerHand<MixedCard> hand, String nickname) {
        this.nickname = nickname;
        InitialCard test = new InitialCard(new Face(), new Face(), 0);
        this.board = new Board(test, true);
        this.hand = hand;
    }

    public void setInitialCard(InitialCard initialCard) {
        this.initialCard = initialCard;
    }

    public Board getBoard() {
        return this.board;
    }
}
