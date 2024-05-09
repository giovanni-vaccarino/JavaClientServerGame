package polimi.ingsoft.server.controller;

import polimi.ingsoft.server.Player;
import polimi.ingsoft.server.enumerations.GAME_PHASE;
import polimi.ingsoft.server.enumerations.INITIAL_STEP;
import polimi.ingsoft.server.enumerations.TURN_STEP;
import polimi.ingsoft.server.exceptions.WrongGamePhaseException;
import polimi.ingsoft.server.exceptions.WrongPlayerForCurrentTurnException;
import polimi.ingsoft.server.exceptions.WrongStepException;

import java.io.Serializable;
import java.util.List;

public class GameState implements Serializable {
    private volatile MatchController matchController;
    private final Integer requestedNumPlayers;

    private int currentPlayerIndex;

    private Integer playerStepCounter = 0;

    private GAME_PHASE gamePhase;

    private TURN_STEP currentTurnStep;

    private INITIAL_STEP currentInitialStep;

    public GameState(MatchController matchController, Integer requestedNumPlayers) {
        this.gamePhase = GAME_PHASE.WAITING_FOR_PLAYERS;
        this.matchController = matchController;
        this.requestedNumPlayers = requestedNumPlayers;
    }

    private boolean isLastRound() {
        int playerScore;
        List<Player> players = this.matchController.getPlayers();

        for(var player : players){
            playerScore = player.getBoard().getScore();

            if (playerScore >= 20){
                return true;
            }
        }

        return false;
    }

    private void setLastRound(){
        boolean isLastRound = this.isLastRound();

        if (isLastRound){
            gamePhase = GAME_PHASE.LAST_ROUND;
        }
    }

    private Player getCurrentPlayer() {
        return matchController.getPlayers().get(currentPlayerIndex);
    }

    private List<String> getNicks(){
        return this.matchController.getNamePlayers();
    }

    public void goToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % requestedNumPlayers;
        this.currentTurnStep = TURN_STEP.DRAW;
    }

    public void validateMove(Player player, TURN_STEP move) throws WrongPlayerForCurrentTurnException, WrongStepException, WrongGamePhaseException {
        if (gamePhase != GAME_PHASE.PLAY && gamePhase != GAME_PHASE.LAST_ROUND) throw new WrongGamePhaseException();
        if (player != getCurrentPlayer()) throw new WrongPlayerForCurrentTurnException();
        if (currentTurnStep != move) throw new WrongStepException();
    }

    public void validateInitialChoice(String player, GAME_PHASE phase, INITIAL_STEP step) throws WrongPlayerForCurrentTurnException, WrongStepException {
        if (GAME_PHASE.INITIALIZATION != phase) throw new WrongStepException();
        if (this.currentInitialStep != step) throw new WrongStepException();
    }


    public void updateState(){
        switch(gamePhase){
            case GAME_PHASE.WAITING_FOR_PLAYERS -> {
                if(getNicks().size() == requestedNumPlayers){
                    this.gamePhase = GAME_PHASE.INITIALIZATION;
                    this.currentInitialStep = INITIAL_STEP.COLOR;
                }
            }

            case GAME_PHASE.INITIALIZATION -> {
                this.matchController.initializePlayers();
                this.gamePhase = GAME_PHASE.PLAY;
            }

            case GAME_PHASE.PLAY -> {
                setLastRound();
            }
        }
    }

    public void updateInitialStep(){
        this.playerStepCounter += 1;

        switch (currentInitialStep){
            case INITIAL_STEP.COLOR -> {
                if (playerStepCounter.equals(requestedNumPlayers)){
                    this.currentInitialStep = INITIAL_STEP.FACE_INITIAL;
                    playerStepCounter = 0;
                }
            }

            case INITIAL_STEP.FACE_INITIAL -> {
                if (playerStepCounter.equals(requestedNumPlayers)){
                    this.currentInitialStep = INITIAL_STEP.QUEST_CARD;
                    playerStepCounter = 0;
                }
            }

            case INITIAL_STEP.QUEST_CARD -> {
                if (playerStepCounter.equals(requestedNumPlayers)){
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

    private Player getWinner(){
        Player winner = null;
        int playerScore, maxScore;

        maxScore = -1;
        List<Player> players = matchController.getPlayers();

        for (var player : players){
            playerScore = player.getBoard().getScore();

            if (playerScore > maxScore){
                maxScore = playerScore;
                winner = player;
            }
        }

        return winner;
    }
}
