package polimi.ingsoft.server.common;

import polimi.ingsoft.client.common.VirtualView;
import polimi.ingsoft.server.common.command.MatchServerCommand;
import polimi.ingsoft.server.controller.MatchController;
import polimi.ingsoft.server.controller.MatchTimer;
import polimi.ingsoft.server.controller.PlayerInitialSetting;
import polimi.ingsoft.server.enumerations.*;
import polimi.ingsoft.server.model.player.PlayerColor;
import polimi.ingsoft.server.exceptions.MatchExceptions.*;
import polimi.ingsoft.server.model.boards.Board;
import polimi.ingsoft.server.model.boards.Coordinates;
import polimi.ingsoft.server.model.boards.PlayedCard;
import polimi.ingsoft.server.model.cards.GoldCard;
import polimi.ingsoft.server.model.cards.MixedCard;
import polimi.ingsoft.server.model.cards.QuestCard;
import polimi.ingsoft.server.model.cards.ResourceCard;
import polimi.ingsoft.server.model.chat.Message;
import polimi.ingsoft.server.model.player.Player;
import polimi.ingsoft.server.model.publicboard.PlaceInPublicBoard;
import polimi.ingsoft.server.exceptions.ExceptionHandler;

import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

public class MatchServer implements VirtualMatchServer {
    private class DrawParams{
        private final TYPE_HAND_CARD deckType;

        private final PlaceInPublicBoard.Slots slot;

        public DrawParams(){
            Random random = new Random();

            this.deckType = (random.nextInt(2) == 0) ? TYPE_HAND_CARD.RESOURCE : TYPE_HAND_CARD.GOLD ;
            this.slot = (random.nextInt(2) == 0) ? PlaceInPublicBoard.Slots.SLOT_A : PlaceInPublicBoard.Slots.SLOT_B ;
        }

        public TYPE_HAND_CARD getDeckType(){
            return this.deckType;
        }

        public PlaceInPublicBoard.Slots getSlot(){
            return this.slot;
        }
    }

    private final PrintStream logger;
    private final MatchController matchController;
    // TODO temp
    private final Server server;

    private final Map<Class<? extends Exception>, ExceptionHandler> exceptionHandlers = new HashMap<>();

    private final MatchTimer matchBlockedTimer = new MatchTimer(this);


    public MatchServer(PrintStream logger, MatchController controller, Server server) {
        this.logger = logger;
        this.matchController = controller;
        this.server = server;

        initExceptionHandlers();
        synchronized (matchController){
            logger.println("Tentativo di apertura match ping");
            if(!matchController.isPingerOn()){
                logger.println("Tentativo di apertura match ping riuscito");
                scheduleTimeoutRoutine();
                matchController.setPingerOn(true);
            }
        }
    }

    private List<VirtualView> getMatchClients(){
        return server.getMatchNotificationClients().get(matchController.getMatchId());
    }

