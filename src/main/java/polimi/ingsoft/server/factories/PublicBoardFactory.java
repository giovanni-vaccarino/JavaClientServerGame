package polimi.ingsoft.server.factories;

import com.fasterxml.jackson.core.JsonProcessingException;
import polimi.ingsoft.server.model.cards.GoldCard;
import polimi.ingsoft.server.model.cards.InitialCard;
import polimi.ingsoft.server.model.cards.QuestCard;
import polimi.ingsoft.server.model.cards.ResourceCard;
import polimi.ingsoft.server.model.decks.Deck;
import polimi.ingsoft.server.model.publicboard.PublicBoard;

import java.io.FileNotFoundException;

public class PublicBoardFactory {
    public static PublicBoard createPublicBoard() {
        try{
            Deck<ResourceCard> resourceCardDeck = DeckFactory.createResourceDeck();
            Deck<GoldCard> goldCardDeck = DeckFactory.createGoldDeck();
            Deck<QuestCard> questCardDeck = DeckFactory.createQuestDeck();
            Deck<InitialCard> initialCards = DeckFactory.createInitialDeck();

            return new PublicBoard(resourceCardDeck, goldCardDeck, questCardDeck, initialCards);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
