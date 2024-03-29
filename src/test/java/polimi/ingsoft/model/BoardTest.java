package polimi.ingsoft.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void add() {
         Board x=new Board() ;
        assertAll(
                () -> assertEquals(false, x.add(new Coordinates(0,0),null,true)),
                () -> assertEquals(true, x.add(new Coordinates(1,1),null,true))

        );
    }

    @Test
    void check() {
    }
}