package polimi.ingsoft.server.common;

import polimi.ingsoft.server.common.command.ServerCommand;
import polimi.ingsoft.client.common.VirtualView;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.Remote;

public interface VirtualServer extends Remote, Serializable {
    void sendMessage(ServerCommand command) throws IOException;

    void connect(VirtualView client) throws IOException;

    void setNickname(String nickname, String stub) throws IOException;

    void getMatches(String nickname) throws IOException;

    void createMatch(String playerNickname, Integer requiredNumPlayers) throws IOException;

    void joinMatch(String playerNickname, Integer matchId) throws IOException;

    void reJoinMatch(Integer matchId, String nickname) throws IOException;
}
