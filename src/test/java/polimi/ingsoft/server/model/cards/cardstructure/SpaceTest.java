package polimi.ingsoft.server.model.cards.cardstructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.model.items.Item;
import polimi.ingsoft.server.model.items.Resource;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SpaceTest {
    CornerSpace a;

    List<Item> list;
    @BeforeEach
    public void init(){
        list=new ArrayList<>();
                list.add(Resource.LEAF);
        a=new CornerSpace(list);
    }
    @Test
    void getItems() {
        assertEquals(list,a.getItems());
    }

    @Test
    void isEmpty() {
        assertFalse(a.isEmpty());
    }

    @Test
    void testEquals() {
        assertTrue(a.equals(new CornerSpace(list)));
        assertFalse(a.equals(null));
        assertFalse(a.equals(new CornerSpace(null)));
    }

    @Test
    void testHashCode() {
        System.out.println(a.hashCode());
    }
}