package polimi.ingsoft.client.rmi;

import polimi.ingsoft.client.Client;
import polimi.ingsoft.server.Player;
import polimi.ingsoft.server.common.VirtualServerInterface;
import polimi.ingsoft.server.controller.MatchController;
import polimi.ingsoft.server.model.Message;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;


public class RmiClient extends UnicastRemoteObject implements VirtualView {
    final VirtualServerInterface server;

    public RmiClient(String rmiServerHostName, String rmiServerName, Integer rmiServerPort) throws RemoteException, NotBoundException{
        Registry registry = LocateRegistry.getRegistry(rmiServerHostName, rmiServerPort);
        VirtualServerInterface rmiServer = (VirtualServerInterface) registry.lookup(rmiServerName);
        this.server = rmiServer;
        this.server.connect(this);
    }

    @Override
    public void showUpdateMatchesList(List<Integer> matches) throws IOException {

    }

    @Override
    public void showUpdateMatchJoin(Boolean success) throws RemoteException {

    }

    @Override
    public void showUpdateMatchCreate(MatchController match) throws RemoteException {

    }

    @Override
    public void showUpdateChat(Message message) throws RemoteException {
        //Attenzione alle data races
        //Aggiungo l'ultimo messaggio alla lista
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
