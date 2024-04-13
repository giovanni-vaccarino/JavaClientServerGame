package polimi.ingsoft.server;

import polimi.ingsoft.server.model.Board;
import polimi.ingsoft.server.model.InitialCard;
import polimi.ingsoft.server.model.MixedCard;
import polimi.ingsoft.server.model.PlayerHand;

public class Player {
    private Board board;
    private final PlayerHand<MixedCard> hand;
    private InitialCard initialCard;

    public Player(PlayerHand<MixedCard> hand) {
        this.hand = hand;
    }

    public void setInitialCard(InitialCard initialCard) {
        this.initialCard = initialCard;
    }

    public Board getBoard() {
        return this.board;
    }
}
