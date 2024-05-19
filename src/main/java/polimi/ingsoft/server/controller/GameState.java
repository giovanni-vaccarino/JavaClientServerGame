package polimi.ingsoft.server.controller;

import polimi.ingsoft.server.enumerations.PlayerColors;
import polimi.ingsoft.server.exceptions.*;
import polimi.ingsoft.server.model.Player;
import polimi.ingsoft.server.enumerations.GAME_PHASE;
import polimi.ingsoft.server.enumerations.INITIAL_STEP;
import polimi.ingsoft.server.enumerations.TURN_STEP;

import java.io.Serializable;
import java.util.*;
import java.util.List;

/**
 * This class manages the state of the game, including the FSMs of the game
 */
public class GameState implements Serializable {
    private volatile MatchController matchController;
    private final Integer requestedNumPlayers;

    private int currentPlayerIndex;

    private int firstPlayerIndex;

    private int turnNumber = 1;

    private int endRound;

    private List<String> playerStepCheck = new ArrayList<>();

    private GAME_PHASE gamePhase;

    private TURN_STEP currentTurnStep;

    private INITIAL_STEP currentInitialStep;

    /**
     * Constructs a GameState with the specified matchController and number of players.
     *
     * @param matchController     the match controller associated with this game state
     * @param requestedNumPlayers the number of players requested for the game
     */
    public GameState(MatchController matchController, Integer requestedNumPlayers) {
        this.gamePhase = GAME_PHASE.WAITING_FOR_PLAYERS;
        this.matchController = matchController;
        this.requestedNumPlayers = requestedNumPlayers;
    }

    public Player getCurrentPlayer() {
        return matchController.getPlayers().get(currentPlayerIndex);
    }

    private List<String> getPlayersNick(){
        return this.matchController.getNamePlayers();
    }

    public Integer getFirstPlayerIndex(){return this.firstPlayerIndex;}

    public GAME_PHASE getGamePhase(){return this.gamePhase;}

    public INITIAL_STEP getCurrentInitialStep(){return this.currentInitialStep;}

    public TURN_STEP getCurrentTurnStep(){return this.currentTurnStep;}

    /**
     * Updates the game phase
     */
    public void updateState(){
        switch(gamePhase){
            case GAME_PHASE.WAITING_FOR_PLAYERS -> {
                if(getPlayersNick().size() == requestedNumPlayers){
                    this.gamePhase = GAME_PHASE.INITIALIZATION;
                    this.currentInitialStep = INITIAL_STEP.COLOR;
                }
            }

            case GAME_PHASE.INITIALIZATION -> {
                if (playerStepCheck.size() == requestedNumPlayers && this.currentInitialStep == INITIAL_STEP.QUEST_CARD){
                    this.matchController.initializePlayers();
                    this.setFirstPlayer();
                    this.gamePhase = GAME_PHASE.PLAY;
                    this.currentTurnStep = TURN_STEP.DRAW;
                }
            }

            case GAME_PHASE.PLAY -> {
                setLastRound();
            }

            case GAME_PHASE.LAST_ROUND -> {
                if(this.turnNumber == this.endRound){
                    this.gamePhase = GAME_PHASE.END;
                    //TODO this.calculateFinalScore();
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
                if (playerStepCheck.size() == requestedNumPlayers){
                    this.currentInitialStep = INITIAL_STEP.FACE_INITIAL;
                    this.playerStepCheck.clear();
                }
            }

            case INITIAL_STEP.FACE_INITIAL -> {
                if (playerStepCheck.size() == requestedNumPlayers){
                    this.currentInitialStep = INITIAL_STEP.QUEST_CARD;
                    this.playerStepCheck.clear();
                }
            }

            case INITIAL_STEP.QUEST_CARD -> {
                if (playerStepCheck.size() == requestedNumPlayers){
                    this.updateState();
                }
            }
        }
    }

    /**
     * Updates the current turn step.
     */
    public void updateTurnStep(){
        switch(currentTurnStep){
            case TURN_STEP.DRAW -> {
                this.currentTurnStep = TURN_STEP.PLACE;
            }

            case TURN_STEP.PLACE -> {
                this.currentTurnStep = TURN_STEP.DRAW;
            }
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
     */
    public void validateMove(Player player, TURN_STEP move) throws WrongPlayerForCurrentTurnException, WrongStepException, WrongGamePhaseException {
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
    public void checkColorAvailability(PlayerColors color) throws ColorAlreadyPickedException {
        // The != null is to cover the case: if a player hasn't picked a color yet
        boolean isColorPicked = matchController.getPlayerInitialSettings().stream()
                .map(PlayerInitialSetting::getColor)
                .anyMatch(playerColor -> playerColor != null && playerColor.equals(color));

        if (isColorPicked) {
            throw new ColorAlreadyPickedException(color);
        }
    }


    /**
     * Checks if it is the last round.
     *
     * @return true if it is the last round, false otherwise
     */
    private boolean isLastRound() {
        return this.matchController.getPlayers().stream()
                .mapToInt(player -> player.getBoard().getScore())
                .anyMatch(score -> score >= 20);
    }


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

        this.firstPlayerIndex = random.nextInt(requestedNumPlayers);
        currentPlayerIndex = firstPlayerIndex;
    }


    private void updateTurnNumber(){
        this.turnNumber += (currentPlayerIndex == firstPlayerIndex) ? 1 : 0;
    }


    public void goToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % requestedNumPlayers;

        this.updateTurnNumber();
        this.updateTurnStep();
    }


    private Player getWinner() {
        Optional<Player> winner = this.matchController.getPlayers().stream()
                .max(Comparator.comparingInt(player -> player.getBoard().getScore()));

        return winner.orElse(null);
    }
}
