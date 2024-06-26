package polimi.ingsoft.server.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import polimi.ingsoft.server.enumerations.*;
import polimi.ingsoft.server.exceptions.MatchExceptions.*;
import polimi.ingsoft.server.exceptions.MatchSelectionExceptions.MatchAlreadyFullException;
import polimi.ingsoft.server.factories.PublicBoardFactory;
import polimi.ingsoft.server.model.boards.Coordinates;
import polimi.ingsoft.server.model.cards.MixedCard;
import polimi.ingsoft.server.model.cards.QuestCard;
import polimi.ingsoft.server.model.items.Item;
import polimi.ingsoft.server.model.items.Resource;
import polimi.ingsoft.server.model.patterns.ItemPattern;
import polimi.ingsoft.server.model.player.Player;
import polimi.ingsoft.server.model.player.PlayerColor;
import polimi.ingsoft.server.model.publicboard.PlaceInPublicBoard;
import polimi.ingsoft.server.model.publicboard.PublicBoard;

import java.util.HashMap;
import java.util.Optional;
import java.util.Timer;

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
            matchController.setPlayerColor("Player1", PlayerColor.RED);
            matchController.setPlayerColor("Player2", PlayerColor.BLUE);
            matchController.setPlayerColor("Player3", PlayerColor.YELLOW);
        } catch (Exception e) {
            fail("Unexpected exception");
        }

        assertEquals(INITIAL_STEP.FACE_INITIAL, matchController.getGameState().getCurrentInitialStep());

        assertEquals(PlayerColor.RED, matchController.getPlayerInitialSettings().get(0).getColor());
        assertEquals(PlayerColor.BLUE, matchController.getPlayerInitialSettings().get(1).getColor());
        assertEquals(PlayerColor.YELLOW, matchController.getPlayerInitialSettings().get(2).getColor());
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
            matchController.setPlayerColor("Player1", PlayerColor.RED);
            matchController.setPlayerColor("Player2", PlayerColor.BLUE);
        } catch (Exception e) {
            fail("Unexpected exception");
        }

        assertThrows(InitalChoiceAlreadySetException.class, () ->
                matchController.setPlayerColor("Player1", PlayerColor.GREEN));
        assertThrows(InitalChoiceAlreadySetException.class, () ->
                matchController.setPlayerColor("Player1", PlayerColor.YELLOW));
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
            matchController.setPlayerColor("Player1", PlayerColor.RED);
            matchController.setPlayerColor("Player2", PlayerColor.BLUE);
        } catch (Exception e) {
            fail("Unexpected exception");
        }

        assertThrows(ColorAlreadyPickedException.class, () ->
                matchController.setPlayerColor("Player3", PlayerColor.BLUE));
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
            matchController.setPlayerColor("Player1", PlayerColor.RED);
            matchController.setPlayerColor("Player2", PlayerColor.BLUE);
            matchController.setPlayerColor("Player3", PlayerColor.YELLOW);
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
            matchController.setPlayerColor("Player1", PlayerColor.RED);
            matchController.setPlayerColor("Player2", PlayerColor.BLUE);
            matchController.setPlayerColor("Player3", PlayerColor.YELLOW);
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

    /*
    @Test
    void testPlaySteps() throws WrongGamePhaseException, WrongPlayerForCurrentTurnException, WrongStepException {
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
            matchController.setPlayerColor("Player1", PlayerColor.RED);
            matchController.setPlayerColor("Player2", PlayerColor.BLUE);
            matchController.setPlayerColor("Player3", PlayerColor.YELLOW);
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
//        try {
            matchController.drawCard(currentPlayer, TYPE_HAND_CARD.RESOURCE, PlaceInPublicBoard.Slots.SLOT_A);
//        } catch (Exception e) {
//            fail("Unexpected exception");
//        }

        assertEquals(TURN_STEP.PLACE, matchController.getGameState().getCurrentTurnStep());

        // Placing a card (for instance, the drawnCard)
        Coordinates coordinates = new Coordinates(1, 1);
        boolean facingUp = true;

        MixedCard drawnCard = null;
        //TODO Here gives unexcepted exception because the card is not placeable
        //      When we got the correct initialization of all cards, repeat the test
        try {
            matchController.placeCard(currentPlayer, drawnCard, coordinates, facingUp);
        } catch (Exception e) {
            fail("Unexpected exception");
        }

    }
*/

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
                matchController.drawCard(notCurrentPlayer, TYPE_HAND_CARD.RESOURCE, PlaceInPublicBoard.Slots.SLOT_B));
    }

    @Test
    void testWrongTurnStepException() {
        this.setupInitialPhase();

        // Starting the play steps
        assertEquals(GAME_PHASE.PLAY, matchController.getGameState().getGamePhase());
        assertEquals(3, matchController.getPlayers().size());

        Player currentPlayer = matchController.getGameState().getCurrentPlayer();

        assertThrows(WrongStepException.class, () ->
                matchController.drawCard(currentPlayer, TYPE_HAND_CARD.RESOURCE, PlaceInPublicBoard.Slots.SLOT_A)
        );
    }

    @Test
    void testSendBroadcastMessage(){
        this.setupInitialPhase();
        String sender = matchController.getGameState().getCurrentPlayer().getNickname();
        String message = "Test Message";

        assertEquals(sender, matchController.writeBroadcastMessage(sender, message).getSender());
        assertEquals(message, matchController.writeBroadcastMessage(sender, message).getText());
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
            matchController.setPlayerColor("Player1", PlayerColor.RED);
            matchController.setPlayerColor("Player2", PlayerColor.BLUE);
            matchController.setPlayerColor("Player3", PlayerColor.YELLOW);
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

    @Test
    void getPublicBoard() {
        System.out.println(matchController.getPublicBoard());
    }

//    @Test
//    void getRequestedNumPlayers() {
//        System.out.println(matchController.getRequestedNumPlayers());
//    }

    @Test
    void getMatchId() {
        System.out.println(matchController.getMatchId());
    }

    @Test
    void getGameState() {
        System.out.println(matchController.getGameState());
    }

    @Test
    void getPlayers() {
        System.out.println(matchController.getPlayers());
    }

    @Test
    void getPlayerInitialSettings() {
        System.out.println(matchController.getPlayerInitialSettings());
    }

    @Test
    void getNamePlayers() {
        System.out.println(matchController.getNamePlayers());
    }

    @Test
    void getPlayerInitialSettingByNickname() {
        System.out.println(matchController.getPlayerInitialSettingByNickname("Player1"));
        System.out.println(matchController.getPlayerInitialSettingByNickname(""));
    }

    @Test
    void getPlayerByNickname() {
        System.out.println(matchController.getPlayerByNickname("Player1"));
        System.out.println(matchController.getPlayerByNickname(""));
    }

    @Test
    void getPlayerBoards() {
        System.out.println(matchController.getPlayerBoards());
    }

    @Test
    void addPlayer() throws MatchAlreadyFullException {
        matchController.addPlayer("Player3");
        matchController.addPlayer("Player4");
        matchController.getPlayers().remove("Player3");
        matchController.addPlayer("Player4");
    }

    @Test
    void initializePlayers() throws MatchAlreadyFullException {
        matchController.addPlayer("player1");
        matchController.initializePlayers();
        System.out.println(matchController.getPlayers().getFirst());

    }

    @Test
    void setPlayerColor() throws WrongGamePhaseException, WrongStepException, ColorAlreadyPickedException, InitalChoiceAlreadySetException, MatchAlreadyFullException {
        matchController.addPlayer("player4");
        matchController.addPlayer("player5");
        matchController.addPlayer("player6");
        matchController.setPlayerColor("player4",PlayerColor.RED);
        System.out.println(matchController.getPlayers());

    }

    @Test
    void setFaceInitialCard() {
    }

    @Test
    void setQuestCard() {
    }

    @Test
    void placeCard() {
    }

    @Test
    void drawCard() {
    }

    @Test
    void writeBroadcastMessage() {
    }

    @Test
    void writePrivateMessage() {
    }

    // Join phase tests
    @Test
    void testGetLobbyPlayers() {
        try {
            matchController.addPlayer("Player1");
            matchController.addPlayer("Player2");
            assertEquals(2, matchController.getLobbyPlayers().size());
        } catch (MatchAlreadyFullException e) {
            fail("Unexpected MatchAlreadyFullException");
        }
    }

    @Test
    void testRemoveLobbyPlayer() {
        try {
            matchController.addPlayer("Player1");
            matchController.addPlayer("Player2");
            matchController.removeLobbyPlayer("Player1");
            assertEquals(1, matchController.getLobbyPlayers().size());
            assertEquals("Player2", matchController.getLobbyPlayers().get(0));
        } catch (MatchAlreadyFullException e) {
            fail("Unexpected MatchAlreadyFullException");
        }
    }

    @Test
    void testAddPlayer() {
        try {
            matchController.addPlayer("Player1");
            matchController.addPlayer("Player2");
            assertEquals(2, matchController.getLobbyPlayers().size());
        } catch (MatchAlreadyFullException e) {
            fail("Unexpected MatchAlreadyFullException");
        }
    }

    // Pinger methods tests
    @Test
    void testSetPinger() {
        try {
            matchController.addPlayer("Player1");
            Timer timer = new Timer();
            matchController.setPinger(timer);
            assertTrue(matchController.hasPinger());
        } catch (MatchAlreadyFullException e) {
            fail("Unexpected MatchAlreadyFullException");
        }
    }


    @Test
    void testTurnPingerOff() {
        try {
            matchController.addPlayer("Player1");
            Timer timer = new Timer();
            matchController.setPinger(timer);
            matchController.turnPingerOff();
        } catch (MatchAlreadyFullException e) {
            fail("Unexpected MatchAlreadyFullException");
        }
        assertTrue(matchController.hasPinger());
    }

    // Player status methods during gameplay
    @Test
    void testGetNumOnlinePlayers() {
        this.setupInitialPhase();
        assertEquals(3, matchController.getNumOnlinePlayers());
    }

    @Test
    void testIsPlayerDisconnected() {
        this.setupInitialPhase();
        matchController.updatePlayerStatus("Player1", true);
        assertTrue(matchController.isPlayerDisconnected(0));
    }

    @Test
    void testUpdatePlayerStatus() {
        this.setupInitialPhase();
        matchController.updatePlayerStatus("Player1", false);
        assertFalse(matchController.isPlayerDisconnected(0));
    }

    // Player initialization settings test
    @Test
    void testInitializePlayersInitialSettings() {
        try {
            matchController.addPlayer("Player1");
            matchController.addPlayer("Player2");
            matchController.initializePlayersInitialSettings();
            assertEquals(2, matchController.getPlayerInitialSettings().size());
        } catch (MatchAlreadyFullException e) {
            fail("Unexpected MatchAlreadyFullException");
        }
    }

    // Player boards test
    @Test
    void testGetPlayerBoards() {
        this.setupInitialPhase();
        assertEquals(3, matchController.getPlayerBoards().size());
    }

    // Check if the player is first during the game
    @Test
    void testIsFirstPlayer() {
        this.setupInitialPhase();
        Player firstPlayer = matchController.getPlayers().getFirst();
        assertTrue(matchController.isFirstPlayer(firstPlayer.getNickname()));
    }

}