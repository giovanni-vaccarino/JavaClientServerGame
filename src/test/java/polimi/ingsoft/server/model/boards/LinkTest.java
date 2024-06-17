package polimi.ingsoft.server.model.boards;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.model.items.Resource;

import static org.junit.jupiter.api.Assertions.*;

class LinkTest {
    static Link link;

    @BeforeAll
    public static void init(){
        link=new Link(Resource.LEAF,new Coordinates(0,0));
    }
    @Test
    void getPosFromBegin() {
        assertEquals(new Coordinates(0,0),link.getPosFromBegin());
    }

    @Test
    void getColor() {
        assertEquals(Resource.LEAF,link.getColor());
    }
}