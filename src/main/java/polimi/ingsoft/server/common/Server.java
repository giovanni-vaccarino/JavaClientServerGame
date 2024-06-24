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
import polimi.ingsoft.server.model.player.Player;
import polimi.ingsoft.server.model.publicboard.PlaceInPublicBoard;

import java.io.IOException;
import java.io.PrintStream;
import java.lang.ref.Cleaner;
import java.util.*;

public abstract class Server implements VirtualServer {
    protected final PrintStream logger;

    protected final MainController controller;

    private final Map<Class<? extends Exception>, ExceptionHandler> exceptionHandlers = new HashMap<>();

    public Server(PrintStream logger, MainController controller) {
        this.logger = logger;
        this.controller = controller;
        initExceptionHandlers();
    }

    //TODO handle this calls to connection manager in another way

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

    protected void addClientConnection(ClientConnection clientConnection){
        ConnectionManager.getInstance().addClientConnection(clientConnection);
    }

    protected void removeClientConnection(ClientConnection clientConnection){
        ConnectionManager.getInstance().removeClientFromMainServer(clientConnection);
    }

    protected List<ClientConnection> getClientsInGame(){
        return ConnectionManager.getInstance().getClientsInGame();
    }

    protected ClientConnection getClientInGame(VirtualView virtualView){
        return ConnectionManager.getInstance().getClientInGame(virtualView);
    }

    protected ClientConnection getClientInGame(String nickname){
        return ConnectionManager.getInstance().getClientInGame(nickname);
    }

    protected void addClientInGame(ClientConnection clientConnection){
        ConnectionManager.getInstance().addClientInGame(clientConnection);
    }

    protected void removeClientInGame(ClientConnection clientConnection){
        ConnectionManager.getInstance().removeClientInGame(clientConnection);
    }

    protected void addDisconnectedClient(ClientConnection clientConnection, Integer matchId){
        ConnectionManager.getInstance().addDisconnectedClient(clientConnection, matchId);
    }

    protected void removeDisconnectedClient(String nickname){
        ConnectionManager.getInstance().removeDisconnectedClient(nickname);
    }

    protected Map<ClientConnection, Integer> getDisconnectedClients(){
        return ConnectionManager.getInstance().getDisconnectedClients();
    }

    protected ClientConnection getDisconnectedClient(String nickname){
        return ConnectionManager.getInstance().getDisconnectedClient(nickname);
    }

    protected Boolean isPlayerDisconnected(String nickname){
        return ConnectionManager.getInstance().isPlayerDisconnected(nickname);
    }

    @Override
    public void connect(VirtualView client) throws IOException {
        String stub = Utils.getRandomNickname();
        logger.println("SERVER: generated stub " + stub);

        synchronized (getClients()) {
            logger.println("aggiunto correttamente stub: " + stub);
            addClientConnection(new ClientConnection(client, stub));
        }

        client.showConnectUpdate(stub);
    }


    @Override
    public void setNickname(String nickname, String stub) throws IOException {
        logger.println("SERVER: " + nickname + " " + stub);
        VirtualView clientToUpdate;
        synchronized (getClients()){
            clientToUpdate = getClient(stub).getVirtualView();
        }

        try {
            //TODO refactor
            if(isPlayerAlreadyInGame(nickname)){
                Integer matchId = handleReconnection(nickname, stub);
                singleUpdateNickname(clientToUpdate);
                reJoinUpdates(nickname, matchId);
            }else{
                setNicknameForClient(clientToUpdate, nickname);
                singleUpdateNickname(clientToUpdate);
            }
        } catch (Exception e){
            handleException(clientToUpdate, e);
        }
    }

    @Override
    public void getMatches(String nickname) throws IOException {
        VirtualView clientToUpdate;
        synchronized (getClients()){
            clientToUpdate = getClient(nickname).getVirtualView();
        }
        List<Integer> matches = controller.getMatches();
        singleUpdateMatchesList(clientToUpdate, matches);
    }

    @Override
    public void createMatch(String playerNickname, Integer requiredNumPlayers) throws IOException {
        VirtualView clientToUpdate;
        synchronized (getClients()){
            clientToUpdate = getClient(playerNickname).getVirtualView();
        }
        int id = controller.createMatch(requiredNumPlayers);
        singleUpdateMatchCreate(clientToUpdate, id);
        List<Integer> matches = controller.getMatches();
        broadcastUpdateMatchesList(matches);

        // Creating a new list for the players
        synchronized (getMatchNotificationClients()){
            getMatchNotificationClients().put(id, new ArrayList<>());
        }

        getMatchServers().put(id, createMatchServer(controller.getMatch(id), getMatchNotificationClients().get(id), logger));

        // It is client's responsibility to join the match right after
    }

