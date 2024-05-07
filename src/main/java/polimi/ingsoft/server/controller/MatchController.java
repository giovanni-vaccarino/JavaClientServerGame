package polimi.ingsoft.server.controller;

import polimi.ingsoft.server.Player;
import polimi.ingsoft.server.enumerations.GAME_PHASE;
import polimi.ingsoft.server.enumerations.Resource;
import polimi.ingsoft.server.enumerations.TURN_STEP;
import polimi.ingsoft.server.exceptions.WrongPlayerForCurrentTurnException;
import polimi.ingsoft.server.exceptions.WrongStepException;
import polimi.ingsoft.server.model.*;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MatchController implements Serializable {

    private final Integer requestedNumPlayers;

    private TURN_STEP currentStep;

    private GAME_PHASE gamePhase;
    private int currentPlayerIndex;

    final ChatController chatController;
    private ArrayList<Player> players = new ArrayList<>();

    private final PublicBoard publicBoard;

    protected transient final PrintStream logger;

    protected final int matchId;

    public MatchController(PrintStream logger,
                           int matchId,
                           int requestedNumPlayers,
                           PublicBoard publicBoard,
                           ChatController chatController
    ) {
        // Aggiungere validazione stato partita a validateMove (inizio, durante, fine partita)
        this.requestedNumPlayers = requestedNumPlayers;
        this.chatController = chatController;
        this.logger = logger;
        this.matchId = matchId;
        this.publicBoard = publicBoard;
        this.gamePhase = GAME_PHASE.PRESTART;
    }

    public int getMatchId() {
        return matchId;
    }

    public void addPlayer(String nickname) {
        // TODO player factory
        // Separare creazione player da inizializzazione player hand
        Player player = new Player(new PlayerHand<>(), nickname);
        players.add(player);

        if(players.size() == requestedNumPlayers){
            this.gamePhase = GAME_PHASE.PLAY;
        }
    }

    public List<Player> getPlayers(){
        return players;
    }

    private Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    private void goToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        currentStep = TURN_STEP.DRAW;
    }

    private boolean isLastRound() {
        return false;
    }

    private void validateMove(Player player, TURN_STEP move) throws WrongPlayerForCurrentTurnException, WrongStepException {
        if (gamePhase != GAME_PHASE.PLAY) throw new WrongStepException();//TODO Add exception for wrong game phase
        if (player != getCurrentPlayer()) throw new WrongPlayerForCurrentTurnException();
        if (currentStep != move) throw new WrongStepException();
    }

    private ResourceCard drawResourceCard(Player player, PlaceInPublicBoard.Slots slot) throws WrongPlayerForCurrentTurnException, WrongStepException {
        validateMove(player, TURN_STEP.DRAW);
        currentStep = TURN_STEP.PLACE;
        return publicBoard.getResource(slot);
    }

    private GoldCard drawGoldCard(Player player, PlaceInPublicBoard.Slots slot) throws WrongPlayerForCurrentTurnException, WrongStepException {
        validateMove(player, TURN_STEP.DRAW);
        currentStep = TURN_STEP.PLACE;
        return publicBoard.getGold(slot);
    }

    public void placeCard(Player player, MixedCard card, Coordinates coordinates, boolean facingUp) throws WrongPlayerForCurrentTurnException, WrongStepException {
        validateMove(player, TURN_STEP.PLACE);
        Board board=player.getBoard();
        Boolean isAdded = board.add(coordinates, card, facingUp);
        if(isAdded && card.getPlayability(board)>0)board.updatePoints(card.getPoints(board,coordinates)*card.getScore(facingUp));
        else //TODO
        goToNextPlayer();
    }


    public Message writeMessage(String message){
        return this.chatController.writeMessage(message);
    }

    public MixedCard drawCard(Player player, String deckType, PlaceInPublicBoard.Slots slot) throws WrongPlayerForCurrentTurnException, WrongStepException {
        switch(deckType){
            case "Resource":
                return this.drawResourceCard(player, slot);

            case "Gold":
                return this.drawGoldCard(player, slot);
        }

        return null;
    }
}
