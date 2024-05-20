package polimi.ingsoft.server.common;

import polimi.ingsoft.client.common.VirtualView;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualServerInterface extends Remote, VirtualServer {
    void connect(VirtualView client) throws RemoteException;
}
