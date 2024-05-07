package polimi.ingsoft.server.socket;

import polimi.ingsoft.client.rmi.VirtualView;
import polimi.ingsoft.server.Player;
import polimi.ingsoft.server.controller.MatchController;
import polimi.ingsoft.server.model.Message;
import polimi.ingsoft.server.socket.protocol.MessageCodes;
import polimi.ingsoft.server.socket.protocol.SocketMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

public class ClientProxy implements VirtualView {
    private final ObjectOutputStream out;

    public ClientProxy(ObjectOutputStream out) {
        this.out = out;
    }

    @Override
    public void showJoinMatchResult(Boolean joinResult, List<String> players) throws IOException {

    }

    @Override
    public void showUpdateMatchesList(List<Integer> matches) throws IOException {
        SocketMessage message = new SocketMessage(MessageCodes.MATCHES_LIST_UPDATE, (Serializable) matches);
        out.writeObject(message);
        out.flush();
    }

    @Override
    public void showUpdateMatchJoin(Boolean success) throws IOException {
        SocketMessage message = new SocketMessage(MessageCodes.MATCH_JOIN_UPDATE, success);
        out.writeObject(message);
        out.flush();
    }

    @Override
    public void showUpdateMatchCreate(MatchController match) throws IOException {
        SocketMessage message = new SocketMessage(MessageCodes.MATCH_CREATE_UPDATE, (Serializable) match);
        out.writeObject(message);
        out.flush();
    }

    @Override
    public void showUpdateChat(Message message) throws IOException {

    }

    @Override
    public void showUpdatePublicBoard() throws IOException {

    }

    @Override
    public void showUpdateBoard() throws IOException {

    }

    @Override
    public void reportError(String details) throws IOException {

    }
}
