package polimi.ingsoft.server.factories;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.model.*;

import java.io.FileNotFoundException;


class DeckFactoryTest {

    @Test
    void createResourceDeck() throws FileNotFoundException, JsonProcessingException {
        Deck<ResourceCard> deck= DeckFactory.createResourceDeck();

        Deck<GoldCard> deck2=DeckFactory.createGoldDeck();

        Deck<InitialCard> deck3=DeckFactory.createInitialDeck();

        Deck<QuestCard> deck4=DeckFactory.createQuestDeck();
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