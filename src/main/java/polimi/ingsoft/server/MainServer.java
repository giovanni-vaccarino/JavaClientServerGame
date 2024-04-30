package polimi.ingsoft.server;

import polimi.ingsoft.server.common.VirtualServerInterface;
import polimi.ingsoft.server.controller.MainController;
import polimi.ingsoft.server.rmi.RmiServer;
import polimi.ingsoft.server.socket.SocketServer;

import java.io.PrintStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class MainServer {
    private final static PrintStream logger = System.out;
    private final static MainController controller = new MainController(logger);

    private final static SocketServer socketServer = new SocketServer(4444, logger, controller);
    private final static RmiServer rmiServer = new RmiServer(controller, logger);

    public static void main(String[] args) {
        logger.println("MAIN: Starting servers (RMI & Socket)");
        runRmiServer();
        runSocketServer();
        logger.println("MAIN: Finished scheduling server setup");
    }

    private static void runSocketServer() {
        Thread socketServerThread = new Thread(socketServer::handleIncomingConnections);
        socketServerThread.start();
        logger.println("MAIN: Socket server is running");
    }

    private static void runRmiServer() {
        try {
            VirtualServerInterface stub = (VirtualServerInterface) UnicastRemoteObject.exportObject(rmiServer, 0);

            Registry registry = LocateRegistry.createRegistry(1234);
            registry.rebind("MatchManagerServer", stub);

            logger.println("MAIN: RMI MatchManager Server is running");
        } catch (RemoteException e) {
            logger.println("MAIN: Error occurred while starting RMI server:" + e.getMessage());
        }
    }
}
