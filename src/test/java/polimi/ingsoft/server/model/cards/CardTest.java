package polimi.ingsoft.server.model.cards;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.factories.DeckFactory;
import polimi.ingsoft.server.model.decks.Deck;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CardTest {
    static Card a;
    @BeforeAll
    static void init() throws FileNotFoundException, JsonProcessingException {
        DeckFactory factory=new DeckFactory();
        Deck<ResourceCard> deck=factory.createResourceDeck();
        a=deck.draw();
    }

    /**
     * Test score getter
     */
    @Test
    void getScore() {
        System.out.println(a.getScore());
    }

    /**
     * Test ID getter
     */
    @Test
    void getID() {

        System.out.println(a.getID());
    }

    /**
     * Test clone method
     */
    @Test
    void testClone() {
        assertEquals(a,a.clone());
    }
}