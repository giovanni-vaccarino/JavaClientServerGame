package polimi.ingsoft.server.model.cards;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.factories.DeckFactory;
import polimi.ingsoft.server.model.decks.Deck;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class InitialCardTest {

    static InitialCard card;

    @BeforeAll
    static void init() throws FileNotFoundException, JsonProcessingException {
        DeckFactory factory=new DeckFactory();
        Deck<InitialCard> deck=factory.createInitialDeck();
        card=deck.draw();
    }

    /**
     * Test score getter
     */
    @Test
    void getScore() {
        assertEquals(0,card.getScore());
    }
}