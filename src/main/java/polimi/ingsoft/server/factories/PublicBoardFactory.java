package polimi.ingsoft.server.factories;

import polimi.ingsoft.server.model.*;

import java.util.ArrayList;
import java.util.List;

public class PublicBoardFactory {
    public static PublicBoard createPublicBoard() {
        //TODO Here put the method to load the decks from the json file, and then the method to shuffle
        Deck<ResourceCard> resourceCardDeck = new Deck<>();
        Deck<GoldCard> goldCardDeck = new Deck<>();
        Deck<QuestCard> questCardDeck = new Deck<>();
        List<InitialCard> initialCards = new ArrayList<>();

        return new PublicBoard(resourceCardDeck, goldCardDeck, questCardDeck, initialCards);
    }
}
