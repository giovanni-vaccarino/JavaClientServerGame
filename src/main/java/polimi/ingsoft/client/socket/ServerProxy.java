package polimi.ingsoft.client.socket;

import polimi.ingsoft.client.rmi.VirtualView;
import polimi.ingsoft.server.common.VirtualMatchController;
import polimi.ingsoft.server.common.VirtualServer;
import polimi.ingsoft.server.enumerations.PlayerColors;
import polimi.ingsoft.server.model.*;
import polimi.ingsoft.server.socket.protocol.MessageCodes;
import polimi.ingsoft.server.socket.protocol.SocketMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;

public class ServerProxy implements VirtualServer, VirtualMatchController {
    private final ObjectOutputStream out;

    public ServerProxy(ObjectOutputStream out) {
        this.out = out;
    }

    @Override
    public void setNickname(VirtualView client, String nickname) throws IOException {
        SocketMessage message = new SocketMessage(MessageCodes.SET_NICKNAME_REQUEST, nickname);
        out.writeObject(message);
        out.flush();
    }

    @Override
    public void getMatches(VirtualView client) throws IOException {
        SocketMessage message = new SocketMessage(MessageCodes.MATCHES_LIST_REQUEST, null);
        out.writeObject(message);
        out.flush();
    }

    @Override
    public void createMatch(Integer requiredNumPlayers) throws IOException {
        SocketMessage message = new SocketMessage(MessageCodes.MATCH_CREATE_REQUEST, requiredNumPlayers);
        out.writeObject(message);
        out.flush();
    }

    @Override
    public void joinMatch(VirtualView client, Integer matchId, String nickname) throws IOException {
        SocketMessage message = new SocketMessage(
                MessageCodes.MATCH_JOIN_REQUEST,
                new SocketMessage.IdAndNickname(matchId, nickname)
        );
        out.writeObject(message);
        out.flush();
    }

    @Override
    public void reJoinMatch(Integer matchId, String nickname) throws IOException {

    }

    @Override
    public void setColor(String nickname, PlayerColors color) throws RemoteException {

    }

    @Override
    public void setFaceInitialCard(String nickname, Boolean isFaceUp) throws RemoteException {

    }

    @Override
    public void setQuestCard(String nickname, QuestCard questCard) throws RemoteException {

    }

    @Override
    public void sendMessage(Player player, String message) throws RemoteException {

    }

    @Override
    public void sendPrivateMessage(Player player, String message) throws RemoteException {

    }

    @Override
    public void drawCard(Player player, String deckType, PlaceInPublicBoard.Slots slot) throws RemoteException {

    }

    @Override
    public void placeCard(Player player, MixedCard card, Coordinates coordinates, boolean facingUp) throws RemoteException {

    }
}
