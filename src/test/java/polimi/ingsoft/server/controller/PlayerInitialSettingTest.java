package polimi.ingsoft.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.factories.DeckFactory;
import polimi.ingsoft.server.model.cards.InitialCard;
import polimi.ingsoft.server.model.cards.QuestCard;
import polimi.ingsoft.server.model.decks.Deck;
import polimi.ingsoft.server.model.decks.PlayerHand;
import polimi.ingsoft.server.model.player.PlayerColor;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class PlayerInitialSettingTest {

    static PlayerInitialSetting player1,player2;
    static QuestCard quest1,quest2;
    static PlayerHand hand;
    static InitialCard initial;

    @BeforeAll
    static void init() throws FileNotFoundException, JsonProcessingException {
        player1=new PlayerInitialSetting("player1");
        quest1= DeckFactory.createQuestDeck().draw();
        quest2= DeckFactory.createQuestDeck().draw();
        initial= DeckFactory.createInitialDeck().draw();
        hand=new PlayerHand(DeckFactory.createResourceDeck().draw(),DeckFactory.createResourceDeck().draw(),DeckFactory.createGoldDeck().draw());
        player2=new PlayerInitialSetting("player2",hand,quest1,quest2,initial);


    }
    @Test
    void getFirstChoosableQuestCard() {
        assertEquals(quest1,player2.getFirstChoosableQuestCard());
    }

    @Test
    void getSecondChoosableQuestCard() {
        assertEquals(quest2,player2.getSecondChoosableQuestCard());
    }

    @Test
    void setIsInitialFaceUp() {
        player2.setIsInitialFaceUp(true);
        assertTrue(player2.getIsInitialFaceUp());
        player2.setIsInitialFaceUp(false);
        assertFalse(player2.getIsInitialFaceUp());
    }

    @Test
    void setQuestCard() {
        player2.setQuestCard(quest1);
        assertEquals(player2.getQuestCard(),quest1);
    }

    @Test
    void setColor() {
        player2.setColor(PlayerColor.RED);
        assertEquals(PlayerColor.RED,player2.getColor());
    }

    @Test
    void getNickname() {
        assertEquals("player2",player2.getNickname());
    }

    @Test
    void getColor() {
        player2.setColor(PlayerColor.RED);
        assertEquals(PlayerColor.RED,player2.getColor());
    }

    @Test
    void getIsInitialFaceUp() {
        player2.setIsInitialFaceUp(true);
        assertTrue(player2.getIsInitialFaceUp());
        player2.setIsInitialFaceUp(false);
        assertFalse(player2.getIsInitialFaceUp());
    }

    @Test
    void getQuestCard() {
        player2.setQuestCard(quest1);
        assertEquals(player2.getQuestCard(),quest1);
    }

    @Test
    void getPlayerHand() {
        assertEquals(hand,player2.getPlayerHand());
    }

    @Test
    void getInitialCard() {
        assertEquals(initial,player2.getInitialCard());
    }

    @Test
    void testClone() {
        assertEquals(player2.getNickname(),player2.clone().getNickname());
        System.out.println(player2);
        System.out.println(player2.clone());
    }
}