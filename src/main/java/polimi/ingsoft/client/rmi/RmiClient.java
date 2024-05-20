package polimi.ingsoft.client.rmi;

import polimi.ingsoft.client.Client;
import polimi.ingsoft.client.ui.UIType;
import polimi.ingsoft.server.common.VirtualMatchServer;
import polimi.ingsoft.server.common.VirtualServer;
import polimi.ingsoft.server.common.VirtualServerInterface;

import java.io.IOException;
import java.io.PrintStream;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class RmiClient extends Client {
    private final VirtualServerInterface server;

    private VirtualMatchServer matchServer;

    public RmiClient(String rmiServerHostName,
                     String rmiServerName,
                     Integer rmiServerPort,
                     UIType ui,
                     PrintStream printStream,
                     Scanner scanner
    ) throws RemoteException, NotBoundException {
        super(ui, printStream, scanner);
        Registry registry = LocateRegistry.getRegistry(rmiServerHostName, rmiServerPort);
        this.server = (VirtualServerInterface) registry.lookup(rmiServerName);
    }

    @Override
    protected VirtualServer getServer() {
        return server;
    }

    @Override
    protected VirtualMatchServer getMatchServer() {
        return matchServer;
    }

    @Override
    public void setMatchControllerServer(VirtualMatchServer controller) {
        this.matchServer = controller;
    }

    @Override
    public void run() {
        try {
            this.server.connect(this);
        } catch (RemoteException e) {
            // TODO handle
        }
    }
}
