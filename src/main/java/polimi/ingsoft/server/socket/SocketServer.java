package polimi.ingsoft.server.socket;

import polimi.ingsoft.client.common.VirtualView;
import polimi.ingsoft.server.common.ConnectionsClient;
import polimi.ingsoft.server.controller.GameState;
import polimi.ingsoft.server.controller.MainController;
import polimi.ingsoft.server.controller.PlayerInitialSetting;
import polimi.ingsoft.server.exceptions.MatchSelectionExceptions.NicknameNotAvailableException;
import polimi.ingsoft.server.model.*;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SocketServer implements ConnectionsClient {
    private final int port;
    private final PrintStream logger;
    private final MainController controller;

    public SocketServer(int port, PrintStream logger, MainController controller) {
        this.port = port;
        this.logger = logger;
        this.controller = controller;
    }

    public void handleIncomingConnections() {
        logger.println("SOCKET: Starting server");
        ServerSocket server;
        List<Thread> handlers = new ArrayList<>();
        try {
            server = new ServerSocket(port);
            logger.println("SOCKET: Server ready");
            while (true) {
                try {
                    Socket socket = server.accept();
                    ConnectionHandler handler = new ConnectionHandler(socket, controller, this, logger);
                    Thread handlerThread = new Thread(handler);
                    handlers.add(handlerThread);
                    handlerThread.setName("ConnectionHandler-" + handlers.size());
                    handlerThread.start();
                    logger.println("SOCKET: new incoming connection accepted " + handlers);
                } catch (IOException e) {
                    break;
                }
            }
            server.close();
        } catch (IOException exception) {
            logger.println("SOCKET: Mainloop exception " + exception.getMessage());
        }
        for (var handler : handlers)
            handler.interrupt();
    }

    public void addClient(VirtualView client, String stub) {
        clients.put(stub, client);
    }

    public void setNicknameForClient(String stub, String nickname) throws NicknameNotAvailableException {
        synchronized (this.clients) {
            if (clients.containsKey(nickname)) {
                logger.println("SOCKET: Nickname not available");
                throw new NicknameNotAvailableException();
            }
            VirtualView client = clients.get(stub);
            clients.remove(stub);
            clients.put(nickname, client);
        }
    }

    public void singleUpdateNickname(VirtualView client) {
        synchronized (this.clients) {
            try {
                client.showNicknameUpdate();
            } catch (IOException ignore) { }
        }
    }

    public void singleUpdateMatchesList(VirtualView client, List<Integer> matches) {
        // TODO fixare e usare granularità sul singolo client
        synchronized (this.clients) {
            try {
                client.showUpdateMatchesList(matches);
            } catch (IOException ignored) { }
        }
    }

    public void broadcastUpdateMatchesList(List<Integer> matches) throws IOException {
        synchronized (this.clients) {
            for (var client : this.clients.values()) {
                client.showUpdateMatchesList(matches);
            }
        }
    }

    public void singleUpdateMatchCreate(VirtualView client, Integer matchId) throws IOException {
        // TODO fixare e usare granularità sul singolo client
        synchronized (this.clients) {
            client.showUpdateMatchCreate(matchId);
        }
    }

    public void singleUpdateMatchJoin(VirtualView client) {
        // TODO fixare e usare granularità sul singolo client
        synchronized (this.clients) {
            try {
                client.showUpdateMatchJoin();
            } catch (IOException ignored) { }
        }
    }

    public void lobbyUpdatePlayerJoin(List<String> nicknames) {
        List<VirtualView> clientsToNotify = new ArrayList<>();
        for (var nickname : nicknames) {
            clientsToNotify.add(this.clients.get(nickname));
        }
        // TODO usare map partite [...]
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

    public void matchUpdatePublicBoard(Integer matchId, PublicBoard publicBoard) {
        List<VirtualView> clientsToNotify = this.matchNotificationList.get(matchId);
        synchronized (clientsToNotify) {
            for (var client : clientsToNotify) {
                try {
                    //TODO
                    client.showUpdatePublicBoard(null, null);
                } catch (IOException ignored) { }
            }
        }
    }

    public void matchUpdatePlayerBoard(Integer matchId, String nickname, Coordinates coordinates, PlayedCard playedCard, Integer score) {
        List<VirtualView> clientsToNotify = this.matchNotificationList.get(matchId);
        synchronized (clientsToNotify) {
            for (var client : clientsToNotify) {
                try {
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
}
