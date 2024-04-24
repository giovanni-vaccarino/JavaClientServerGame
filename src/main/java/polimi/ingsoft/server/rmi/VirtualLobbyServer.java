package polimi.ingsoft.server.rmi;

import polimi.ingsoft.client.rmi.VirtualView;
import polimi.ingsoft.server.Player;
import polimi.ingsoft.server.model.Coordinates;
import polimi.ingsoft.server.model.MixedCard;
import polimi.ingsoft.server.model.PlaceInPublicBoard;

import java.rmi.RemoteException;

public interface VirtualLobbyServer {
    void createLobby() throws RemoteException;

    void joinLobby() throws RemoteException;
}
