package polimi.ingsoft.server;

import polimi.ingsoft.server.common.MatchManagerInterface;
import polimi.ingsoft.server.controllerg.MainController;
import polimi.ingsoft.server.rmi.RmiServer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class MainServer {
    public static void main(String[] args) {
        MainController mainController = new MainController();

        startRmiServer(mainController);
        startSocketServer(mainController);
    }

    private static void startRmiServer(MainController mainController) {
        try {
            MatchManagerInterface matchManagerServer = new RmiServer(mainController);

            MatchManagerInterface stub = (MatchManagerInterface) UnicastRemoteObject.exportObject(matchManagerServer, 0);

            Registry registry = LocateRegistry.createRegistry(1234);
            registry.rebind("MatchManagerServer", stub);

            System.out.println("RMI MatchManager Server is running");
        } catch (RemoteException e) {
            System.err.println("Error occurred while starting RMI server:");
            e.printStackTrace();
        }
    }

    private static void startSocketServer(MainController mainController) {

    }
}

