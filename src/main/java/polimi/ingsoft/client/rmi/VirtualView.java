package polimi.ingsoft.client.rmi;


import polimi.ingsoft.server.controller.MatchController;
import polimi.ingsoft.server.model.Message;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

//Interfaccia che utilizziamo dal server per interagire coi client
public interface VirtualView extends Remote{
    void showUpdateMatchesList(List<Integer> matches) throws IOException;
    void showUpdateMatchJoin(Boolean success) throws IOException;
    void showUpdateMatchCreate(MatchController match) throws IOException;
    void showUpdateChat(Message message) throws IOException;

    void showUpdatePublicBoard() throws IOException;

    void showUpdateBoard() throws IOException;

    void reportError(String details) throws IOException;
}
