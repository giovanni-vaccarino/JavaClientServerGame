package polimi.ingsoft.model;

import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.model.Coordinates;

import static org.junit.jupiter.api.Assertions.*;

class CoordinatesTest {
    int x=1,y=-1;
Coordinates t=new Coordinates(x,y);
    @Test
    void upLeft() {
        assertEquals(new Coordinates(x-1,y+1),t.upLeft());
    }

    @Test
    void upRight() {
        assertEquals(new Coordinates(x+1,y+1),t.upRight());
    }

    @Test
    void downLeft() {
        assertEquals(new Coordinates(x-1,y-1),t.downLeft());
    }

    @Test
    void downRight() {
        assertEquals(new Coordinates(x+1,y-1),t.downRight());
    }

    @Test
    void up() {
        assertEquals(new Coordinates(x,y+2),t.up());
    }

    @Test
    void down() {
        assertEquals(new Coordinates(x,y-2),t.down());
    }

    @Test
    void left() {
        assertEquals(new Coordinates(x-2,y),t.left());
    }

    @Test
    void right() {
        assertEquals(new Coordinates(x+2,y),t.right());
    }
}