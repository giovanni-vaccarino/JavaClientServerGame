package polimi.ingsoft.client.socket;

import polimi.ingsoft.client.Client;
import polimi.ingsoft.client.rmi.VirtualView;
import polimi.ingsoft.client.ui.UI;
import polimi.ingsoft.client.ui.UIType;
import polimi.ingsoft.client.ui.cli.CLI;
import polimi.ingsoft.server.Player;
import polimi.ingsoft.server.common.VirtualServer;
import polimi.ingsoft.server.controller.MatchController;
import polimi.ingsoft.server.model.Coordinates;
import polimi.ingsoft.server.model.Message;
import polimi.ingsoft.server.model.MixedCard;
import polimi.ingsoft.server.model.PlaceInPublicBoard;
import polimi.ingsoft.server.socket.protocol.MessageCodes;
import polimi.ingsoft.server.socket.protocol.SocketMessage;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;

public class SocketClient implements Client {
    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private final VirtualServer server;

    private final UI ui;

    public SocketClient(
            String hostName,
            int port,
            UIType uiType,
            PrintStream printStream,
            InputStream inputStream
    ) throws IOException {
        socket = new Socket(hostName, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        server = new ServerProxy(out);
        if (uiType == UIType.CLI)
            ui = new CLI(inputStream, printStream, this);
        else
            // TODO create GUI here
            ui = new CLI(inputStream, printStream, this);
    }

    public void run() {
        new Thread(() -> {
            try {
                runVirtualServer();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private void runVirtualServer() throws IOException, ClassNotFoundException {
        SocketMessage item;
        while ((item = (SocketMessage) in.readObject()) != null) {
            MessageCodes type = item.type;
            Object payload = item.payload;
            // Read message and perform action
            switch (type) {
                case MATCHES_LIST_UPDATE -> {
                    List<Integer> matches = (List<Integer>) payload;
                    this.showUpdateMatchesList(matches);
                }
                case MATCH_JOIN_UPDATE -> {
                    Boolean success = (Boolean) payload;
                    this.showUpdateMatchJoin(success);
                }
                case MATCH_CREATE_UPDATE -> {
                    MatchController match = (MatchController) payload;
                    this.showUpdateMatchCreate(match);
                }
                default -> System.err.println("[INVALID MESSAGE]");
            }
        }
    }

    @Override
    public void close() throws IOException {
        out.close();
        in.close();
        socket.close();
    }

    @Override
    public void showJoinMatchResult(Boolean joinResult, List<Player> players) throws IOException {

    }

    @Override
    public void showUpdateMatchesList(List<Integer> matches) throws IOException {
        System.out.println("Got update: MATCHES LIST: " + matches);
        ui.showMatchesList(matches);
    }

    @Override
    public void showUpdateMatchJoin(Boolean success) throws IOException {
        System.out.println("Got update: MATCH JOIN: " + success);
    }

    @Override
    public void showUpdateMatchCreate(MatchController match) throws IOException {
        System.out.println("Got update: MATCH CREATE: " + match.getMatchId());
    }

    @Override
    public void showUpdateChat(Message message) throws IOException {

    }

    @Override
    public void showUpdatePublicBoard() throws IOException {

    }

    @Override
    public void showUpdateBoard() throws IOException {

    }

    @Override
    public void reportError(String details) throws IOException {

    }

    @Override
    public void getMatches(VirtualView client) throws IOException {
        server.getMatches(client);
    }

    @Override
    public void createMatch(Integer requiredNumPlayers) throws IOException {
        server.createMatch(requiredNumPlayers);
    }

    @Override
    public void joinMatch(VirtualView client, Integer matchId, String nickname) throws IOException {
        server.joinMatch(client, matchId, nickname);
    }

    @Override
    public void reJoinMatch(Integer matchId, String nickname) throws IOException {
        server.reJoinMatch(matchId, nickname);
    }

    @Override
    public void addMessage(int matchId, String message) throws IOException {
        server.addMessage(matchId, message);
    }

    @Override
    public void drawCard(int matchId, String playerName, String deckType, PlaceInPublicBoard.Slots slot) throws IOException {
        server.drawCard(matchId, playerName, deckType, slot);
    }

    @Override
    public void placeCard(int matchId, String playerName, MixedCard card, Coordinates coordinates, boolean facingUp) throws IOException {
        server.placeCard(matchId, playerName, card, coordinates, facingUp);
    }

    @Override
    public UI getUI() {
        return ui;
    }

    @Override
    public void clientJoinMatch(Integer matchId, String nickname) throws RemoteException {

    }
}
