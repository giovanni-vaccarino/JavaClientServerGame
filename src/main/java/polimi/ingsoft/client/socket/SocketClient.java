package polimi.ingsoft.client.socket;

import polimi.ingsoft.client.Client;
import polimi.ingsoft.client.ui.UIType;
import polimi.ingsoft.server.common.VirtualServer;
import polimi.ingsoft.server.controller.MatchController;
import polimi.ingsoft.server.socket.protocol.MessageCodes;
import polimi.ingsoft.server.socket.protocol.SocketMessage;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class SocketClient extends Client {
    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private final VirtualServer server;

    public SocketClient(
            String hostName,
            int port,
            UIType uiType,
            PrintStream printStream,
            Scanner scanner
    ) throws IOException {
        super(uiType, printStream, scanner);
        socket = new Socket(hostName, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        server = new ServerProxy(out);
    }

    @Override
    public VirtualServer getServer() {
        return server;
    }

    public void run() {
        new Thread(() -> {
            try {
                runVirtualServer();
            } catch (IOException | ClassNotFoundException e) {
                // TODO handle
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
                case SET_NICKNAME_UPDATE -> {
                    Boolean result = (Boolean) payload;
                    this.showNicknameUpdate(result);
                }
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
}
