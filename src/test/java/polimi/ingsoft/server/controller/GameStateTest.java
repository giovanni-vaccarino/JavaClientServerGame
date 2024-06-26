package polimi.ingsoft.server.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.exceptions.MatchExceptions.ColorAlreadyPickedException;
import polimi.ingsoft.server.factories.PublicBoardFactory;
import polimi.ingsoft.server.model.player.PlayerColor;
import polimi.ingsoft.server.model.publicboard.PublicBoard;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {
    private MatchController matchController;
    private GameState gameState;

    @BeforeEach
    void setUp() {
        PublicBoard publicBoard = PublicBoardFactory.createPublicBoard();
        matchController = new MatchController(System.out, 1, 3, publicBoard, new ChatController());
        gameState = matchController.getGameState();
    }

    @Test
    void testClone() {
        gameState.clone();
    }
}