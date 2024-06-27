package polimi.ingsoft.server.model.cards;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.factories.DeckFactory;
import polimi.ingsoft.server.model.boards.Board;
import polimi.ingsoft.server.model.boards.Link;
import polimi.ingsoft.server.model.cards.QuestCard;
import polimi.ingsoft.server.model.decks.Deck;
import polimi.ingsoft.server.model.items.Item;
import polimi.ingsoft.server.model.patterns.ItemPattern;
import polimi.ingsoft.server.model.patterns.SchemePattern;
import polimi.ingsoft.server.model.player.PlayerColor;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuestCardTest {
    static QuestCard a;
    static Board board;
    @BeforeAll
    static void init() throws FileNotFoundException, JsonProcessingException {
        DeckFactory factory=new DeckFactory();
        Deck<QuestCard> deck=factory.createQuestDeck();
        a=deck.draw();
        Deck<InitialCard> initial=factory.createInitialDeck();
        board=new Board(initial.draw(),true,true, PlayerColor.RED);
    }

    /**
     * Test score getter
     */
    @Test
    void getScore() {
        System.out.println(a.getScore());
    }

    /**
     * Test quest card condition matches on a given board
     */
    @Test
    void getMatches() {
        assertEquals(0,a.getMatches(board));
    }

    /**
     * Test quest card point pattern getter
     */
    @Test
    void getPointPattern() {
        System.out.println("pointpattern:"+ a.getPointPattern());
    }

    /**
     * Test quest card play pattern getter
     */
    @Test
    void getPlayPattern() {
        System.out.println("playpattern:" + a.getPlayPattern());
    }


}