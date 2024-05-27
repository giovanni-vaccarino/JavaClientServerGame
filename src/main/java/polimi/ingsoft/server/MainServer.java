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

/**
 * The MainServer class initializes and starts the RMI and Socket servers for the application.
 */
public class MainServer {
    private final static PrintStream logger = System.out;
    private final static MainController controller = new MainController(logger);

    private final static SocketServer socketServer = new SocketServer(4444, logger, controller);
    private final static RmiServer rmiServer = new RmiServer(controller, logger);


    /**
     * The main method of the MainServer class. Starts the RMI and Socket servers.
     * @param args Command-line arguments - not used
     */
    public static void main(String[] args) {
        logger.println("MAIN: Starting servers (RMI & Socket)");
        runRmiServer();
        runSocketServer();
        logger.println("MAIN: Finished scheduling server setup");
    }


    /**
     * Starts the Socket server.
     */
    private static void runSocketServer() {
        Thread socketServerThread = new Thread(socketServer::handleIncomingConnections);
        socketServerThread.start();
        logger.println("MAIN: Socket server is running");
    }


    /**
     * Starts the RMI server and binds it to the RMI registry.
     */
    private static void runRmiServer() {
        try {
            //System.setProperty("java.rmi.server.hostname", "192.168.195.134");
            VirtualServerInterface stub = (VirtualServerInterface) UnicastRemoteObject.exportObject(rmiServer, 0);

            Registry registry = LocateRegistry.createRegistry(1234);
            registry.rebind("MatchManagerServer", stub);

            logger.println("MAIN: RMI MatchManager Server is running");
        } catch (RemoteException e) {
            logger.println("MAIN: Error occurred while starting RMI server:" + e.getMessage());
        }
    }
}
