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
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

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
    public List<Integer> getAvailableMatches() {
        try{
            return server.getMatches();
        }catch(RemoteException exception){
            System.out.println(exception);
        }

        return null;
    }

    @Override
    public Integer createMatch(Integer requestedNumPlayers) {
        try{
            return server.createMatch(requestedNumPlayers);
        }catch(RemoteException exception){
            System.out.println(exception);
        }

        return null;
    }

    @Override
    public Boolean joinMatch(int matchId, String nickname) {
        try{
            server.joinMatch(matchId, nickname);
            return true;
        }catch(RemoteException exception){
            System.out.println(exception);
        }
        return false;
    }

    @Override
    public Boolean isMatchJoinable(int matchId) {
        return false;
    }

    @Override
    public void close() throws Exception {

    }
}
