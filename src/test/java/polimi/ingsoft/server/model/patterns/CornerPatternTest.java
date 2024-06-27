package polimi.ingsoft.server.model.patterns;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.factories.DeckFactory;
import polimi.ingsoft.server.model.boards.Board;
import polimi.ingsoft.server.model.boards.Coordinates;
import polimi.ingsoft.server.model.cards.InitialCard;
import polimi.ingsoft.server.model.cards.ResourceCard;
import polimi.ingsoft.server.model.decks.Deck;
import polimi.ingsoft.server.model.player.PlayerColor;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit tests for the CornerPattern class.
 */
class CornerPatternTest {
    static Board board;

    /**
     * Initializes the board for testing.
     * @throws FileNotFoundException if a required file is not found
     * @throws JsonProcessingException if there is an error processing JSON
     */
    @BeforeAll
    static void init() throws FileNotFoundException, JsonProcessingException {
        DeckFactory factory=new DeckFactory();
        Deck<InitialCard> ini=factory.createInitialDeck();
        board=new Board(ini.draw(),false,true, PlayerColor.RED);
        Deck<ResourceCard> res=factory.createResourceDeck();
        board.add(new Coordinates(1,1),res.draw(),true);
        board.add(new Coordinates(-1,1),res.draw(),true);
        board.add(new Coordinates(-1,-1),res.draw(),true);
        board.add(new Coordinates(1,-1),res.draw(),true);


    }

    /**
     * Tests the getMatch method of CornerPattern class.
     */
    @Test
    void getMatch() {
        assertEquals(4,new CornerPattern().getMatch(board,new Coordinates(0,0)));
    }

    /**
     * Tests the setCost method of CornerPattern class with a string parameter.
     */
    @Test
    void setCost() {
        new CornerPattern().setCost("string");
    }
}