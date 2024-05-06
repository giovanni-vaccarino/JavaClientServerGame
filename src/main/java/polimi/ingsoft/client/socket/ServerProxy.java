package polimi.ingsoft.client.socket;

import polimi.ingsoft.client.rmi.VirtualView;
import polimi.ingsoft.server.common.VirtualServer;
import polimi.ingsoft.server.model.Coordinates;
import polimi.ingsoft.server.model.MixedCard;
import polimi.ingsoft.server.model.PlaceInPublicBoard;
import polimi.ingsoft.server.socket.protocol.MessageCodes;
import polimi.ingsoft.server.socket.protocol.SocketMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class ServerProxy implements VirtualServer {
    private final ObjectOutputStream out;

    public ServerProxy(ObjectOutputStream out) {
        this.out = out;
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
                MessageCodes.MATCH_CREATE_REQUEST,
                new SocketMessage.IdAndNickname(matchId, nickname)
        );
        out.writeObject(message);
        out.flush();
    }

    @Override
    public void reJoinMatch(Integer matchId, String nickname) throws IOException {

    }

    @Override
    public void addMessage(int matchId, String message) throws IOException {

    }

    @Override
    public void drawCard(int matchId, String playerName, String deckType, PlaceInPublicBoard.Slots slot) throws IOException {

    }

    @Override
    public void placeCard(int matchId, String playerName, MixedCard card, Coordinates coordinates, boolean facingUp) throws IOException {

    }
}
