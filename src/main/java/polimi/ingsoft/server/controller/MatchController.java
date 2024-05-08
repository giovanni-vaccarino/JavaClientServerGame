package polimi.ingsoft.server.controller;

import polimi.ingsoft.server.Player;
import polimi.ingsoft.server.enumerations.*;
import polimi.ingsoft.server.exceptions.WrongGamePhaseException;
import polimi.ingsoft.server.exceptions.WrongPlayerForCurrentTurnException;
import polimi.ingsoft.server.exceptions.WrongStepException;
import polimi.ingsoft.server.model.*;

import javax.sound.sampled.BooleanControl;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MatchController implements Serializable {

    private final Integer requestedNumPlayers;

    private TURN_STEP currentStep;

    private GAME_PHASE gamePhase;

    private INITIAL_STEP initialStep;
    private int currentPlayerIndex;

    final ChatController chatController;
    private List<Player> players = new ArrayList<>();

    private final PublicBoard publicBoard;

    protected transient final PrintStream logger;

    protected final int matchId;

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
        this.gamePhase = GAME_PHASE.WAITING_FOR_PLAYERS;
    }

    public int getMatchId() {
        return matchId;
    }

    public void addPlayer(String nickname) {
        // TODO player factory
        Player player = new Player(new PlayerHand<>(),  new InitialCard(new Face(), new Face(), 0), nickname);

        players.add(player);

        updateGamePhase();
    }

    private void updateGamePhase() {
        if(players.size() == requestedNumPlayers){
            this.gamePhase = GAME_PHASE.INITIALIZATION;
        }
    }

    private List<Player> getPlayers(){
        return players;
    }

    public List<String> getNamePlayers(){
        return players.stream().
                map(Player::getNickname).
                toList();
    }

    private Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    private void goToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        currentStep = TURN_STEP.DRAW;
    }

    private boolean isLastRound() {
        int playerScore;
        List<Player> players = this.getPlayers();

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

    private void validateMove(Player player, TURN_STEP move) throws WrongPlayerForCurrentTurnException, WrongStepException, WrongGamePhaseException {
        if (gamePhase != GAME_PHASE.PLAY && gamePhase != GAME_PHASE.LAST_ROUND) throw new WrongGamePhaseException();
        if (player != getCurrentPlayer()) throw new WrongPlayerForCurrentTurnException();
        if (currentStep != move) throw new WrongStepException();
    }

    private void validateInitialChoice(Player player, GAME_PHASE phase, INITIAL_STEP step) throws WrongPlayerForCurrentTurnException, WrongStepException {
        if (this.gamePhase != phase) throw new WrongPlayerForCurrentTurnException();
        if (this.initialStep != step) throw new WrongStepException();
    }

    private ResourceCard drawResourceCard(Player player, PlaceInPublicBoard.Slots slot) throws WrongPlayerForCurrentTurnException, WrongStepException, WrongGamePhaseException {
        validateMove(player, TURN_STEP.DRAW);
        currentStep = TURN_STEP.PLACE;
        return publicBoard.getResource(slot);
    }

    private GoldCard drawGoldCard(Player player, PlaceInPublicBoard.Slots slot) throws WrongPlayerForCurrentTurnException, WrongStepException, WrongGamePhaseException {
        validateMove(player, TURN_STEP.DRAW);
        currentStep = TURN_STEP.PLACE;
        return publicBoard.getGold(slot);
    }

    public void setColor(Player player, PlayerColors color) throws WrongPlayerForCurrentTurnException, WrongStepException {
        validateInitialChoice(player, GAME_PHASE.INITIALIZATION, INITIAL_STEP.FACE_INITIAL);
        //player.setColor(isFaceUp);

        //TODO Implementare il controllo se tutti hanno scelto
        //if(){
        //  initialStep = INITIAL_STEP.QUEST_CARD;
        //}
    }

    public void setFaceInitialCard(Player player, Boolean isFaceUp) throws WrongPlayerForCurrentTurnException, WrongStepException {
        validateInitialChoice(player, GAME_PHASE.INITIALIZATION, INITIAL_STEP.FACE_INITIAL);
        player.setBoard(isFaceUp); //TODO Cambiare e avere un costruttore unico per tutto

        //TODO Implementare il controllo se tutti hanno scelto
        //if(){
        //  initialStep = INITIAL_STEP.QUEST_CARD;
        //}
    }

    public void setQuestCard(Player player, QuestCard questCard) throws WrongPlayerForCurrentTurnException, WrongStepException {
        validateInitialChoice(player, GAME_PHASE.INITIALIZATION, INITIAL_STEP.FACE_INITIAL);
        player.setQuestCard(questCard);

        //TODO Implementare il controllo se tutti hanno scelto
        //if(){
        //  gamePhase = GAME_PHASE.PLAY;
        //}
    }

    public void placeCard(Player player, MixedCard card, Coordinates coordinates, boolean facingUp) throws WrongPlayerForCurrentTurnException, WrongStepException, WrongGamePhaseException {
        validateMove(player, TURN_STEP.PLACE);
        Board board = player.getBoard();
        boolean isAdded = board.add(coordinates, card, facingUp);

        if(isAdded && card.getPlayability(board) > 0){
            board.updatePoints(card.getPoints(board,coordinates)*card.getScore(facingUp));
        }
        else{
            //TODO
        }

        setLastRound();
        goToNextPlayer();
    }

    private Player getWinner(){
        Player winner = null;
        int playerScore, maxScore;

        maxScore = -1;
        List<Player> players = this.getPlayers();

        for (var player : players){
            playerScore = player.getBoard().getScore();

            if (playerScore > maxScore){
                maxScore = playerScore;
                winner = player;
            }
        }

        return winner;
    }


    public Message writeMessage(String message){
        return this.chatController.writeMessage(message);
    }

    public MixedCard drawCard(Player player, String deckType, PlaceInPublicBoard.Slots slot) throws WrongPlayerForCurrentTurnException, WrongStepException, WrongGamePhaseException {
        switch(deckType){
            case "Resource"-> {
                return this.drawResourceCard(player, slot);
            }

            case "Gold" -> {
                return this.drawGoldCard(player, slot);
            }
        }

        return null;
    }
}
