package polimi.ingsoft.server.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.common.Utils.MatchData;
import polimi.ingsoft.server.exceptions.MatchSelectionExceptions.MatchAlreadyFullException;
import polimi.ingsoft.server.exceptions.MatchSelectionExceptions.MatchNotFoundException;
import polimi.ingsoft.server.exceptions.MatchSelectionExceptions.NotValidNumPlayersException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MainController.
 */
class MainControllerTest {
    private MainController mainController;

    /**
     * Sets up the MainController before each test.
     */
    @BeforeEach
    public void setUp() {
        mainController = new MainController(System.out);
    }

    /**
     * Tests the creation of a match.
     */
    @Test
    public void testCreateMatch() {
        Integer matchId = mainController.createMatch(4);

        assertNotNull(matchId);
        List<Integer > matches = mainController.getMatches().stream()
                        .map(MatchData::matchId)
                                .toList();

        assertTrue(matches.contains(matchId));
    }

    /**
     * Tests that NotValidNumPlayersException is thrown when creating a match
     * with an invalid number of players.
     */
    @Test
    public void testNotValidNumPlayersException() {
        assertThrows(NotValidNumPlayersException.class, () -> mainController.createMatch(5));
    }

    /**
     * Tests the deletion of a match.
     */
    @Test
    public void testDeleteMatch() {
        Integer matchId = mainController.createMatch(4);

        assertNotNull(matchId);

        mainController.deleteMatch(matchId);

        List<Integer > matches = mainController.getMatches().stream()
                .map(MatchData::matchId)
                .toList();

        assertFalse(matches.contains(matchId));
    }

    /**
     * Tests joining a match.
     */
    @Test
    public void testJoinMatch(){
        Integer matchId = mainController.createMatch(2);

        try{
            mainController.joinMatch(matchId, "Player1");
        } catch (MatchNotFoundException | MatchAlreadyFullException exception){
            fail("Unexpected exception");
        }
    }

    /**
     * Tests that MatchNotFoundException is thrown when joining a non-existent match.
     */
    @Test
    public void testJoinMatchNotExists() {
        assertThrows(MatchNotFoundException.class, () -> mainController.joinMatch(5, "Player1"));
    }

    /**
     * Tests that MatchAlreadyFullException is thrown when joining a full match.
     */
    @Test
    public void testJoinMatchFull() {
        Integer matchId = mainController.createMatch(2);

        try{
            mainController.joinMatch(matchId, "Player1");
            mainController.joinMatch(matchId, "Player2");
        } catch (MatchAlreadyFullException | MatchNotFoundException e) {
            fail("Unexpected exception");
        }

        assertThrows(MatchAlreadyFullException.class, () -> mainController.joinMatch(matchId, "Player3"));
    }

    /**
     * Tests retrieving the list of matches.
     */
    @Test
    public void testGetMatches() {
        Integer matchId1 = mainController.createMatch(2);
        Integer matchId2 = mainController.createMatch(3);

        List<MatchData> matches = mainController.getMatches();
        List<Integer> matchIDs = matches.stream().map( match -> match.matchId()).toList();
        assertTrue(matchIDs.contains(matchId1));
        assertTrue(matchIDs.contains(matchId2));
    }

}