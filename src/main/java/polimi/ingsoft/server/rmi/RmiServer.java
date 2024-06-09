package polimi.ingsoft.server.rmi;

import polimi.ingsoft.server.common.command.ServerCommand;
import polimi.ingsoft.server.common.*;
import polimi.ingsoft.client.common.VirtualView;
import polimi.ingsoft.server.controller.MainController;
import polimi.ingsoft.server.controller.MatchController;

import java.io.IOException;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RmiServer extends Server {
    private final BlockingQueue<ServerCommand> methodQueue = new LinkedBlockingQueue<>();
    private final Map<Integer, VirtualMatchServer> matchServers = new HashMap<>();

    public RmiServer(MainController mainController, PrintStream logger) {
        super(logger, mainController);

        new Thread(() -> {
            while (true) {
                try {
                    ServerCommand command = methodQueue.take();
                    command.execute(this);
                } catch (InterruptedException | IOException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "RmiServerCommandReader").start();
    }

    @Override
    public void connect(VirtualView client) throws IOException {
        super.connect(new RmiClientProxy(client));
    }

    @Override
    public void sendMessage(ServerCommand command) throws IOException {
        try {
            methodQueue.put(command);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    protected VirtualMatchServer createMatchServer(MatchController matchController, List<VirtualView> clients, PrintStream logger) {
        try {
            RmiMatchServer matchServer = new RmiMatchServer(matchController, logger, this);
            VirtualMatchServer stub = (VirtualMatchServer) UnicastRemoteObject.exportObject(matchServer, 0);

            logger.println("RMI: New RMI MatchController Server is running");

            return stub;
        } catch (RemoteException e) {
            logger.println("RmiServer: Error occurred while starting RMI MatchController server:" + e.getMessage());
            return null;
        }
    }

    @Override
    protected Map<Integer, VirtualMatchServer> getMatchServers() {
        return matchServers;
    }
}
