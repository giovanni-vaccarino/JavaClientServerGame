package polimi.ingsoft.server.model.decks;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.factories.DeckFactory;
import polimi.ingsoft.server.model.cards.ResourceCard;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class CardCollectionTest {
    static CardCollection<ResourceCard> deck=new Deck<>(), deck2;
    @BeforeAll
    static void init() throws FileNotFoundException, JsonProcessingException {
        DeckFactory factory=new DeckFactory();
        deck2=factory.createResourceDeck();

    }

    @Test
    void shuffle() {
        System.out.println(deck2.cards.getFirst());
        deck2.shuffle();
        System.out.println(deck2.cards.getFirst());
    }

    @Test
    void isCardCollectionEmpty() {
        assertTrue(deck.isCardCollectionEmpty());
        assertFalse(deck2.isCardCollectionEmpty());
    }
}