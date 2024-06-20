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

class PlayerTest {
    static Player player;
    static PlayerHand hand;
    static QuestCard quest;
    static ResourceCard res;
    static Board board;
    static Deck<ResourceCard> deck;

    @BeforeAll
    static void init() throws FileNotFoundException, JsonProcessingException {
        DeckFactory factory=new DeckFactory();
        quest= factory.createQuestDeck().draw();
        deck=factory.createResourceDeck();
        hand=new PlayerHand(deck.draw(),deck.draw(),factory.createGoldDeck().draw());
        board=new Board(factory.createInitialDeck().draw(),false,true,PlayerColor.RED);
        player=new Player(hand,"name",PlayerColor.RED,board,null);
    }
    @Test
    void getBoard() {
        assertEquals(board,player.getBoard());
    }

    @Test
    void getNickname() {
        assertEquals("name",player.getNickname());
    }

    @Test
    void getQuestCard() {
        assertNull(player.getQuestCard());
    }

    @Test
    void setQuestCard() {
        assertNull(player.getQuestCard());
        player.setQuestCard(quest);
        assertEquals(player.getQuestCard(),quest);
    }

    @Test
    void getHand() {
        assertEquals(hand,player.getHand());
    }

    @Test
    void addToHand() {
        res=deck.draw();
        assertFalse(player.getHand().getCards().contains(res));
        player.addToHand(res);
        assertTrue(player.getHand().getCards().contains(res));
    }

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