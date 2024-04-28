package polimi.ingsoft.server.socket;

import polimi.ingsoft.server.controller.MainController;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class SocketServer {
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
        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket server;
        try {
            server = new ServerSocket(port);
            logger.println("SOCKET: Server ready");
            while (true) {
                try {
                    Socket socket = server.accept();
                    executor.submit(new ConnectionHandler(socket, controller));
                } catch (IOException e) {
                    break;
                }
            }
            server.close();
        } catch (IOException ignored) { }
        executor.shutdown();
    }
}

