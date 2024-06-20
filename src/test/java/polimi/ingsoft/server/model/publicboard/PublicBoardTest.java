package polimi.ingsoft.server.model.publicboard;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.factories.DeckFactory;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class PublicBoardTest {

    static PublicBoard board;
    @BeforeAll
    static void init() throws FileNotFoundException, JsonProcessingException {
        DeckFactory factory=new DeckFactory();
        board=new PublicBoard(factory.createResourceDeck(),factory.createGoldDeck(),factory.createQuestDeck(),factory.createInitialDeck());
    }

    @Test
    void getPublicBoardResource() {
        System.out.println(board.getPublicBoardResource());
    }

    @Test
    void getPublicBoardGold() {
        System.out.println(board.getPublicBoardGold());
    }

    @Test
    void getPublicBoardQuest() {
        System.out.println(board.getPublicBoardQuest());
    }

    @Test
    void getInitial() {
        System.out.println(board.getInitial());
    }

    @Test
    void getResource() {
        System.out.println(board.getResource(PlaceInPublicBoard.Slots.SLOT_A));
    }

    @Test
    void getGold() {
        System.out.println(board.getGold(PlaceInPublicBoard.Slots.SLOT_A));
    }

    @Test
    void getQuest() {
        System.out.println(board.getQuest(PlaceInPublicBoard.Slots.DECK));
    }

    @Test
    void isDecksEmpty() {
       assertFalse(board.isDecksEmpty());
    }
}