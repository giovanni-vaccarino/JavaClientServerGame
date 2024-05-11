package polimi.ingsoft.server.controller;

import polimi.ingsoft.server.exceptions.InitalChoiceAlreadySetException;
import polimi.ingsoft.server.model.Player;
import polimi.ingsoft.server.enumerations.GAME_PHASE;
import polimi.ingsoft.server.enumerations.INITIAL_STEP;
import polimi.ingsoft.server.enumerations.TURN_STEP;
import polimi.ingsoft.server.exceptions.WrongGamePhaseException;
import polimi.ingsoft.server.exceptions.WrongPlayerForCurrentTurnException;
import polimi.ingsoft.server.exceptions.WrongStepException;

import java.io.Serializable;
import java.util.*;

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

    public GameState(MatchController matchController, Integer requestedNumPlayers) {
        this.gamePhase = GAME_PHASE.WAITING_FOR_PLAYERS;
        this.matchController = matchController;
        this.requestedNumPlayers = requestedNumPlayers;
    }

    private Player getCurrentPlayer() {
        return matchController.getPlayers().get(currentPlayerIndex);
    }

    private List<String> getPlayersNick(){
        return this.matchController.getNamePlayers();
    }

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
                }
            }

            case GAME_PHASE.PLAY -> {
                setLastRound();
            }

            case GAME_PHASE.LAST_ROUND -> {
                if(this.turnNumber == this.endRound){
                    this.gamePhase = GAME_PHASE.END;
                }
            }

            case GAME_PHASE.END -> {

            }
        }
    }

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

    public void validateMove(Player player, TURN_STEP move) throws WrongPlayerForCurrentTurnException, WrongStepException, WrongGamePhaseException {
        if (gamePhase != GAME_PHASE.PLAY && gamePhase != GAME_PHASE.LAST_ROUND) throw new WrongGamePhaseException();
        if (player != getCurrentPlayer()) throw new WrongPlayerForCurrentTurnException();
        if (currentTurnStep != move) throw new WrongStepException();
    }

    public void validateInitialChoice(String player, GAME_PHASE phase, INITIAL_STEP step) throws WrongGamePhaseException, WrongStepException, InitalChoiceAlreadySetException {
        if (GAME_PHASE.INITIALIZATION != phase) throw new WrongGamePhaseException();
        if (this.currentInitialStep != step) throw new WrongStepException();
        if (this.playerStepCheck.contains(player)) throw new InitalChoiceAlreadySetException(step);
    }

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
