package polimi.ingsoft.server.factories;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.enumerations.Resource;
import polimi.ingsoft.server.model.Deck;
import polimi.ingsoft.server.model.ResourceCard;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class DeckFactoryTest {

    @Test
    void createDeck() throws FileNotFoundException, JsonProcessingException {
        DeckFactory cards=new DeckFactory();
        Deck<ResourceCard> deck=new Deck<ResourceCard>(cards.createResourceDeck());
    }
}