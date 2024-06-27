package polimi.ingsoft.server.controller;

import polimi.ingsoft.server.exceptions.MatchExceptions.*;
import polimi.ingsoft.server.exceptions.MatchSelectionExceptions.MatchAlreadyFullException;
import polimi.ingsoft.server.factories.PlayerInitialSettingFactory;
import polimi.ingsoft.server.model.player.Player;
import polimi.ingsoft.server.enumerations.*;
import polimi.ingsoft.server.factories.PlayerFactory;
import polimi.ingsoft.server.model.boards.Board;
import polimi.ingsoft.server.model.boards.Coordinates;
import polimi.ingsoft.server.model.cards.MixedCard;
import polimi.ingsoft.server.model.cards.QuestCard;
import polimi.ingsoft.server.model.chat.Message;
import polimi.ingsoft.server.model.player.PlayerColor;
import polimi.ingsoft.server.model.publicboard.PlaceInPublicBoard;
import polimi.ingsoft.server.model.publicboard.PublicBoard;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


/**
 * The MatchController class manages the state and behavior of a single match.
 */
public class MatchController implements Serializable {
    private final Integer requestedNumPlayers;

    private final GameState gameState;

    private final ChatController chatController;

    private List<Player> players = new ArrayList<>();

    private final List<PlayerInitialSetting> playerInitialSettings = new ArrayList<>();

    private final List<String> lobbyPlayers = new ArrayList<>();

    public PublicBoard getPublicBoard() {
        return publicBoard;
    }

    private final PublicBoard publicBoard;

    protected transient final PrintStream logger;

    protected final int matchId;

    private transient Timer pinger = null;


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
     * Checks if the pinger timer is set.
     *
     * @return true if the pinger is set, false otherwise
     */
    public boolean hasPinger() {
        return pinger != null;
    }


    /**
     * Sets the pinger timer.
     *
     * @param pinger the pinger timer to set
     */
    public void setPinger(Timer pinger) {
        this.pinger = pinger;
    }


    /**
     * Stops the timer
     */
    public void turnPingerOff() {
        if (pinger != null) pinger.cancel();
    }


    /**
     * Gets the requested number of players for this match.
     *
     * @return the requested number of players
     */
    public Integer getRequestedNumPlayers(){
        return this.requestedNumPlayers;
    }


    /**
     * Updates the disconnected status of a player.
     *
     * @param nickname        the nickname of the player to update
     * @param isDisconnected  true if the player is disconnected, false otherwise
     * @return the rejoin state for the player
     */
    public synchronized REJOIN_STATE updatePlayerStatus(String nickname, Boolean isDisconnected){
        Player player = getPlayerByNickname(nickname).orElse(null);

        player.setIsDisconnected(isDisconnected);

        if(gameState.getCurrentPlayer() == player){
            return (gameState.getCurrentTurnStep() == TURN_STEP.PLACE) ? REJOIN_STATE.HAVE_TO_UPDATE_TURN
                    : REJOIN_STATE.HAVE_TO_DRAW;
        }

        return REJOIN_STATE.NO_UPDATE;
    }


    /**
     * Checks if a player is disconnected.
     *
     * @param index the index of the player to check
     * @return true if the player is disconnected, false otherwise
     */
    public synchronized Boolean isPlayerDisconnected(Integer index){
        return players.get(index).getIsDisconnected();
    }


    /**
     * Gets the number of online players.
     *
     * @return the number of online players
     */
    public synchronized Integer getNumOnlinePlayers(){
        return Math.toIntExact(players.stream()
                .filter(player -> !player.getIsDisconnected())
                .count());
    }


    /**
     * Gets the list of players in the lobby.
     *
     * @return the list of lobby players
     */
    public List<String> getLobbyPlayers(){
        return lobbyPlayers;
    }


    /**
     * Removes a player from the lobby.
     *
     * @param nickname the nickname of the player to remove
     */
    public void removeLobbyPlayer(String nickname){
        lobbyPlayers.stream()
                .filter(p -> p.equals(nickname))
                .findFirst()
                .ifPresent(lobbyPlayers::remove);
    }


    /**
     * Removes the initial setting for a player.
     *
     * @param nickname the nickname of the player whose initial setting to remove
     */
    public void removePlayerInitialSetting(String nickname){
        playerInitialSettings.stream()
                .filter(p -> p.getNickname().equals(nickname))
                .findFirst()
                .ifPresent(playerInitialSettings::remove);

        gameState.decreaseSettingPlayer();
    }


    /**
     * Initializes the initial settings for the players in the lobby.
     */
    public void initializePlayersInitialSettings(){
        for(var player : lobbyPlayers){
            // Retrieving from Public Board 1 initial card, 2 resource, 1 gold card and 2 possible quest cards
            PlayerInitialSetting playerInitialSetting = PlayerInitialSettingFactory.createPlayerInitialSetting(this.publicBoard, player);

            //Adding a new private chat for the player
            chatController.addPrivateChat(player);

            playerInitialSettings.add(playerInitialSetting);
        }
    }

    /**
     * Returns the ID of this match.
     *
     * @return the match ID
     */
    public synchronized int getMatchId() {
        return matchId;
    }


    /**
     * Returns the game state associated with this match.
     *
     * @return the game state
     */
    public synchronized GameState getGameState(){return this.gameState;}


    /**
     * Returns the list of players in this match.
     *
     * @return the list of players
     */
    public synchronized List<Player> getPlayers(){
        return players;
    }


