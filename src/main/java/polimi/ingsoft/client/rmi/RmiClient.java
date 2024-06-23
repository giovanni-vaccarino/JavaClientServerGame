package polimi.ingsoft.client.rmi;

import polimi.ingsoft.client.common.Client;
import polimi.ingsoft.client.ui.UIType;
import polimi.ingsoft.server.common.VirtualMatchServer;
import polimi.ingsoft.server.common.VirtualServer;
import polimi.ingsoft.server.common.command.ClientCommand;

import java.io.IOException;
import java.io.PrintStream;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RmiClient extends Client {
    private final BlockingQueue<ClientCommand> methodQueue = new LinkedBlockingQueue<>();

    private final VirtualServer server;

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
        this.server = new RmiServerProxy((VirtualServer) registry.lookup(rmiServerName));
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
    public void setMatchServer(VirtualMatchServer server) {
        this.matchServer = new RmiMatchServerProxy(server);
    }

    @Override
    public void run() {
        try {
            this.server.connect(this);
            new Thread(() -> {
                while (true) {
                    try {
                        ClientCommand command = methodQueue.take();
                        command.execute(this);
                    } catch (InterruptedException  e) {
                        System.out.println("ERRORE INTERR");
                        Thread.currentThread().interrupt();
                    } catch (IOException e){
                        System.out.println("ERRORE IOEXC");
                        System.out.println(e.getMessage());
                    }
                }
            }, "RmiClientCommandReader").start();
        } catch (IOException e) {
            // TODO handle
        }
    }

    @Override
    public void sendMessage(ClientCommand command) throws RemoteException{
        try {
            methodQueue.put(command);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
