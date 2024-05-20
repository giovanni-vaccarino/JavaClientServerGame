package polimi.ingsoft.server.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.*;
import polimi.ingsoft.server.enumerations.Object;
import polimi.ingsoft.server.model.CornerSpace;
import polimi.ingsoft.server.model.Item;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CornerSpaceTest {

    static ArrayList<Item> a,t;
    static CornerSpace x;

     @BeforeAll public static void init(){
        a =new ArrayList<Item>();
        a.add(Object.POTION);
        a.add(Object.SCROLL);
         t= new ArrayList<Item>();
         t.add(Object.POTION);
         t.add(Object.SCROLL);
         x=new CornerSpace(t);
    }
    @Test
    void getItems() {
        assertEquals(t,x.getItems());
    }

    @Test
    void isEmpty() {
         assertEquals(false,x.isEmpty());
    }
}