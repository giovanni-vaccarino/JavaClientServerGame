package polimi.ingsoft.server.model.items;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link Object} class.
 */
class ObjectTest {

    /**
     * Tests the getAbbreviation method of the {@link Object}.
     * Prints the abbreviations of different objects.
     */
    @Test
    void getAbbreviation() {
        System.out.println(Object.FEATHER.getAbbreviation());
        System.out.println(Object.SCROLL.getAbbreviation());
        System.out.println(Object.POTION.getAbbreviation());
    }
}