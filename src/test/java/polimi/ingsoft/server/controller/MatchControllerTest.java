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

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link MatchController} class.
 */
class MatchControllerTest {
    private MatchController matchController;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        PublicBoard publicBoard = PublicBoardFactory.createPublicBoard();
        matchController = new MatchController(System.out, 1, 3, publicBoard, new ChatController());
    }

    /**
     * Tests player joining and initialization process.
     */
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

    /**
     * Tests that {@link MatchAlreadyFullException} is thrown when adding more players than the maximum allowed.
     */
    @Test
    void testMatchAlreadyFullException() {
        try {
            matchController.addPlayer("Player1");
            matchController.addPlayer("Player2");
            matchController.addPlayer("Player3");
        } catch (MatchAlreadyFullException e) {
            fail("Unexpected MatchAlreadyFullException");
        }

        assertThrows(MatchAlreadyFullException.class, () -> matchController.addPlayer("Player4"));
    }

    /**
     * Tests player color selection process.
     */
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

    /**
     * Tests that {@link InitalChoiceAlreadySetException} is thrown when a player tries to reselect a color.
     */
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

    /**
     * Tests that {@link ColorAlreadyPickedException} is thrown when a player tries to select an already picked color.
     */
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

    /**
     * Tests the switch from the initial card face selection step to the quest card selection step.
     */
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

    /**
     * Tests the switch from the quest card selection step to the play phase.
     */
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


    /**
     * Tests the WrongInitialStepException scenario.
     * Ensures the match controller throws the correct exception when methods are called out of the expected order.
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

    /**
     * Tests the WrongPlayerForCurrentTurnException scenario.
     * Ensures the match controller throws the correct exception when a non-current player attempts to take a turn.
     */
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

    /**
     * Tests the WrongTurnStepException scenario.
     * Ensures the match controller throws the correct exception when a player attempts an action outside of their turn step.
     */
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

    /**
     * Tests the broadcasting message functionality.
     * Ensures the broadcast message is correctly sent and received.
     */
    @Test
    void testSendBroadcastMessage() {
        this.setupInitialPhase();
        String sender = matchController.getGameState().getCurrentPlayer().getNickname();
        String message = "Test Message";

        assertEquals(sender, matchController.writeBroadcastMessage(sender, message).getSender());
        assertEquals(message, matchController.writeBroadcastMessage(sender, message).getText());
    }

    /**
     * Sets up the initial phase for the match.
     * Adds players, sets their colors, initial card faces, and quest cards.
     */
    void setupInitialPhase() {
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
            assertTrue(matchController.getGameState().isLastPlayerSetting());
            matchController.setFaceInitialCard("Player3", true);
        } catch (Exception e) {
            fail("Unexpected exception");
        }

        QuestCard firstQuestCard = matchController.getPublicBoard().getQuest(PlaceInPublicBoard.Slots.SLOT_A);
        // Selecting the quest card
        try {
            matchController.setQuestCard("Player1", firstQuestCard);
            matchController.setQuestCard("Player2", firstQuestCard);
            matchController.setQuestCard("Player3", firstQuestCard);
        } catch (Exception e) {
            fail("Unexpected exception");
        }
    }

    /**
     * Tests the retrieval of the public board.
     */
    @Test
    void getPublicBoard() {
        System.out.println(matchController.getPublicBoard());
    }

