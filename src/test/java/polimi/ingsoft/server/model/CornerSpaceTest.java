package polimi.ingsoft.server.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.*;
import polimi.ingsoft.server.model.items.Object;
import polimi.ingsoft.server.model.cards.cardstructure.CornerSpace;
import polimi.ingsoft.server.model.items.Item;

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