package polimi.ingsoft.server.controller;

import polimi.ingsoft.server.enumerations.BLOCKED_MATCH_STATE;
import polimi.ingsoft.server.model.player.PlayerColor;
import polimi.ingsoft.server.exceptions.MatchExceptions.*;
import polimi.ingsoft.server.model.publicboard.PlaceInPublicBoard;
import polimi.ingsoft.server.model.player.Player;
import polimi.ingsoft.server.enumerations.GAME_PHASE;
import polimi.ingsoft.server.enumerations.INITIAL_STEP;
import polimi.ingsoft.server.enumerations.TURN_STEP;
import polimi.ingsoft.server.model.cards.QuestCard;

import java.io.Serializable;
import java.util.*;
import java.util.List;

/**
 * This class manages the state of the game, including the FSMs of the game
 */
public class GameState implements Serializable, Cloneable {
    /**
     * The match controller associated with this game state.
     * Declared transient to avoid serialization and network transmission.
     */
    private final transient MatchController matchController;

    private final Integer requestedNumPlayers;

    private Integer effectiveNumPlayers;

    private int currentPlayerIndex;

    private String currentPlayerNickname;

    private int firstPlayerIndex;

    private int turnNumber = 1;

    private int endRound;

    private List<String> winners;

    /**
     * List of players that have already made their initial choice.
     */
    private final List<String> playerStepCheck = new ArrayList<>();

    private GAME_PHASE gamePhase;

    private TURN_STEP currentTurnStep;

    private INITIAL_STEP currentInitialStep;

    private BLOCKED_MATCH_STATE blockedMatchState;

    /**
     * Constructs a GameState with the specified matchController and number of players.
     *
     * @param matchController     the match controller associated with this game state
     * @param requestedNumPlayers the number of players requested for the game
     */
    public GameState(MatchController matchController, Integer requestedNumPlayers) {
        this.gamePhase = GAME_PHASE.WAITING_FOR_PLAYERS;
        this.blockedMatchState = BLOCKED_MATCH_STATE.NOT_BLOCKED;
        this.matchController = matchController;
        this.requestedNumPlayers = requestedNumPlayers;
        this.effectiveNumPlayers = requestedNumPlayers;
    }


    /**
     * Returns the player that needs to make the move.
     *
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return matchController.getPlayers().get(currentPlayerIndex);
    }


    /**
     * Returns the index of the first player.
     *
     * @return the index of the first player
     */
    public Integer getFirstPlayerIndex(){return this.firstPlayerIndex;}


    /**
     * Returns the current game phase.
     *
     * @return the current game phase
     */
    public GAME_PHASE getGamePhase(){return this.gamePhase;}


    /**
     * Returns the current initial step.
     *
     * @return the current initial step
     */
    public INITIAL_STEP getCurrentInitialStep(){return this.currentInitialStep;}


    /**
     * Returns the current turn step.
     *
     * @return the current turn step
     */
    public TURN_STEP getCurrentTurnStep(){return this.currentTurnStep;}


    /**
     * Returns the current player's nickname.
     *
     * @return the current player's nickname
     */
    public String getCurrentPlayerNickname() {
        return this.currentPlayerNickname;
    }


    /**
     * Returns the number of players in the lobby.
     *
     * @return the number of players in lobby
     */
    private Integer getNumLobbyPlayers(){
        return matchController.getLobbyPlayers().size();
    }


    /**
     * Decreases the number of player setting their initial choices.
     */
    public void decreaseSettingPlayer(){
        effectiveNumPlayers -= 1;
    }


    /**
     * Checks if the current player is the last one setting their initial choice.
     *
     * @return true if the current player is the last one setting, false otherwise
     */
    public Boolean isLastPlayerSetting(){
        return playerStepCheck.size() == effectiveNumPlayers - 1;
    }


    /**
     * Sets the blocked match state.
     *
     * @param blockedMatchState the blocked match state to set
     */
    public void setBlockedMatchState(BLOCKED_MATCH_STATE blockedMatchState){this.blockedMatchState = blockedMatchState;}


    /**
     * Returns the blocked match state.
     *
     * @return the blocked match state
     */
    public BLOCKED_MATCH_STATE getBlockedMatchState(){return this.blockedMatchState;}


    /**
     * Updates the game phase
     */
    public void updateState(){
        switch(gamePhase){
            case GAME_PHASE.WAITING_FOR_PLAYERS -> {
                if(getNumLobbyPlayers().equals(requestedNumPlayers)){
                    this.matchController.initializePlayersInitialSettings();
                    this.gamePhase = GAME_PHASE.INITIALIZATION;
                    this.currentInitialStep = INITIAL_STEP.COLOR;
                }
            }

            case GAME_PHASE.INITIALIZATION -> {
                if (playerStepCheck.size() == effectiveNumPlayers && this.currentInitialStep == INITIAL_STEP.QUEST_CARD){
                    this.setFirstPlayer();
                    this.matchController.initializePlayers();
                    this.gamePhase = GAME_PHASE.PLAY;
                    this.currentTurnStep = TURN_STEP.PLACE;
                }
            }

            case GAME_PHASE.PLAY -> {
                setLastRound();
            }

            case GAME_PHASE.LAST_ROUND -> {
                if(this.turnNumber == this.endRound){
                    this.gamePhase = GAME_PHASE.END;
                    this.calculateWinners();
                }
            }

            case GAME_PHASE.END -> {

            }
        }
    }


