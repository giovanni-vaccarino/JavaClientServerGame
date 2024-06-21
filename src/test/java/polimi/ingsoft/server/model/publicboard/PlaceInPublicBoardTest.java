package polimi.ingsoft.server.model.publicboard;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.factories.DeckFactory;
import polimi.ingsoft.server.model.cards.ResourceCard;
import polimi.ingsoft.server.model.decks.Deck;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class PlaceInPublicBoardTest {
    static PlaceInPublicBoard<ResourceCard> place, place2 = new PlaceInPublicBoard<>(new Deck<>());

    @BeforeAll
    static void init() throws FileNotFoundException, JsonProcessingException {
        DeckFactory factory = new DeckFactory();
        place = new PlaceInPublicBoard<>(factory.createResourceDeck());
    }

    @Test
    void draw() {
        System.out.println(place.draw(PlaceInPublicBoard.Slots.SLOT_A));
        System.out.println(place.draw(PlaceInPublicBoard.Slots.SLOT_B));
        System.out.println(place.draw(PlaceInPublicBoard.Slots.DECK));
        System.out.println(place.draw(null));
    }

    @Test
    void get() {
        System.out.println(place.get(PlaceInPublicBoard.Slots.SLOT_B));
        System.out.println(place.get(PlaceInPublicBoard.Slots.SLOT_A));
        System.out.println(place.get(PlaceInPublicBoard.Slots.DECK));
        System.out.println(place.get(null));
    }

    @Test
    void isPlaceInPublicBoardEmpty() {
        assertTrue(place2.isPlaceInPublicBoardEmpty());
        assertFalse(place.isPlaceInPublicBoardEmpty());
    }

    @Test
    void testClone() {
        PlaceInPublicBoard<ResourceCard>place3=place.clone();
        System.out.println(place.get(PlaceInPublicBoard.Slots.SLOT_B).toString());
        System.out.println(place3.get(PlaceInPublicBoard.Slots.SLOT_B).toString());
    }
}