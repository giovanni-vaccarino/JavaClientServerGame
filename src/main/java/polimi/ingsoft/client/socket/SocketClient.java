package polimi.ingsoft.client.socket;

import polimi.ingsoft.client.Client;
import polimi.ingsoft.client.rmi.VirtualView;
import polimi.ingsoft.server.Player;
import polimi.ingsoft.server.controller.MatchController;
import polimi.ingsoft.server.model.Message;
import polimi.ingsoft.server.socket.protocol.MessageCodes;
import polimi.ingsoft.server.socket.protocol.SocketMessage;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

public class SocketClient implements AutoCloseable, VirtualView {
    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    public SocketClient(String hostName, int port) throws IOException {
        socket = new Socket(hostName, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
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
                    // show update matches
                }
                case MATCH_JOIN_UPDATE -> {
                    Boolean success = (Boolean) payload;
                }
                case MATCH_CREATE_UPDATE -> {
                    MatchController match = (MatchController) payload;
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
    public void showUpdateMatchesList(List<Integer> matches) throws IOException {
        // output in UI (GUI or CLI)
    }

    @Override
    public void showUpdateMatchJoin(Boolean success) throws RemoteException {

    }

    @Override
    public void showUpdateMatchCreate(MatchController match) throws RemoteException {

    }

    @Override
    public void showUpdateChat(Message message) throws RemoteException {

    }

    @Override
    public void showUpdatePublicBoard() throws RemoteException {

    }

    @Override
    public void showUpdateBoard() throws RemoteException {

    }

    @Override
    public void reportError(String details) throws RemoteException {

    }
}
