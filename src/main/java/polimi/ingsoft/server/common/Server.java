package polimi.ingsoft.server.common;

import polimi.ingsoft.client.common.VirtualView;
import polimi.ingsoft.server.controller.GameState;
import polimi.ingsoft.server.controller.MainController;
import polimi.ingsoft.server.controller.MatchController;
import polimi.ingsoft.server.controller.PlayerInitialSetting;
import polimi.ingsoft.server.enumerations.ERROR_MESSAGES;
import polimi.ingsoft.server.enumerations.GAME_PHASE;
import polimi.ingsoft.server.enumerations.TYPE_HAND_CARD;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Server implements VirtualServer, ConnectionsClient {
    protected final PrintStream logger;
    protected final MainController controller;

    public Server(PrintStream logger, MainController controller) {
        this.logger = logger;
        this.controller = controller;
    }

    protected abstract VirtualMatchServer createMatchServer(MatchController matchController, List<VirtualView> clients, PrintStream logger);
    protected abstract Map<Integer, VirtualMatchServer> getMatchServers();

    @Override
    public void connect(VirtualView client) throws IOException {
        String stub = Utils.getRandomNickname();
        logger.println("SERVER: generated stub " + stub);
        synchronized (this.clients) {
            this.clients.put(stub, client);
        }
        client.showConnectUpdate(stub);
    }

    @Override
    public void setNickname(String nickname, String stub) throws IOException {
        logger.println("SERVER: " + nickname + " " + stub);
        VirtualView clientToUpdate = clients.get(stub);

        try {
            setNicknameForClient(clientToUpdate, nickname);
            singleUpdateNickname(clientToUpdate);
        } catch (NicknameNotAvailableException exception) {
            clientToUpdate.reportError(ERROR_MESSAGES.NICKNAME_NOT_AVAILABLE);
        }
    }

    @Override
    public void getMatches(String nickname) throws IOException {
        VirtualView clientToUpdate = clients.get(nickname);
        List<Integer> matches = controller.getMatches();
        singleUpdateMatchesList(clientToUpdate, matches);
    }

    @Override
    public void createMatch(String playerNickname, Integer requiredNumPlayers) throws IOException {
        VirtualView clientToUpdate = clients.get(playerNickname);

        int id = controller.createMatch(requiredNumPlayers);
        singleUpdateMatchCreate(clientToUpdate, id);
        List<Integer> matches = controller.getMatches();
        broadcastUpdateMatchesList(matches);

        getMatchServers().put(id, createMatchServer(controller.getMatch(id), matchNotificationList.get(id), logger));

        // Creating a new list for the players
        synchronized (matchNotificationList){
            matchNotificationList.put(id, new ArrayList<>());
        }
        // It is client's responsibility to join the match right after
    }

    @Override
    public void joinMatch(String playerNickname, Integer matchId) throws IOException {
        VirtualView clientToUpdate = clients.get(playerNickname);

        try {
            controller.joinMatch(matchId, playerNickname);
            singleUpdateMatchJoin(clientToUpdate);

            MatchController matchController = controller.getMatch(matchId);
            GameState gameState = matchController.getGameState();
            //Adding the client to the match notification list
            synchronized (matchNotificationList){
                matchNotificationList.get(matchId).add(clientToUpdate);
            }

            VirtualMatchServer matchServer;
            if (getMatchServers().containsKey(matchId)) {
                matchServer = getMatchServers().get(matchId);
            } else {
                // Match was created by a client using a protocol different from the one used by this client
                matchServer = createMatchServer(controller.getMatch(matchId), matchNotificationList.get(matchId), logger);
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
        } catch (MatchAlreadyFullException exception) {
            reportError(clientToUpdate, ERROR_MESSAGES.MATCH_IS_ALREADY_FULL);
        } catch (MatchNotFoundException exception) {
            reportError(clientToUpdate, ERROR_MESSAGES.MATCH_DOES_NOT_EXIST);
        }
    }

    @Override
    public void reJoinMatch(Integer matchId, String nickname) throws IOException {

    }

    protected void setNicknameForClient(VirtualView client, String nickname) throws NicknameNotAvailableException {
        synchronized (this.clients) {
            if (clients.containsKey(nickname)) {
                throw new NicknameNotAvailableException();
            }

            //Removing the precedent (key, value) of the client
            clients.entrySet().stream()
                    .filter(entry -> entry.getValue().equals(client))
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .ifPresent(clients::remove);

            clients.put(nickname, client);
        }
    }

    protected void singleUpdateNickname(VirtualView client) {
        synchronized (this.clients) {
            try {
                client.showNicknameUpdate();
            } catch (IOException ignore) { }
        }
    }

    protected void singleUpdateMatchesList(VirtualView client, List<Integer> matches) {
        synchronized (this.clients) {
            try {
                client.showUpdateMatchesList(matches);
            } catch (IOException ignored) { }
        }
    }

    protected void broadcastUpdateMatchesList(List<Integer> matches) throws IOException {
        synchronized (this.clients) {
            for (var client : this.clients.values()) {
                client.showUpdateMatchesList(matches);
            }
        }
    }

    protected void singleUpdateMatchCreate(VirtualView client, Integer matchId) throws IOException {
        synchronized (this.clients) {
            client.showUpdateMatchCreate(matchId);
        }
    }

    protected void singleUpdateMatchJoin(VirtualView client) {
        synchronized (this.clients) {
            try {
                client.showUpdateMatchJoin();
            } catch (IOException ignored) { }
        }
    }

    protected void lobbyUpdatePlayerJoin(List<String> nicknames) {
        List<VirtualView> clientsToNotify = new ArrayList<>();
        for (var nickname : nicknames) {
            clientsToNotify.add(this.clients.get(nickname));
        }
        synchronized (this.clients) {
            for (var client : clientsToNotify) {
                try {
                    client.showUpdateLobbyPlayers(nicknames);
                } catch (IOException ignored) { }
            }
        }
    }

    public void singleUpdateInitialSettings(VirtualView client, PlayerInitialSetting playerInitialSetting) {
        synchronized (this.clients) {
            try {
                client.showUpdateInitialSettings(playerInitialSetting);
            } catch (IOException ignored) { }
        }
    }

    public void matchUpdateGameState(Integer matchId, GameState gameState) {
        List<VirtualView> clientsToNotify = this.matchNotificationList.get(matchId);
        synchronized (clientsToNotify) {
            for (var client : clientsToNotify) {
                try {
                    client.showUpdateGameState(gameState);
                } catch (IOException ignored) { }
            }
        }
    }

    public void singleUpdatePlayerHand(VirtualView client, PlayerHand playerHand) {
        synchronized (this.clients) {
            try {
                client.showUpdatePlayerHand(playerHand);
            } catch (IOException ignored) { }
        }
    }

    public void matchUpdatePublicBoard(Integer matchId, TYPE_HAND_CARD deckType, PlaceInPublicBoard<?> publicBoardUpdate) {
        List<VirtualView> clientsToNotify = this.matchNotificationList.get(matchId);
        synchronized (clientsToNotify) {
            for (var client : clientsToNotify) {
                try {
                    client.showUpdatePublicBoard(deckType, publicBoardUpdate);
                } catch (IOException ignored) { }
            }
        }
    }

    public void matchUpdatePlayerBoard(Integer matchId, String nickname, Coordinates coordinates, PlayedCard playedCard, Integer score) {
        List<VirtualView> clientsToNotify = this.matchNotificationList.get(matchId);
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
        List<VirtualView> clientsToNotify = this.matchNotificationList.get(matchId);
        synchronized (clientsToNotify) {
            for (var client : clientsToNotify) {
                try {
                    client.showUpdateBroadcastChat(message);
                } catch (IOException ignored) { }
            }
        }
    }

    public void singleUpdatePrivateMessage(String nickname, String recipient, Message message) {
        VirtualView client = this.clients.get(nickname);
        synchronized (this.clients) {
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
        List<VirtualView> clientsToNotify = this.matchNotificationList.get(matchId);
        synchronized (clientsToNotify) {
            for (var client : clientsToNotify) {
                try {
                    client.showUpdateGameStart(resource, gold, quest, boards);
                } catch (IOException ignored) { }
            }
        }
    }

    public void reportError(VirtualView client, ERROR_MESSAGES error) {
        synchronized (this.clients) {
            try {
                client.reportError(error);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
