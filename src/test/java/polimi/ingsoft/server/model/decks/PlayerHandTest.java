package polimi.ingsoft.server.model.decks;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.factories.DeckFactory;
import polimi.ingsoft.server.model.cards.Card;
import polimi.ingsoft.server.model.cards.GoldCard;
import polimi.ingsoft.server.model.cards.MixedCard;
import polimi.ingsoft.server.model.cards.ResourceCard;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link PlayerHand} class.
 */
class PlayerHandTest {

    static PlayerHand hand;
    static Deck<ResourceCard> res;
    static Deck<GoldCard> gold;

    /**
     * Initializes the test environment by creating instances of decks and a PlayerHand before all tests.
     *
     * @throws FileNotFoundException if the resource file is not found.
     * @throws JsonProcessingException if there is an error processing JSON.
     */
    @BeforeAll
    static void init() throws FileNotFoundException, JsonProcessingException {
        DeckFactory factory=new DeckFactory();
        res=factory.createResourceDeck();
        gold=factory.createGoldDeck();
        hand=new PlayerHand(res.draw(),res.draw(),gold.draw());
    }

    /**
     * Tests the get method of the {@link PlayerHand}.
     * Verifies that the correct card is returned.
     */
    @Test
    void get() {
        assertEquals(hand.get(0),hand.cards.get(0));
    }

    /**
     * Tests the getCards method of the {@link PlayerHand}.
     * Verifies that the list of cards in the hand is correctly returned.
     */
    @Test
    void getCards() {
        assertEquals(hand.getCards(),hand.cards);
    }

    /**
     * Tests the add method of the {@link PlayerHand}.
     * Verifies that a card is correctly added to the hand.
     */
    @Test
    void add() {
        Card card=res.show();
        hand.add(res.draw());
        assertTrue(hand.getCards().contains(card));
    }

    /**
     * Tests the remove method of the {@link PlayerHand}.
     * Verifies that a card is correctly removed from the hand.
     */
    @Test
    void remove() {
        MixedCard card=res.show();
        hand.add(res.draw());
        assertTrue(hand.getCards().contains(card));
        hand.remove(card);
        assertFalse(hand.getCards().contains(card));
    }

    /**
     * Tests the flip method of the {@link PlayerHand}.
     * Verifies that a card is correctly flipped in the hand.
     */
    @Test
    void flip() {
        hand.flip(0);
        assertTrue(hand.getIsFlipped().getFirst());
    }

    /**
     * Tests the getIsFlipped method of the {@link PlayerHand}.
     * Prints the flipped state of the cards in the hand.
     */
    @Test
    void getIsFlipped() {
        System.out.println(hand.getIsFlipped());

    }

    /**
     * Tests the clone method of the {@link PlayerHand}.
     * Verifies that the hand is correctly cloned.
     */
    @Test
    void testClone() {
        PlayerHand hand2=hand.clone();
        assertEquals(hand2.getCards().getFirst(),hand.getCards().getFirst());
    }
}