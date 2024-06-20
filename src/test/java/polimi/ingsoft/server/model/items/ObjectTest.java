package polimi.ingsoft.server.model.items;

import org.junit.jupiter.api.Test;

class ObjectTest {

    @Test
    void getAbbreviation() {
        System.out.println(Object.FEATHER.getAbbreviation());
        System.out.println(Object.SCROLL.getAbbreviation());
        System.out.println(Object.POTION.getAbbreviation());
    }
}