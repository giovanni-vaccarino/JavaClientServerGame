package polimi.ingsoft.server.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainControllerTest {
    private MainController mainController;

    @BeforeEach
    public void setUp() {
        mainController = new MainController(System.out);
    }

    @Test
    public void testCreateMatch() {
        Integer matchId = mainController.createMatch(4);

        assertNotNull(matchId);
        assertTrue(mainController.getMatches().contains(matchId));
    }

    @Test
    public void testJoinMatch(){
        Integer matchId = mainController.createMatch(2);

        assertTrue(mainController.joinMatch(matchId, "Player1"));
    }

    @Test
    public void testJoinMatchNotExists() {
        assertFalse(mainController.joinMatch(5, "Player1"));
    }

    @Test
    public void testJoinMatchFull() {
        Integer matchId = mainController.createMatch(2);

        mainController.joinMatch(matchId, "Player1");
        mainController.joinMatch(matchId, "Player2");

        assertFalse(mainController.joinMatch(matchId, "Player3"));
    }

    @Test
    public void testGetMatches() {
        Integer matchId1 = mainController.createMatch(2);
        Integer matchId2 = mainController.createMatch(3);

        List<Integer> matches = mainController.getMatches();

        assertTrue(matches.contains(matchId1));
        assertTrue(matches.contains(matchId2));
    }
}