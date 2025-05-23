package polimi.ingsoft.server.model.cards;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.factories.DeckFactory;
import polimi.ingsoft.server.model.boards.Board;
import polimi.ingsoft.server.model.boards.Coordinates;
import polimi.ingsoft.server.model.decks.Deck;
import polimi.ingsoft.server.model.player.PlayerColor;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class ResourceCardTest {
    static ResourceCard a;
    static Board board;
    @BeforeAll
    static void init() throws FileNotFoundException, JsonProcessingException {
        DeckFactory factory=new DeckFactory();
        Deck<ResourceCard> res=factory.createResourceDeck();
        Deck<InitialCard> ini= factory.createInitialDeck();
        board=new Board(ini.draw(),true,true, PlayerColor.RED);
        a=res.draw();
    }

    /**
     * Test resource card playability
     */
    @Test
    void getPlayability() {
        assertEquals(a.getPlayability(board),1);
    }

    /**
     * Test points getter
     */
    @Test
    void getPoints() {
        assertEquals(1,a.getPoints(board,new Coordinates(0,0)));
    }

    /**
     * Test play pattern getter
     */
    @Test
    void getPlayPattern() {
        assertNull(a.getPlayPattern());
    }

    /**
     * Test point pattern
     */
    @Test
    void getPointPattern() {
        assertNull(a.getPointPattern());
    }

    /**
     * Test resource card matches on a given board
     */
    @Test
    void getMatches() {
        assertEquals(1,a.getMatches(board));
    }

    /**
     * Test score getter
     */
    @Test
    void getScore() {
        System.out.println(a.getScore());
    }
}