    /**
     * Updates the initial step
     *
     * @param playerNickname the nickname of the player
     */
    public void updateInitialStep(String playerNickname){
        this.playerStepCheck.add(playerNickname);

        switch (currentInitialStep){
            case INITIAL_STEP.COLOR -> {
                if (playerStepCheck.size() == effectiveNumPlayers){
                    this.currentInitialStep = INITIAL_STEP.FACE_INITIAL;
                    this.playerStepCheck.clear();
                }
            }

            case INITIAL_STEP.FACE_INITIAL -> {
                if (playerStepCheck.size() == effectiveNumPlayers){
                    this.currentInitialStep = INITIAL_STEP.QUEST_CARD;
                    this.playerStepCheck.clear();
                }
            }

            case INITIAL_STEP.QUEST_CARD -> {
                if (playerStepCheck.size() == effectiveNumPlayers){
                    this.updateState();
                }
            }
        }
    }

    /**
     * Updates the current turn step.
     */
    public void updateTurnStep(){
        if(gamePhase == GAME_PHASE.PLAY){
            switch(currentTurnStep){
                case TURN_STEP.PLACE -> {
                    this.currentTurnStep = TURN_STEP.DRAW;
                }

                case TURN_STEP.DRAW -> {
                    this.currentTurnStep = TURN_STEP.PLACE;
                }
            }
        }else{
            goToNextPlayer();
        }
    }


    /**
     * Validates a player's move
     *
     * @param player the player making the move
     * @param move   the move to validate
     * @throws WrongPlayerForCurrentTurnException if the player is not the current player
     * @throws WrongStepException                if the move is not allowed in the current step
     * @throws WrongGamePhaseException           if the move is not allowed in the current game phase
     * @throws MatchBlockedException              if the match is blocked
     */
    public void validateMove(Player player, TURN_STEP move) throws WrongPlayerForCurrentTurnException, WrongStepException, WrongGamePhaseException, MatchBlockedException {
        if (blockedMatchState != BLOCKED_MATCH_STATE.NOT_BLOCKED) throw new MatchBlockedException();
        if (gamePhase != GAME_PHASE.PLAY && gamePhase != GAME_PHASE.LAST_ROUND) throw new WrongGamePhaseException();
        if (player != getCurrentPlayer()) throw new WrongPlayerForCurrentTurnException();
        if (currentTurnStep != move) throw new WrongStepException();
    }


    /**
     * Validates a player's initial choice
     *
     * @param player the player making the move
     * @param phase  the game phase
     * @param step   the initial step
     * @throws WrongGamePhaseException        if the game phase is not correct
     * @throws WrongStepException             if the step is not correct
     * @throws InitalChoiceAlreadySetException if the initial choice has already been set
     */
    public void validateInitialChoice(String player, GAME_PHASE phase, INITIAL_STEP step) throws WrongGamePhaseException, WrongStepException, InitalChoiceAlreadySetException {
        if (GAME_PHASE.INITIALIZATION != phase) throw new WrongGamePhaseException();
        if (this.currentInitialStep != step) throw new WrongStepException();
        if (this.playerStepCheck.contains(player)) throw new InitalChoiceAlreadySetException(step);
    }


    /**
     * Checks if a color has already been picked by another player.
     *
     * @param color the color to check
     * @throws ColorAlreadyPickedException if the color is already picked
     */
    public void checkColorAvailability(PlayerColor color) throws ColorAlreadyPickedException {
        // The != null is to cover the case: if a player hasn't picked a color yet
        boolean isColorPicked = matchController.getPlayerInitialSettings().stream()
                .map(PlayerInitialSetting::getColor)
                .anyMatch(playerColor -> playerColor != null && playerColor.equals(color));

        if (isColorPicked) {
            throw new ColorAlreadyPickedException(color);
        }
    }


    /**
     * Checks if it is the last round. It is the last round if any player has reached
     * 20 points or the decks are empty
     *
     * @return true if it is the last round, false otherwise
     */
    private boolean isLastRound() {
        return (this.matchController.getPlayers().stream()
                .mapToInt(player -> player.getBoard().getScore())
                .anyMatch(score -> score >= 2))
                ||
                matchController.getPublicBoard().isDecksEmpty();
    }


    /**
     * Sets the game to the last round if the condition is met.
     */
    private void setLastRound(){
        boolean isLastRound = this.isLastRound();

        if (isLastRound){
            gamePhase = GAME_PHASE.LAST_ROUND;
            this.endRound = this.turnNumber + 2;
        }
    }


