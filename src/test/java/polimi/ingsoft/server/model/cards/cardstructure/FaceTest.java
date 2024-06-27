package polimi.ingsoft.server.model.cards.cardstructure;

import polimi.ingsoft.server.model.items.Resource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.model.items.Object;
import polimi.ingsoft.server.model.cards.cardstructure.CenterSpace;
import polimi.ingsoft.server.model.cards.cardstructure.CornerSpace;
import polimi.ingsoft.server.model.cards.cardstructure.Face;
import polimi.ingsoft.server.model.items.Item;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FaceTest {
    Face t=new Face();

    static CornerSpace x,y,z,w;
    static CenterSpace k;

    static ArrayList<Item> a=new ArrayList<Item>(),b=new ArrayList<Item>(),c=new ArrayList<Item>(),d=new ArrayList<Item>();
    static ArrayList<Resource> e=new ArrayList<Resource>();
   @BeforeAll public static void init() {
        a.add(Object.POTION);
        a.add(Object.SCROLL);
        b.add(Object.POTION);
        c.add(Object.SCROLL);
        e.add(Resource.LEAF);
        e.add(Resource.WOLF);
        x=new CornerSpace(a);
        y=new CornerSpace(b);
        z=new CornerSpace(c);
        w=new CornerSpace(d);
        k=new CenterSpace(e);
    }

    /**
     * Test center space getter
     */
    @Test
    void getCenter(){
        t.setCenter(k);
        assertEquals(k,t.getCenter());
    }

    /**
     * Test center space setter
     */
    @Test
    void setCenter(){
       t.setCenter(k);
       assertEquals(k,t.getCenter());
    }

    /**
     * Test up left getter
     */
    @Test
    void getUpLeft() {
       t.setUpLeft(x);
       assertEquals(x,t.getUpLeft());
    }

    /**
     * Test up left setter
     */
    @Test
    void setUpLeft() {
       t.setUpLeft(x);
       assertEquals(x,t.getUpLeft());
    }

    /**
     * Test up right getter
     */
    @Test
    void getUpRight() {
        t.setUpRight(y);
        assertEquals(y,t.getUpRight());
    }

    /**
     * Test up right setter
     */
    @Test
    void setUpRight() {
        t.setUpRight(y);
        assertEquals(y,t.getUpRight());
    }

    /**
     * Test bottom left getter
     */
    @Test
    void getBottomLeft() {
        t.setBottomLeft(z);
        assertEquals(z,t.getBottomLeft());
    }

    /**
     * Test bottom left setter
     */
    @Test
    void setBottomLeft() {
        t.setBottomLeft(z);
        assertEquals(z,t.getBottomLeft());
    }

    /**
     * Test bottom right getter
     */
    @Test
    void getBottomRight() {
        t.setBottomRight(w);
        assertEquals(w,t.getBottomRight());
    }

    /**
     * Test bottom right setter
     */
    @Test
    void setBottomRight() {
       t.setBottomRight(w);
        assertEquals(w,t.getBottomRight());
    }

    /**
     * Test equals method
     */
    @Test
    void equalsTest(){
       assertFalse(t.equals(null));
       assertTrue(t.equals(new Face()));

    }
}