//    @Test
//    void getRequestedNumPlayers() {
//        System.out.println(matchController.getRequestedNumPlayers());
//    }

    /**
     * Tests the retrieval of the match ID.
     */
    @Test
    void getMatchId() {
        System.out.println(matchController.getMatchId());
    }

    /**
     * Tests the retrieval of the game state.
     */
    @Test
    void getGameState() {
        System.out.println(matchController.getGameState());
    }

    /**
     * Tests the retrieval of players.
     */
    @Test
    void getPlayers() {
        System.out.println(matchController.getPlayers());
    }

    /**
     * Tests the retrieval of player initial settings.
     */
    @Test
    void getPlayerInitialSettings() {
        System.out.println(matchController.getPlayerInitialSettings());
    }

    /**
     * Tests the retrieval of player names.
     */
    @Test
    void getNamePlayers() {
        System.out.println(matchController.getNamePlayers());
    }

    /**
     * Tests the retrieval of initial settings by player nickname.
     */
    @Test
    void getPlayerInitialSettingByNickname() {
        System.out.println(matchController.getPlayerInitialSettingByNickname("Player1"));
        System.out.println(matchController.getPlayerInitialSettingByNickname(""));
    }

    /**
     * Tests the retrieval of a player by their nickname.
     */
    @Test
    void getPlayerByNickname() {
        System.out.println(matchController.getPlayerByNickname("Player1"));
        System.out.println(matchController.getPlayerByNickname(""));
    }

    /**
     * Tests the retrieval of player boards.
     */
    @Test
    void getPlayerBoards() {
        System.out.println(matchController.getPlayerBoards());
    }

    /**
     * Tests the addition of players to the match.
     */
    @Test
    void addPlayer() throws MatchAlreadyFullException {
        matchController.addPlayer("Player3");
        matchController.addPlayer("Player4");
        matchController.getPlayers().remove("Player3");
        matchController.addPlayer("Player4");
    }

    /**
     * Tests the initialization of players.
     * Ensures that the players are correctly initialized.
     */
    @Test
    void initializePlayers() {
        setupInitialPhase();
        List<Player> players = matchController.getPlayers();

        matchController.initializePlayers();
        assertEquals(players, matchController.getPlayers());
    }

    /**
     * Tests setting the player color.
     */
    @Test
    void setPlayerColor() throws WrongGamePhaseException, WrongStepException, ColorAlreadyPickedException, InitalChoiceAlreadySetException, MatchAlreadyFullException {
        matchController.addPlayer("player4");
        matchController.addPlayer("player5");
        matchController.addPlayer("player6");
        matchController.setPlayerColor("player4", PlayerColor.RED);
        System.out.println(matchController.getPlayers());

    }

    /**
     * Tests writing a broadcast message.
     * Ensures the broadcast message is correctly sent.
     */
    @Test
    void writeBroadcastMessage() {
        setupInitialPhase();

        Player player = matchController.getPlayers().get(0);

        matchController.writeBroadcastMessage(player.getNickname(), "Example message");
    }

    /**
     * Tests writing a private message.
     * Ensures the private message is correctly sent.
     */
    @Test
    void writePrivateMessage() {
        setupInitialPhase();

        Player player1 = matchController.getPlayers().get(0);
        Player player2 = matchController.getPlayers().get(1);

        try{
            matchController.writePrivateMessage(player1.getNickname(), player2.getNickname(), "Example message");
        } catch (PlayerNotFoundException e){
            fail("unexpected exception");
        }
    }

    /**
     * Tests removing a player's initial setting.
     * Ensures the player's initial setting is correctly removed.
     */
    @Test
    void removePlayerInitialSettingTest() {
        setupInitialPhase();
        Player player1 = matchController.getPlayers().get(0);
        Integer size = matchController.getPlayerInitialSettings().size();

        matchController.removePlayerInitialSetting(player1.getNickname());
        assertEquals(matchController.getPlayerInitialSettings().size(), size - 1);
    }

    /**
     * Tests the retrieval of players in the lobby.
     */
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

    /**
     * Tests if removing a lobby player works as expected.
     * This test adds two players to the lobby and then removes one,
     * verifying the size of the lobby players list and the remaining player.
     */
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

    /**
     * Tests adding players to the lobby.
     * This test adds two players and verifies the size of the lobby players list.
     */
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

    /**
     * Tests setting and checking the presence of a pinger for the match.
     */
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

    /**
     * Tests turning off the pinger for the match.
     */
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

    /**
     * Tests getting the number of online players in the match.
     */
    @Test
    void testGetNumOnlinePlayers() {
        this.setupInitialPhase();
        assertEquals(3, matchController.getNumOnlinePlayers());
    }

    /**
     * Tests updating player status to disconnected and checking if the player is disconnected.
     */
    @Test
    void testIsPlayerDisconnected() {
        this.setupInitialPhase();
        matchController.updatePlayerStatus("Player1", true);
        assertTrue(matchController.isPlayerDisconnected(0));
    }

    /**
     * Tests updating player status to connected and checking if the player is not disconnected.
     */
    @Test
    void testUpdatePlayerStatus() {
        this.setupInitialPhase();
        matchController.updatePlayerStatus("Player1", false);
        assertFalse(matchController.isPlayerDisconnected(0));
    }

    /**
     * Tests initializing initial settings for players.
     */
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

    /**
     * Tests retrieving player boards during the match.
     */
    @Test
    void testGetPlayerBoards() {
        this.setupInitialPhase();
        assertEquals(3, matchController.getPlayerBoards().size());
    }

    /**
     * Tests checking if a specific player is the first player in the match.
     */
    @Test
    void testIsFirstPlayer() {
        this.setupInitialPhase();
        Integer firstPlayerIndex = matchController.getGameState().getFirstPlayerIndex();
        Player firstPlayer = matchController.getPlayers().get(firstPlayerIndex);

        assertTrue(matchController.isFirstPlayer(firstPlayer.getNickname()));
    }

    /**
     * Tests setting the match state to have only winners and retrieves the list of winners.
     */
    @Test
    void testCalculateWinner() {
        this.setupInitialPhase();
        matchController.getGameState().setOnlyWinner();
        List<String> winners = matchController.getGameState().getWinners();
        assertNotNull(winners);
    }

    /**
     * Tests setting and retrieving the blocked state of the match.
     */
    @Test
    void testBlockedState() {
        this.setupInitialPhase();
        matchController.getGameState().setBlockedMatchState(BLOCKED_MATCH_STATE.NOT_BLOCKED);
        assertEquals(matchController.getGameState().getBlockedMatchState(), BLOCKED_MATCH_STATE.NOT_BLOCKED);
    }

    /**
     * Tests simulating gameplay within the match controller.
     */
    @Test
    void testGame() {
        this.setupInitialPhase();
        Player currentPlayer = null;
        Integer counter = 0;
        Random random = new Random();

        while (!matchController.getGameState().getGamePhase().equals(GAME_PHASE.LAST_ROUND) && counter<100000) {

            counter++;

            System.out.println(matchController.getGameState().getGamePhase().toString());
            placeCard(getCurrentPlayer());
            System.out.println(matchController.getGameState().getGamePhase().toString());
            drawCard(getCurrentPlayer());

            if(random.nextInt(2) == 0){
                matchController.updatePlayerStatus(getCurrentPlayer().getNickname(), true);
                matchController.updatePlayerStatus(getCurrentPlayer().getNickname(), false);
            }
        }
    }

    /**
     * Retrieves the current player based on the nickname from the match controller.
     *
     * @return The current player object.
     */
    public Player getCurrentPlayer(){
        String nickname = matchController.getGameState().getCurrentPlayerNickname();
        Player currentPlayer = null;
        for (Player player : matchController.getPlayers()) {
            if (player.getNickname().equals(nickname)) {
                currentPlayer = player;
            }
        }

        return currentPlayer;
    }


    /**
     * Simulates placing a card for the given player within the match controller.
     *
     * @param myPlayer The player for whom the card is being placed.
     */
    public void placeCard(Player myPlayer){
        Random random = new Random();
        Integer size;

        size = myPlayer.getHand().getCards().size();
        MixedCard mixedCard = myPlayer.getHand().getCards().get(random.nextInt(size));

        if(mixedCard!=null){
            size = matchController.getPlayerBoards().get(myPlayer.getNickname()).getAvailablePlaces().size();
            Coordinates coordinates = matchController.getPlayerBoards().get(myPlayer.getNickname()).getAvailablePlaces().get(random.nextInt(size));

            try {
                matchController.placeCard(myPlayer, mixedCard, coordinates, false);
            } catch (NotEnoughResourcesException | WrongPlayerForCurrentTurnException | WrongStepException |
                     WrongGamePhaseException | CoordinateNotValidException | MatchBlockedException e) {
                try {
                    matchController.placeCard(myPlayer, mixedCard, coordinates, true);
                } catch (WrongPlayerForCurrentTurnException | WrongStepException | WrongGamePhaseException |
                         CoordinateNotValidException | NotEnoughResourcesException | MatchBlockedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    /**
     * Simulates drawing a card for the given player within the match controller.
     *
     * @param myPlayer The player for whom the card is being drawn.
     */
    public void drawCard(Player myPlayer){
        Random random = new Random();
        TYPE_HAND_CARD typeHandCard = null;
        PlaceInPublicBoard.Slots slot= null;

        if(random.nextInt(2) == 0){
            typeHandCard = TYPE_HAND_CARD.RESOURCE;
        }else{
            typeHandCard = TYPE_HAND_CARD.GOLD;
        }

        if(random.nextInt(2) == 0){
            slot = PlaceInPublicBoard.Slots.SLOT_A;
        }else{
            slot = PlaceInPublicBoard.Slots.SLOT_B;
        }

        if (matchController.getGameState().getCurrentTurnStep().equals(TURN_STEP.DRAW)){
            try {
                matchController.drawCard(myPlayer, typeHandCard, slot);
            } catch (WrongPlayerForCurrentTurnException e) {
                throw new RuntimeException(e);
            } catch (WrongStepException e) {
                throw new RuntimeException(e);
            } catch (WrongGamePhaseException e) {
                throw new RuntimeException(e);
            } catch (MatchBlockedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}