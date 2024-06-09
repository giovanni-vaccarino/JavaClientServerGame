package polimi.ingsoft.server.socket;

import polimi.ingsoft.client.common.VirtualView;
import polimi.ingsoft.server.common.Dispatcher;
import polimi.ingsoft.server.common.VirtualMatchServer;
import polimi.ingsoft.server.common.command.ClientCommand;
import polimi.ingsoft.server.common.command.Command;
import polimi.ingsoft.server.controller.GameState;
import polimi.ingsoft.server.controller.PlayerInitialSetting;
import polimi.ingsoft.server.enumerations.ERROR_MESSAGES;
import polimi.ingsoft.server.enumerations.TYPE_HAND_CARD;
import polimi.ingsoft.server.model.boards.Board;
import polimi.ingsoft.server.model.boards.Coordinates;
import polimi.ingsoft.server.model.boards.PlayedCard;
import polimi.ingsoft.server.model.cards.GoldCard;
import polimi.ingsoft.server.model.cards.QuestCard;
import polimi.ingsoft.server.model.cards.ResourceCard;
import polimi.ingsoft.server.model.chat.Message;
import polimi.ingsoft.server.model.decks.PlayerHand;
import polimi.ingsoft.server.model.publicboard.PlaceInPublicBoard;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.Map;

public class ConnectionHandler implements Runnable, VirtualView {
    private final Socket socket;
    private final VirtualView view;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private final Dispatcher dispatcher;

    private final PrintStream logger;

    public ConnectionHandler(Socket socket, SocketServer server, PrintStream logger) throws IOException {
        this.socket = socket;
        this.in = new ObjectInputStream(socket.getInputStream());
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.view = new SocketClientProxy(out);
        this.logger = logger;
        this.dispatcher = new Dispatcher(server);
    }

    @Override
    public void run() {
        try {
            Command<?> command;

            while ((command = (Command<?>) in.readObject()) != null)
                try {
                    dispatcher.dispatchCommand(command);
                } catch (NullPointerException e) {
                    logger.println("SOCKET: NullPointerException raised " + e.getMessage());
                }

            // Shutdown socket
            in.close();
            out.close();
            socket.close();
        } catch (EOFException | SocketException e) {
            // TODO handle disconnection here
            logger.println("SOCKET: Connection closed");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendMessage(ClientCommand command) throws IOException {
        synchronized (this.view) {
            this.view.sendMessage(command);
        }
    }

    @Override
    public void showConnectUpdate(String stubNickname) throws IOException {
        logger.println("SOCKET: Sending connect update");
        synchronized (this.view) {
            this.view.showConnectUpdate(stubNickname);
        }
    }

    @Override
    public void showNicknameUpdate() throws IOException {
        logger.println("SOCKET: Sending nickname update");
        synchronized (this.view) {
            this.view.showNicknameUpdate();
        }
    }

    @Override
    public void showUpdateLobbyPlayers(List<String> players) throws IOException {
        logger.println("SOCKET: Sending lobby player updates: " + players);
        synchronized (this.view) {
            this.view.showUpdateLobbyPlayers(players);
        }
    }

    @Override
    public void showUpdateMatchesList(List<Integer> matches) throws IOException {
        logger.println("SOCKET: Sending matches list update: " + matches.toString());
        synchronized (this.view) {
            this.view.showUpdateMatchesList(matches);
        }
    }

    @Override
    public void showUpdateMatchJoin() {
        logger.println("SOCKET: Sending match join update");
        synchronized (this.view) {
            try {
                this.view.showUpdateMatchJoin();
            } catch (IOException ignore) { }
        }
    }

    @Override
    public void showUpdateMatchCreate(Integer matchId) throws IOException {
        logger.println("SOCKET: Sending match create update: " + matchId);
        synchronized (this.view) {
            this.view.showUpdateMatchCreate(matchId);
        }
    }

    @Override
    public void showUpdateBroadcastChat(Message message) {
        synchronized (this.view) {
            try {
                this.view.showUpdateBroadcastChat(message);
            } catch (IOException ignore) { }
        }
    }

    @Override
    public void showUpdatePrivateChat(String recipient, Message message) {
        synchronized (this.view) {
            try {
                this.view.showUpdatePrivateChat(recipient, message);
            } catch (IOException ignore) { }
        }
    }

    @Override
    public void showUpdateInitialSettings(PlayerInitialSetting playerInitialSetting) {
        logger.println("SOCKET: Sending update player initial settings");
        synchronized (this.view) {
            try {
                this.view.showUpdateInitialSettings(playerInitialSetting);
            } catch (IOException ignore) { }
        }
    }

    @Override
    public void showUpdateGameState(GameState gameState) {
        logger.println("SOCKET: Sending update game state");
        synchronized (this.view) {
            try {
                this.view.showUpdateGameState(gameState);
            } catch (IOException ignore) { }
        }
    }

    @Override
    public void showUpdateGameStart(PlaceInPublicBoard<ResourceCard> resource, PlaceInPublicBoard<GoldCard> gold, PlaceInPublicBoard<QuestCard> quest, Map<String, Board> boards) throws IOException {
        logger.println("SOCKET: Sending update game state start");
        synchronized (this.view) {
            try {
                this.view.showUpdateGameStart(resource, gold, quest, boards);
            } catch (IOException ignore) { }
        }
    }

    @Override
    public void showUpdatePlayerHand(PlayerHand playerHand) {
        synchronized (this.view) {
            try {
                this.view.showUpdatePlayerHand(playerHand);
            } catch (IOException ignore) { }
        }
    }

    @Override
    public void showUpdatePublicBoard(TYPE_HAND_CARD deckType, PlaceInPublicBoard<?> placeInPublicBoard) throws IOException {
        synchronized (this.view) {
            try {
                this.view.showUpdatePublicBoard(deckType, placeInPublicBoard);
            } catch (IOException ignore) { }
        }
    }

    @Override
    public void showUpdateBoard(String nickname, Coordinates coordinates, PlayedCard playedCard, Integer score) {
        synchronized (this.view) {
            try {
                this.view.showUpdateBoard(nickname, coordinates, playedCard, score);
            } catch (IOException ignore) { }
        }
    }

    @Override
    public void reportError(ERROR_MESSAGES errorMessage) {
        synchronized (this.view) {
            try {
                this.view.reportError(errorMessage);
            } catch (IOException ignore) { }
        }
    }

    @Override
    public void setMatchServer(VirtualMatchServer matchServer) {
        this.dispatcher.setMatchServer(matchServer);
    }
}
