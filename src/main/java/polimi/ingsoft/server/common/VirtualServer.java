package polimi.ingsoft.server.common;

import polimi.ingsoft.client.common.VirtualView;

import java.io.IOException;
import java.rmi.Remote;

public interface VirtualServer extends Remote {
    void setNickname(VirtualView client, String nickname) throws IOException;

    void getMatches(VirtualView client) throws IOException;

    void createMatch(String playerNickname, Integer requiredNumPlayers) throws IOException;

    void joinMatch(String playerNickname, Integer matchId) throws IOException;

    void reJoinMatch(Integer matchId, String nickname) throws IOException;
}
