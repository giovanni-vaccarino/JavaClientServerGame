package polimi.ingsoft.server.model.publicboard;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.factories.DeckFactory;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the PublicBoard class.
 * These tests validate the behavior of various methods in the PublicBoard class.
 */
class PublicBoardTest {

    static PublicBoard board;

    /**
     * Initializes a PublicBoard instance using DeckFactory to create required decks.
     * @throws FileNotFoundException If a required file is not found.
     * @throws JsonProcessingException If there is an error in processing JSON data.
     */
    @BeforeAll
    static void init() throws FileNotFoundException, JsonProcessingException {
        DeckFactory factory=new DeckFactory();
        board=new PublicBoard(factory.createResourceDeck(),factory.createGoldDeck(),factory.createQuestDeck(),factory.createInitialDeck());
    }

    /**
     * Tests the getPublicBoardResource() method of the PublicBoard class.
     * Prints the contents of the resource area of the public board.
     */
    @Test
    void getPublicBoardResource() {
        System.out.println(board.getPublicBoardResource());
    }

    /**
     * Tests the getPublicBoardGold() method of the PublicBoard class.
     * Prints the contents of the gold area of the public board.
     */
    @Test
    void getPublicBoardGold() {
        System.out.println(board.getPublicBoardGold());
    }

    /**
     * Tests the getPublicBoardQuest() method of the PublicBoard class.
     * Prints the contents of the quest area of the public board.
     */
    @Test
    void getPublicBoardQuest() {
        System.out.println(board.getPublicBoardQuest());
    }

    /**
     * Tests the getInitial() method of the PublicBoard class.
     * Prints the initial card of the public board.
     */
    @Test
    void getInitial() {
        System.out.println(board.getInitial());
    }

    /**
     * Tests the getResource() method of the PublicBoard class.
     * Prints the contents of the resource slot specified.
     */
    @Test
    void getResource() {
        System.out.println(board.getResource(PlaceInPublicBoard.Slots.SLOT_A));
    }

    /**
     * Tests the getGold() method of the PublicBoard class.
     * Prints the contents of the gold slot specified.
     */
    @Test
    void getGold() {
        System.out.println(board.getGold(PlaceInPublicBoard.Slots.SLOT_A));
    }

    /**
     * Tests the getQuest() method of the PublicBoard class.
     * Prints the contents of the quest slot specified.
     */
    @Test
    void getQuest() {
        System.out.println(board.getQuest(PlaceInPublicBoard.Slots.DECK));
    }

    /**
     * Tests the isDecksEmpty() method of the PublicBoard class.
     * Verifies if all decks in the public board are empty or not.
     */
    @Test
    void isDecksEmpty() {
       assertFalse(board.isDecksEmpty());
    }
}