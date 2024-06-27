package polimi.ingsoft.server.model.player;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PlayerColorTest {

    /**
     * Tests the toString() method of the PlayerColor enumeration.
     * Verifies that each PlayerColor constant returns the expected string representation.
     */
    @Test
    void testToString() {
        assertEquals("RED",PlayerColor.RED.toString());
        assertEquals("BLUE",PlayerColor.BLUE.toString());
        assertEquals("YELLOW",PlayerColor.YELLOW.toString());
        assertEquals("GREEN",PlayerColor.GREEN.toString());
    }

    /**
     * Tests the values() method of the PlayerColor enumeration.
     * Prints out all the constants defined in the PlayerColor enumeration using Arrays.toString().
     * Useful for verifying the completeness of enumeration constants during development.
     */
    @Test
    void values() {
        System.out.println(Arrays.toString(PlayerColor.values()));
    }
}