package polimi.ingsoft.server.model.cards;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.model.items.Resource;
import polimi.ingsoft.server.model.cards.GameCard;
import polimi.ingsoft.server.model.cards.ResourceCard;
import polimi.ingsoft.server.model.cards.cardstructure.CenterSpace;
import polimi.ingsoft.server.model.cards.cardstructure.CornerSpace;
import polimi.ingsoft.server.model.cards.cardstructure.Face;
import polimi.ingsoft.server.model.items.Item;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameCardTest {
    static GameCard t;
    static Face front,back;
    static int score=1;
    static CornerSpace front1,front2,front3,front4,back1,back2,back3,back4;
    static ArrayList<Item> b=new ArrayList<Item>(),c=new ArrayList<Item>(),d=new ArrayList<Item>(), f=new ArrayList<Item>(),g=new ArrayList<Item>(),h=new ArrayList<Item>(),i=new ArrayList<Item>();
    static ArrayList<Resource> e=new ArrayList<Resource>();
    static CenterSpace center;
    @BeforeAll public static void init() {
        c.add(Resource.BUTTERFLY);
        d.add(Resource.BUTTERFLY);
        e.add(Resource.BUTTERFLY);
        center=new CenterSpace(e);
        front1=new CornerSpace(null);
        front2=new CornerSpace(b);
        front3=new CornerSpace(c);
        front4=new CornerSpace(d);
        front=new Face(front1,front2,front3,front4);
        back1=new CornerSpace(f);
        back2=new CornerSpace(g);
        back3=new CornerSpace(h);
        back4=new CornerSpace(i);
        back=new Face(back1,back2,back3,back4,center);
        t=new ResourceCard("id",front,back,score);
    }

    @Test
    void getScore() {
        assertEquals(1,t.getScore(false));
        assertEquals(0,t.getScore(true));
    }

    @Test
    void getFront() {
        assertEquals(front,t.getFront());
    }

    @Test
    void getBack() {
        assertEquals(back,t.getBack());
    }

    @Test
    void getUpLeftCorner() {
        assertEquals(front1,t.getUpLeftCorner(true));
        assertEquals(back1,t.getUpLeftCorner(false));
    }

    @Test
    void getUpRightCorner() {
        assertEquals(front2,t.getUpRightCorner(true));
        assertEquals(back2,t.getUpRightCorner(false));
    }

    @Test
    void getBottomLeftCorner() {
        assertEquals(front3,t.getBottomLeftCorner(true));
        assertEquals(back3,t.getBottomLeftCorner(false));
    }

    @Test
    void getBottomRightCorner() {
        assertEquals(front4,t.getBottomRightCorner(true));
        assertEquals(back4,t.getBottomRightCorner(false));
    }

    @Test
    void equals(){
        assertTrue(t.equals(t));
        assertFalse(t.equals(null));
        assertTrue(t.equals(new ResourceCard("id",front,back,score)));
    }
    @Test
    void hashCodeTest(){
        System.out.println(t.hashCode());
    }
}