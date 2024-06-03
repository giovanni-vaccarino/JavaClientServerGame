package polimi.ingsoft.server.socket;

import polimi.ingsoft.client.common.VirtualView;
import polimi.ingsoft.server.common.VirtualServer;
import polimi.ingsoft.server.common.command.ServerCommand;

import java.io.IOException;

public class ServerLocalProxy implements VirtualServer {
    private final VirtualServer server;
    private final String stub;

    public ServerLocalProxy(VirtualServer server, String stub) {
        this.server = server;
        this.stub = stub;
    }

    @Override
    public void connect(VirtualView client) throws IOException {
        server.connect(client);
    }

    @Override
    public void setNickname(String nickname, String stub) throws IOException {
        server.setNickname(nickname, this.stub);
    }

    @Override
    public void getMatches(String nickname) throws IOException {
        server.getMatches(nickname);
    }

    @Override
    public void createMatch(String playerNickname, Integer requiredNumPlayers) throws IOException {
        server.createMatch(playerNickname, requiredNumPlayers);
    }

    @Override
    public void joinMatch(String playerNickname, Integer matchId) throws IOException {
        server.joinMatch(playerNickname, matchId);
    }

    @Override
    public void reJoinMatch(Integer matchId, String nickname) throws IOException {
        server.reJoinMatch(matchId, nickname);
    }

    @Override
    public void sendMessage(ServerCommand command) throws IOException {
        server.sendMessage(command);
    }
}
