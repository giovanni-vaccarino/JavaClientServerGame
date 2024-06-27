package polimi.ingsoft.server.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.common.Utils.MatchData;
import polimi.ingsoft.server.exceptions.MatchSelectionExceptions.MatchAlreadyFullException;
import polimi.ingsoft.server.exceptions.MatchSelectionExceptions.MatchNotFoundException;
import polimi.ingsoft.server.exceptions.MatchSelectionExceptions.NotValidNumPlayersException;

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
        List<Integer > matches = mainController.getMatches().stream()
                        .map(MatchData::matchId)
                                .toList();

        assertTrue(matches.contains(matchId));
    }

    @Test
    public void testNotValidNumPlayersException() {
        assertThrows(NotValidNumPlayersException.class, () -> mainController.createMatch(5));
    }

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



    @Test
    public void testJoinMatch(){
        Integer matchId = mainController.createMatch(2);

        try{
            mainController.joinMatch(matchId, "Player1");
        } catch (MatchNotFoundException | MatchAlreadyFullException exception){
            fail("Unexpected exception");
        }
    }

    @Test
    public void testJoinMatchNotExists() {
        assertThrows(MatchNotFoundException.class, () -> mainController.joinMatch(5, "Player1"));
    }

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