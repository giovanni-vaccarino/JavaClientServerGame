package polimi.ingsoft.client.common;

import polimi.ingsoft.server.common.VirtualServer;
import polimi.ingsoft.server.common.command.MatchServerCommand;
import polimi.ingsoft.server.common.command.ServerCommand;

import java.io.IOException;

public abstract class ServerProxy implements VirtualServer {
    @Override
    public abstract void sendMessage(ServerCommand command) throws IOException;

    @Override
    public void connect(VirtualView client) throws IOException {
        sendMessage((ServerCommand) server -> server.connect(client));
    }

    @Override
    public void setNickname(String nickname, String stub) throws IOException {
        sendMessage((ServerCommand) server -> server.setNickname(nickname, stub));
    }

    @Override
    public void getMatches(String nickname) throws IOException {
        sendMessage((ServerCommand) server -> server.getMatches(nickname));
    }

    @Override
    public void createMatch(String nickname, Integer requiredNumPlayers) throws IOException {
        sendMessage((ServerCommand) server -> server.createMatch(nickname, requiredNumPlayers));
    }

    @Override
    public void joinMatch(String nickname, Integer matchId) throws IOException {
        sendMessage((ServerCommand) server -> server.joinMatch(nickname, matchId));
    }

    @Override
    public void ping(String nickname) throws IOException {
        sendMessage((ServerCommand) server -> server.ping(nickname));
    }
}
