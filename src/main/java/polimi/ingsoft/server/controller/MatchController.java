package polimi.ingsoft.server.controller;

import polimi.ingsoft.server.exceptions.*;
import polimi.ingsoft.server.model.Player;
import polimi.ingsoft.server.enumerations.*;
import polimi.ingsoft.server.factories.PlayerFactory;
import polimi.ingsoft.server.model.*;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

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

    public List<Player> getPlayers(){
        return players;
    }

    public List<String> getNamePlayers(){
        return playerInitialSettings.stream().
                map(PlayerInitialSetting::getNickname).
                toList();
    }

    private Optional<PlayerInitialSetting> getPlayerByNickname(String nickname) {
        return playerInitialSettings.stream()
                .filter(player -> player.getNickname().equals(nickname))
                .findFirst();
    }

    public void addPlayer(String nickname) throws MatchAlreadyFullException {
        if(playerInitialSettings.size() == requestedNumPlayers){
            throw new MatchAlreadyFullException();
        }

        //TODO Retrieve from Public Board 1 initial card, 2 resource cards and 1 gold card
        //     and then change the PlayerInitialSetting constructor
        playerInitialSettings.add(new PlayerInitialSetting(nickname));

        gameState.updateState();
    }

    public void initializePlayers(){
        this.players = this.players.isEmpty() ?
                this.playerInitialSettings.stream()
                        .map(PlayerFactory::createPlayer)
                        .collect(Collectors.toList())
                :
                this.players;
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

    public void setPlayerColor(String playerNickname, PlayerColors color) throws WrongGamePhaseException, WrongStepException, InitalChoiceAlreadySetException {
        gameState.validateInitialChoice(playerNickname, GAME_PHASE.INITIALIZATION, INITIAL_STEP.COLOR);

        Optional<PlayerInitialSetting> playerInitialSetting = this.getPlayerByNickname(playerNickname);

        playerInitialSetting.ifPresent(player-> player.setColor(color));

        gameState.updateInitialStep(playerNickname);
    }

    public void setFaceInitialCard(String playerNickname, Boolean isFaceUp) throws WrongGamePhaseException, WrongStepException, InitalChoiceAlreadySetException {
        gameState.validateInitialChoice(playerNickname, GAME_PHASE.INITIALIZATION, INITIAL_STEP.FACE_INITIAL);

        Optional<PlayerInitialSetting> playerInitialSetting = this.getPlayerByNickname(playerNickname);

        playerInitialSetting.ifPresent(player-> player.setIsInitialFaceUp(isFaceUp));

        gameState.updateInitialStep(playerNickname);
    }

    public void setQuestCard(String playerNickname, QuestCard questCard) throws WrongGamePhaseException, WrongStepException, InitalChoiceAlreadySetException {
        gameState.validateInitialChoice(playerNickname, GAME_PHASE.INITIALIZATION, INITIAL_STEP.FACE_INITIAL);

        Optional<PlayerInitialSetting> playerInitialSetting = this.getPlayerByNickname(playerNickname);

        playerInitialSetting.ifPresent(player-> player.setQuestCard(questCard));

        gameState.updateInitialStep(playerNickname);
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