    private void scheduleTimeoutRoutine() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // Send ping to all clients that are playing the game
                //TODO ensure there are no deadlocks with this nested synchronized
                synchronized (server.getClientsInGame()){
                    synchronized (getMatchClients()) {
                        logger.println("CLIENTS IN GAME" + matchController.getMatchId() + ": " + server.getClientsInGame().stream().map(ClientConnection::getNickname).toList());
                        List<ClientConnection> disconnectedClients = new ArrayList<>();

                        for (var client : getMatchClients()) {
                            ClientConnection clientConnection = server.getClientInGame(client);

                            if (!clientConnection.getConnected()) {
                                // Client has disconnected :(
                                logger.println("Il bro " + clientConnection.getNickname() + " si è disconnesso dal game :(");
                                disconnectedClients.add(clientConnection);
                            }
                        }

                        for(var client: disconnectedClients){
                            GAME_PHASE gamePhase = matchController.getGameState().getGamePhase();
                            if(gamePhase == GAME_PHASE.PLAY || gamePhase == GAME_PHASE.LAST_ROUND){
                                server.addDisconnectedClient(client, matchController.getMatchId());
                            }
                            server.removeClientInGame(client);
                            getMatchClients().remove(client.getVirtualView());
                            handlePlayerDisconnection(client.getNickname(), gamePhase);
                        }

                        for (var client : getMatchClients()) {
                            ClientConnection clientConnection = server.getClientInGame(client);

                            clientConnection.setConnected(false);
                            try {
                                clientConnection.getVirtualView().matchPong();
                            } catch (IOException ignore) {
                            }
                        }

                        logger.println("ALL THE PINGS TO THE CLIENTS IN GAME HAVE BEEN SENT");
                    }
                }
            }
        };

        new Timer().scheduleAtFixedRate(task, 1000, 5000);
    }

    @Override
    public void ping(String nickname) throws IOException {
        logger.println("Received MATCH PING");
        logger.println(nickname);
        synchronized (getMatchClients()){
            synchronized (server.getClientsInGame()){
                ClientConnection client = server.getClientInGame(nickname);
                client.setConnected(true);
            }
        }
    }

    @Override
    public void sendMessage(MatchServerCommand command) throws IOException {

    }

    @Override
    public void setColor(String nickname, PlayerColor color) throws IOException {
        VirtualView clientToUpdate;
        synchronized (getMatchClients()){
            clientToUpdate = server.getClientInGame(nickname).getVirtualView();
        }

        try {
            matchController.setPlayerColor(nickname, color);
            PlayerInitialSetting settings = matchController.getPlayerInitialSettingByNickname(nickname).orElse(null);
            this.server.singleUpdateInitialSettings(
                    matchController.getMatchId(),
                    clientToUpdate,
                    settings
            );
            this.server.matchUpdateGameState(
                    matchController.getMatchId(),
                    matchController.getGameState()
            );
        } catch (Exception e){
            handleException(clientToUpdate, e);
        }
    }

    @Override
    public void setIsInitialCardFacingUp(String nickname, Boolean isInitialCardFacingUp) throws IOException {
        VirtualView clientToUpdate;
        synchronized (getMatchClients()){
            clientToUpdate = server.getClientInGame(nickname).getVirtualView();
        }

        try {
            matchController.setFaceInitialCard(nickname, isInitialCardFacingUp);
            PlayerInitialSetting settings = matchController.getPlayerInitialSettingByNickname(nickname).orElse(null);
            this.server.singleUpdateInitialSettings(
                    matchController.getMatchId(),
                    clientToUpdate,
                    settings
            );
            this.server.matchUpdateGameState(
                    matchController.getMatchId(),
                    matchController.getGameState()
            );
        } catch (Exception e){
            handleException(clientToUpdate, e);
        }
    }

    @Override
    public void setQuestCard(String nickname, QuestCard questCard) throws IOException {
        VirtualView clientToUpdate;
        synchronized (getMatchClients()){
            clientToUpdate = server.getClientInGame(nickname).getVirtualView();
        }

        try {
            matchController.setQuestCard(nickname, questCard);
            PlayerInitialSetting settings = matchController.getPlayerInitialSettingByNickname(nickname).orElse(null);
            this.server.singleUpdateInitialSettings(
                    matchController.getMatchId(),
                    clientToUpdate,
                    settings
            );
            if (matchController.getGameState().getGamePhase() == GAME_PHASE.PLAY)
                this.startGameUpdate();

            this.server.matchUpdateGameState(
                    matchController.getMatchId(),
                    matchController.getGameState()
            );
        } catch (Exception e){
            handleException(clientToUpdate, e);
        }
    }

    @Override
    public void sendBroadcastMessage(String player, String message) throws IOException {
        Message messageSent = matchController.writeBroadcastMessage(player, message);

        this.server.matchUpdateBroadcastMessage(
                matchController.getMatchId(),
                messageSent
        );
    }

    @Override
    public void sendPrivateMessage(String player, String recipient, String message) throws IOException {
        VirtualView clientToUpdate;
        synchronized (getMatchClients()){
            clientToUpdate = server.getClientInGame(player).getVirtualView();
        }

        try {
            Message _message = matchController.writePrivateMessage(player, recipient, message);

            this.server.singleUpdatePrivateMessage(matchController.getMatchId(), player, recipient, _message);
            this.server.singleUpdatePrivateMessage(matchController.getMatchId(), recipient, player, _message);
        } catch (Exception e){
            handleException(clientToUpdate, e);
        }
    }

    @Override
    public void drawCard(String nickname, TYPE_HAND_CARD deckType, PlaceInPublicBoard.Slots slot){
        VirtualView clientToUpdate;
        synchronized (getMatchClients()){
            clientToUpdate = server.getClientInGame(nickname).getVirtualView();
        }
        Player player = matchController.getPlayerByNickname(nickname)
                .orElse(null);

        try {
            matchController.drawCard(player, deckType, slot);
            PlaceInPublicBoard<?> publicBoardUpdate = (deckType == TYPE_HAND_CARD.RESOURCE) ?
                    matchController.getPublicBoard().getPublicBoardResource()
                    :
                    matchController.getPublicBoard().getPublicBoardGold();

            this.server.singleUpdatePlayerHand(matchController.getMatchId(), clientToUpdate, player.getHand());
            this.server.matchUpdatePublicBoard(
                    matchController.getMatchId(),
                    deckType,
                    publicBoardUpdate
            );
            this.server.matchUpdateGameState(
                    matchController.getMatchId(),
                    matchController.getGameState()
            );
        } catch (Exception e){
            handleException(clientToUpdate, e);
        }
    }

    @Override
    public void placeCard(String nickname, MixedCard card, Coordinates coordinates, boolean facingUp) throws IOException {
        VirtualView clientToUpdate;
        synchronized (getMatchClients()){
            clientToUpdate = server.getClientInGame(nickname).getVirtualView();
        }

        Player player = matchController.getPlayerByNickname(nickname)
                .orElse(null);

        try {
            matchController.placeCard(player, card, coordinates, facingUp);
            PlayedCard playedCard = player.getBoard().getCard(coordinates);
            Integer score = player.getBoard().getScore();
            this.server.singleUpdatePlayerHand(matchController.getMatchId(), clientToUpdate, player.getHand());
            this.server.matchUpdatePlayerBoard(
                    matchController.getMatchId(),
                    nickname,
                    coordinates,
                    playedCard,
                    score
            );
            this.server.matchUpdateGameState(
                    matchController.getMatchId(),
                    matchController.getGameState()
            );

            if(matchController.getGameState().getGamePhase() == GAME_PHASE.END){
                server.deleteGame(matchController.getMatchId());
            }
        } catch (Exception e){
            handleException(clientToUpdate, e);
        }
    }

    private void startGameUpdate() {
        PlaceInPublicBoard<ResourceCard> resourcePublicBoard = matchController.getPublicBoard().getPublicBoardResource();
        PlaceInPublicBoard<GoldCard> goldPublicBoard = matchController.getPublicBoard().getPublicBoardGold();
        PlaceInPublicBoard<QuestCard> questPublicBoard = matchController.getPublicBoard().getPublicBoardQuest();
        Map<String, Board> playerBoards = matchController.getPlayerBoards();

        this.server.matchUpdateGameStart(
                matchController.getMatchId(),
                resourcePublicBoard,
                goldPublicBoard,
                questPublicBoard,
                playerBoards
        );
    }


    private void handlePlayerDisconnection(String nickname, GAME_PHASE gamePhase) {
        switch (gamePhase) {
            case WAITING_FOR_PLAYERS -> {
                matchController.removeLobbyPlayer(nickname);
                logger.println("Ho rimosso " + nickname + " dalla lobby");
            }

            case INITIALIZATION -> {
                if(matchController.getGameState().isLastPlayerSetting()){
                    matchController.getGameState().updateInitialStep(nickname);

                    if (matchController.getGameState().getGamePhase() == GAME_PHASE.PLAY)
                        this.startGameUpdate();

                    this.server.matchUpdateGameState(
                            matchController.getMatchId(),
                            matchController.getGameState()
                    );
                }

                matchController.removePlayerInitialSetting(nickname);
                logger.println("Ho rimosso " + nickname + " dalla partita in fase di settaggio");
            }

            case PLAY, LAST_ROUND -> {
                handleRejoinState(nickname);
            }
        }
    }


    public void notifyTimerEnd(){
        switch (matchController.getGameState().getBlockedMatchState()){
            case BLOCKED_NO_PLAYERS -> {
                logger.println("TIMER IS EXPIRED. THE GAME HAS BEEN DELETED");
                server.deleteGame(matchController.getMatchId());
            }

            case BLOCKED_ONE_PLAYER -> {
                logger.println("TIMER IS EXPIRED. THE WINNER IS THE ONLY PLAYER REMAINED");
                //notify the win to the player
            }
        }
    }


    private BLOCKED_MATCH_STATE handleBlockedMatch(){
        Integer numOnlinePlayers = matchController.getNumOnlinePlayers();
        switch (numOnlinePlayers){
            case 0 -> {
                logger.println("TIMER HAS BEEN SET. THE GAME IS GOING TO BE DELETED IN x SECONDS");
                matchController.getGameState().setBlockedMatchState(BLOCKED_MATCH_STATE.BLOCKED_NO_PLAYERS);
                matchBlockedTimer.startTimer(10);
            }

            case 1 -> {
                logger.println("TIMER HAS BEEN SET. THE GAME IS GOING TO BE DELETED IN x SECONDS");
                matchController.getGameState().setBlockedMatchState(BLOCKED_MATCH_STATE.BLOCKED_ONE_PLAYER);
                matchBlockedTimer.startTimer(10);
            }

            default -> {
                logger.println("TIMER HAS BEEN STOPPED");
                matchBlockedTimer.stopTimer();
            }
        }

        return matchController.getGameState().getBlockedMatchState();
    }


    private void handleRejoinState(String nickname) {
        REJOIN_STATE rejoinState = matchController.updatePlayerStatus(nickname, true);

        BLOCKED_MATCH_STATE blockedMatchState = handleBlockedMatch();

        if(blockedMatchState.equals(BLOCKED_MATCH_STATE.BLOCKED_NO_PLAYERS)){
            return;
        }

        switch (rejoinState) {
            case HAVE_TO_DRAW -> {
                drawRandomCard(nickname, new DrawParams());
            }

            case HAVE_TO_UPDATE_TURN -> {
                matchController.getGameState().goToNextPlayer();
                this.server.matchUpdateGameState(
                        matchController.getMatchId(),
                        matchController.getGameState()
                );
            }
        }
    }


    private void drawRandomCard(String nickname, DrawParams drawParams){
        TYPE_HAND_CARD deckType = drawParams.getDeckType();
        PlaceInPublicBoard.Slots slot = drawParams.getSlot();

        Player player = matchController.getPlayerByNickname(nickname)
                .orElse(null);

        try {
            matchController.drawCard(player, deckType, slot);
            PlaceInPublicBoard<?> publicBoardUpdate = (deckType == TYPE_HAND_CARD.RESOURCE) ?
                    matchController.getPublicBoard().getPublicBoardResource()
                    :
                    matchController.getPublicBoard().getPublicBoardGold();

            this.server.matchUpdatePublicBoard(
                    matchController.getMatchId(),
                    deckType,
                    publicBoardUpdate
            );
            this.server.matchUpdateGameState(
                    matchController.getMatchId(),
                    matchController.getGameState()
            );
        } catch (Exception ignore){
        }
    }


    private void initExceptionHandlers() {
        exceptionHandlers.put(NullPointerException.class,
                (client, exception) -> server.reportMatchError(matchController.getMatchId(), client, ERROR_MESSAGES.UNKNOWN_ERROR));

        exceptionHandlers.put(WrongGamePhaseException.class,
                (client, exception) -> server.reportMatchError(matchController.getMatchId(), client, ERROR_MESSAGES.WRONG_GAME_PHASE));

        exceptionHandlers.put(MatchBlockedException.class,
                (client, exception) -> server.reportMatchError(matchController.getMatchId(), client, ERROR_MESSAGES.MATCH_IS_BLOCKED));

        exceptionHandlers.put(WrongStepException.class,
                (client, exception) -> server.reportMatchError(matchController.getMatchId(), client, ERROR_MESSAGES.WRONG_STEP));

        exceptionHandlers.put(ColorAlreadyPickedException.class,
                (client, exception) -> server.reportMatchError(matchController.getMatchId(), client, ERROR_MESSAGES.COLOR_ALREADY_PICKED));

        exceptionHandlers.put(InitalChoiceAlreadySetException.class,
                (client, exception) -> server.reportMatchError(matchController.getMatchId(), client, ERROR_MESSAGES.INITIAL_SETTING_ALREADY_SET));

        exceptionHandlers.put(WrongPlayerForCurrentTurnException.class,
                (client, exception) -> server.reportMatchError(matchController.getMatchId(), client, ERROR_MESSAGES.WRONG_PLAYER_TURN));

        exceptionHandlers.put(PlayerNotFoundException.class,
                (client, exception) -> server.reportMatchError(matchController.getMatchId(), client, ERROR_MESSAGES.PLAYER_NOT_FOUND));

        exceptionHandlers.put(CoordinateNotValidException.class,
                (client, exception) -> server.reportMatchError(matchController.getMatchId(), client, ERROR_MESSAGES.COORDINATE_NOT_VALID));

        exceptionHandlers.put(NotEnoughResourcesException.class,
                (client, exception) -> server.reportMatchError(matchController.getMatchId(), client, ERROR_MESSAGES.NOT_ENOUGH_RESOURCES));
    }

    private void handleException(VirtualView clientToUpdate, Exception exception) {
        ExceptionHandler handler = exceptionHandlers.get(exception.getClass());
        if (handler != null) {
            handler.handle(clientToUpdate, exception);
        } else {
            server.reportError(clientToUpdate, ERROR_MESSAGES.UNKNOWN_ERROR);
        }
    }
}
