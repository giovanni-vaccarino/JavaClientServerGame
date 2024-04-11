package polimi.ingsoft.model;

import polimi.ingsoft.enumerations.Resource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.enumerations.Object;

import java.sql.Array;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static polimi.ingsoft.model.CornerSpaceTest.t;

class FaceTest {
    Face t=new Face();

    static CornerSpace x,y,z,w;
    static CenterSpace k;

    static ArrayList<Item> a=new ArrayList<Item>(),b=new ArrayList<Item>(),c=new ArrayList<Item>(),d=new ArrayList<Item>();
    static ArrayList<Resource> e=new ArrayList<Resource>();
   @BeforeAll public static void init() {
        a.add(Object.SPELL);
        a.add(Object.SCROLL);
        b.add(Object.SPELL);
        c.add(Object.SCROLL);
        e.add(Resource.LEAF);
        e.add(Resource.WOLF);
        x=new CornerSpace(a);
        y=new CornerSpace(b);
        z=new CornerSpace(c);
        w=new CornerSpace(d);
        k=new CenterSpace(e);
    }

    @Test
    void getCenter(){
        t.setCenter(k);
        assertEquals(k,t.getCenter());
    }
    @Test
    void setCenter(){
       t.setCenter(k);
       assertEquals(k,t.getCenter());
    }
    @Test
    void getUpLeft() {
       t.setUpLeft(x);
       assertEquals(x,t.getUpLeft());
    }

    @Test
    void setUpLeft() {
       t.setUpLeft(x);
       assertEquals(x,t.getUpLeft());
    }

    @Test
    void getUpRight() {
        t.setUpRight(y);
        assertEquals(y,t.getUpRight());
    }

    @Test
    void setUpRight() {
        t.setUpRight(y);
        assertEquals(y,t.getUpRight());
    }

    @Test
    void getBottomLeft() {
        t.setBottomLeft(z);
        assertEquals(z,t.getBottomLeft());
    }

    @Test
    void setBottomLeft() {
        t.setBottomLeft(z);
        assertEquals(z,t.getBottomLeft());
    }

    @Test
    void getBottomRight() {
        t.setBottomRight(w);
        assertEquals(w,t.getBottomRight());
    }

    @Test
    void setBottomRight() {
       t.setBottomRight(w);
        assertEquals(w,t.getBottomRight());
    }
}