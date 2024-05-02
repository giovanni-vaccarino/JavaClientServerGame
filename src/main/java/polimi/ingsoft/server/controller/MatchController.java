package polimi.ingsoft.server.controller;

import polimi.ingsoft.server.Player;
import polimi.ingsoft.server.exceptions.WrongPlayerForCurrentTurnException;
import polimi.ingsoft.server.exceptions.WrongStepException;
import polimi.ingsoft.server.model.*;

import java.io.PrintStream;
import java.util.ArrayList;

public class MatchController implements Serializable {
    private enum TURN_STEP {
        DRAW, PLACE
    }

    private final Integer requestedNumPlayers;

    private TURN_STEP currentStep;

    private GamePhase gamePhase;
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
        this.gamePhase = GamePhase.PRESTART;
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
            this.gamePhase = GamePhase.PLAY;
        }
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

    private void validateMove(Player player, TurnPhase move) throws WrongPlayerForCurrentTurnException, WrongStepException {
        if (gamePhase != GamePhase.PLAY) throw new WrongStepException();//TODO Add exception for wrong game phase
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

    private QuestCard drawQuestCard(Player player, PlaceInPublicBoard.Slots slot) throws WrongPlayerForCurrentTurnException, WrongStepException {
        validateMove(player, TURN_STEP.DRAW);
        currentStep = TURN_STEP.PLACE;
        return publicBoard.getQuest(slot);
    }

    private void place(Player player, MixedCard card, Coordinates coordinates, boolean facingUp) throws WrongPlayerForCurrentTurnException, WrongStepException {
        validateMove(player, TURN_STEP.PLACE);
        player.getBoard().add(coordinates, card, facingUp);
        goToNextPlayer();
    }

    public Message writeMessage(String message){
        return this.chatController.writeMessage(message);
    }

    public void drawCard(Player player, String deckType, PlaceInPublicBoard.Slots slot){
        // Ensure that the player sending the request is the right player and that it's draw phase

        //adding to the playerhand the card drawed

    }

    public MixedCard drawCard(String deckType, PlaceInPublicBoard.Slots slot){
        switch(deckType){
            case "Resource" -> {
                return this.publicBoard.getResource(slot);
            }
            case "Gold" -> {
                return this.publicBoard.getGold(slot);
            }
        }

        return null;
    }
}
