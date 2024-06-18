package polimi.ingsoft.server.common;

import polimi.ingsoft.client.common.VirtualView;
import polimi.ingsoft.server.controller.GameState;
import polimi.ingsoft.server.controller.MainController;
import polimi.ingsoft.server.controller.MatchController;
import polimi.ingsoft.server.controller.PlayerInitialSetting;
import polimi.ingsoft.server.enumerations.ERROR_MESSAGES;
import polimi.ingsoft.server.enumerations.GAME_PHASE;
import polimi.ingsoft.server.enumerations.TYPE_HAND_CARD;
import polimi.ingsoft.server.exceptions.ExceptionHandler;
import polimi.ingsoft.server.exceptions.MatchExceptions.*;
import polimi.ingsoft.server.exceptions.MatchSelectionExceptions.MatchAlreadyFullException;
import polimi.ingsoft.server.exceptions.MatchSelectionExceptions.MatchNotFoundException;
import polimi.ingsoft.server.exceptions.MatchSelectionExceptions.NicknameNotAvailableException;
import polimi.ingsoft.server.model.boards.Board;
import polimi.ingsoft.server.model.boards.Coordinates;
import polimi.ingsoft.server.model.boards.PlayedCard;
import polimi.ingsoft.server.model.cards.GoldCard;
import polimi.ingsoft.server.model.cards.QuestCard;
import polimi.ingsoft.server.model.cards.ResourceCard;
import polimi.ingsoft.server.model.chat.Message;
import polimi.ingsoft.server.model.decks.PlayerHand;
import polimi.ingsoft.server.model.publicboard.PlaceInPublicBoard;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Server implements VirtualServer {
    protected final PrintStream logger;

    protected final MainController controller;

    private final Map<Class<? extends Exception>, ExceptionHandler> exceptionHandlers = new HashMap<>();

    public Server(PrintStream logger, MainController controller) {
        this.logger = logger;
        this.controller = controller;
        initExceptionHandlers();
    }

    protected abstract VirtualMatchServer createMatchServer(MatchController matchController, List<VirtualView> clients, PrintStream logger);

    protected abstract Map<Integer, VirtualMatchServer> getMatchServers();

    protected List<ClientConnection> getClients(){
        return ConnectionManager.getInstance().getClients();
    }

    protected ClientConnection getClient(String nickname){
        return ConnectionManager.getInstance().getClient(nickname);
    }

    protected Boolean isNicknameAvailable(String nickname){
        return ConnectionManager.getInstance().isNicknameAvailable(nickname);
    }

    protected Map<Integer, List<VirtualView>> getMatchNotificationClients(){
        return ConnectionManager.getInstance().getMatchNotificationList();
    }

    @Override
    public void connect(VirtualView client) throws IOException {
        String stub = Utils.getRandomNickname();
        logger.println("SERVER: generated stub " + stub);

        synchronized (getClients()) {
            getClients().add(new ClientConnection(client, stub));
        }

        client.showConnectUpdate(stub);
    }

    @Override
    public void setNickname(String nickname, String stub) throws IOException {
        logger.println("SERVER: " + nickname + " " + stub);
        VirtualView clientToUpdate = getClient(nickname).getVirtualView();

        try {
            setNicknameForClient(clientToUpdate, nickname);
            singleUpdateNickname(clientToUpdate);
        } catch (Exception e){
            handleException(clientToUpdate, e);
        }
    }

    @Override
    public void getMatches(String nickname) throws IOException {
        VirtualView clientToUpdate = getClient(nickname).getVirtualView();
        List<Integer> matches = controller.getMatches();
        singleUpdateMatchesList(clientToUpdate, matches);
    }

    @Override
    public void createMatch(String playerNickname, Integer requiredNumPlayers) throws IOException {
        VirtualView clientToUpdate = getClient(playerNickname).getVirtualView();

        int id = controller.createMatch(requiredNumPlayers);
        singleUpdateMatchCreate(clientToUpdate, id);
        List<Integer> matches = controller.getMatches();
        broadcastUpdateMatchesList(matches);

        getMatchServers().put(id, createMatchServer(controller.getMatch(id), getMatchNotificationClients().get(id), logger));

        // Creating a new list for the players
        synchronized (getMatchNotificationClients()){
            getMatchNotificationClients().put(id, new ArrayList<>());
        }
        // It is client's responsibility to join the match right after
    }

    @Override
    public void joinMatch(String playerNickname, Integer matchId) throws IOException {
        VirtualView clientToUpdate = getClient(playerNickname).getVirtualView();

        try {
            controller.joinMatch(matchId, playerNickname);
            singleUpdateMatchJoin(clientToUpdate);

            MatchController matchController = controller.getMatch(matchId);
            GameState gameState = matchController.getGameState();
            //Adding the client to the match notification list
            synchronized (getMatchNotificationClients()){
                getMatchNotificationClients().get(matchId).add(clientToUpdate);
            }

            VirtualMatchServer matchServer;
            if (getMatchServers().containsKey(matchId)) {
                matchServer = getMatchServers().get(matchId);
            } else {
                // Match was created by a client using a protocol different from the one used by this client
                matchServer = createMatchServer(controller.getMatch(matchId), getMatchNotificationClients().get(matchId), logger);
            }

            clientToUpdate.setMatchServer(matchServer);

            List<String> nicknames = matchController.getNamePlayers();
            lobbyUpdatePlayerJoin(nicknames);
            if (gameState.getGamePhase() == GAME_PHASE.INITIALIZATION) {
                matchUpdateGameState(
                        matchController.getMatchId(),
                        gameState
                );
            }
        } catch (Exception e){
            handleException(clientToUpdate, e);
        }
    }

    @Override
    public void reJoinMatch(Integer matchId, String nickname) throws IOException {

    }

    protected void setNicknameForClient(VirtualView client, String nickname) throws NicknameNotAvailableException {
        synchronized (getClients()) {
            if (isNicknameAvailable(nickname)) {
                throw new NicknameNotAvailableException();
            }

            //Removing the precedent (key, value) of the client
            getClients().stream()
                    .filter(entry -> entry.getVirtualView().equals(client))
                    .findFirst()
                    .ifPresent(getClients()::remove);

            getClients().add(new ClientConnection(client, nickname));
        }
    }

    protected void singleUpdateNickname(VirtualView client) {
        synchronized (getClients()) {
            try {
                client.showNicknameUpdate();
            } catch (IOException ignore) { }
        }
    }

    protected void singleUpdateMatchesList(VirtualView client, List<Integer> matches) {
        synchronized (getClients()) {
            try {
                client.showUpdateMatchesList(matches);
            } catch (IOException ignored) { }
        }
    }

    protected void broadcastUpdateMatchesList(List<Integer> matches) throws IOException {
        synchronized (getClients()) {
            for (var client : getClients()) {
                client.getVirtualView().showUpdateMatchesList(matches);
            }
        }
    }

    protected void singleUpdateMatchCreate(VirtualView client, Integer matchId) throws IOException {
        synchronized (getClients()) {
            client.showUpdateMatchCreate(matchId);
        }
    }

    protected void singleUpdateMatchJoin(VirtualView client) {
        synchronized (getClients()) {
            try {
                client.showUpdateMatchJoin();
            } catch (IOException ignored) { }
        }
    }

    protected void lobbyUpdatePlayerJoin(List<String> nicknames) {
        List<VirtualView> clientsToNotify = new ArrayList<>();

        for (var nickname : nicknames) {
            clientsToNotify.add(getClient(nickname).getVirtualView());
        }

        synchronized (getClients()) {
            for (var client : clientsToNotify) {
                try {
                    client.showUpdateLobbyPlayers(nicknames);
                } catch (IOException ignored) { }
            }
        }
    }

    public void singleUpdateInitialSettings(Integer matchId, VirtualView client, PlayerInitialSetting playerInitialSetting) {
        List<VirtualView> clientsToNotify = getMatchNotificationClients().get(matchId);

        synchronized (clientsToNotify) {
            try {
                client.showUpdateInitialSettings(playerInitialSetting);
            } catch (IOException ignored) { }
        }
    }

    public void matchUpdateGameState(Integer matchId, GameState gameState) {
        List<VirtualView> clientsToNotify = getMatchNotificationClients().get(matchId);

        synchronized (clientsToNotify) {
            for (var client : clientsToNotify) {
                try {
                    client.showUpdateGameState(gameState);
                } catch (IOException ignored) { }
            }
        }
    }

    public void singleUpdatePlayerHand(Integer matchId, VirtualView client, PlayerHand playerHand) {
        List<VirtualView> clientsToNotify = getMatchNotificationClients().get(matchId);

        synchronized (clientsToNotify) {
            try {
                client.showUpdatePlayerHand(playerHand);
            } catch (IOException ignored) { }
        }
    }

    public void matchUpdatePublicBoard(Integer matchId, TYPE_HAND_CARD deckType, PlaceInPublicBoard<?> publicBoardUpdate) {
        List<VirtualView> clientsToNotify = getMatchNotificationClients().get(matchId);

        synchronized (clientsToNotify) {
            for (var client : clientsToNotify) {
                try {
                    client.showUpdatePublicBoard(deckType, publicBoardUpdate);
                } catch (IOException ignored) { }
            }
        }
    }

    public void matchUpdatePlayerBoard(Integer matchId, String nickname, Coordinates coordinates, PlayedCard playedCard, Integer score) {
        List<VirtualView> clientsToNotify = getMatchNotificationClients().get(matchId);

        synchronized (clientsToNotify) {
            for (var client : clientsToNotify) {
                try {
                    // TODO implement score
                    client.showUpdateBoard(nickname, coordinates, playedCard, score);
                } catch (IOException ignored) { }
            }
        }
    }

    public void matchUpdateBroadcastMessage(Integer matchId, Message message) {
        List<VirtualView> clientsToNotify = getMatchNotificationClients().get(matchId);

        synchronized (clientsToNotify) {
            for (var client : clientsToNotify) {
                try {
                    client.showUpdateBroadcastChat(message);
                } catch (IOException ignored) { }
            }
        }
    }

    public void singleUpdatePrivateMessage(Integer matchId, String nickname, String recipient, Message message) {
        List<VirtualView> clientsToNotify = getMatchNotificationClients().get(matchId);
        VirtualView client = getClient(nickname).getVirtualView();

        synchronized (clientsToNotify) {
            try {
                client.showUpdatePrivateChat(recipient, message);
            } catch (IOException ignored) { }
        }
    }

    public void matchUpdateGameStart(
            Integer matchId,
            PlaceInPublicBoard<ResourceCard> resource,
            PlaceInPublicBoard<GoldCard> gold,
            PlaceInPublicBoard<QuestCard> quest,
            Map<String, Board> boards
    ) {
        List<VirtualView> clientsToNotify = getMatchNotificationClients().get(matchId);

        synchronized (clientsToNotify) {
            for (var client : clientsToNotify) {
                try {
                    client.showUpdateGameStart(resource, gold, quest, boards);
                } catch (IOException ignored) { }
            }
        }
    }

    public void reportError(VirtualView client, ERROR_MESSAGES error) {
        synchronized (getClients()) {
            try {
                client.reportError(error);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void reportMatchError(Integer matchId, VirtualView client, ERROR_MESSAGES error) {
        List<VirtualView> clientsToNotify = getMatchNotificationClients().get(matchId);

        synchronized (clientsToNotify) {
            try {
                client.reportError(error);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected void deleteGame(Integer matchId){
        controller.deleteMatch(matchId);

        synchronized (getMatchNotificationClients()){
            getMatchNotificationClients().remove(matchId);
        }
    }

    private void initExceptionHandlers() {
        exceptionHandlers.put(NullPointerException.class,
                (client, exception) -> reportError(client, ERROR_MESSAGES.UNKNOWN_ERROR));

        exceptionHandlers.put(NicknameNotAvailableException.class,
                (client, exception) -> reportError(client, ERROR_MESSAGES.NICKNAME_NOT_AVAILABLE));

        exceptionHandlers.put(MatchAlreadyFullException.class,
                (client, exception) -> reportError(client, ERROR_MESSAGES.MATCH_IS_ALREADY_FULL));

        exceptionHandlers.put(MatchNotFoundException.class,
                (client, exception) -> reportError(client, ERROR_MESSAGES.MATCH_DOES_NOT_EXIST));
    }

    private void handleException(VirtualView clientToUpdate, Exception exception) {
        ExceptionHandler handler = exceptionHandlers.get(exception.getClass());
        if (handler != null) {
            handler.handle(clientToUpdate, exception);
        } else {
           reportError(clientToUpdate, ERROR_MESSAGES.UNKNOWN_ERROR);
        }
    }
}