    @Override
    public void joinMatch(String playerNickname, Integer matchId) throws IOException {
        VirtualView clientToUpdate;
        synchronized (getClients()){
            clientToUpdate = getClient(playerNickname).getVirtualView();
        }

        try {
            controller.joinMatch(matchId, playerNickname);
            singleUpdateMatchJoin(clientToUpdate);

            MatchController matchController = controller.getMatch(matchId);
            GameState gameState = matchController.getGameState();

            //Adding the client to the match notification list
            synchronized (getMatchNotificationClients()){
                getMatchNotificationClients().get(matchId).add(clientToUpdate);
            }

            //Removing the client connection from the clients list, and moving it to the in game list
            synchronized (getClients()){
                synchronized (getClientsInGame()) {
                    addClientInGame(getClient(playerNickname));

                    removeClientConnection(getClient(playerNickname));
                    logger.println("Moved " + playerNickname + " to the in game list");
                }
            }

            VirtualMatchServer matchServer;
            if (getMatchServers().containsKey(matchId)) {
                matchServer = getMatchServers().get(matchId);
            } else {
                // Match was created by a client using a protocol different from the one used by this client
                matchServer = createMatchServer(controller.getMatch(matchId), getMatchNotificationClients().get(matchId), logger);
            }

            clientToUpdate.setMatchServer(matchServer);

            //List<String> nicknames = matchController.getNamePlayers();
            //lobbyUpdatePlayerJoin(nicknames);

            if (gameState.getGamePhase() == GAME_PHASE.INITIALIZATION) {
                logger.println("SENDING START GAME UPDATE");
                matchUpdateGameState(
                        matchController.getMatchId(),
                        gameState
                );
                logger.println("FINISHED SENDING START GAME UPDATE");
            }
        } catch (Exception e){
            handleException(clientToUpdate, e);
        }
    }

    @Override
    public void reJoinMatch(Integer matchId, String nickname) throws IOException {

    }

    @Override
    public void ping(String nickname) throws IOException {
        logger.println("Received PING");
        logger.println(nickname);
        synchronized (getClients()){
            ClientConnection client = getClient(nickname);
            client.setConnected(true);
        }
    }

    protected void setNicknameForClient(VirtualView client, String nickname) throws NicknameNotAvailableException {
        synchronized (getClients()) {
            synchronized (getClientsInGame()){
                if (isNicknameAvailable(nickname)) {
                    throw new NicknameNotAvailableException();
                }
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
        VirtualView client = getClientInGame(nickname).getVirtualView();

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

    public void reJoinMatchUpdate(
            String nickname,
            PlayerInitialSetting playerInitialSetting,
            GameState gameState,
            PlaceInPublicBoard<ResourceCard> resource,
            PlaceInPublicBoard<GoldCard> gold,
            PlaceInPublicBoard<QuestCard> quest,
            Map<String, Board> boards,
            PlayerHand playerHand
    ) {
        synchronized (getClientsInGame()) {
            VirtualView client = getClientInGame(nickname).getVirtualView();

            try {
                client.showUpdateRejoinMatch(playerInitialSetting, gameState, resource, gold, quest, boards, playerHand);
            } catch (IOException ignored) { }
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
        List<String> players = controller.getMatch(matchId).getNamePlayers();
        controller.deleteMatch(matchId);

        synchronized (getMatchNotificationClients()){
            getMatchNotificationClients().remove(matchId);
        }

        //Removing the client connections from the in game list, and moving it to the client list
        synchronized (getClients()){
            synchronized (getClientsInGame()) {
                for(var playerNickname : players){
                    addClientConnection(getClient(playerNickname));
                    removeClientInGame(getClient(playerNickname));

                    logger.println("Moving back " + playerNickname + " to the client list");
                }
            }
        }

        //Removing the disconnected clients associated to that game
        synchronized (getDisconnectedClients()){
            for(var playerNickname : players){
                getDisconnectedClients().keySet().stream()
                        .filter(clientConnection -> Objects.equals(clientConnection.getNickname(), playerNickname))
                        .findFirst()
                        .ifPresent(getDisconnectedClients()::remove);
            }
        }
    }


    private Boolean isPlayerAlreadyInGame(String nickname){
        synchronized (getDisconnectedClients()){
            return isPlayerDisconnected(nickname);
        }
    }


    private Integer handleReconnection(String nickname, String stub){
        VirtualView virtualView;
        Integer matchId;

        synchronized (getDisconnectedClients()){
            matchId = getDisconnectedClients().get(getDisconnectedClient(nickname));
            removeDisconnectedClient(nickname);
        }

        synchronized (getClients()){
            virtualView = getClient(stub).getVirtualView();
        }

        synchronized (getClientsInGame()){
            addClientInGame(new ClientConnection(virtualView, nickname));
        }

        synchronized (getMatchNotificationClients()){
            getMatchNotificationClients().get(matchId).add(virtualView);
        }
        logger.println(nickname + " has reconnected to the Game " + matchId);

        return matchId;
    }


    private void reJoinUpdates(String nickname, Integer matchId){
        MatchController matchController = controller.getMatch(matchId);
        Player player = matchController.getPlayerByNickname(nickname)
                .orElse(null);
        PlayerInitialSetting playerInitialSetting = matchController.getPlayerInitialSettingByNickname(nickname).orElse(null);

        synchronized (getMatchNotificationClients().get(matchId)){
            matchController.updatePlayerStatus(nickname, false);
        }

        GameState gameState = matchController.getGameState();
        PlaceInPublicBoard<ResourceCard> resourcePublicBoard = matchController.getPublicBoard().getPublicBoardResource();
        PlaceInPublicBoard<GoldCard> goldPublicBoard = matchController.getPublicBoard().getPublicBoardGold();
        PlaceInPublicBoard<QuestCard> questPublicBoard = matchController.getPublicBoard().getPublicBoardQuest();
        Map<String, Board> playerBoards = matchController.getPlayerBoards();
        PlayerHand playerHand = player.getHand();

        //retrieve chats
        reJoinMatchUpdate(
                nickname,
                playerInitialSetting,
                gameState,
                resourcePublicBoard,
                goldPublicBoard,
                questPublicBoard,
                playerBoards,
                playerHand
        );
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
