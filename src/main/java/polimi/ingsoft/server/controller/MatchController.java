package polimi.ingsoft.server.controller;

import polimi.ingsoft.server.Player;
import polimi.ingsoft.server.enumerations.*;
import polimi.ingsoft.server.exceptions.WrongGamePhaseException;
import polimi.ingsoft.server.exceptions.WrongPlayerForCurrentTurnException;
import polimi.ingsoft.server.exceptions.WrongStepException;
import polimi.ingsoft.server.factories.PlayerFactory;
import polimi.ingsoft.server.model.*;

import javax.sound.sampled.BooleanControl;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MatchController implements Serializable {

    private final Integer requestedNumPlayers;

    private final GameState gameState;

    final ChatController chatController;

    private List<Player> players = new ArrayList<>();

    private List<PlayerInitialSetting> playerInitialSettings = new ArrayList<>();

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
        this.gameState = new GameState(this, requestedNumPlayers);
    }

    public int getMatchId() {
        return matchId;
    }

    public void addPlayer(String nickname) {
        if(playerInitialSettings.size() == requestedNumPlayers){
            //TODO Throw match already full
        }

        playerInitialSettings.add(new PlayerInitialSetting(nickname));

        gameState.updateState();
    }

    public void initializePlayers(){
        for (var playerSetting : this.playerInitialSettings){
            Player player = PlayerFactory.createPlayer(playerSetting);

            this.players.add(player);
        }
    }

    public List<Player> getPlayers(){
        return players;
    }

    public List<String> getNamePlayers(){
        return playerInitialSettings.stream().
                map(PlayerInitialSetting::getNickname).
                toList();
    }

    private ResourceCard drawResourceCard(Player player, PlaceInPublicBoard.Slots slot) throws WrongPlayerForCurrentTurnException, WrongStepException, WrongGamePhaseException {
        gameState.validateMove(player, TURN_STEP.DRAW);

        gameState.updateTurnStep();

        return publicBoard.getResource(slot);
    }

    private GoldCard drawGoldCard(Player player, PlaceInPublicBoard.Slots slot) throws WrongPlayerForCurrentTurnException, WrongStepException, WrongGamePhaseException {
        gameState.validateMove(player, TURN_STEP.DRAW);

        gameState.updateTurnStep();

        return publicBoard.getGold(slot);
    }

    public void setPlayerColor(String player, PlayerColors color) throws WrongPlayerForCurrentTurnException, WrongStepException {
        gameState.validateInitialChoice(player, GAME_PHASE.INITIALIZATION, INITIAL_STEP.COLOR);

        //player.setColor(isFaceUp);

        gameState.updateInitialStep();
    }

    public void setFaceInitialCard(String player, Boolean isFaceUp) throws WrongPlayerForCurrentTurnException, WrongStepException {
        gameState.validateInitialChoice(player, GAME_PHASE.INITIALIZATION, INITIAL_STEP.FACE_INITIAL);

        //player.setFaceInitialCard(...)

        gameState.updateInitialStep();
    }

    public void setQuestCard(String player, QuestCard questCard) throws WrongPlayerForCurrentTurnException, WrongStepException {
        gameState.validateInitialChoice(player, GAME_PHASE.INITIALIZATION, INITIAL_STEP.FACE_INITIAL);

        //player.setQuestCard(questCard);

        gameState.updateInitialStep();
    }

    public void placeCard(Player player, MixedCard card, Coordinates coordinates, boolean facingUp) throws WrongPlayerForCurrentTurnException, WrongStepException, WrongGamePhaseException {
        gameState.validateMove(player, TURN_STEP.PLACE);
        Board board = player.getBoard();
        boolean isAdded = board.add(coordinates, card, facingUp);

        if(isAdded && card.getPlayability(board) > 0){
            board.updatePoints(card.getPoints(board,coordinates)*card.getScore(facingUp));
        }
        else{
            //TODO
        }

        gameState.updateState();
        gameState.goToNextPlayer();
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

    public Message writeMessage(String message){
        return this.chatController.writeMessage(message);
    }
}
