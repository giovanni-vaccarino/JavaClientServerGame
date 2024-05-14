package polimi.ingsoft.server.common;

import polimi.ingsoft.client.rmi.VirtualView;
import polimi.ingsoft.server.model.Coordinates;
import polimi.ingsoft.server.model.MixedCard;
import polimi.ingsoft.server.model.PlaceInPublicBoard;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface VirtualServer extends Remote {
    void getMatches(VirtualView client) throws IOException;

    void createMatch(Integer requiredNumPlayers) throws IOException;

    void joinMatch(VirtualView client, Integer matchId, String nickname) throws IOException;

    void reJoinMatch(Integer matchId, String nickname) throws IOException;
}
