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

class PlayerHandTest {

    static PlayerHand hand;
    static Deck<ResourceCard> res;
    static Deck<GoldCard> gold;
    @BeforeAll
    static void init() throws FileNotFoundException, JsonProcessingException {
        DeckFactory factory=new DeckFactory();
        res=factory.createResourceDeck();
        gold=factory.createGoldDeck();
        hand=new PlayerHand(res.draw(),res.draw(),gold.draw());
    }

    @Test
    void get() {
        assertEquals(hand.get(0),hand.cards.get(0));
    }

    @Test
    void getCards() {
        assertEquals(hand.getCards(),hand.cards);
    }

    @Test
    void add() {
        Card card=res.show();
        hand.add(res.draw());
        assertTrue(hand.getCards().contains(card));
    }

    @Test
    void remove() {
        MixedCard card=res.show();
        hand.add(res.draw());
        assertTrue(hand.getCards().contains(card));
        hand.remove(card);
        assertFalse(hand.getCards().contains(card));
    }

    @Test
    void flip() {
        hand.flip(0);
        assertTrue(hand.getIsFlipped().getFirst());
    }

    @Test
    void getIsFlipped() {
        System.out.println(hand.getIsFlipped());

    }

    @Test
    void testClone() {
        PlayerHand hand2=hand.clone();
        assertEquals(hand2.getCards().getFirst(),hand.getCards().getFirst());
    }
}