    /**
     * Sets the first player randomly.
     */
    private void setFirstPlayer(){
        Random random = new Random();

        this.firstPlayerIndex = random.nextInt(effectiveNumPlayers);
        currentPlayerIndex = firstPlayerIndex;
        currentPlayerNickname = matchController.getPlayerInitialSettings().get(firstPlayerIndex).getNickname();

        System.out.println("The first player is: " + currentPlayerNickname + " with index: " + currentPlayerIndex);
    }


    /**
     * Updates the turn number.
     */
    private void updateTurnNumber(){
        this.turnNumber += (currentPlayerIndex == firstPlayerIndex) ? 1 : 0;
    }


    /**
     * Advances to the next player.
     */
    public void goToNextPlayer() {
        if(blockedMatchState.equals(BLOCKED_MATCH_STATE.BLOCKED_ONE_PLAYER)){
            return;
        }

        do{
            System.out.println("The previous player was: " + currentPlayerNickname + " with index: " + currentPlayerIndex + ". Status: " + getCurrentPlayer().getIsDisconnected());
            currentPlayerIndex = (currentPlayerIndex + 1) % effectiveNumPlayers;
            System.out.println("The current player has become: " + getCurrentPlayer().getNickname() + " with index " + currentPlayerIndex
                    + ". Status: " + getCurrentPlayer().getIsDisconnected());
            this.updateTurnNumber();
        } while(matchController.isPlayerDisconnected(currentPlayerIndex));

        currentPlayerNickname = getCurrentPlayer().getNickname();
        if(gamePhase == GAME_PHASE.PLAY && currentTurnStep == TURN_STEP.DRAW){
            this.updateTurnStep();
        }
        this.updateState();
    }


    /**
     * Sets the only winner of the game. Called if only one player is remained in the game(after the timer is expired)
     */
    public void setOnlyWinner(){
        this.gamePhase = GAME_PHASE.END;
        this.calculateWinners();
    }


    /**
     * Add for each player the points from the quest cards(personal and common)
     */
    private void calculateWinners(){
        Map<String, Integer> questCardPoints = new HashMap<>();
        QuestCard firstCommonQuest = matchController.getPublicBoard().getQuest(PlaceInPublicBoard.Slots.SLOT_A);
        QuestCard secondCommonQuest = matchController.getPublicBoard().getQuest(PlaceInPublicBoard.Slots.SLOT_B);

        for(var player : matchController.getPlayers()){
            QuestCard personalQuest = player.getQuestCard();

            Integer pointsFirstCommon = firstCommonQuest.getMatches(player.getBoard());
            Integer pointsSecondCommon = secondCommonQuest.getMatches(player.getBoard());
            Integer pointsPersonal = personalQuest.getMatches(player.getBoard());

            System.out.println(player.getNickname() + " ha fatto: " + pointsFirstCommon + " e " + pointsSecondCommon + " e " + pointsPersonal);
            player.getBoard().updatePoints(pointsFirstCommon + pointsSecondCommon + pointsPersonal);
            questCardPoints.put(player.getNickname(), pointsFirstCommon + pointsSecondCommon + pointsPersonal);
        }

        List<String> winners = calculatePointsWinners();

        if(winners.size() == 1){
            setWinners(winners);
            return;
        }

        setWinners(calculateQuestPointsWinners(questCardPoints, winners));
    }


    /**
     * Calculates the winners based on points.
     *
     * @return a list of winners
     */
    private List<String> calculatePointsWinners(){
        List<String> winners = new ArrayList<>();
        int maxPoints = -1;

        for(var player : matchController.getPlayers()){
            if(!player.getIsDisconnected()){
                if(player.getBoard().getScore() > maxPoints){
                    winners.clear();
                    winners.add(player.getNickname());
                    maxPoints = player.getBoard().getScore();
                }else if(player.getBoard().getScore() == maxPoints){
                    winners.add(player.getNickname());
                }
            }
        }

        return winners;
    }


    /**
     * Calculates the winners based on quest card points in case of a tie.
     *
     * @param questCardPoints a map of quest card points
     * @param winners         a list of current winners
     * @return a list of winners based on quest card points
     */
    private List<String> calculateQuestPointsWinners(Map<String, Integer> questCardPoints, List<String> winners){
        List<String> tieWinners = new ArrayList<>(winners);
        winners.clear();
        int maxQuestPoints = -1;

        for(var player : tieWinners){
            if(questCardPoints.get(player) > maxQuestPoints){
                winners.clear();
                winners.add(player);
                maxQuestPoints = questCardPoints.get(player);
            }else if(questCardPoints.get(player) == maxQuestPoints){
                winners.add(player);
            }
        }

        return winners;
    }


    /**
     * Sets the winners of the game.
     *
     * @param winners a list of winners
     */
    private void setWinners(List<String> winners){
        this.winners = winners;
    }


    /**
     * Returns the winners of the game.
     *
     * @return a list of winners
     */
    public List<String> getWinners(){
        return this.winners;
    }


    @Override
    public GameState clone() {
        try {
            GameState clone = (GameState) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
