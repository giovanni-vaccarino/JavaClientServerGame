package polimi.ingsoft.server.model.decks;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.factories.DeckFactory;
import polimi.ingsoft.server.model.cards.ResourceCard;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link CardCollection} class.
 */
class CardCollectionTest {
    static CardCollection<ResourceCard> deck=new Deck<>(), deck2;

    /**
     * Initializes the test environment by creating instances of decks before all tests.
     *
     * @throws FileNotFoundException if the resource file is not found.
     * @throws JsonProcessingException if there is an error processing JSON.
     */
    @BeforeAll
    static void init() throws FileNotFoundException, JsonProcessingException {
        DeckFactory factory=new DeckFactory();
        deck2=factory.createResourceDeck();

    }

    /**
     * Tests the shuffle method of the {@link CardCollection}.
     * Prints the first card before and after shuffling to demonstrate the change.
     */
    @Test
    void shuffle() {
        System.out.println(deck2.cards.getFirst());
        deck2.shuffle();
        System.out.println(deck2.cards.getFirst());
    }

    /**
     * Tests the isCardCollectionEmpty method of the {@link CardCollection}.
     * Verifies that an empty deck returns true and a non-empty deck returns false.
     */
    @Test
    void isCardCollectionEmpty() {
        assertTrue(deck.isCardCollectionEmpty());
        assertFalse(deck2.isCardCollectionEmpty());
    }
}