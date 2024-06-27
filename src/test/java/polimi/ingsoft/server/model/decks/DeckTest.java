package polimi.ingsoft.server.model.decks;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.factories.DeckFactory;
import polimi.ingsoft.server.model.cards.GoldCard;
import polimi.ingsoft.server.model.cards.ResourceCard;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Deck} class.
 */
class DeckTest {

    static Deck<ResourceCard> deck;
    static Deck<GoldCard> deck2=new Deck<>();

    /**
     * Initializes the test environment by creating instances of decks before each test.
     *
     * @throws FileNotFoundException if the resource file is not found.
     * @throws JsonProcessingException if there is an error processing JSON.
     */
    @BeforeEach
    void init() throws FileNotFoundException, JsonProcessingException {
        DeckFactory factory=new DeckFactory();
        deck=factory.createResourceDeck();

    }

    /**
     * Tests the draw method of the {@link Deck}.
     * Prints the drawn card and verifies that drawing from an empty deck returns null.
     */
    @Test
    void draw() {
        System.out.println(deck.draw());
        assertNull(deck2.draw());
    }

    /**
     * Tests the show method of the {@link Deck}.
     * Prints the top card of the deck without removing it, then draws a card and verifies that showing from an empty deck returns null.
     */
    @Test
    void show() {
        System.out.println(deck.show());
        System.out.println(deck.draw());
        assertNull(deck2.show());
    }

    /**
     * Tests the clone method of the {@link Deck}.
     * Clones the deck and prints the top card of both the original and cloned decks to verify they are identical.
     */
    @Test
    void testClone() {
        Deck<ResourceCard> a=deck.clone();
        System.out.println(a.show());
        System.out.println(deck.show());
    }
}