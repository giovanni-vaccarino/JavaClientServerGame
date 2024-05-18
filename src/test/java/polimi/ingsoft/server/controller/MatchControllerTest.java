package polimi.ingsoft.server.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.enumerations.*;
import polimi.ingsoft.server.exceptions.*;
import polimi.ingsoft.server.factories.PublicBoardFactory;
import polimi.ingsoft.server.model.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MatchControllerTest {
    private MatchController matchController;

    @BeforeEach
    void setUp() {
        PublicBoard publicBoard = PublicBoardFactory.createPublicBoard();
        matchController = new MatchController(System.out, 1, 3, publicBoard, new ChatController());
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

        assertEquals(3, matchController.getPlayerInitialSettings().size());
        assertEquals("Player1", matchController.getPlayerInitialSettings().get(0).getNickname());
        assertEquals("Player2", matchController.getPlayerInitialSettings().get(1).getNickname());
        assertEquals("Player3", matchController.getPlayerInitialSettings().get(2).getNickname());
        assertEquals(GAME_PHASE.INITIALIZATION, matchController.getGameState().getGamePhase());
        assertEquals(INITIAL_STEP.COLOR, matchController.getGameState().getCurrentInitialStep());
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

        try {
            matchController.setPlayerColor("Player1", PlayerColors.RED);
            matchController.setPlayerColor("Player2", PlayerColors.BLUE);
            matchController.setPlayerColor("Player3", PlayerColors.YELLOW);
        } catch (Exception e) {
            fail("Unexpected exception");
        }

        assertEquals(INITIAL_STEP.FACE_INITIAL, matchController.getGameState().getCurrentInitialStep());

        assertEquals(PlayerColors.RED, matchController.getPlayerInitialSettings().get(0).getColor());
        assertEquals(PlayerColors.BLUE, matchController.getPlayerInitialSettings().get(1).getColor());
        assertEquals(PlayerColors.YELLOW, matchController.getPlayerInitialSettings().get(2).getColor());
    }

    @Test
    void testPlayerColorAlreadySelectedException() {
        //Adding 3 players to the match (requested number of players to start = 3)
        try {
            matchController.addPlayer("Player1");
            matchController.addPlayer("Player2");
            matchController.addPlayer("Player3");
        } catch (MatchAlreadyFullException e) {
            fail("Unexpected MatchAlreadyFullException");
        }

        try {
            matchController.setPlayerColor("Player1", PlayerColors.RED);
            matchController.setPlayerColor("Player2", PlayerColors.BLUE);
        } catch (Exception e) {
            fail("Unexpected exception");
        }

        assertThrows(InitalChoiceAlreadySetException.class, () ->
                matchController.setPlayerColor("Player1", PlayerColors.GREEN));
        assertThrows(InitalChoiceAlreadySetException.class, () ->
                matchController.setPlayerColor("Player1", PlayerColors.YELLOW));
    }

    @Test
    void testPlayerColorAlreadyPickedException() {
        //Adding 3 players to the match (requested number of players to start = 3)
        try {
            matchController.addPlayer("Player1");
            matchController.addPlayer("Player2");
            matchController.addPlayer("Player3");
        } catch (MatchAlreadyFullException e) {
            fail("Unexpected MatchAlreadyFullException");
        }

        try {
            matchController.setPlayerColor("Player1", PlayerColors.RED);
            matchController.setPlayerColor("Player2", PlayerColors.BLUE);
        } catch (Exception e) {
            fail("Unexpected exception");
        }

        assertThrows(ColorAlreadyPickedException.class, () ->
                matchController.setPlayerColor("Player3", PlayerColors.BLUE));
    }

    @Test
    void testInitialStepSwitchFromInitialCardToQuestCard() {
        //Adding 3 players to the match (requested number of players to start = 3)
        try {
            matchController.addPlayer("Player1");
            matchController.addPlayer("Player2");
            matchController.addPlayer("Player3");
        } catch (MatchAlreadyFullException e) {
            fail("Unexpected MatchAlreadyFullException");
        }

        //Selecting the colors
        try {
            matchController.setPlayerColor("Player1", PlayerColors.RED);
            matchController.setPlayerColor("Player2", PlayerColors.BLUE);
            matchController.setPlayerColor("Player3", PlayerColors.YELLOW);
        } catch (Exception e) {
            fail("Unexpected exception");
        }

        //Selecting the face of the initial card
        try {
            matchController.setFaceInitialCard("Player1", true);
            matchController.setFaceInitialCard("Player2", false);
            matchController.setFaceInitialCard("Player3", true);
        } catch (Exception e) {
            fail("Unexpected exception");
        }

        assertEquals(INITIAL_STEP.QUEST_CARD, matchController.getGameState().getCurrentInitialStep());
    }


    @Test
    void testInitialStepSwitchFromQuestCardToPlay() {
        //Adding 3 players to the match (requested number of players to start = 3)
        try {
            matchController.addPlayer("Player1");
            matchController.addPlayer("Player2");
            matchController.addPlayer("Player3");
        } catch (MatchAlreadyFullException e) {
            fail("Unexpected MatchAlreadyFullException");
        }

        //Selecting the colors
        try {
            matchController.setPlayerColor("Player1", PlayerColors.RED);
            matchController.setPlayerColor("Player2", PlayerColors.BLUE);
            matchController.setPlayerColor("Player3", PlayerColors.YELLOW);
        } catch (Exception e) {
            fail("Unexpected exception");
        }

        //Selecting the face of the initial card
        try {
            matchController.setFaceInitialCard("Player1", true);
            matchController.setFaceInitialCard("Player2", false);
            matchController.setFaceInitialCard("Player3", true);
        } catch (Exception e) {
            fail("Unexpected exception");
        }

        HashMap<Item, Integer> questCardFirstPlayerCost = new HashMap<>();
        HashMap<Item, Integer> questCardSecondPlayerCost = new HashMap<>();
        HashMap<Item, Integer> questCardThirdPlayerCost = new HashMap<>();
        questCardFirstPlayerCost.put(Resource.LEAF, 3);
        questCardSecondPlayerCost.put(Resource.BUTTERFLY, 2);
        questCardThirdPlayerCost.put(Resource.WOLF, 3);

        QuestCard firstQuestCard = new QuestCard("first", new ItemPattern(questCardFirstPlayerCost), 3);
        QuestCard secondQuestCard = new QuestCard("second", new ItemPattern(questCardSecondPlayerCost), 3);
        QuestCard thirdQuestCard = new QuestCard("third", new ItemPattern(questCardThirdPlayerCost), 3);

        //Selecting the questcard
        try {
            matchController.setQuestCard("Player1", firstQuestCard);
            matchController.setQuestCard("Player2", secondQuestCard);
            matchController.setQuestCard("Player3", thirdQuestCard);
        } catch (Exception e) {
            fail("Unexpected exception");
        }

        assertEquals(GAME_PHASE.PLAY, matchController.getGameState().getGamePhase());
        assertEquals(3, matchController.getPlayers().size());
    }

    @Test
    void testPlaySteps() {
        //Adding 3 players to the match (requested number of players to start = 3)
        try {
            matchController.addPlayer("Player1");
            matchController.addPlayer("Player2");
            matchController.addPlayer("Player3");
        } catch (MatchAlreadyFullException e) {
            fail("Unexpected MatchAlreadyFullException");
        }

        //Selecting the colors
        try {
            matchController.setPlayerColor("Player1", PlayerColors.RED);
            matchController.setPlayerColor("Player2", PlayerColors.BLUE);
            matchController.setPlayerColor("Player3", PlayerColors.YELLOW);
        } catch (Exception e) {
            fail("Unexpected exception");
        }

        //Selecting the face of the initial card
        try {
            matchController.setFaceInitialCard("Player1", true);
            matchController.setFaceInitialCard("Player2", false);
            matchController.setFaceInitialCard("Player3", true);
        } catch (Exception e) {
            fail("Unexpected exception");
        }

        HashMap<Item, Integer> questCardFirstPlayerCost = new HashMap<>();
        HashMap<Item, Integer> questCardSecondPlayerCost = new HashMap<>();
        HashMap<Item, Integer> questCardThirdPlayerCost = new HashMap<>();
        questCardFirstPlayerCost.put(Resource.LEAF, 3);
        questCardSecondPlayerCost.put(Resource.BUTTERFLY, 2);
        questCardThirdPlayerCost.put(Resource.WOLF, 3);

        QuestCard firstQuestCard = new QuestCard("first", new ItemPattern(questCardFirstPlayerCost), 3);
        QuestCard secondQuestCard = new QuestCard("second", new ItemPattern(questCardSecondPlayerCost), 3);
        QuestCard thirdQuestCard = new QuestCard("third", new ItemPattern(questCardThirdPlayerCost), 3);

        //Selecting the questcard
        try {
            matchController.setQuestCard("Player1", firstQuestCard);
            matchController.setQuestCard("Player2", secondQuestCard);
            matchController.setQuestCard("Player3", thirdQuestCard);
        } catch (Exception e) {
            fail("Unexpected exception");
        }

        assertEquals(GAME_PHASE.PLAY, matchController.getGameState().getGamePhase());
        assertEquals(3, matchController.getPlayers().size());

        Player currentPlayer = matchController.getGameState().getCurrentPlayer();

        // Drawing a card
        MixedCard drawnCard = null;
        try {
            drawnCard = matchController.drawCard(currentPlayer, "Resource", PlaceInPublicBoard.Slots.SLOT_A);
        } catch (Exception e) {
            fail("Unexpected exception");
        }

        assertEquals(TURN_STEP.PLACE, matchController.getGameState().getCurrentTurnStep());

        // Placing a card (for instance, the drawnCard)
        Coordinates coordinates = new Coordinates(1, 1);
        boolean facingUp = true;

        //TODO Here gives unexcepted exception because the card is not placeable
        //      When we got the correct initialization of all cards, repeat the test
        try {
            matchController.placeCard(currentPlayer, drawnCard, coordinates, facingUp);
        } catch (Exception e) {
            fail("Unexpected exception");
        }

    }


    @Test
    void testWrongInitialStepException() {
        //Adding 3 players to the match (requested number of players to start = 3)
        try {
            matchController.addPlayer("Player1");
            matchController.addPlayer("Player2");
            matchController.addPlayer("Player3");
        } catch (MatchAlreadyFullException e) {
            fail("Unexpected MatchAlreadyFullException");
        }

        HashMap<Item, Integer> questCardFirstPlayerCost = new HashMap<>();
        questCardFirstPlayerCost.put(Resource.LEAF, 3);

        QuestCard firstQuestCard = new QuestCard("first", new ItemPattern(questCardFirstPlayerCost), 3);

        assertThrows(WrongStepException.class, () -> matchController.setFaceInitialCard("Player1", true));
        assertThrows(WrongStepException.class, () -> matchController.setQuestCard("Player2", firstQuestCard));
    }


    @Test
    void testWrongPlayerForCurrentTurnException() {
        this.setupInitialPhase();

        // Starting the play steps
        assertEquals(GAME_PHASE.PLAY, matchController.getGameState().getGamePhase());
        assertEquals(3, matchController.getPlayers().size());

        // Getting a player that is not the current
        Player currentPlayer = matchController.getGameState().getCurrentPlayer();

        Optional<Player> firstNonSpecifiedPlayer = matchController.getPlayers().stream()
                .filter(player -> !player.equals(currentPlayer))
                .findFirst();
        Player notCurrentPlayer = firstNonSpecifiedPlayer.orElse(null);

        assertNotNull(notCurrentPlayer);

        assertThrows(WrongPlayerForCurrentTurnException.class, () ->
                matchController.drawCard(notCurrentPlayer, "Resource", PlaceInPublicBoard.Slots.SLOT_B));
    }

    @Test
    void testWrongTurnStepException() {
        this.setupInitialPhase();

        // Starting the play steps
        assertEquals(GAME_PHASE.PLAY, matchController.getGameState().getGamePhase());
        assertEquals(3, matchController.getPlayers().size());

        Player currentPlayer = matchController.getGameState().getCurrentPlayer();
        MixedCard card = null;

        assertThrows(WrongStepException.class, () ->
                matchController.placeCard(currentPlayer, card, new Coordinates(1,1), true)
        );
    }

    @Test
    void testSendBroadcastMessage(){
        this.setupInitialPhase();
        String sender = matchController.getGameState().getCurrentPlayer().getNickname();
        String message = "Test Message";

        assertEquals(sender, matchController.writeMessage(sender, message).getSender());
        assertEquals(message, matchController.writeMessage(sender, message).getText());
    }

    void setupInitialPhase(){
        // Adding 3 players to the match (requested number of players to start = 3)
        try {
            matchController.addPlayer("Player1");
            matchController.addPlayer("Player2");
            matchController.addPlayer("Player3");
        } catch (MatchAlreadyFullException e) {
            fail("Unexpected MatchAlreadyFullException");
        }

        // Selecting the colors
        try {
            matchController.setPlayerColor("Player1", PlayerColors.RED);
            matchController.setPlayerColor("Player2", PlayerColors.BLUE);
            matchController.setPlayerColor("Player3", PlayerColors.YELLOW);
        } catch (Exception e) {
            fail("Unexpected exception");
        }

        // Selecting the face of the initial card
        try {
            matchController.setFaceInitialCard("Player1", true);
            matchController.setFaceInitialCard("Player2", false);
            matchController.setFaceInitialCard("Player3", true);
        } catch (Exception e) {
            fail("Unexpected exception");
        }

        HashMap<Item, Integer> questCardFirstPlayerCost = new HashMap<>();
        questCardFirstPlayerCost.put(Resource.LEAF, 3);

        QuestCard firstQuestCard = new QuestCard("first", new ItemPattern(questCardFirstPlayerCost), 3);

        // Selecting the quest card
        try {
            matchController.setQuestCard("Player1", firstQuestCard);
            matchController.setQuestCard("Player2", firstQuestCard);
            matchController.setQuestCard("Player3", firstQuestCard);
        } catch (Exception e) {
            fail("Unexpected exception");
        }
    }

}