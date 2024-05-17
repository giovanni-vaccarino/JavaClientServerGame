package polimi.ingsoft.server.controller;

import polimi.ingsoft.server.exceptions.*;
import polimi.ingsoft.server.factories.PlayerInitialSettingFactory;
import polimi.ingsoft.server.model.Player;
import polimi.ingsoft.server.enumerations.*;
import polimi.ingsoft.server.factories.PlayerFactory;
import polimi.ingsoft.server.model.*;

import java.awt.*;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class MatchController implements Serializable {

    private final Integer requestedNumPlayers;

    private final GameState gameState;

    final ChatController chatController;

    private List<Player> players = new ArrayList<>();

    private final List<PlayerInitialSetting> playerInitialSettings = new ArrayList<>();

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

    public GameState getGameState(){return this.gameState;}

    public List<Player> getPlayers(){
        return players;
    }

    public List<PlayerInitialSetting> getPlayerInitialSettings(){return playerInitialSettings;}

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

        // Retrieving from Public Board 1 initial card, 2 resource, 1 gold card and 2 possible quest cards
        PlayerInitialSetting playerInitialSetting = PlayerInitialSettingFactory.createPlayerInitialSetting(this.publicBoard, nickname);

        playerInitialSettings.add(playerInitialSetting);

        gameState.updateState();
    }

    public void initializePlayers() {
        this.players = this.players.isEmpty() ?
                this.playerInitialSettings.stream()
                        .map(PlayerFactory::createPlayer)
                        .collect(Collectors.toList())
                :
                this.players;
    }

    public void setPlayerColor(String playerNickname, PlayerColors color) throws WrongGamePhaseException, WrongStepException, InitalChoiceAlreadySetException, ColorAlreadyPickedException{
        gameState.checkColorAvailability(color);
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
        gameState.validateInitialChoice(playerNickname, GAME_PHASE.INITIALIZATION, INITIAL_STEP.QUEST_CARD);

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

        //TODO Ensures what should go first(goToNextPlayer or updateState)
        gameState.goToNextPlayer();
        gameState.updateState();
    }

    public MixedCard drawCard(Player player, String deckType, PlaceInPublicBoard.Slots slot) throws WrongPlayerForCurrentTurnException, WrongStepException, WrongGamePhaseException {
        switch(deckType){
            //TODO Change to TYPE_HAND_CARD.RESOURCE and TYPE_HAND_CARD.GOLD when network is updated
            case "Resource"-> {
                return this.drawResourceCard(player, slot);
            }

            case "Gold" -> {
                return this.drawGoldCard(player, slot);
            }
        }

        return null;
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

    public Message writeMessage(String playerSender, String message){
        return this.chatController.writeMessage(playerSender, message);
    }
}
