package polimi.ingsoft.server.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MatchManagerInterface extends Remote {
    void createMatch(String nickname, Integer requiredNumPlayers) throws RemoteException;

    void joinMatch(Integer matchId, String nickname) throws RemoteException;

    void reJoinMatch(Integer matchId, String nickname) throws RemoteException;
}
