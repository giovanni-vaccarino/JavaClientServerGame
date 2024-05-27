package polimi.ingsoft.server.factories;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.model.*;

import java.io.FileNotFoundException;


class DeckFactoryTest {

    @Test
    void createResourceDeck() throws FileNotFoundException, JsonProcessingException {
        DeckFactory cards=new DeckFactory();
        Deck<ResourceCard> deck=cards.createResourceDeck();
    }

    @Test
    void createGoldDeck() throws FileNotFoundException, JsonProcessingException {
        DeckFactory cards=new DeckFactory();
        Deck<GoldCard> deck=cards.createGoldDeck();
    }

    @Test
    void createQuestDeck() throws JsonProcessingException, FileNotFoundException {
        DeckFactory cards=new DeckFactory();
        Deck<QuestCard> deck=cards.createQuestDeck();
    }

    @Test
    void createInitialDeck() throws JsonProcessingException, FileNotFoundException {
        DeckFactory cards=new DeckFactory();
        Deck<InitialCard> deck=cards.createInitialDeck();
    }
}