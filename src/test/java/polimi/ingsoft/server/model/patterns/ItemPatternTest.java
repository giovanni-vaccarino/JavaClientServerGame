package polimi.ingsoft.server.model.patterns;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.factories.DeckFactory;
import polimi.ingsoft.server.model.boards.Board;
import polimi.ingsoft.server.model.boards.Coordinates;
import polimi.ingsoft.server.model.cards.InitialCard;
import polimi.ingsoft.server.model.items.Item;
import polimi.ingsoft.server.model.items.Object;
import polimi.ingsoft.server.model.items.Resource;
import polimi.ingsoft.server.model.player.PlayerColor;

import java.io.FileNotFoundException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the ItemPattern class.
 */
class ItemPatternTest {
    static HashMap<Item, Integer> cost;
    static Board board;
    static ItemPattern pattern;

    /**
     * Initializes the cost map, board, and item pattern for testing.
     * @throws FileNotFoundException if a required file is not found
     * @throws JsonProcessingException if there is an error processing JSON
     */
    @BeforeAll
    static void init() throws FileNotFoundException, JsonProcessingException {
        cost=new HashMap<>();
        cost.put(Object.POTION,0);
        cost.put(Object.SCROLL,0);
        cost.put(Object.FEATHER,0);
        cost.put(Resource.LEAF,0);
        cost.put(Resource.MUSHROOM,0);
        cost.put(Resource.BUTTERFLY,0);
        cost.put(Resource.WOLF,1);
        DeckFactory factory=new DeckFactory();
        InitialCard ini=factory.createInitialDeck().draw();
        board=new Board(ini,false,true, PlayerColor.RED);
        pattern=new ItemPattern(cost);
    }

    /**
     * Tests the getMatch method of ItemPattern class under different conditions.
     */
    @Test
    void getMatch() {
        assertEquals(pattern.getMatch(board,new Coordinates(0,0)),1);
        cost.put(Object.POTION,1);
        cost.put(Object.SCROLL,1);
        cost.put(Object.FEATHER,1);
        cost.put(Resource.LEAF,1);
        cost.put(Resource.MUSHROOM,1);
        cost.put(Resource.BUTTERFLY,1);
        assertEquals(pattern.getMatch(board,new Coordinates(0,0)),0);
        cost.put(Object.POTION,0);
        cost.put(Object.SCROLL,0);
        cost.put(Object.FEATHER,0);
        cost.put(Resource.LEAF,1);
        cost.put(Resource.MUSHROOM,1);
        cost.put(Resource.BUTTERFLY,1);
        assertEquals(pattern.getMatch(board,new Coordinates(0,0)),1);
        board.getResources().put(Object.SCROLL,1);
        board.getResources().put(Object.POTION,1);
        board.getResources().put(Object.FEATHER,1);
        cost.put(Object.POTION,1);
        cost.put(Object.SCROLL,1);
        cost.put(Object.FEATHER,1);

        assertEquals(pattern.getMatch(board,new Coordinates(0,0)),1);

    }

    /**
     * Tests the getCost method of ItemPattern class.
     */
    @Test
    void getCost() {
        assertEquals(cost,pattern.getCost());
    }
}