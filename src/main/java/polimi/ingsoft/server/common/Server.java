package polimi.ingsoft.server.common;

import polimi.ingsoft.client.common.VirtualView;
import polimi.ingsoft.server.common.Utils.MatchData;
import polimi.ingsoft.server.common.Utils.Utils;
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

    /**
     * Creates a new match server for the given match controller.
     *
     * @param matchController the match controller
     * @param clients the list of clients
     * @param logger the PrintStream for logging
     * @return the created VirtualMatchServer
     */
    protected abstract VirtualMatchServer createMatchServer(MatchController matchController, List<VirtualView> clients, PrintStream logger);


    /**
     * Gets a map of match servers.
     *
     * @return the map of match servers
     */
    protected abstract Map<Integer, VirtualMatchServer> getMatchServers();


    /**
     * Gets the list of clients connected to the server(not in game).
     *
     * @return the list of clients
     */
    protected List<ClientConnection> getClients(){
        return ConnectionManager.getInstance().getClients();
    }


    /**
     * Gets a client connection by nickname.
     *
     * @param nickname the nickname of the client
     * @return the client connection
     */
    protected ClientConnection getClient(String nickname){
        return ConnectionManager.getInstance().getClient(nickname);
    }


    /**
     * Checks if a nickname is available.
     *
     * @param nickname the nickname to check
     * @return true if the nickname is available, false otherwise
     */
    protected Boolean isNicknameAvailable(String nickname){
        return ConnectionManager.getInstance().isNicknameAvailable(nickname);
    }


    /**
     * Gets a map of match notification clients.
     *
     * @return the map of match notification clients
     */
    protected Map<Integer, List<VirtualView>> getMatchNotificationClients(){
        return ConnectionManager.getInstance().getMatchNotificationList();
    }


    /**
     * Adds a client connection to the server.
     *
     * @param clientConnection the client connection to add
     */
    protected void addClientConnection(ClientConnection clientConnection){
        ConnectionManager.getInstance().addClientConnection(clientConnection);
    }


    /**
     * Removes a client connection from the server.
     *
     * @param clientConnection the client connection to remove
     */
    protected void removeClientConnection(ClientConnection clientConnection){
        ConnectionManager.getInstance().removeClientFromMainServer(clientConnection);
    }


    /**
     * Gets the list of clients currently in a game.
     *
     * @return the list of clients in game
     */
    protected List<ClientConnection> getClientsInGame(){
        return ConnectionManager.getInstance().getClientsInGame();
    }


    /**
     * Gets a client in game by their virtual view.
     *
     * @param virtualView the virtual view of the client
     * @return the client connection
     */
    protected ClientConnection getClientInGame(VirtualView virtualView){
        return ConnectionManager.getInstance().getClientInGame(virtualView);
    }


    /**
     * Gets a client in game by their nickname.
     *
     * @param nickname the nickname of the client
     * @return the client connection
     */
    protected ClientConnection getClientInGame(String nickname){
        return ConnectionManager.getInstance().getClientInGame(nickname);
    }


    /**
     * Adds a client to the in-game client list.
     *
     * @param clientConnection the client connection to add
     */
    protected void addClientInGame(ClientConnection clientConnection){
        ConnectionManager.getInstance().addClientInGame(clientConnection);
    }


    /**
     * Removes a client from the in-game client list.
     *
     * @param clientConnection the client connection to remove
     */
    protected void removeClientInGame(ClientConnection clientConnection){
        ConnectionManager.getInstance().removeClientInGame(clientConnection);
    }


    /**
     * Adds a disconnected client to the list of disconnected clients for a specific match.
     *
     * @param clientConnection the client connection to add
     * @param matchId the ID of the match
     */
    protected void addDisconnectedClient(ClientConnection clientConnection, Integer matchId){
        ConnectionManager.getInstance().addDisconnectedClient(clientConnection, matchId);
    }


    /**
     * Removes a disconnected client by their nickname.
     *
     * @param nickname the nickname of the client
     */
    protected void removeDisconnectedClient(String nickname){
        ConnectionManager.getInstance().removeDisconnectedClient(nickname);
    }


    /**
     * Gets a map of disconnected clients and their match IDs.
     *
     * @return the map of disconnected clients
     */
    protected Map<ClientConnection, Integer> getDisconnectedClients(){
        return ConnectionManager.getInstance().getDisconnectedClients();
    }


    /**
     * Gets a disconnected client by their nickname.
     *
     * @param nickname the nickname of the client
     * @return the disconnected client connection
     */
    protected ClientConnection getDisconnectedClient(String nickname){
        return ConnectionManager.getInstance().getDisconnectedClient(nickname);
    }


    /**
     * Checks if a player is disconnected based on their nickname.
     *
     * @param nickname the nickname of the player
     * @return true if the player is disconnected, false otherwise
     */
    protected Boolean isPlayerDisconnected(String nickname){
        return ConnectionManager.getInstance().isPlayerDisconnected(nickname);
    }

    @Override
    public void connect(VirtualView client) throws IOException {
        String stub = Utils.getRandomNickname();
        logger.println("SERVER: generated stub " + stub);

        synchronized (getClients()) {
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
        VirtualView clientToUpdate = null;
        synchronized (getClients()){
            if(getClient(nickname) != null){
                clientToUpdate = getClient(nickname).getVirtualView();
            }else{
                return;
            }
        }

        List<MatchData> matches = controller.getMatches();
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
        List<MatchData> matches = controller.getMatches();
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
    public void ping(String nickname) throws IOException {
        logger.println(nickname);

        synchronized (getClientsInGame()){
            ClientConnection clientConnection = getClientInGame(nickname);

            if(clientConnection != null) {
                clientConnection.setConnected(true);
                return;
            }
        }

        synchronized (getClients()){
            ClientConnection client = getClient(nickname);
            if(client != null)
                client.setConnected(true);
        }
    }


    /**
     * Sets the nickname for a client.
     *
     * @param client the virtual view of the client
     * @param nickname the new nickname to be set
     * @throws NicknameNotAvailableException if the nickname is already in use
     */
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



    /**
     * Sends a nickname update to a single client.
     *
     * @param client the virtual view of the client to update
     */
    protected void singleUpdateNickname(VirtualView client) {
        synchronized (getClients()) {
            try {
                client.showNicknameUpdate();
            } catch (IOException ignore) { }
        }
    }


    /**
     * Sends a matches list update to a single client.
     *
     * @param client the virtual view of the client to update
     * @param matches the list of matches to send
     */
    protected void singleUpdateMatchesList(VirtualView client, List<MatchData> matches) {
        synchronized (getClients()) {
            try {
                client.showUpdateMatchesList(matches);
            } catch (IOException ignored) { }
        }
    }


    /**
     * Broadcasts a matches list update to all clients.
     *
     * @param matches the list of matches to broadcast
     * @throws IOException if an I/O error occurs
     */
    protected void broadcastUpdateMatchesList(List<MatchData> matches) throws IOException {
        synchronized (getClients()) {
            for (var client : getClients()) {
                client.getVirtualView().showUpdateMatchesList(matches);
            }
        }
    }


    /**
     * Sends a match creation update to a single client.
     *
     * @param client the virtual view of the client to update
     * @param matchId the ID of the created match
     * @throws IOException if an I/O error occurs
     */
    protected void singleUpdateMatchCreate(VirtualView client, Integer matchId) throws IOException {
        synchronized (getClients()) {
            client.showUpdateMatchCreate(matchId);
        }
    }


    /**
     * Sends a match join update to a single client.
     *
     * @param client the virtual view of the client to update
     */
    protected void singleUpdateMatchJoin(VirtualView client) {
        synchronized (getClients()) {
            try {
                client.showUpdateMatchJoin();
            } catch (IOException ignored) { }
        }
    }



    /**
     * Sends initial settings update to a single client.
     *
     * @param matchId the ID of the match
     * @param client the virtual view of the client to update
     * @param playerInitialSetting the initial settings of the player
     */
    public void singleUpdateInitialSettings(Integer matchId, VirtualView client, PlayerInitialSetting playerInitialSetting) {
        List<VirtualView> clientsToNotify = getMatchNotificationClients().get(matchId);

        synchronized (clientsToNotify) {
            try {
                client.showUpdateInitialSettings(playerInitialSetting);
            } catch (IOException ignored) { }
        }
    }


    /**
     * Sends game state update to clients in a match.
     *
     * @param matchId the ID of the match
     * @param gameState the updated game state
     */
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


    /**
     * Sends player hand update to a single client.
     *
     * @param matchId the ID of the match
     * @param client the virtual view of the client to update
     * @param playerHand the updated player hand
     */
    public void singleUpdatePlayerHand(Integer matchId, VirtualView client, PlayerHand playerHand) {
        List<VirtualView> clientsToNotify = getMatchNotificationClients().get(matchId);

        synchronized (clientsToNotify) {
            try {
                client.showUpdatePlayerHand(playerHand);
            } catch (IOException ignored) { }
        }
    }


    /**
     * Sends public board update to clients in a match.
     *
     * @param matchId the ID of the match
     * @param deckType the type of deck
     * @param publicBoardUpdate the updated public board
     */
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


    /**
     * Sends player board update to clients in a match.
     *
     * @param matchId the ID of the match
     * @param nickname the nickname of the player
     * @param coordinates the coordinates of the update
     * @param playedCard the played card
     * @param score the updated score
     */
    public void matchUpdatePlayerBoard(Integer matchId, String nickname, Coordinates coordinates, PlayedCard playedCard, Integer score) {
        List<VirtualView> clientsToNotify = getMatchNotificationClients().get(matchId);

        synchronized (clientsToNotify) {
            for (var client : clientsToNotify) {
                try {
                    client.showUpdateBoard(nickname, coordinates, playedCard, score);
                } catch (IOException ignored) { }
            }
        }
    }


    /**
     * Sends broadcast message update to clients in a match.
     *
     * @param matchId the ID of the match
     * @param message the broadcast message
     */
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


    /**
     * Sends private message update to a single client.
     *
     * @param matchId the ID of the match
     * @param nickname the nickname of the sender
     * @param recipient the nickname of the recipient
     * @param message the private message
     */
    public void singleUpdatePrivateMessage(Integer matchId, String nickname, String recipient, Message message) {
        List<VirtualView> clientsToNotify = getMatchNotificationClients().get(matchId);
        VirtualView client = getClientInGame(nickname).getVirtualView();

        synchronized (clientsToNotify) {
            try {
                client.showUpdatePrivateChat(recipient, message);
            } catch (IOException ignored) { }
        }
    }


    /**
     * Sends game start update to clients in a match.
     *
     * @param matchId the ID of the match
     * @param resource the resource cards on the public board
     * @param gold the gold cards on the public board
     * @param quest the quest cards on the public board
     * @param boards the player boards
     */
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



    /**
     * Sends rejoin match update to a player.
     *
     * @param nickname the nickname of the player
     * @param playerInitialSetting the initial settings of the player
     * @param gameState the current game state
     * @param resource the resource cards on the public board
     * @param gold the gold cards on the public board
     * @param quest the quest cards on the public board
     * @param boards the player boards
     * @param playerHand the player's hand
     */
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


    /**
     * Reports an error to a client.
     *
     * @param client the virtual view of the client to report the error to
     * @param error the error message to report
     */
    public void reportError(VirtualView client, ERROR_MESSAGES error) {
        synchronized (getClients()) {
            try {
                client.reportError(error);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }



    /**
     * Reports a match-related error to clients in a match.
     *
     * @param matchId the ID of the match
     * @param client the virtual view of the client to report the error to
     * @param error the error message to report
     */
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


    /**
     * Deletes a game, moving active players back to the client list and cleaning up associated resources.
     *
     * @param matchId the ID of the match to delete
     */
    protected void deleteGame(Integer matchId){
        List<Player> players = controller.getMatch(matchId).getPlayers();
        getMatchServers().get(matchId);
        controller.deleteMatch(matchId);

        synchronized (getMatchNotificationClients()){
            getMatchNotificationClients().remove(matchId);
        }

        //Removing the client connections from the in game list, and moving it to the client list
        synchronized (getClients()){
            synchronized (getClientsInGame()) {
                for(var player : players){
                    if(!player.getIsDisconnected()){
                        addClientConnection(getClientInGame(player.getNickname()));
                        ClientConnection client = getClient(player.getNickname());
                        client.setConnected(true);
                        removeClientInGame(getClient(player.getNickname()));

                        logger.println("Moving back " + player.getNickname() + " to the client list");
                    }
                }
            }
        }

        //Removing the disconnected clients associated to that game
        synchronized (getDisconnectedClients()){
            for(var player : players){
                getDisconnectedClients().keySet().stream()
                        .filter(clientConnection -> Objects.equals(clientConnection.getNickname(), player.getNickname()))
                        .findFirst()
                        .ifPresent(getDisconnectedClients()::remove);
            }
        }
    }


    /**
     * Checks if a player is already in a game.
     *
     * @param nickname the nickname of the player to check
     * @return true if the player is already in a game, false otherwise
     */
    private Boolean isPlayerAlreadyInGame(String nickname){
        synchronized (getDisconnectedClients()){
            return isPlayerDisconnected(nickname);
        }
    }


    /**
     * Handles reconnection of a player to a match.
     *
     * @param nickname the nickname of the player reconnecting
     * @param stub the stub associated with the client
     * @return the ID of the match the player reconnected to
     */
    private Integer handleReconnection(String nickname, String stub){
        VirtualView virtualView;
        Integer matchId;

        synchronized (getDisconnectedClients()){
            matchId = getDisconnectedClients().get(getDisconnectedClient(nickname));
            removeDisconnectedClient(nickname);
        }

        synchronized (getClients()){
            virtualView = getClient(stub).getVirtualView();
            removeClientConnection(getClient(stub));
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



    /**
     * Handles updates when a player rejoins a match.
     *
     * @param nickname the nickname of the player rejoining
     * @param matchId the ID of the match
     */
    private void reJoinUpdates(String nickname, Integer matchId){
        MatchController matchController = controller.getMatch(matchId);
        Player player = matchController.getPlayerByNickname(nickname)
                .orElse(null);
        PlayerInitialSetting playerInitialSetting = matchController.getPlayerInitialSettingByNickname(nickname).orElse(null);

        synchronized (getMatchNotificationClients().get(matchId)){
            matchController.updatePlayerStatus(nickname, false);
        }

        VirtualMatchServer matchServer;
        if (getMatchServers().containsKey(matchId)) {
            matchServer = getMatchServers().get(matchId);
        } else {
            // Match was created by a client using a protocol different from the one used by this client
            matchServer = createMatchServer(controller.getMatch(matchId), getMatchNotificationClients().get(matchId), logger);
        }

        synchronized (getClientsInGame()){
            VirtualView client = getClientInGame(nickname).getVirtualView();
            try{
                client.setMatchServer(matchServer);
            } catch(IOException e){
                System.out.println(e.getMessage());
            }
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


    /**
     * Initializes exception handlers for various exceptions.
     */
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


    /**
     * Handles an exception that occurs for a specific client by delegating
     * to the appropriate exception handler or reporting an unknown error.
     *
     * @param clientToUpdate the VirtualView of the client that encountered the exception
     * @param exception the exception that occurred
     */
    private void handleException(VirtualView clientToUpdate, Exception exception) {
        ExceptionHandler handler = exceptionHandlers.get(exception.getClass());
        if (handler != null) {
            handler.handle(clientToUpdate, exception);
        } else {
           reportError(clientToUpdate, ERROR_MESSAGES.UNKNOWN_ERROR);
        }
    }
}
