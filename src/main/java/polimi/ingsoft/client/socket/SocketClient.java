package polimi.ingsoft.client.socket;

import polimi.ingsoft.client.common.Client;
import polimi.ingsoft.client.ui.UIType;
import polimi.ingsoft.server.common.VirtualMatchServer;
import polimi.ingsoft.server.common.VirtualServer;
import polimi.ingsoft.server.common.command.ClientCommand;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient extends Client {
    private transient final Socket socket;
    private transient final ObjectInputStream in;
    private transient final VirtualServer server;

    private transient VirtualMatchServer matchServer;

    public SocketClient(
            String hostName,
            int port,
            UIType uiType,
            PrintStream printStream,
            Scanner scanner
    ) throws IOException {
        super(uiType, printStream, scanner);
        socket = new Socket(hostName, port);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        server = new SocketServerProxy(out);
        matchServer = new SocketMatchServerProxy(out);
    }

    @Override
    public VirtualServer getServer() {
        return server;
    }

    @Override
    public VirtualMatchServer getMatchServer() {
        return matchServer;
    }

    @Override
    public void sendMessage(ClientCommand command) throws IOException {

    }

    @Override
    public void setMatchServer(VirtualMatchServer controller) {
        this.matchServer = controller;
    }

    @Override
    public void run() {
        new Thread(() -> {
            try {
                ClientCommand command;
                while ((command = (ClientCommand) in.readObject()) != null)
                    command.execute(this);
            } catch (IOException | ClassNotFoundException e) {
                // TODO handle
            }
        }, "SocketClientCommandReader").start();
    }
}
