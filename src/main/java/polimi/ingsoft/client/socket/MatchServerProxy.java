package polimi.ingsoft.client.socket;

import polimi.ingsoft.server.common.VirtualMatchServer;
import polimi.ingsoft.server.enumerations.PlayerColor;
import polimi.ingsoft.server.model.*;
import polimi.ingsoft.server.socket.protocol.MessageCodes;
import polimi.ingsoft.server.socket.protocol.SocketMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;

public class MatchServerProxy implements VirtualMatchServer {
    private final ObjectOutputStream out;

    public MatchServerProxy(ObjectOutputStream out) {
        this.out = out;
    }

    @Override
    public void setInitialSettings(String nickname, PlayerColor color, Boolean isInitialCardFacingUp, QuestCard questCard) throws IOException {
        SocketMessage message = new SocketMessage(
                MessageCodes.SET_INITIAL_SETTINGS_REQUEST,
                new SocketMessage.InitialSettings(color, isInitialCardFacingUp, questCard)
        );
        out.writeObject(message);
        out.flush();
    }

    @Override
    public void sendMessage(String player, String message) throws IOException {

    }

    @Override
    public void sendPrivateMessage(String player, String message) throws IOException {

    }

    @Override
    public void drawCard(String player, String deckType, PlaceInPublicBoard.Slots slot) throws IOException {
        SocketMessage message = new SocketMessage(
                MessageCodes.MATCH_DRAW_REQUEST,
                new SocketMessage.DrawCardPayload(deckType, slot)
        );
        out.writeObject(message);
        out.flush();
    }

    @Override
    public void placeCard(String player, MixedCard card, Coordinates coordinates, boolean isFacingUp) throws IOException {
        SocketMessage message = new SocketMessage(
                MessageCodes.MATCH_PLACE_REQUEST,
                new SocketMessage.PlaceCardPayload(card, coordinates, isFacingUp)
        );
        out.writeObject(message);
        out.flush();
    }
}
