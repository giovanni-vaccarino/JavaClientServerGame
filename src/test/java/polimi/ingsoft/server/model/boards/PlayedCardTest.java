package polimi.ingsoft.server.model.boards;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.model.cards.ResourceCard;
import polimi.ingsoft.server.model.cards.cardstructure.CenterSpace;
import polimi.ingsoft.server.model.cards.cardstructure.CornerSpace;
import polimi.ingsoft.server.model.cards.cardstructure.Face;
import polimi.ingsoft.server.model.items.Item;
import polimi.ingsoft.server.model.items.Resource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayedCardTest {

    static PlayedCard card;
    static Face front,back;
    static ResourceCard a;

    @BeforeEach
    public void init(){
        ArrayList<Item> items=new ArrayList<>();
        items.add(Resource.LEAF);
        ArrayList<Resource> center=new ArrayList<>();
        center.add(Resource.BUTTERFLY);
        back=new Face(new CornerSpace(items),null,null,new CornerSpace(items),null);
        front=new Face(new CornerSpace(new ArrayList<>()),new CornerSpace(new ArrayList<>()),new CornerSpace(new ArrayList<>()),new CornerSpace(new ArrayList<>()),new CenterSpace(center));
        a=new ResourceCard("resouce",front,back,2);
        card=new PlayedCard(a,true,0);
    }

    /**
     * Test color getter
     */
    @Test
    void getColor() {
        assertEquals(card.getColor(),Resource.BUTTERFLY);
    }

    /**
     * Test face getter
     */
    @Test
    void getFace() {
        assertEquals(front,card.getFace());
        card=new PlayedCard(a,false,0);
        assertEquals(back,card.getFace());
    }

    /**
     * Test score getter
     */
    @Test
    void getScore() {
        card=new PlayedCard(a,true,0);
        assertEquals(0,card.getScore());
        card=new PlayedCard(a,false,0);
        assertEquals(2,card.getScore());
    }

    /**
     * Test upRight setter
     */
    @Test
    void setUpRight() {
        assertTrue(card.isFreeUpRight());
        card.setUpRight();
        assertFalse(card.isFreeUpRight());
    }

    /**
     * Test upLeft setter
     */
    @Test
    void setUpLeft() {
        assertTrue(card.isFreeUpLeft());
        card.setUpLeft();
        assertFalse(card.isFreeUpLeft());
    }

    /**
     * Test downRight setter
     */
    @Test
    void setDownRight() {
        assertTrue(card.isFreeDownRight());
        card.setDownRight();
        assertFalse(card.isFreeDownRight());
    }

    /**
     * Test downLeft setter
     */
    @Test
    void setDownLeft() {
        assertTrue(card.isFreeDownLeft());
        card.setDownLeft();
        assertFalse(card.isFreeDownLeft());
    }

    /**
     * Test order getter
     */
    @Test
    void getOrder() {
            assertEquals(0,card.getOrder());
    }

    /**
     * Test clone method
     */
    @Test
    void testClone() {
        System.out.println(card.clone().getCard().getFront().getCenter().getItems().toString());
        System.out.println(card.clone().getCard().getBack().getUpLeft().getItems().toString());
    }
}