package polimi.ingsoft.server.model.cards;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.factories.DeckFactory;
import polimi.ingsoft.server.model.boards.Board;
import polimi.ingsoft.server.model.boards.Coordinates;
import polimi.ingsoft.server.model.cards.cardstructure.CenterSpace;
import polimi.ingsoft.server.model.cards.cardstructure.CornerSpace;
import polimi.ingsoft.server.model.cards.cardstructure.Face;
import polimi.ingsoft.server.model.decks.Deck;
import polimi.ingsoft.server.model.items.Item;
import polimi.ingsoft.server.model.items.Object;
import polimi.ingsoft.server.model.items.Resource;
import polimi.ingsoft.server.model.patterns.ItemPattern;
import polimi.ingsoft.server.model.player.PlayerColor;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class GoldCardTest {
    static GoldCard gold;
    static Deck<InitialCard> initialdeck;
    static Deck<GoldCard> goldDeck;
    static Face front,back;
    static Board board;
    static ItemPattern pattern;
    static DeckFactory factory;

    @BeforeAll
    static void init() throws FileNotFoundException, JsonProcessingException {
        factory=new DeckFactory();
        ArrayList<Item> items=new ArrayList<>(),items2=new ArrayList<>(),items3=new ArrayList<>();
        ArrayList<Resource> center=new ArrayList<>();
        center.add(Resource.LEAF);
        front=new Face(new CornerSpace(items),new CornerSpace(items),new CornerSpace(items),new CornerSpace(items),new CenterSpace(center));
        items2.add(Resource.BUTTERFLY);
        items3.add(Object.FEATHER);
        back=new Face(null,new CornerSpace(items2),new CornerSpace(new ArrayList<>()),new CornerSpace(items3),null);
        HashMap<Item,Integer> cost=new HashMap<>();
        cost.put(Resource.WOLF,0);
        cost.put(Resource.LEAF,0);
        cost.put(Resource.BUTTERFLY,0);
        cost.put(Resource.MUSHROOM,0);
        cost.put(Object.FEATHER,0);
        cost.put(Object.SCROLL,0);
        cost.put(Object.POTION,0);
        pattern= new ItemPattern(cost);
        gold=new GoldCard("gold",front,back,pattern,null,3);
        initialdeck=factory.createInitialDeck();
        goldDeck=factory.createGoldDeck();
        board=new Board(initialdeck.draw(),true,true, PlayerColor.RED);
    }

    /**
     * Test gold card play pattern getter
     */
    @Test
    void getPlayPattern() {
        assertEquals(pattern,gold.getPlayPattern());
        System.out.println(goldDeck.draw().getPlayPattern());
    }

    /**
     * Test gold card point pattern getter
     */
    @Test
    void getPointPattern() {
        assertNull(gold.getPointPattern());
        System.out.println(goldDeck.draw().getPlayPattern());
    }

    /**
     * Test gold card condition matches on a given board
     */
    @Test
    void getMatches() {
        assertEquals(0,gold.getMatches(board));
    }

    /**
     * Test gold card playability getter
     */
    @Test
    void getPlayability() {
        assertEquals(0,gold.getPlayability(board));
    }

    /**
     * Test points getter
     */
    @Test
    void getPoints() {
        assertEquals(1,gold.getPoints(board,new Coordinates(1,1)));
    }

    /**
     * Test score getter
     */
    @Test
    void getScore() {
        assertEquals(3,gold.getScore());
    }
}