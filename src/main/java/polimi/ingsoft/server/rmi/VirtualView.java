package polimi.ingsoft.server.rmi;


import polimi.ingsoft.server.model.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

//Interfaccia che utilizziamo dal server per interagire coi client
public interface VirtualView extends Remote{
    void showUpdateChat(List<Message> messages) throws RemoteException;

    void showUpdatePublicBoard() throws RemoteException;

    void showUpdateBoard() throws RemoteException;

    void reportError(String details) throws RemoteException;

}
