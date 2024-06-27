package polimi.ingsoft.server.model.player;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.factories.DeckFactory;
import polimi.ingsoft.server.model.boards.Board;
import polimi.ingsoft.server.model.cards.QuestCard;
import polimi.ingsoft.server.model.cards.ResourceCard;
import polimi.ingsoft.server.model.decks.Deck;
import polimi.ingsoft.server.model.decks.PlayerHand;
import polimi.ingsoft.server.model.items.Resource;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Player class.
 * These tests validate the behavior of the Player class methods.
 */
class PlayerTest {
    static Player player;
    static PlayerHand hand;
    static QuestCard quest;
    static ResourceCard res;
    static Board board;
    static Deck<ResourceCard> deck;

    /**
     * Initializes common objects needed for testing.
     * Creates a Player instance with a hand, board, and initial state.
     * @throws FileNotFoundException If a required file is not found.
     * @throws JsonProcessingException If there is an error in processing JSON data.
     */
    @BeforeAll
    static void init() throws FileNotFoundException, JsonProcessingException {
        DeckFactory factory=new DeckFactory();
        quest= factory.createQuestDeck().draw();
        deck=factory.createResourceDeck();
        hand=new PlayerHand(deck.draw(),deck.draw(),factory.createGoldDeck().draw());
        board=new Board(factory.createInitialDeck().draw(),false,true,PlayerColor.RED);
        player=new Player(hand,"name",PlayerColor.RED,board,null);
    }

    /**
     * Tests the getBoard() method of the Player class.
     * Verifies that the player's board is retrieved correctly.
     */
    @Test
    void getBoard() {
        assertEquals(board,player.getBoard());
    }

    /**
     * Tests the getNickname() method of the Player class.
     * Verifies that the player's nickname is retrieved correctly.
     */
    @Test
    void getNickname() {
        assertEquals("name",player.getNickname());
    }

    /**
     * Tests the getQuestCard() method of the Player class.
     * Verifies that initially, no quest card is assigned to the player.
     */
    @Test
    void getQuestCard() {
        assertNull(player.getQuestCard());
    }

    /**
     * Tests the setQuestCard() method of the Player class.
     * Verifies that the player's quest card can be set and retrieved correctly.
     */
    @Test
    void setQuestCard() {
        assertNull(player.getQuestCard());
        player.setQuestCard(quest);
        assertEquals(player.getQuestCard(),quest);
    }

    /**
     * Tests the getHand() method of the Player class.
     * Verifies that the player's hand is retrieved correctly.
     */
    @Test
    void getHand() {
        assertEquals(hand,player.getHand());
    }

    /**
     * Tests the addToHand() method of the Player class.
     * Verifies that a resource card can be added to the player's hand.
     */
    @Test
    void addToHand() {
        res=deck.draw();
        assertFalse(player.getHand().getCards().contains(res));
        player.addToHand(res);
        assertTrue(player.getHand().getCards().contains(res));
    }

    /**
     * Tests the removeFromHand() method of the Player class.
     * Verifies that a resource card can be removed from the player's hand.
     */
    @Test
    void removeFromHand() {
        res=deck.draw();
        assertFalse(player.getHand().getCards().contains(res));
        player.addToHand(res);
        assertTrue(player.getHand().getCards().contains(res));
        player.removeFromHand(res);
        assertFalse(player.getHand().getCards().contains(res));
    }
}