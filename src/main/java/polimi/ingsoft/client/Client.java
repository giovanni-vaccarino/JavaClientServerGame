package polimi.ingsoft.client;

import polimi.ingsoft.client.rmi.VirtualView;
import polimi.ingsoft.client.ui.UI;
import polimi.ingsoft.server.common.VirtualServer;

import java.rmi.RemoteException;

public interface Client extends AutoCloseable, VirtualView, VirtualServer {
    void run() throws RemoteException;
    UI getUI();
}
