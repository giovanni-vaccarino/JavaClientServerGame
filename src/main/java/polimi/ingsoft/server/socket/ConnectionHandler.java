package polimi.ingsoft.server.socket;

import polimi.ingsoft.client.rmi.VirtualView;
import polimi.ingsoft.server.controller.MainController;
import polimi.ingsoft.server.controller.MatchController;
import polimi.ingsoft.server.model.Message;
import polimi.ingsoft.server.socket.protocol.MessageCodes;
import polimi.ingsoft.server.socket.protocol.SocketMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;

public class ConnectionHandler implements Runnable, VirtualView {
    private final Socket socket;
    private final MainController controller;
    private final VirtualView view;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private final SocketServer server;

    private final PrintStream logger;
    private String nickname = getRandomNickname();

    public ConnectionHandler(Socket socket, MainController controller, SocketServer server, PrintStream logger) throws IOException {
        this.socket = socket;
        this.controller = controller;
        this.server = server;
        this.in = new ObjectInputStream(socket.getInputStream());
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.view = new ClientProxy(out);
        this.logger = logger;
        this.server.addClient(this, nickname);
    }

    @Override
    public void run() {
        try {
            SocketMessage item;

            while ((item = (SocketMessage) in.readObject()) != null) {
                MessageCodes type = item.type;
                Object payload = item.payload;
                logger.println("SOCKET: Received request with type: " + type.toString());
                logger.println("SOCKET: And payload: " + payload);

                // Read message and perform action
                switch (type) {
                    case CONNECT -> this.server.clients.put(nickname, this);
                    case SET_NICKNAME_REQUEST -> {
                        String nickname = (String) payload;
                        boolean result = this.server.setNicknameForClient(this.nickname, nickname);
                        this.nickname = nickname;
                        this.server.singleUpdateNickname(this, result);
                    }
                    case MATCHES_LIST_REQUEST -> {
                        List<Integer> matches = controller.getMatches();
                        this.server.singleUpdateMatchesList(this, matches);
                    }
                    case MATCH_JOIN_REQUEST -> {
                        int id = (Integer) payload;
                        Boolean success = controller.joinMatch(id, nickname);
                        this.server.singleUpdateMatchJoin(this, success);
                        // TODO Send LOBBY_PLAYER_JOINED_UPDATE to other players in lobby
                    }
                    case MATCH_CREATE_REQUEST -> {
                        int numberForPlayers = (int) payload;
                        int id = controller.createMatch(numberForPlayers);
                        MatchController matchController = controller.getMatch(id);
                        this.server.singleUpdateMatchCreate(this, matchController);
                        List<Integer> matches = controller.getMatches();
                        this.server.broadcastUpdateMatchesList(matches);
                    }
                    default -> System.err.println("[INVALID MESSAGE]");
                }
            }

            // Shutdown socket
            in.close();
            out.close();
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void showNicknameUpdate(boolean result) throws IOException {
        logger.println("SOCKET: Sending nickname update: " + result);
        synchronized (this.view) {
            this.view.showNicknameUpdate(result);
        }
    }

    @Override
    public void showJoinMatchResult(Boolean joinResult, List<String> players) throws IOException {

    }

    @Override
    public void showUpdateMatchesList(List<Integer> matches) throws IOException {
        logger.println("SOCKET: Sending matches list update: " + matches.toString());
        synchronized (this.view) {
            this.view.showUpdateMatchesList(matches);
        }
    }

    @Override
    public void showUpdateMatchJoin(Boolean success) {
        logger.println("SOCKET: Sending match join update: " + success);
        synchronized (this.view) {
            try {
                this.view.showUpdateMatchJoin(success);
            } catch (IOException ignore) { }
        }
    }

    @Override
    public void showUpdateMatchCreate(MatchController match) throws IOException {
        logger.println("SOCKET: Sending match create update: " + match);
        synchronized (this.view) {
            this.view.showUpdateMatchCreate(match);
        }
    }

    @Override
    public void showUpdateChat(Message message) {
        synchronized (this.view) {
            try {
                this.view.showUpdateChat(message);
            } catch (IOException ignore) { }
        }
    }

    @Override
    public void showUpdatePublicBoard() {
        synchronized (this.view) {
            try {
                this.view.showUpdatePublicBoard();
            } catch (IOException ignore) { }
        }
    }

    @Override
    public void showUpdateBoard() {
        synchronized (this.view) {
            try {
                this.view.showUpdateBoard();
            } catch (IOException ignore) { }
        }
    }

    @Override
    public void reportError(String details) {
        synchronized (this.view) {
            try {
                this.view.reportError(details);
            } catch (IOException ignore) { }
        }
    }

    static String getRandomNickname() {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(10);

        for (int i = 0; i < 10; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }

        return sb.toString();
    }
}
