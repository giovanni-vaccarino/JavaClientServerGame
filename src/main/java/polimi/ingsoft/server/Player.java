package polimi.ingsoft.server;

import polimi.ingsoft.server.model.*;

public class Player {
    private Board board;
    private final PlayerHand<MixedCard> hand;
    private InitialCard initialCard;

    public Player(PlayerHand<MixedCard> hand) {
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
