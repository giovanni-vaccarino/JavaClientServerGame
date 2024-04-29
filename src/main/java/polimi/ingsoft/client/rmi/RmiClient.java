package polimi.ingsoft.client.rmi;

import polimi.ingsoft.client.Client;
import polimi.ingsoft.server.Player;
import polimi.ingsoft.server.common.VirtualServerInterface;
import polimi.ingsoft.server.model.Message;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.Scanner;

//Rmi client extends UnicastRemoteObject in modo che possa essere passato al server
public class RmiClient extends UnicastRemoteObject implements VirtualView, Client {
    final VirtualServerInterface server;

    public RmiClient(String rmiServerHostName, String rmiServerName, Integer rmiServerPort) throws RemoteException, NotBoundException{
        Registry registry = LocateRegistry.getRegistry(rmiServerHostName, rmiServerPort);
        VirtualServerInterface rmiServer = (VirtualServerInterface) registry.lookup(rmiServerName);
        this.server = rmiServer;
        this.server.connect(this);
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
    public Map<Integer, String> getAvailableMatches() {
        return null;
    }

    @Override
    public Player createMatch(String nickname) {
        return null;
    }

    @Override
    public Player joinMatch(int matchId, String nickname) {
        return null;
    }

    @Override
    public void close() throws Exception {

    }
}
