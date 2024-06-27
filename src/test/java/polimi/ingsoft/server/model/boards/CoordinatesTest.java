package polimi.ingsoft.server.model.boards;

import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.model.boards.Coordinates;

import static org.junit.jupiter.api.Assertions.*;

class CoordinatesTest {
    int x=1,y=-1;
    Coordinates t=new Coordinates(x,y);

    /**
     * Test upLeft relative coordinate
     */
    @Test
    void upLeft() {
        assertEquals(new Coordinates(x-1,y+1),t.upLeft());
    }

    /**
     * Test upRight relative coordinate
     */
    @Test
    void upRight() {
        assertEquals(new Coordinates(x+1,y+1),t.upRight());
    }

    /**
     * Test downLeft relative coordinate
     */
    @Test
    void downLeft() {
        assertEquals(new Coordinates(x-1,y-1),t.downLeft());
    }

    /**
     * Test downRight relative coordinate
     */
    @Test
    void downRight() {
        assertEquals(new Coordinates(x+1,y-1),t.downRight());
    }

    /**
     * Test up relative coordinate
     */
    @Test
    void up() {
        assertEquals(new Coordinates(x,y+2),t.up());
    }

    /**
     * Test down relative coordinate
     */
    @Test
    void down() {
        assertEquals(new Coordinates(x,y-2),t.down());
    }

    /**
     * Test left relative coordinate
     */
    @Test
    void left() {
        assertEquals(new Coordinates(x-2,y),t.left());
    }

    /**
     * Test right relative coordinate
     */
    @Test
    void right() {
        assertEquals(new Coordinates(x+2,y),t.right());
    }

    /**
     * Test clone method
     */
    @Test
    void testClone() {
        assertEquals(new Coordinates(0,0),new Coordinates(0,0).clone());
    }

    /**
     * Test coordinates sub
     */
    @Test
    void sub(){
        assertEquals(new Coordinates(0,0),new Coordinates(2,2).sub(new Coordinates(2,2)));
    }

    /**
     * Test hash method
     */
    @Test
    void hashcode(){
        assertEquals(new Coordinates(0,0).hashCode(),new Coordinates(0,0).hashCode());
    }

    /**
     * Test coordinates sum
     */
    @Test
    void sum(){
        assertEquals(new Coordinates(2,2),new Coordinates(1,1).sum(new Coordinates(1,1)));
    }
}