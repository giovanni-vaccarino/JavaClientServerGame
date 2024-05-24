package polimi.ingsoft.server.factories;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.model.Deck;
import polimi.ingsoft.server.model.GoldCard;
import polimi.ingsoft.server.model.ResourceCard;

import java.io.FileNotFoundException;


class DeckFactoryTest {

    @Test
    void createDeck() throws FileNotFoundException, JsonProcessingException {
        DeckFactory cards=new DeckFactory();
        Deck<ResourceCard> deck=cards.createResourceDeck();
    }

    @Test
    void createGoldDeck() throws FileNotFoundException, JsonProcessingException {
        DeckFactory cards=new DeckFactory();
        Deck<GoldCard> deck=cards.createGoldDeck();
    }

    @Test
    void createQuestDeck() {
    }

    @Test
    void createInitialDeck() {
    }
}