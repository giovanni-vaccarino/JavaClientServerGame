package polimi.ingsoft.server.controller;

import polimi.ingsoft.server.exceptions.*;
import polimi.ingsoft.server.factories.PlayerInitialSettingFactory;
import polimi.ingsoft.server.model.Player;
import polimi.ingsoft.server.enumerations.*;
import polimi.ingsoft.server.factories.PlayerFactory;
import polimi.ingsoft.server.model.*;

import java.awt.*;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


/**
 * The MatchController class manages the state and behavior of a single match.
 */
public class MatchController implements Serializable {

    public Integer getRequestedNumPlayers() {
        return requestedNumPlayers;
    }

    private final Integer requestedNumPlayers;

    private final GameState gameState;

    private final ChatController chatController;

    private List<Player> players = new ArrayList<>();

    private final List<PlayerInitialSetting> playerInitialSettings = new ArrayList<>();

    public PublicBoard getPublicBoard() {
        return publicBoard;
    }

    private final PublicBoard publicBoard;

    protected transient final PrintStream logger;

    protected final int matchId;


    /**
     * Constructs a MatchController with the specified parameters.
     *
     * @param logger              the logger to be used for logging information
     * @param matchId             the ID of this match
     * @param requestedNumPlayers the number of players requested for this match
     * @param publicBoard         the public board for this match
     * @param chatController      the chat controller for this match
     */
    public MatchController(PrintStream logger,
                           int matchId,
                           int requestedNumPlayers,
                           PublicBoard publicBoard,
                           ChatController chatController
    ) {
        this.requestedNumPlayers = requestedNumPlayers;
        this.chatController = chatController;
        this.logger = logger;
        this.matchId = matchId;
        this.publicBoard = publicBoard;
        this.gameState = new GameState(this, requestedNumPlayers);
    }


    /**
     * Returns the ID of this match.
     *
     * @return the match ID
     */
    public int getMatchId() {
        return matchId;
    }


    /**
     * Returns the game state associated with this match.
     *
     * @return the game state
     */
    public GameState getGameState(){return this.gameState;}


    /**
     * Returns the list of players in this match.
     *
     * @return the list of players
     */
    public List<Player> getPlayers(){
        return players;
    }


    /**
     * Returns the list of initial settings for the players in this match.
     *
     * @return the list of initial settings
     */
    public List<PlayerInitialSetting> getPlayerInitialSettings(){return playerInitialSettings;}


    /**
     * Returns the nicknames of the players in this match.
     *
     * @return a list of player nicknames
     */
    public List<String> getNamePlayers(){
        return playerInitialSettings.stream().
                map(PlayerInitialSetting::getNickname).
                toList();
    }

    public Optional<PlayerInitialSetting> getPlayerInitialSettingByNickname(String nickname) {
        return playerInitialSettings.stream()
                .filter(player -> player.getNickname().equals(nickname))
                .findFirst();
    }

    public Optional<Player> getPlayerByNickname(String nickname) {
        return players.stream()
                .filter(player -> player.getNickname().equals(nickname))
                .findFirst();
    }


    /**
     * Adds a player to this match.
     *
     * @param nickname the nickname of the player to add
     * @throws MatchAlreadyFullException if the match is already full
     */
    public synchronized void addPlayer(String nickname) throws MatchAlreadyFullException {
        if(playerInitialSettings.size() == requestedNumPlayers){
            throw new MatchAlreadyFullException();
        }

        // Retrieving from Public Board 1 initial card, 2 resource, 1 gold card and 2 possible quest cards
        PlayerInitialSetting playerInitialSetting = PlayerInitialSettingFactory.createPlayerInitialSetting(this.publicBoard, nickname);

        playerInitialSettings.add(playerInitialSetting);

        gameState.updateState();
    }


    /**
     * Initializes the players for this match, using the information collected in PlayerInitialSetting.
     * The initialization of the players can be done only once.
     */
    public void initializePlayers() {
        this.players = this.players.isEmpty() ?
                this.playerInitialSettings.stream()
                        .map(PlayerFactory::createPlayer)
                        .collect(Collectors.toList())
                :
                this.players;
    }


    /**
     * Sets the color for a player.
     *
     * @param playerNickname the nickname of the player
     * @param color          the color to set
     * @throws WrongGamePhaseException        if the game is not in the correct phase
     * @throws WrongStepException             if the game is not in the correct step
     * @throws InitalChoiceAlreadySetException if the initial choice is already set
     * @throws ColorAlreadyPickedException    if the color is already picked by another player
     */
    public synchronized void setPlayerColor(String playerNickname, PlayerColor color) throws WrongGamePhaseException, WrongStepException, InitalChoiceAlreadySetException, ColorAlreadyPickedException{
        gameState.checkColorAvailability(color);
        gameState.validateInitialChoice(playerNickname, GAME_PHASE.INITIALIZATION, INITIAL_STEP.COLOR);

        Optional<PlayerInitialSetting> playerInitialSetting = this.getPlayerInitialSettingByNickname(playerNickname);

        playerInitialSetting.ifPresent(player-> player.setColor(color));

        gameState.updateInitialStep(playerNickname);
    }


