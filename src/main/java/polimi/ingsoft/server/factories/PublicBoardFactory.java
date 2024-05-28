package polimi.ingsoft.server.factories;

import com.fasterxml.jackson.core.JsonProcessingException;
import polimi.ingsoft.server.model.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class PublicBoardFactory {
    public static PublicBoard createPublicBoard() {
        try{
            Deck<ResourceCard> resourceCardDeck = DeckFactory.createResourceDeck();
            Deck<GoldCard> goldCardDeck = DeckFactory.createGoldDeck();
            Deck<QuestCard> questCardDeck = new Deck<>();
            Deck<InitialCard> initialCards = DeckFactory.createInitialDeck();

            return new PublicBoard(resourceCardDeck, goldCardDeck, questCardDeck, initialCards);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
