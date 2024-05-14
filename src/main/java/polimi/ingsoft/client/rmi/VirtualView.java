package polimi.ingsoft.client.rmi;


import polimi.ingsoft.server.controller.MatchController;
import polimi.ingsoft.server.model.Message;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface VirtualView extends Remote {
    void showNicknameUpdate(boolean result) throws IOException;
    void showJoinMatchResult(Boolean joinResult, List<String> players) throws  IOException;
    void showUpdateMatchesList(List<Integer> matches) throws RemoteException, IOException;
    void showUpdateMatchJoin(Boolean success) throws IOException, RemoteException;
    void showUpdateMatchCreate(MatchController match) throws IOException, RemoteException;
    void showUpdateChat(Message message) throws IOException, RemoteException;

    void showUpdatePublicBoard() throws IOException, RemoteException;

    void showUpdateBoard() throws IOException, RemoteException;

    void reportError(String details) throws IOException, RemoteException;
}
