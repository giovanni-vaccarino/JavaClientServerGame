package polimi.ingsoft.server.factories;

import polimi.ingsoft.server.model.*;

public class PublicBoardFactory {
    public static PublicBoard createPublicBoard() {
        Deck<ResourceCard> resourceCardDeck = new Deck<>();
        Deck<GoldCard> goldCardDeck = new Deck<>();
        Deck<QuestCard> questCardDeck = new Deck<>();
        return new PublicBoard(resourceCardDeck, goldCardDeck, questCardDeck);
    }
}
