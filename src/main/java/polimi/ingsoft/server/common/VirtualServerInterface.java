package polimi.ingsoft.server.common;

import polimi.ingsoft.client.rmi.VirtualView;
import polimi.ingsoft.server.model.Coordinates;
import polimi.ingsoft.server.model.MixedCard;
import polimi.ingsoft.server.model.PlaceInPublicBoard;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface VirtualServerInterface extends Remote, VirtualServer {
    void connect(VirtualView client, String nickname) throws RemoteException;

    void getMatches(VirtualView client) throws RemoteException;

    void createMatch(Integer requiredNumPlayers) throws RemoteException;

    void joinMatch(VirtualView client, Integer matchId, String nickname) throws RemoteException;

    void reJoinMatch(Integer matchId, String nickname) throws RemoteException;

}
