package polimi.ingsoft.client.socket;

import polimi.ingsoft.server.common.VirtualMatchServer;
import polimi.ingsoft.server.enumerations.PlayerColor;
import polimi.ingsoft.server.model.*;
import polimi.ingsoft.server.socket.protocol.MessageCodes;
import polimi.ingsoft.server.socket.protocol.NetworkMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class MatchServerProxy implements VirtualMatchServer {
    private final ObjectOutputStream out;

    public MatchServerProxy(ObjectOutputStream out) {
        this.out = out;
    }

    @Override
    public void setColor(String nickname, PlayerColor color) throws IOException {
        NetworkMessage message = new NetworkMessage(
                MessageCodes.SET_COLOR_REQUEST,
                color
        );
        out.writeObject(message);
        out.flush();
    }

    @Override
    public void setIsInitialCardFacingUp(String nickname, Boolean isInitialCardFacingUp) throws IOException {
        NetworkMessage message = new NetworkMessage(
                MessageCodes.SET_INITIAL_CARD_REQUEST,
                isInitialCardFacingUp
        );
        out.writeObject(message);
        out.flush();
    }

    @Override
    public void setQuestCard(String nickname, QuestCard questCard) throws IOException {
        NetworkMessage message = new NetworkMessage(
                MessageCodes.SET_QUEST_CARD_REQUEST,
                questCard
        );
        out.writeObject(message);
        out.flush();
    }

    @Override
    public void sendBroadcastMessage(String player, String _message) throws IOException {
        NetworkMessage message = new NetworkMessage(
                MessageCodes.MATCH_SEND_BROADCAST_MESSAGE_REQUEST,
                new NetworkMessage.BroadcastMessagePayload(player, _message)
        );
        out.writeObject(message);
        out.flush();
    }

    @Override
    public void sendPrivateMessage(String player, String recipient, String _message) throws IOException {
        NetworkMessage message = new NetworkMessage(
                MessageCodes.MATCH_SEND_PRIVATE_MESSAGE_REQUEST,
                new NetworkMessage.PrivateMessagePayload(player, recipient, _message)
        );
        out.writeObject(message);
        out.flush();
    }

    @Override
    public void drawCard(String player, String deckType, PlaceInPublicBoard.Slots slot) throws IOException {
        NetworkMessage message = new NetworkMessage(
                MessageCodes.MATCH_DRAW_REQUEST,
                new NetworkMessage.DrawCardPayload(deckType, slot)
        );
        out.writeObject(message);
        out.flush();
    }

    @Override
    public void placeCard(String player, MixedCard card, Coordinates coordinates, boolean isFacingUp) throws IOException {
        NetworkMessage message = new NetworkMessage(
                MessageCodes.MATCH_PLACE_REQUEST,
                new NetworkMessage.PlaceCardPayload(card, coordinates, isFacingUp)
        );
        out.writeObject(message);
        out.flush();
    }
}