    /**
     * Returns the list of initial settings for the players in this match.
     *
     * @return the list of initial settings
     */
    public synchronized List<PlayerInitialSetting> getPlayerInitialSettings(){return playerInitialSettings;}


    /**
     * Returns the nicknames of the players in this match.
     *
     * @return a list of player nicknames
     */
    public synchronized List<String> getNamePlayers(){
        return playerInitialSettings.stream().
                map(PlayerInitialSetting::getNickname).
                toList();
    }


    /**
     * Retrieves the initial settings for a player by their nickname.
     *
     * @param nickname the nickname of the player
     * @return an optional containing the initial settings if found, empty otherwise
     */
    public synchronized Optional<PlayerInitialSetting> getPlayerInitialSettingByNickname(String nickname) {
        return playerInitialSettings.stream()
                .filter(player -> player.getNickname().equals(nickname))
                .findFirst();
    }


    /**
     * Retrieves a player by their nickname.
     *
     * @param nickname the nickname of the player
     * @return an optional containing the player if found, empty otherwise
     */
    public synchronized Optional<Player> getPlayerByNickname(String nickname) {
        return players.stream()
                .filter(player -> player.getNickname().equals(nickname))
                .findFirst();
    }


    /**
     * Retrieves the boards of all players.
     *
     * @return a map of player nicknames to their respective boards
     */
    public synchronized Map<String, Board> getPlayerBoards(){
        Map<String, Board> playerBoards = new HashMap<>();

        for(var player : getPlayers()){
            playerBoards.put(player.getNickname(), player.getBoard());
        }

        return playerBoards;
    }


    /**
     * Adds a player to this match.
     *
     * @param nickname the nickname of the player to add
     * @throws MatchAlreadyFullException if the match is already full
     */
    public synchronized void addPlayer(String nickname) throws MatchAlreadyFullException {
        if(lobbyPlayers.size() == requestedNumPlayers){
            throw new MatchAlreadyFullException();
        }

        lobbyPlayers.add(nickname);

        gameState.updateState();
    }


    /**
     * Initializes the players for this match, using the information collected in PlayerInitialSetting.
     * The initialization of the players can be done only once.
     */
    public void initializePlayers() {
        this.players = this.players.isEmpty() ?
                this.playerInitialSettings.stream()
                        .map(playerInitialSetting ->
                                PlayerFactory.createPlayer(playerInitialSetting, isFirstPlayer(playerInitialSetting.getNickname())))
                        .collect(Collectors.toList())
                :
                this.players;
    }


    /**
     * Checks if a player is the first player.
     *
     * @param nickname the nickname of the player to check
     * @return true if the player is the first player, false otherwise
     */
    public Boolean isFirstPlayer(String nickname){
        Integer firstPlayerIndex = gameState.getFirstPlayerIndex();

        return getPlayerInitialSettings().get(firstPlayerIndex).getNickname().equals(nickname);
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
    public synchronized void placeCard(Player player, MixedCard card, Coordinates coordinates, boolean facingUp) throws WrongPlayerForCurrentTurnException, WrongStepException, WrongGamePhaseException, CoordinateNotValidException, NotEnoughResourcesException, MatchBlockedException {
        gameState.validateMove(player, TURN_STEP.PLACE);
        Board board = player.getBoard();

        if(facingUp||card.getPlayability(board) > 0){
            boolean isAdded = board.add(coordinates, card, facingUp);
            if(isAdded){
                board.updatePoints(card.getPoints(board,coordinates) * card.getScore(facingUp));
                player.removeFromHand(card);
            }else{
                throw new CoordinateNotValidException();
            }
        }
        else{
            throw new NotEnoughResourcesException();
        }

        gameState.updateTurnStep();
    }


    /**
     * Draws a card from the specified deck type for a player.
     *
     * @param player   the player drawing the card
     * @param deckType the type of deck to draw from
     * @param slot     the slot from which to draw the card
     * @throws WrongPlayerForCurrentTurnException if it's not the player's turn
     * @throws WrongStepException                 if the game is not in the correct step
     * @throws WrongGamePhaseException            if the game is not in the correct phase
     */
    public synchronized void drawCard(Player player, TYPE_HAND_CARD deckType, PlaceInPublicBoard.Slots slot) throws WrongPlayerForCurrentTurnException, WrongStepException, WrongGamePhaseException, MatchBlockedException {
        gameState.validateMove(player, TURN_STEP.DRAW);

        gameState.goToNextPlayer();

        if(deckType == TYPE_HAND_CARD.RESOURCE){
            player.addToHand(publicBoard.getResource(slot));
        }else{
            player.addToHand(publicBoard.getGold(slot));
        }
    }


    /**
     * Adds a message in the broadcast chat.
     *
     * @param playerSender the nickname of the player sending the message
     * @param message      the message to send
     * @return the added message
     * @throws NotValidMessageException if the message typed is not valid
     */
    public synchronized Message writeBroadcastMessage(String playerSender, String message) throws NotValidMessageException{
        return this.chatController.writeBroadcastMessage(playerSender, message);
    }


    /**
     * Adds a message in a private chat.
     *
     * @param sender the nickname of the player sending the message
     * @param recipient the nickname of the player receiving the message
     * @param message      the message to send
     * @return the added message
     * @throws PlayerNotFoundException if the receiver player has not been found
     * @throws NotValidMessageException if the message typed is not valid
     */
    public synchronized Message writePrivateMessage(String sender, String recipient, String message) throws PlayerNotFoundException, NotValidMessageException {
        return this.chatController.writePrivateMessage(sender, recipient, message);
    }
}
