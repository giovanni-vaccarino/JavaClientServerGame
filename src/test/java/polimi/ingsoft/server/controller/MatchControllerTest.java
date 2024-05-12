package polimi.ingsoft.server.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.enumerations.GAME_PHASE;
import polimi.ingsoft.server.enumerations.INITIAL_STEP;
import polimi.ingsoft.server.enumerations.PlayerColors;
import polimi.ingsoft.server.exceptions.InitalChoiceAlreadySetException;
import polimi.ingsoft.server.exceptions.MatchAlreadyFullException;

import static org.junit.jupiter.api.Assertions.*;

class MatchControllerTest {
    private MatchController matchController;
    private GameState gameState;

    @BeforeEach
    void setUp() {
        matchController = new MatchController(System.out, 1, 3, null, null);
        gameState = new GameState(matchController, 3);
    }

    @Test
    void testPlayerJoiningAndInitialization() {
        try {
            matchController.addPlayer("Player1");
            matchController.addPlayer("Player2");
            matchController.addPlayer("Player3");
        } catch (MatchAlreadyFullException e) {
            fail("Unexpected MatchAlreadyFullException");
        }

        gameState.updateState();

        assertEquals(3, matchController.getPlayerInitialSettings().size());
        assertEquals("Player1", matchController.getPlayerInitialSettings().get(0).getNickname());
        assertEquals("Player2", matchController.getPlayerInitialSettings().get(1).getNickname());
        assertEquals("Player3", matchController.getPlayerInitialSettings().get(2).getNickname());
        assertEquals(GAME_PHASE.INITIALIZATION, gameState.getGamePhase());
        assertEquals(INITIAL_STEP.COLOR, gameState.getCurrentInitialStep());
    }

    @Test
    void testMatchAlreadyFullException() {
        try {
            matchController.addPlayer("Player1");
            matchController.addPlayer("Player2");
            matchController.addPlayer("Player3");
        } catch (MatchAlreadyFullException e) {
            fail("Unexpected MatchAlreadyFullException");
        }

        assertThrows(MatchAlreadyFullException.class, () ->matchController.addPlayer("Player4"));
    }

    @Test
    void testPlayerColorSelection() {
        try {
            matchController.addPlayer("Player1");
            matchController.addPlayer("Player2");
            matchController.addPlayer("Player3");
        } catch (MatchAlreadyFullException e) {
            fail("Unexpected MatchAlreadyFullException");
        }

        gameState.updateState();

        try {
            matchController.setPlayerColor("Player1", PlayerColors.RED);
            gameState.updateInitialStep("Player1");
            matchController.setPlayerColor("Player2", PlayerColors.BLUE);
            gameState.updateInitialStep("Player2");
            matchController.setPlayerColor("Player3", PlayerColors.YELLOW);
            gameState.updateInitialStep("Player3");
        } catch (Exception e) {
            fail("Unexpected exception");
        }

        assertEquals(INITIAL_STEP.FACE_INITIAL, gameState.getCurrentInitialStep());

        assertEquals(PlayerColors.RED, matchController.getPlayerInitialSettings().get(0).getColor());
        assertEquals(PlayerColors.BLUE, matchController.getPlayerInitialSettings().get(1).getColor());
        assertEquals(PlayerColors.YELLOW, matchController.getPlayerInitialSettings().get(2).getColor());
    }

    @Test
    void testPlayerColorAlreadySelectedException() {
        try {
            matchController.addPlayer("Player1");
            matchController.addPlayer("Player2");
            matchController.addPlayer("Player3");
        } catch (MatchAlreadyFullException e) {
            fail("Unexpected MatchAlreadyFullException");
        }

        gameState.updateState();

        try {
            matchController.setPlayerColor("Player1", PlayerColors.RED);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }

        assertThrows(InitalChoiceAlreadySetException.class, () ->
                matchController.setPlayerColor("Player1", PlayerColors.GREEN));
        assertThrows(InitalChoiceAlreadySetException.class, () ->
                matchController.setPlayerColor("Player1", PlayerColors.RED));
    }
}