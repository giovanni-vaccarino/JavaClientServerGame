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

class DeckTest {

    static Deck<ResourceCard> deck;
    static Deck<GoldCard> deck2=new Deck<>();
    @BeforeEach
    void init() throws FileNotFoundException, JsonProcessingException {
        DeckFactory factory=new DeckFactory();
        deck=factory.createResourceDeck();

    }

    @Test
    void draw() {
        System.out.println(deck.draw());
        assertNull(deck2.draw());
    }

    @Test
    void show() {
        System.out.println(deck.show());
        System.out.println(deck.draw());
        assertNull(deck2.show());
    }

    @Test
    void testClone() {
        Deck<ResourceCard> a=deck.clone();
        System.out.println(a.show());
        System.out.println(deck.show());
    }
}