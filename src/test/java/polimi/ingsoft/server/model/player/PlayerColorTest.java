package polimi.ingsoft.server.model.player;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PlayerColorTest {

    @Test
    void testToString() {
        assertEquals("RED",PlayerColor.RED.toString());
        assertEquals("BLUE",PlayerColor.BLUE.toString());
        assertEquals("YELLOW",PlayerColor.YELLOW.toString());
        assertEquals("GREEN",PlayerColor.GREEN.toString());
    }

    @Test
    void values() {
        System.out.println(Arrays.toString(PlayerColor.values()));
    }
}