    /**
     * Sets the initial face-up card status for a player.
     *
     * @param playerNickname the nickname of the player
     * @param isFaceUp       the face-up status to set
     * @throws WrongGamePhaseException        if the game is not in the correct phase
     * @throws WrongStepException             if the game is not in the correct step
     * @throws InitalChoiceAlreadySetException if the initial choice is already set
     */
    public synchronized void setFaceInitialCard(String playerNickname, Boolean isFaceUp) throws WrongGamePhaseException, WrongStepException, InitalChoiceAlreadySetException {
        gameState.validateInitialChoice(playerNickname, GAME_PHASE.INITIALIZATION, INITIAL_STEP.FACE_INITIAL);

        Optional<PlayerInitialSetting> playerInitialSetting = this.getPlayerInitialSettingByNickname(playerNickname);

        playerInitialSetting.ifPresent(player-> player.setIsInitialFaceUp(isFaceUp));

        gameState.updateInitialStep(playerNickname);
    }


    /**
     * Sets the quest card for a player.
     *
     * @param playerNickname the nickname of the player
     * @param questCard      the quest card to set
     * @throws WrongGamePhaseException        if the game is not in the correct phase
     * @throws WrongStepException             if the game is not in the correct step
     * @throws InitalChoiceAlreadySetException if the initial choice is already set
     */
    public synchronized void setQuestCard(String playerNickname, QuestCard questCard) throws WrongGamePhaseException, WrongStepException, InitalChoiceAlreadySetException {
        gameState.validateInitialChoice(playerNickname, GAME_PHASE.INITIALIZATION, INITIAL_STEP.QUEST_CARD);

        Optional<PlayerInitialSetting> playerInitialSetting = this.getPlayerInitialSettingByNickname(playerNickname);

        playerInitialSetting.ifPresent(player-> player.setQuestCard(questCard));

        gameState.updateInitialStep(playerNickname);
    }


    /**
     * Places a card on the board for a player.
     *
     * @param player      the player placing the card
     * @param card        the card to place
     * @param coordinates the coordinates to place the card
     * @param facingUp    the facing-up status of the card
     * @throws WrongPlayerForCurrentTurnException if it's not the player's turn
     * @throws WrongStepException                 if the game is not in the correct step
     * @throws WrongGamePhaseException            if the game is not in the correct phase
     */
    public synchronized void placeCard(Player player, MixedCard card, Coordinates coordinates, boolean facingUp) throws WrongPlayerForCurrentTurnException, WrongStepException, WrongGamePhaseException {
        gameState.validateMove(player, TURN_STEP.PLACE);
        Board board = player.getBoard();
        boolean isAdded = board.add(coordinates, card, facingUp);

        if(isAdded && card.getPlayability(board) > 0){
            board.updatePoints(card.getPoints(board,coordinates)*card.getScore(facingUp));
        }
        else{
            //TODO
        }

        //TODO Ensures what should go first(goToNextPlayer or updateState)
        gameState.goToNextPlayer();
        gameState.updateState();
    }


    /**
     * Draws a card from the specified deck type for a player.
     *
     * @param player   the player drawing the card
     * @param deckType the type of deck to draw from
     * @param slot     the slot from which to draw the card
     * @return the drawn card
     * @throws WrongPlayerForCurrentTurnException if it's not the player's turn
     * @throws WrongStepException                 if the game is not in the correct step
     * @throws WrongGamePhaseException            if the game is not in the correct phase
     */
    public synchronized MixedCard drawCard(Player player, String deckType, PlaceInPublicBoard.Slots slot) throws WrongPlayerForCurrentTurnException, WrongStepException, WrongGamePhaseException {
        switch(deckType){
            //TODO Change to TYPE_HAND_CARD.RESOURCE and TYPE_HAND_CARD.GOLD when network is updated
            case "Resource"-> {
                return this.drawResourceCard(player, slot);
            }

            case "Gold" -> {
                return this.drawGoldCard(player, slot);
            }
        }

        return null;
    }


    /**
     * Draws a resource card from the specified slot for a player.
     *
     * @param player the player drawing the card
     * @param slot   the slot from which to draw the card
     * @return the drawn resource card
     * @throws WrongPlayerForCurrentTurnException if it's not the player's turn
     * @throws WrongStepException                 if the game is not in the correct step
     * @throws WrongGamePhaseException            if the game is not in the correct phase
     */
    private ResourceCard drawResourceCard(Player player, PlaceInPublicBoard.Slots slot) throws WrongPlayerForCurrentTurnException, WrongStepException, WrongGamePhaseException {
        gameState.validateMove(player, TURN_STEP.DRAW);

        gameState.updateTurnStep();

        return publicBoard.getResource(slot);
    }


    /**
     * Draws a gold card from the specified slot for a player.
     *
     * @param player the player drawing the card
     * @param slot   the slot from which to draw the card
     * @return the drawn gold card
     * @throws WrongPlayerForCurrentTurnException if it's not the player's turn
     * @throws WrongStepException                 if the game is not in the correct step
     * @throws WrongGamePhaseException            if the game is not in the correct phase
     */
    private GoldCard drawGoldCard(Player player, PlaceInPublicBoard.Slots slot) throws WrongPlayerForCurrentTurnException, WrongStepException, WrongGamePhaseException {
        gameState.validateMove(player, TURN_STEP.DRAW);

        gameState.updateTurnStep();

        return publicBoard.getGold(slot);
    }


    /**
     * Adds a message in the chat.
     *
     * @param playerSender the nickname of the player sending the message
     * @param message      the message to send
     * @return the added message
     */
    public synchronized Message writeMessage(String playerSender, String message){
        return this.chatController.writeMessage(playerSender, message);
    }
}
