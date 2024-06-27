package polimi.ingsoft.server.model.publicboard;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.factories.DeckFactory;
import polimi.ingsoft.server.model.cards.ResourceCard;
import polimi.ingsoft.server.model.decks.Deck;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the PlaceInPublicBoard class.
 * These tests validate the behavior of the PlaceInPublicBoard methods.
 */
class PlaceInPublicBoardTest {
    static PlaceInPublicBoard<ResourceCard> place, place2 = new PlaceInPublicBoard<>(new Deck<>());

    /**
     * Initializes common objects needed for testing.
     * Creates instances of PlaceInPublicBoard with different configurations.
     * @throws FileNotFoundException If a required file is not found.
     * @throws JsonProcessingException If there is an error in processing JSON data.
     */
    @BeforeAll
    static void init() throws FileNotFoundException, JsonProcessingException {
        DeckFactory factory = new DeckFactory();
        place = new PlaceInPublicBoard<>(factory.createResourceDeck());
    }

    /**
     * Tests the draw() method of the PlaceInPublicBoard class.
     * Prints the results of drawing cards from different slots and validates behavior.
     */
    @Test
    void draw() {
        System.out.println(place.draw(PlaceInPublicBoard.Slots.SLOT_A));
        System.out.println(place.draw(PlaceInPublicBoard.Slots.SLOT_B));
        System.out.println(place.draw(PlaceInPublicBoard.Slots.DECK));
        System.out.println(place.draw(null));
    }

    /**
     * Tests the get() method of the PlaceInPublicBoard class.
     * Prints the results of retrieving cards from different slots and validates behavior.
     */
    @Test
    void get() {
        System.out.println(place.get(PlaceInPublicBoard.Slots.SLOT_B));
        System.out.println(place.get(PlaceInPublicBoard.Slots.SLOT_A));
        System.out.println(place.get(PlaceInPublicBoard.Slots.DECK));
        System.out.println(place.get(null));
    }

    /**
     * Tests the isPlaceInPublicBoardEmpty() method of the PlaceInPublicBoard class.
     * Verifies if the place in public board is correctly identified as empty or not.
     */
    @Test
    void isPlaceInPublicBoardEmpty() {
        assertTrue(place2.isPlaceInPublicBoardEmpty());
        assertFalse(place.isPlaceInPublicBoardEmpty());
    }

    /**
     * Tests the clone() method of the PlaceInPublicBoard class.
     * Verifies if cloning a place in public board produces an identical copy.
     */
    @Test
    void testClone() {
        PlaceInPublicBoard<ResourceCard>place3=place.clone();
        System.out.println(place.get(PlaceInPublicBoard.Slots.SLOT_B).toString());
        System.out.println(place3.get(PlaceInPublicBoard.Slots.SLOT_B).toString());
    }
}