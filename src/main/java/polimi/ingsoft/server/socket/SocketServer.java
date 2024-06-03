package polimi.ingsoft.server.socket;

import polimi.ingsoft.server.common.MatchServer;
import polimi.ingsoft.server.common.command.ServerCommand;
import polimi.ingsoft.client.common.VirtualView;
import polimi.ingsoft.server.common.Server;
import polimi.ingsoft.server.common.Utils;
import polimi.ingsoft.server.common.VirtualMatchServer;
import polimi.ingsoft.server.controller.MainController;
import polimi.ingsoft.server.controller.MatchController;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SocketServer extends Server {
    private final int port;
    private static final String CONNECTION_HANDLER_NAME_PREFIX = "ConnectionHandler-";
    private final Map<Integer, VirtualMatchServer> matchServers = new HashMap<>();

    public SocketServer(int port, PrintStream logger, MainController controller) {
        super(logger, controller);
        this.port = port;
    }

    public void handleIncomingConnections() {
        logger.println("SOCKET: Starting server");
        ServerSocket server;
        String stub;
        List<Thread> handlers = new ArrayList<>();
        try {
            server = new ServerSocket(port);
            logger.println("SOCKET: Server ready");
            while (true) {
                try {
                    Socket socket = server.accept();
                    stub = Utils.getRandomNickname();
                    ConnectionHandler handler = new ConnectionHandler(socket, this, logger, stub);
                    this.addClient(handler, stub);

                    Thread handlerThread = new Thread(handler);
                    handlers.add(handlerThread);
                    handlerThread.setName(CONNECTION_HANDLER_NAME_PREFIX + handlers.size());
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

    @Override
    protected VirtualMatchServer createMatchServer(MatchController matchController, List<VirtualView> clients, PrintStream logger) {
        return new MatchServer(logger, matchController, this);
    }

    @Override
    protected Map<Integer, VirtualMatchServer> getMatchServers() {
        return matchServers;
    }

    @Override
    public void connect(VirtualView client) throws IOException {

    }

    @Override
    public void sendMessage(ServerCommand command) throws IOException { }
}
